package com.faizal.bottomnavigation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.chats.*
import com.faizal.bottomnavigation.chats.kotlin.*
import com.faizal.bottomnavigation.databinding.ActivityDiscussionBinding
import com.faizal.bottomnavigation.databinding.ActivityMydiscussionBinding
import com.faizal.bottomnavigation.fragments.BaseFragment
import com.faizal.bottomnavigation.model2.Comments
import com.faizal.bottomnavigation.util.GenericValues
import com.faizal.bottomnavigation.util.getUserName
import com.faizal.bottomnavigation.utils.Constants
import com.faizal.bottomnavigation.viewmodel.ActivityDiscussionViewModel
import com.faizal.bottomnavigation.viewmodel.ActivityMyDiscussionViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

/**
 * Class demonstrating how to setup a [RecyclerView] with an adapter while taking sign-in
 * states into consideration. Also demonstrates adding data to a ref and then reading it back using
 * the [FirestoreRecyclerAdapter] to build a simple chat app.
 *
 *
 * For a general intro to the RecyclerView, see [Creating
 * Lists](https://developer.android.com/training/material/lists-cards.html).
 */
class FirestoreMyDisccussFragmment : BaseFragment(), AuthStateListener {
    companion object {
        private const val TAG = "FirestoreChatFragmment"

        init {
            FirebaseFirestore.setLoggingEnabled(true)
        }
    }


    @Transient
    lateinit internal var areaViewModel: ActivityMyDiscussionViewModel

    lateinit var binding : ActivityMydiscussionBinding

    private var mRecyclerView: RecyclerView? = null
    private var mSendButton: ImageButton? = null
    private var mMessageEdit: EditText? = null
    private var mEmptyListMessage: TextView? = null
    private lateinit var sChatCollection :CollectionReference
    /** Get the last 50 chat messages ordered by timestamp .  */
    private lateinit var sChatQuery :Query

    override fun onResume() {
        super.onResume()
        areaViewModel.registerListeners()
    }

    override fun onStop() {
        super.onStop()
        areaViewModel.unRegisterListeners()
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        val postAdObj  = arguments!!.getString(Constants.POSTAD_OBJECT)
        val c = GenericValues()
        val groups = c.getDisccussion(postAdObj!!, this.context!!)

        binding = DataBindingUtil.inflate(inflater, R.layout.activity_mydiscussion, container, false)
        areaViewModel = ActivityMyDiscussionViewModel(this.context!!, this,postAdObj)
        binding.mainDataModel = areaViewModel
        binding.countriesInfoModel = areaViewModel.postDiscussion

        sChatCollection = FirebaseFirestore.getInstance().collection("discussion/"+groups.postedDate!!+"/comments")
        /** Get the last 50 chat messages ordered by timestamp .  */
        sChatQuery = sChatCollection.orderBy("timestamp", Query.Direction.DESCENDING).limit(200)


        mRecyclerView = binding.messagesList
        mSendButton = binding.sendButton
        mMessageEdit = binding.messageEdit
        mEmptyListMessage = binding.emptyTextView

        val manager = LinearLayoutManager(this.activity)
        manager.reverseLayout = true
        manager.stackFromEnd = true
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.setLayoutManager(manager)
        mRecyclerView!!.addOnLayoutChangeListener(View.OnLayoutChangeListener { view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (bottom < oldBottom) {
                mRecyclerView!!.postDelayed(Runnable { mRecyclerView!!.smoothScrollToPosition(0) }, 100)
            }
        })
        ImeHelper.setImeOnDoneListener(mMessageEdit!!, object : ImeHelper.DonePressedListener {
            override fun onDonePressed() {
                setMessageValue()
            }
        })


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (isSignedIn) {
            attachRecyclerViewAdapter()
        }
        FirebaseAuth.getInstance().addAuthStateListener(this)
    }

    override fun onAuthStateChanged(auth: FirebaseAuth) {
        mSendButton!!.isEnabled = isSignedIn
        mSendButton!!.setOnClickListener {
            setMessageValue()
        }
        mMessageEdit!!.isEnabled = isSignedIn
        if (isSignedIn) {
            attachRecyclerViewAdapter()
        } else {
            Toast.makeText(this.activity, "signing_in", Toast.LENGTH_SHORT).show()
            auth.signInAnonymously().addOnCompleteListener(SignInResultNotifier(this.activity!!))
        }
    }

    private fun setMessageValue() {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val name = getUserName(activity!!.applicationContext!!, FirebaseAuth.getInstance().currentUser?.uid!!).name!!
        onAddMessage(Chat(name, mMessageEdit!!.text.toString(), uid))
        mMessageEdit!!.setText("")
    }

    private val isSignedIn: Boolean
        private get() = FirebaseAuth.getInstance().currentUser != null

    private fun attachRecyclerViewAdapter() {
        val adapter = newAdapter()
        // Scroll to bottom on new messages
        adapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                mRecyclerView!!.smoothScrollToPosition(0)
            }
        })
        mRecyclerView!!.adapter = adapter
    }

    private fun newAdapter(): RecyclerView.Adapter<*> {
        val options = FirestoreRecyclerOptions.Builder<Chat>()
                .setQuery(sChatQuery, Chat::class.java)
                .setLifecycleOwner(this)
                .build()
        return object : FirestoreRecyclerAdapter<Chat, DiscussionHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscussionHolder {
                return DiscussionHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.comments_message, parent, false))
            }

            override fun onBindViewHolder(holder: DiscussionHolder, position: Int, model: Chat) {
                holder.bind(model)
            }

            override fun onDataChanged() { // If there are no chat messages, show a view that invites the user to add a message.
                mEmptyListMessage!!.visibility = if (itemCount == 0) View.VISIBLE else View.GONE
            }
        }
    }

    private fun onAddMessage(chat: Chat) {
        sChatCollection.add(chat).addOnFailureListener(this.activity!!) { e -> Log.e(TAG, "Failed to write message", e) }
    }
}
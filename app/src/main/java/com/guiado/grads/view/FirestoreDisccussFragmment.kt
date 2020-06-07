package com.guiado.grads.view

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.guiado.grads.R
import com.guiado.grads.chats.*
import com.guiado.grads.chats.kotlin.*
import com.guiado.grads.databinding.ActivityDiscussionBinding
import com.guiado.grads.fragments.BaseFragment
import com.guiado.grads.util.GenericValues
import com.guiado.grads.util.getUserName
import com.guiado.grads.utils.Constants
import com.guiado.grads.viewmodel.ActivityDiscussionViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.guiado.grads.model2.Chat

/**
 * Class demonstrating how to setup a [RecyclerView] with an adapter while taking sign-in
 * states into consideration. Also demonstrates adding data to a ref and then reading it back using
 * the [FirestoreRecyclerAdapter] to build a simple chat app.
 *
 *
 * For a general intro to the RecyclerView, see [Creating
 * Lists](https://developer.android.com/training/material/lists-cards.html).
 */
class FirestoreDisccussFragmment : Activity(), AuthStateListener,LifecycleOwner {
    companion object {
        private const val TAG = "FirestoreChatFragmment"

        init {
            FirebaseFirestore.setLoggingEnabled(true)
        }
    }


    @Transient
    lateinit internal var areaViewModel: ActivityDiscussionViewModel

    lateinit var binding : ActivityDiscussionBinding

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val postAdObj  = intent.extras!!.getString(Constants.POSTAD_OBJECT)
        val c = GenericValues()
        val groups = c.getDisccussion(postAdObj!!, this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_discussion)
        areaViewModel = ActivityDiscussionViewModel(this, this,postAdObj)
        binding.mainDataModel = areaViewModel
        binding.countriesInfoModel = areaViewModel.postDiscussion

        sChatCollection = FirebaseFirestore.getInstance().collection("discussion/"+groups.postedDate!!+"/comments")
        /** Get the last 50 chat messages ordered by timestamp .  */
        sChatQuery = sChatCollection.orderBy("timestamp", Query.Direction.DESCENDING).limit(200)


        mRecyclerView = binding.messagesList
        mSendButton = binding.sendButton
        mMessageEdit = binding.messageEdit
        mEmptyListMessage = binding.emptyTextView

        val manager = LinearLayoutManager(this)
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
        lifecycleRegistry = LifecycleRegistry(this)
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED)

    }

    override fun onStart() {
        super.onStart()
        lifecycleRegistry.setCurrentState(Lifecycle.State.STARTED)

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
//            Toast.makeText(this.activity, "signing_in", Toast.LENGTH_SHORT).show()
           // auth.signInAnonymously().addOnCompleteListener(SignInResultNotifier(this.activity!!))
        }
    }

    private fun setMessageValue() {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val name = getUserName(applicationContext!!, FirebaseAuth.getInstance().currentUser?.uid!!).name!!
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

    private lateinit var lifecycleRegistry: LifecycleRegistry


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
        sChatCollection.add(chat).addOnFailureListener(this) { e -> Log.e(TAG, "Failed to write message", e) }
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}
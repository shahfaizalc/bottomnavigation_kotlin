package com.faizal.bottomnavigation.chats.kotlin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.chats.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
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
class FirestoreChatFragmment : Fragment(), AuthStateListener {
    companion object {
        private const val TAG = "FirestoreChatFragmment"
        private val sChatCollection = FirebaseFirestore.getInstance().collection("chats")
        /** Get the last 50 chat messages ordered by timestamp .  */
        private val sChatQuery = sChatCollection.orderBy("timestamp", Query.Direction.DESCENDING).limit(50)

        init {
            FirebaseFirestore.setLoggingEnabled(true)
        }
    }

    private var mRecyclerView: RecyclerView? = null
    private var mSendButton: Button? = null
    private var mMessageEdit: EditText? = null
    private var mEmptyListMessage: TextView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_chat, container, false)
        mRecyclerView = view.findViewById(R.id.messagesList)
        mSendButton = view.findViewById(R.id.sendButton)
        mMessageEdit = view.findViewById(R.id.messageEdit)
        mEmptyListMessage = view.findViewById(R.id.emptyTextView)
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
        ImeHelper.setImeOnDoneListener(mMessageEdit){
            val uid = FirebaseAuth.getInstance().currentUser!!.uid
            val name = "User " + uid.substring(0, 6)
            onAddMessage(Chat(name, mMessageEdit!!.getText().toString(), uid))
            mMessageEdit!!.setText("")
        }
        return view
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
            val uid = FirebaseAuth.getInstance().currentUser!!.uid
            val name = "User " + uid.substring(0, 6)
            onAddMessage(Chat(name, mMessageEdit!!.text.toString(), uid))
            mMessageEdit!!.setText("")
        }
        mMessageEdit!!.isEnabled = isSignedIn
        if (isSignedIn) {
            attachRecyclerViewAdapter()
        } else {
            Toast.makeText(this.activity, "signing_in", Toast.LENGTH_SHORT).show()
            auth.signInAnonymously().addOnCompleteListener(SignInResultNotifier(this.activity!!))
        }
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
        return object : FirestoreRecyclerAdapter<Chat, ChatHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
                return ChatHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.message, parent, false))
            }

            override fun onBindViewHolder(holder: ChatHolder, position: Int, model: Chat) {
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
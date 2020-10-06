package com.guiado.linkify.fragments

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.guiado.linkify.R
import com.guiado.linkify.activities.Main2Activity
import kotlinx.android.synthetic.main.tab_item_bottom.view.*


/**
 */

open class BaseFragment : Fragment() {


    lateinit  var mFragmentNavigation: FragmentNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigation) {
            mFragmentNavigation = context
        }
    }

    interface FragmentNavigation {
        fun popFragment(depth: Int)
        fun pushFragment(fragment: Fragment)
        fun replaceFragment(fragment: Fragment)
        fun switchTab(position : Int)
        fun viewBottom(viewState : Int)
        fun viewToolbar(b: Boolean)
    }

    companion object {

        val ARGS_INSTANCE = "com.guiado.linkify"
    }

    fun newInstance(instance: Int, fragment: BaseFragment, bundle: Bundle): BaseFragment {
        val args = bundle
        args.putInt(ARGS_INSTANCE, instance)
        fragment.arguments = args
        return fragment
    }


    fun addNotification(baseFragment: BaseFragment) {
        val builder = NotificationCompat.Builder(baseFragment.context)
                .setSmallIcon(R.drawable.icon_filter) //set icon for notification
                .setContentTitle("Notifications Example") //set title of notification
                .setContentText("This is a notification message") //this is notification message
                .setAutoCancel(true) // makes auto cancel of notification
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) //set priority of notification
        val notificationIntent = Intent(baseFragment.activity, Main2Activity::class.java)
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        //notification message will get at NotificationView
        notificationIntent.putExtra("message", "This is a notification message")
        val pendingIntent = PendingIntent.getActivity(baseFragment.context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)

        // Add as notification
        val manager = baseFragment.activity!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, builder.build())
    }


}

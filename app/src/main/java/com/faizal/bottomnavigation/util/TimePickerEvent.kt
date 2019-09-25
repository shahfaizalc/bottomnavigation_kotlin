package com.itravis.ticketexchange.utils

import android.app.TimePickerDialog
import android.content.Context
import com.itravis.ticketexchange.listeners.TimeListener
import java.text.SimpleDateFormat
import java.util.*

class TimePickerEvent{


    fun onTimePickerClick(context: Context, timeListener: TimeListener)  {

        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            timeListener.onTimeSet(SimpleDateFormat("HH:mm").format(cal.time))
        }
        TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
    }
}
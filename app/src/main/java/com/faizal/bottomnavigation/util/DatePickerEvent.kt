package com.itravis.ticketexchange.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import com.itravis.ticketexchange.listeners.DateListener
import java.util.*

class DatePickerEvent{


    fun onDatePickerClick(context: Context, timeListener: DateListener) {

        val cal = Calendar.getInstance();
        val mYear  =cal.get(Calendar.YEAR);
        val mMonth= cal.get(Calendar.MONTH);
        val mDay = cal.get(Calendar.DAY_OF_MONTH);

        val datePickerDialog = DatePickerDialog(context,
                DatePickerDialog.OnDateSetListener() { datePicker: DatePicker, year: Int, month: Int, day: Int ->
                    timeListener.onDateSet("" + day + "/" + (month + 1) + "/" + year);
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
    }
}
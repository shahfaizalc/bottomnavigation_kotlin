package com.faizal.bottomnavigation.utils

import android.R
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

/**
 */

object Utils {


    fun showToast(context: Context, message: String) {

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    fun getDeviceID(context: Context): String {
        return Settings.Secure.getString(context.contentResolver,
                Settings.Secure.ANDROID_ID)
    }


    fun getVersionName(context: Context): String {

        var pInfo: PackageInfo? = null
        try {
            pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return pInfo!!.versionName

    }


    fun setButtonBackgroundColor(context: Context, button: Button, color: Int) {

        if (Build.VERSION.SDK_INT >= 23) {
            button.setBackgroundColor(context.resources.getColor(color, null))
        } else {
            button.setBackgroundColor(context.resources.getColor(color))
        }
    }


    fun setButtonBackgroundColor(context: Context, textView: TextView, color: Int) {

        if (Build.VERSION.SDK_INT >= 23) {
            textView.setBackgroundColor(context.resources.getColor(color, null))
        } else {
            textView.setBackgroundColor(context.resources.getColor(color))
        }
    }


    fun setDrawableSelector(context: Context, normal: Int, selected: Int): Drawable {


        val state_normal = ContextCompat.getDrawable(context, normal)

        val state_pressed = ContextCompat.getDrawable(context, selected)


        val state_normal_bitmap = (state_normal as BitmapDrawable).bitmap

        // Setting alpha directly just didn't work, so we draw a new bitmap!
        val disabledBitmap = Bitmap.createBitmap(
                state_normal.intrinsicWidth,
                state_normal.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(disabledBitmap)

        val paint = Paint()
        paint.alpha = 126
        canvas.drawBitmap(state_normal_bitmap, 0f, 0f, paint)

        val state_normal_drawable = BitmapDrawable(context.resources, disabledBitmap)


        val drawable = StateListDrawable()

        drawable.addState(intArrayOf(android.R.attr.state_selected),
                state_pressed)
        drawable.addState(intArrayOf(android.R.attr.state_enabled),
                state_normal_drawable)

        return drawable
    }


    fun selectorRadioImage(context: Context, normal: Drawable, pressed: Drawable): StateListDrawable {
        val states = StateListDrawable()
        states.addState(intArrayOf(android.R.attr.state_checked), pressed)
        states.addState(intArrayOf(), normal)
        //                imageView.setImageDrawable(states);
        return states
    }

    fun selectorRadioButton(context: Context, normal: Int, pressed: Int): StateListDrawable {
        val states = StateListDrawable()
        states.addState(intArrayOf(android.R.attr.state_checked), ColorDrawable(pressed))
        states.addState(intArrayOf(), ColorDrawable(normal))
        return states
    }

    fun selectorRadioText(context: Context, normal: Int, pressed: Int): ColorStateList {
        return ColorStateList(arrayOf(intArrayOf(R.attr.state_checked), intArrayOf()), intArrayOf(pressed, normal))
    }


    fun selectorRadioDrawable(normal: Drawable, pressed: Drawable): StateListDrawable {
        val states = StateListDrawable()
        states.addState(intArrayOf(android.R.attr.state_checked), pressed)
        states.addState(intArrayOf(), normal)
        return states
    }

    fun selectorBackgroundColor(context: Context, normal: Int, pressed: Int): StateListDrawable {
        val states = StateListDrawable()
        states.addState(intArrayOf(android.R.attr.state_pressed), ColorDrawable(pressed))
        states.addState(intArrayOf(), ColorDrawable(normal))
        return states
    }

    fun selectorBackgroundDrawable(normal: Drawable, pressed: Drawable): StateListDrawable {
        val states = StateListDrawable()
        states.addState(intArrayOf(android.R.attr.state_pressed), pressed)
        states.addState(intArrayOf(), normal)
        return states
    }

    fun selectorText(context: Context, normal: Int, pressed: Int): ColorStateList {
        return ColorStateList(arrayOf(intArrayOf(R.attr.state_pressed), intArrayOf()), intArrayOf(pressed, normal))
    }


}

package com.faizal.bottomnavigation.viewmodel


import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import com.faizal.bottomnavigation.BR
import com.faizal.bottomnavigation.model.Category
import com.faizal.bottomnavigation.model.PostAdModel
import com.faizal.bottomnavigation.util.GenericValues
import com.faizal.bottomnavigation.util.MultipleClickHandler
import com.faizal.bottomnavigation.utils.Constants.POSTAD_OBJECT
import com.faizal.bottomnavigation.view.FragmentPostAd
import com.faizal.bottomnavigation.view.FragmentPostAdPricing
import com.itravis.ticketexchange.listeners.DateListener
import com.itravis.ticketexchange.listeners.TimeListener
import com.itravis.ticketexchange.utils.DatePickerEvent
import com.itravis.ticketexchange.utils.TimePickerEvent
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.ListView
import android.widget.TextView
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.fragment.app.FragmentActivity
import com.faizal.bottomnavigation.R
import com.faizal.bottomnavigation.listeners.MultipleClickListener
import com.faizal.bottomnavigation.view.FragmentAddress


class PostAdViewModel(internal var activity: FragmentActivity, internal val fragmentProfileInfo: FragmentPostAd, internal var postAdModel: PostAdModel)
    : BaseObservable() , MultipleClickListener {

    var singleAttribute: Category? = null


    companion object {

        private val TAG = "ProfileGalleryViewModel"
    }

    init {
        readSingleAttribute();
    }

    @get:Bindable
    var showDate: String? = null
        set(showDate) {
            field = showDate
            notifyPropertyChanged(BR.showDate)
        }

    @get:Bindable
    var showTime: String? = null
        set(showTime) {
            field = showTime
            notifyPropertyChanged(BR.showTime)
        }

    @get:Bindable
    var title: String? = null
        set(title) {
            field = title
            notifyPropertyChanged(BR.title)
        }

    @get:Bindable
    var description: String? = null
        set(description) {
            field = description
            notifyPropertyChanged(BR.description)
        }

    @Override
    fun onNextButtonClick() = OnClickListener() {
        if(!(title.isNullOrEmpty()|| description.isNullOrEmpty()||showDate.isNullOrEmpty()|| showTime.isNullOrEmpty())) {

            if (!handleMultipleClicks()) {
                postAdModel.title = title
                postAdModel.description = description
                postAdModel.showDate = showDate
                postAdModel.showTime = showTime
                val fragment = FragmentAddress()
                val bundle = Bundle()
                bundle.putParcelable(POSTAD_OBJECT, postAdModel)
                fragment.setArguments(bundle)
                fragmentProfileInfo.mFragmentNavigation.pushFragment(fragmentProfileInfo.newInstance(1,fragment,bundle));
            }
        } else{
            Toast.makeText(fragmentProfileInfo.context,fragmentProfileInfo.context!!.resources.getText(R.string.mandatoryField),Toast.LENGTH_SHORT).show()
        }
    }

    @Override
    fun timePickerClick() = OnClickListener() {
        if (!handleMultipleClicks()) {
            TimePickerEvent().onTimePickerClick(fragmentProfileInfo.context!!, object : TimeListener {
                override fun onTimeSet(result: String) {
                    showTime = result;
                }
            })
        }
    }

    @Override
    fun datePickerClick() = OnClickListener() {
        if (!handleMultipleClicks()) {
            DatePickerEvent().onDatePickerClick(fragmentProfileInfo.context!!, object : DateListener {
                override fun onDateSet(result: String) {
                    showDate = result
                }
            })
        }
    }

    @Override
    fun onPopupClick() = OnClickListener() {
        if (!handleMultipleClicks()) {
            var popupWindow  = popupWindowDogs()
            popupWindow.showAsDropDown(it)

        }
    }


    fun popupWindowDogs(): PopupWindow {

        // initialize a pop up window type
        val popupWindow = PopupWindow(activity)

        // the drop down list is a list view
        val listViewDogs = ListView(activity)
        val dogsList = ArrayList<String>()
        dogsList.add("Akita Inu::1")
        dogsList.add("Alaskan Klee Kai::2")
        dogsList.add("Papillon::3")
        dogsList.add("Tibetan Spaniel::4")
        val stockArr = arrayOf(dogsList.size)


        // set our adapter and pass our pop up window contents
        listViewDogs.setAdapter(dogsAdapter(dogsList.toArray() as Array<String>))

        // set the item click listener
       // listViewDogs.setOnItemClickListener(DogsDropdownOnItemClickListener())

        // some other visual settings
        popupWindow.setFocusable(true)
        popupWindow.setWidth(250)
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT)

        // set the list view as pop up window content
        popupWindow.setContentView(listViewDogs)

        return popupWindow
    }
    /*
     * adapter where the list values will be set
     */
    private fun dogsAdapter(dogsArray: Array<String>): ArrayAdapter<String> {


        return object : ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, dogsArray) {

            override fun getView(position: Int, convertView: View, parent: ViewGroup): View {

                // setting the ID and text for every items in the list
                val item = getItem(position)
                val itemArr = item!!.split("::".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val text = itemArr[0]
                val id = itemArr[1]

                // visual settings for the list item
                val listItem = TextView(activity)

                listItem.text = text
                listItem.tag = id
                listItem.textSize = 22f
                listItem.setPadding(10, 10, 10, 10)
                listItem.setTextColor(Color.WHITE)

                return listItem
            }
        }
    }


    private fun readSingleAttribute() {
        val c = GenericValues()
        singleAttribute = c.readCategory(fragmentProfileInfo.context!!)
    }

    override fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }
}

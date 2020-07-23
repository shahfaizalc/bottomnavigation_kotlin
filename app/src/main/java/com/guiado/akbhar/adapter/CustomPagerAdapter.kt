package com.guiado.akbhar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide.with
import com.guiado.akbhar.R
import com.guiado.akbhar.viewmodel.MoroccoViewModel
import com.squareup.picasso.Picasso

class CustomPagerAdapter(private val mContext: Context, internal val countriesViewModel: MoroccoViewModel) : PagerAdapter() {

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {

        val inflater = LayoutInflater.from(mContext)
        val layout = inflater.inflate(R.layout.pagerlayout, collection, false) as ViewGroup
        val textxt3 = layout.findViewById<TextView>(R.id.textView3)
        val textter = layout.findViewById<ImageView>(R.id.imageView);
        Picasso.get().load(countriesViewModel.talentProfilesList.get(position).imgurl).into(textter);

        textxt3.text = countriesViewModel.talentProfilesList.get(position).title
        collection.addView(layout)
        return layout
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount() : Int {

        val val1  = countriesViewModel.talentProfilesList.size

        return  if (val1 > 5) 6 else val1
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return position.toString() + "abc"
    }

}
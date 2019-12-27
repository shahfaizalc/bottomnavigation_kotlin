package com.guiado.grads.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.guiado.grads.R
import com.guiado.grads.model.CategoryItem
import com.guiado.grads.util.EnumSingleAttribute
import com.itravis.ticketexchange.model.*

internal class SpinnerAdapter(context: Context, @param:LayoutRes private val mResource: Int,
                              private val items: List<Any>, private val enumVal: EnumSingleAttribute) : ArrayAdapter<String>(context, mResource, 0, items as  List<String>) {

    private val mInflater: LayoutInflater

    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    private fun createItemView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = mInflater.inflate(mResource, parent, false)

        val offTypeTv = view.findViewById<TextView>(R.id.text_title)
        when (enumVal) {
            EnumSingleAttribute.ETHINICITY -> {
                val ethnicityItem = items[position] as EthnicityItem
                offTypeTv.text = ethnicityItem.name
            }
            EnumSingleAttribute.GENDER -> {
                val genderItem = items[position] as GenderItem
                offTypeTv.text = genderItem.name
            }

            EnumSingleAttribute.FIGURE -> {
                val figureItem = items[position] as FigureItem
                offTypeTv.text = figureItem.name
            }

            EnumSingleAttribute.RELIGION -> {
                val religionItem = items[position] as ReligionItem
                offTypeTv.text = religionItem.name
            }

            EnumSingleAttribute.MARTIALSTATUS -> {
                val maritalStatusItem = items[position] as MaritalStatusItem
                offTypeTv.text = maritalStatusItem.name
            }

            EnumSingleAttribute.CATEGORY -> {
                val maritalStatusItem = items[position] as CategoryItem
                offTypeTv.text = maritalStatusItem.name
            }
        }

        return view
    }
}

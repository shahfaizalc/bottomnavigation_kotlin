package com.nioneer.nioneer.rss.utils

import com.nioneer.nioneer.model.BlogItemModel

import java.util.Comparator

/**
 * The SortUserInfoByTitle class implements an application that
 * simply sorts given list.
 */
class SortUserInfoByTitle : Comparator<BlogItemModel> {
    //To be  used to sort the list by ttile name
    override fun compare(list1: BlogItemModel, list2: BlogItemModel): Int {
        return list1.title.compareTo(list2.title)
    }
}

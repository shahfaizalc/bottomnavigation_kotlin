package com.nioneer.nioneer.rss.utils

import android.util.Log
import com.nioneer.nioneer.model.BlogItemModel
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import java.io.IOException
import java.io.StringReader
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException


/**
 * RSS Feed parser
 */
class RSSParser {

    /**
     * TAG: Class name
     */
    private val TAG = "RSSParser"

    /**
     * RSS XML document CHANNEL tag
     */
    private val TAG_CHANNEL = "channel"

    /**
     * RSS XML document TITLE tag
     */
    private val TAG_TITLE = "title"

    /**
     * RSS XML document LINK tag
     */
    private val TAG_LINK = "link"

    /**
     * RSS XML document DESCRIPTION tag
     */
    private val TAG_DESRIPTION = "description"

    /**
     * RSS XML document ITEM tag
     */
    private val TAG_ITEM = "item"

    /**
     * RSS XML document PUB DATE tag
     */
    private val TAG_PUB_DATE = "pubDate"

    /**
     * RSS XML document GUID tag
     */
    private val TAG_GUID = "guid"

    /**
     * RSS XML document CREATOR tag
     */
    private val TAG_CREATOR = "dc:creator"

    fun getRSSFeedItems(feedXML: String): MutableList<BlogItemModel> {
        var itemsList = ArrayList<BlogItemModel>()

        if (feedXML.isNotEmpty()) {
            try {
                val doc = getDomElement(feedXML)
                val nodeList = doc!!.getElementsByTagName(TAG_CHANNEL)
                val element = nodeList.item(0) as Element
                val items = element.getElementsByTagName(TAG_ITEM)
                itemsList = getItemList(items)
            } catch (e: Exception) {
                // Check log for errors
                Log.e(TAG, "getRSSFeedItems: Exception: ${e.message}")
                e.printStackTrace()
            }
        }

        // return item list
        return itemsList
    }

    /**
     * Fetchh Blog items from the node list
     * @param  items NodeList
     */
    private fun getItemList(items: NodeList): ArrayList<BlogItemModel> {
        val itemsList = ArrayList<BlogItemModel>()
        for (itemCount in 0 until items.length) {
            val element = items.item(itemCount) as Element
            val rssItem = getBlogItem(element)
            // adding item to list
            itemsList.add(rssItem)
        }
        return itemsList;
    }

    /**
     * Get BlogItem model form the Element
     * @param element Element
     */
    private fun getBlogItem(element: Element): BlogItemModel {
        val title = getValue(element, TAG_TITLE)
        val link = getValue(element, TAG_LINK)
        val description = getValue(element, TAG_DESRIPTION)
        val pubdate = getValue(element, TAG_PUB_DATE)
        val guid = getValue(element, TAG_GUID)
        val creator = getValue(element, TAG_CREATOR)
        return BlogItemModel(title, link, description, pubdate, guid, creator)
    }

    /**
     * Get Dom element from the response Feed.
     */
    private fun getDomElement(xml: String): Document? {
        val doc: Document?
        try {
            val dbf = DocumentBuilderFactory.newInstance()
            val input = InputSource()
            input.characterStream = StringReader(xml)
            doc = dbf.newDocumentBuilder().parse(input)
            Log.d(TAG, "getDomElement: doc : ${doc}")

        } catch (e: ParserConfigurationException) {
            Log.e(TAG, "getDomElement: Exception: ${e.message}")
            return null
        } catch (e: SAXException) {
            Log.e(TAG, "getDomElement: Exception: ${e.message}")
            return null
        } catch (e: IOException) {
            Log.e(TAG, "getDomElement: Exception: ${e.message}")
            return null
        }

        return doc
    }

    /**
     * get value from the element
     * @param elem : Node
     */
    private fun getElementValue(elem: Node?): String {

        var child: Node?
        if (elem != null) {
            if (elem.hasChildNodes()) {
                child = elem.firstChild
                with(child) {
                    while (child != null) {
                        if (nodeType == Node.TEXT_NODE || nodeType == Node.CDATA_SECTION_NODE) {
                            return nodeValue
                        }
                        child = nextSibling
                    }
                }
            }
        }
        // return empty string  if no child nodes found
        return ""
    }

    fun getValue(item: Element, str: String) = getElementValue(item.getElementsByTagName(str).item(0))
}

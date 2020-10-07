package com.nioneer.nioneer.rss.utils

import android.content.Context
import android.content.Intent
import com.nioneer.nioneer.R

/**
 * Çlass to share the sharable items from the app via  chooser
 */
class ShareLink {


    /**
     * Method to create the intent to share the fairphone blog article
     * @param baseText : Heading
     * @param title : title of the article
     * @param link : link to the article
     *
     */
    fun shareArticleLink(baseText: String, title: String, link: String): Intent {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val msg = java.lang.String.format(baseText + "\n%s\n%s", title, link)
        sharingIntent.putExtra(Intent.EXTRA_TEXT, msg)
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return sharingIntent;
    }

    /**
     * Open chooser to pick sharig platform and so.
     * @param sharingIntent intent with the respective content to share
     * @param context : Context
     */
    fun openChooser(sharingIntent: Intent, context: Context) {
        val chooserIntent = Intent.createChooser(sharingIntent, context.resources.getString(R.string.shareVia))
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooserIntent)
    }

}
package com.reelme.app.utils

import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.widget.MultiAutoCompleteTextView


// As this java class implements MultiAutoCompleteTextView.Tokenizer,
// we should write 3 methods i.e. findTokenStart,findTokenEnd and terminateToken
class SpaceTokenizer : MultiAutoCompleteTextView.Tokenizer {
    private val i = 0

    // Returns the start of the token that ends at offset cursor within text.
    override fun findTokenStart(inputText: CharSequence, cursor: Int): Int {
        var idx = cursor
        while (idx > 0 && inputText[idx - 1] != ' ') {
            idx--
        }
        while (idx < cursor && inputText[idx] == ' ') {
            idx++
        }
        return idx
    }

    // Returns the end of the token (minus trailing punctuation) that
    // begins at offset cursor within text.
    override fun findTokenEnd(inputText: CharSequence, cursor: Int): Int {
        var idx = cursor
        val length = inputText.length
        while (idx < length) {
            if (inputText[i] == ' ') {
                return idx
            } else {
                idx++
            }
        }
        return length
    }

    // Returns text, modified, if necessary, to ensure that it ends with a token terminator
    // (for example a space or comma).
    override fun terminateToken(inputText: CharSequence): CharSequence {
        var idx = inputText.length
        while (idx > 0 && inputText[idx - 1] == ' ') {
            idx--
        }
        return if (idx > 0 && inputText[idx - 1] == ' ') {
            inputText
        } else {
            if (inputText is Spanned) {
                val sp = SpannableString("$inputText ")
                TextUtils.copySpansFrom(inputText, 0, inputText.length,
                        Any::class.java, sp, 0)
                sp
            } else {
                "$inputText "
            }
        }
    }
}
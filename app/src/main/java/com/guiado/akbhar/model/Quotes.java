
package com.guiado.akbhar.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Quotes {

    @SerializedName("Quotes")
    private List<Quote> mQuotes;

    public List<Quote> getQuotes() {
        return mQuotes;
    }

    public void setQuotes(List<Quote> quotes) {
        mQuotes = quotes;
    }

}

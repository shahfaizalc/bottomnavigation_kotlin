
package com.guiado.grads.model_sales.goal;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Goals {

    @SerializedName("done")
    private Boolean mDone;
    @SerializedName("records")
    private List<Record> mRecords;
    @SerializedName("totalSize")
    private Long mTotalSize;

    public Boolean getDone() {
        return mDone;
    }

    public void setDone(Boolean done) {
        mDone = done;
    }

    public List<Record> getRecords() {
        return mRecords;
    }

    public void setRecords(List<Record> records) {
        mRecords = records;
    }

    public Long getTotalSize() {
        return mTotalSize;
    }

    public void setTotalSize(Long totalSize) {
        mTotalSize = totalSize;
    }

}

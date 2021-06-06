package com.reelme.app.trim;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.deep.videotrimmer.DeepVideoTrimmer;
import com.deep.videotrimmer.interfaces.OnTrimVideoListener;
import com.deep.videotrimmer.utils.FileUtils;
import com.deep.videotrimmer.view.RangeSeekBarView;
import com.reelme.app.R;

import static com.reelme.app.trim.Constants.EXTRA_VIDEO_PATH;

public class VideoTrimmerActivity extends BaseActivity implements OnTrimVideoListener {
    private DeepVideoTrimmer mVideoTrimmer;
    TextView textSize, tvCroppingMessage;
    RangeSeekBarView timeLineBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView( R.layout.activity_video_trimmer);

//        String path = "";
//
//        Uri uii = Uri.parse("content://com.android.providers.media.documents/document/video%3A480740");
//        System.out.println("videoo "+uii);
//
//        path = FileUtils.getPath(this, uii);

        Intent extraIntent = getIntent();
        String path = "";

        if (extraIntent != null) {
            path =  extraIntent.getStringExtra(EXTRA_VIDEO_PATH);
        }

        Log.i("TAG", "videoooTrim "+path);

        path = FileUtils.getPath(this,  Uri.parse(path));


        mVideoTrimmer = ((DeepVideoTrimmer) findViewById(R.id.timeLine));
        timeLineBar = (RangeSeekBarView) findViewById(R.id.timeLineBar);
        textSize = (TextView) findViewById(R.id.textSize);
        tvCroppingMessage = (TextView) findViewById(R.id.tvCroppingMessage);

        if (mVideoTrimmer != null && path != null) {
            mVideoTrimmer.setMaxDuration(100);
            mVideoTrimmer.setOnTrimVideoListener(this);
            mVideoTrimmer.setVideoURI(Uri.parse(path));
        } else {
            showToastLong(getString(R.string.toast_cannot_retrieve_selected_video));
        }
    }

    @Override
    public void getResult(final Uri uri) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvCroppingMessage.setVisibility(View.GONE);
            }
        });
        Constants.croppedVideoURI = uri.toString();

        System.out.println("uriString() "+uri.toString());

//        Intent intent = new Intent();
//        intent.setData(uri);
//        setResult(RESULT_OK, intent);
//        finish();

    }

    @Override
    public void cancelAction() {
        mVideoTrimmer.destroy();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvCroppingMessage.setVisibility(View.GONE);
            }
        });
        finish();
    }
}

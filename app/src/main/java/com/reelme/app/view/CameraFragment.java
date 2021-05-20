package com.reelme.app.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.vision.CameraSource;
import com.reelme.app.R;
import com.reelme.app.view.camera.AutoFitTextureView;
import com.reelme.app.view.camera.CameraVideoFragment;
import com.reelme.app.view.camera.MyDraw;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CameraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CameraFragment extends CameraVideoFragment {

    private static final int ID_TIME_COUNT = 0x1006;
    MyDraw md;
    AutoFitTextureView mTextureView;
    ImageView mRecordVideo;
    VideoView mVideoView;
    ImageView mPlayVideo;

    ImageView cameraFlip;
    private String mOutputFilePath;

    private static final int MAX_VIDEO_DURATION = 20 * 1000;

    private ProgressBar progressBar;
    private int pStatus = 0;
    private Handler handler = new Handler();

    private TextView txtProgress;

    private ProgressBar  progressBar2;
    private TextView txtProgress2;

    public CameraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */


    public static CameraFragment newInstance() {
        CameraFragment fragment = new CameraFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        md = new MyDraw(getContext(), null);

        mTextureView = new AutoFitTextureView(getContext());
        mVideoView = view.findViewById(R.id.mVideoView);


        mPlayVideo = view.findViewById(R.id.mPlayVideo);
        mPlayVideo.setOnClickListener(v -> {
            mVideoView.start();
            mPlayVideo.setVisibility(View.GONE);
        });

        mRecordVideo = view.findViewById(R.id.mRecordVideo);
        mRecordVideo.setOnClickListener(v -> {
            if (mIsRecordingVideo) {
                try {
                    md.setPause(true);
                    stopRecordingVideo();
                    prepareViews();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                md.setPause(false);
                startRecordingVideo();
                mRecordVideo.setImageResource(R.drawable.ic_stop);
                //Receive out put file here
                mOutputFilePath = getCurrentFile().getAbsolutePath();
            }
        });

        cameraFlip = view.findViewById(R.id.cameraFlip);
        cameraFlip.setOnClickListener(v -> {
            if (getCameraMode() == CameraSource.CAMERA_FACING_BACK) {
                setCameraMode(CameraSource.CAMERA_FACING_FRONT);
            } else {
                setCameraMode(CameraSource.CAMERA_FACING_BACK);
            }
            closeCamera();
            openCamera(getCameraParamsWidth(), getCameraParamsHeight());
        });

        txtProgress =  view.findViewById(R.id.txtProgress);

        progressBar =  view.findViewById(R.id.progressBar);

        txtProgress2 =  view.findViewById(R.id.txtProgress2);

        progressBar2 =  view.findViewById(R.id.progressBar2);
        startCountdown();

        return view;
    }


    void startCountdown(){
        new Thread(() -> {
            while (pStatus <= 5) {
                handler.post(() -> {
                    progressBar.setProgress(pStatus);
                    txtProgress.setText(""+(5-pStatus));
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pStatus++;
            }
            mIsRecordingVideo = true;

            int minutes = (MAX_VIDEO_DURATION / 1000);
            int h = minutes/60;
            int m = minutes%60;
            getActivity().runOnUiThread(() -> {

                progressBar2.setProgress(minutes);
                txtProgress2.setText(String.format("%02d:%02d",h,m));
                txtProgress2.setVisibility(View.VISIBLE);

            });

            Message msg = mHandler.obtainMessage(ID_TIME_COUNT, 1,
                    MAX_VIDEO_DURATION / 1000);
            mHandler.sendMessage(msg);
//            startRecordingVideo();

            hideProgress();

        }).start();
    }


    private Handler mHandler = new Handler(Looper.myLooper()) {

        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case ID_TIME_COUNT:
                    if (mIsRecordingVideo) {
                        if (msg.arg1 > msg.arg2) {
                            // mTvTimeCount.setVisibility(View.INVISIBLE);
                            txtProgress2.setText("00:00");
                           // stopRecord();
                        } else {
                            int minutes = (msg.arg2 - msg.arg1);
                            int h = minutes/60;
                            int m = minutes%60;

                            progressBar2.setProgress(minutes);
                            txtProgress2.setText(String.format("%02d:%02d",h,m));
                            Message msg2 = mHandler.obtainMessage(ID_TIME_COUNT,
                                    msg.arg1 + 1, msg.arg2);
                            mHandler.sendMessageDelayed(msg2, 1000);
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    };


    private void hideProgress() {
        getActivity().runOnUiThread(() -> {
            progressBar.setVisibility(View.GONE);
            txtProgress.setVisibility(View.GONE);
        });
    }

    @Override
    public int getTextureResource() {
        return R.id.mTextureView;
    }

    @Override
    protected void setUp(View view) {
    }

    private void prepareViews() {
        if (mVideoView.getVisibility() == View.GONE) {
            mVideoView.setVisibility(View.VISIBLE);
            mPlayVideo.setVisibility(View.VISIBLE);
            mTextureView.setVisibility(View.GONE);
            setMediaForRecordVideo();
        }
    }

    private void setMediaForRecordVideo() {
        // Set media controller
        mVideoView.setMediaController(new MediaController(getActivity()));
        mVideoView.requestFocus();
        mVideoView.setVideoPath(mOutputFilePath);
        mVideoView.seekTo(100);
        mVideoView.setOnCompletionListener(mp -> {
            // Reset player
            mVideoView.setVisibility(View.GONE);
            mTextureView.setVisibility(View.VISIBLE);
            mPlayVideo.setVisibility(View.GONE);
            mRecordVideo.setImageResource(R.drawable.ic_record);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
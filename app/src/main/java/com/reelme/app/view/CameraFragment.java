package com.reelme.app.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

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

    MyDraw md;
    AutoFitTextureView mTextureView;
    ImageView mRecordVideo;
    VideoView mVideoView;
    ImageView mPlayVideo;
    Unbinder unbinder;

    ImageView cameraFlip;
    private String mOutputFilePath;

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
        unbinder = ButterKnife.bind(this, view);

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
            if (getCameraMode() == 0) {
                setCameraMode(1);
            } else {
                setCameraMode(0);
            }
            closeCamera();
            openCamera(getCameraParamsWidth(), getCameraParamsHeight());
        });

        return view;
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
        unbinder.unbind();
    }
}
package com.reelme.app.deepar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import ai.deepar.ar.ARErrorType;
import ai.deepar.ar.AREventListener;
import ai.deepar.ar.CameraResolutionPreset;
import ai.deepar.ar.DeepAR;
import ai.deepar.ar.DeepARImageFormat;

import com.reelme.app.R;
import com.reelme.app.trim.VideoTrimmerActivity;

import static com.reelme.app.trim.Constants.EXTRA_VIDEO_PATH;

public class ARMainActivity extends AppCompatActivity implements SurfaceHolder.Callback, AREventListener {

    public boolean mIsRecordingVideo;

    // Default camera lens value, change to CameraSelector.LENS_FACING_BACK to initialize with back camera
    private int defaultLensFacing = CameraSelector.LENS_FACING_FRONT;
    private int lensFacing = defaultLensFacing;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ByteBuffer[] buffers;
    private int currentBuffer = 0;
    private static final int NUMBER_OF_BUFFERS = 2;
    private static final int ID_TIME_COUNT = 0x1006;

    private DeepAR deepAR;

    private int currentMask = 0;
    private int currentEffect = 0;
    private int currentFilter = 0;

    ArrayList<String> masks;
    private static final int MAX_VIDEO_DURATION = 20 * 1000;
    VideoView mVideoView;
    ImageView mPlayVideo;
    private String mOutputFilePath;


    private boolean recording = false;
    // private boolean currentSwitchRecording = false;
    private String recordingPath = Environment.getExternalStorageDirectory() + File.separator + "video.mp4";

    private ProgressBar progressBar;
    private int pStatus = 0;
    private Handler handler = new Handler();

    private TextView txtProgress;

    private ProgressBar progressBar2;
    private TextView txtProgress2;

    private static final String VIDEO_DIRECTORY_NAME = "AndroidWave";

    private File getOutputMediaFile() {

        // External sdcard file location
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(),
                VIDEO_DIRECTORY_NAME);
        // Create storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("ARMainActivity", "Oops! Failed create "
                        + VIDEO_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;

        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "VID_" + timeStamp + ".mp4");
        return mediaFile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ar_activity_main);
        mVideoView = findViewById(R.id.mVideoView);
        mPlayVideo = findViewById(R.id.mPlayVideo);
        txtProgress = findViewById(R.id.txtProgress);

        progressBar = findViewById(R.id.progressBar);

        txtProgress2 = findViewById(R.id.txtProgress2);

        progressBar2 = findViewById(R.id.progressBar2);

        mPlayVideo.setOnClickListener(v -> {
            mVideoView.start();
            mPlayVideo.setVisibility(View.GONE);
        });
    }

    void startCountdown() {
        if (pStatus <= 5) {
            new Thread(() -> {
                while (pStatus <= 5) {
                    handler.post(() -> {
                        progressBar.setProgress(pStatus);
                        txtProgress.setText("" + (5 - pStatus));
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pStatus++;
                }


                int minutes = (MAX_VIDEO_DURATION / 1000);
                int h = minutes / 60;
                int m = minutes % 60;
                runOnUiThread(() -> {

                    progressBar2.setProgress(minutes);
                    txtProgress2.setText(String.format("%02d:%02d", h, m));
                    txtProgress2.setVisibility(View.VISIBLE);

                });

                Message msg = mHandler.obtainMessage(ID_TIME_COUNT, 1,
                        MAX_VIDEO_DURATION / 1000);
                mHandler.sendMessage(msg);
                recordingPath = getOutputMediaFile().getAbsolutePath();
                deepAR.startVideoRecording(recordingPath);

//            startRecordingVideo();

                hideProgress();

            }).start();
        }
    }

    private void prepareViews() {
        if (mVideoView.getVisibility() == View.GONE) {
            mVideoView.setVisibility(View.VISIBLE);
            mPlayVideo.setVisibility(View.VISIBLE);
        }
    }


    private void hideProgress() {
        runOnUiThread(() -> {
            progressBar.setVisibility(View.GONE);
            txtProgress.setVisibility(View.GONE);
        });
    }


    private Handler mHandler = new Handler(Looper.myLooper()) {

        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case ID_TIME_COUNT:
                    if (recording) {
                        if (msg.arg1 > msg.arg2) {
                            // mTvTimeCount.setVisibility(View.INVISIBLE);
                            txtProgress2.setText("00:00");
                            try {
                                deepAR.stopVideoRecording();
                                Toast.makeText(getApplicationContext(), "Saved video to: " + recordingPath, Toast.LENGTH_LONG).show();
                                prepareViews();
                                startTrimActivity();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            int minutes = (msg.arg2 - msg.arg1);
                            int h = minutes / 60;
                            int m = minutes % 60;

                            progressBar2.setProgress(minutes);
                            txtProgress2.setText(String.format("%02d:%02d", h, m));
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


    @Override
    protected void onStart() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO},
                    1);
        } else {
            // Permission has already been granted
            initialize();
        }
        super.onStart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1 && grantResults.length > 0) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return; // no permission
                }
            }
            initialize();
        }
    }

    private void initialize() {
        initializeDeepAR();
        initializeFilters();
        initalizeViews();
    }

    private void initializeFilters() {
        masks = new ArrayList<>();
        masks.add("none");
        masks.add("lion");

    }

    private void initalizeViews() {
        ImageButton previousMask = findViewById(R.id.previousMask);

        SurfaceView arView = findViewById(R.id.surface);

        arView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deepAR.onClick();
            }
        });

        arView.getHolder().addCallback(this);

        // Surface might already be initialized, so we force the call to onSurfaceChanged
        arView.setVisibility(View.GONE);
        arView.setVisibility(View.VISIBLE);

        final ImageButton screenshotBtn = findViewById(R.id.recordButton);
        screenshotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deepAR.takeScreenshot();
            }
        });

        ImageButton switchCamera = findViewById(R.id.switchCamera);
        switchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lensFacing = lensFacing == CameraSelector.LENS_FACING_FRONT ? CameraSelector.LENS_FACING_BACK : CameraSelector.LENS_FACING_FRONT;
                //unbind immediately to avoid mirrored frame.
                ProcessCameraProvider cameraProvider = null;
                try {
                    cameraProvider = cameraProviderFuture.get();
                    cameraProvider.unbindAll();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setupCamera();
            }
        });

        ImageButton openActivity = findViewById(R.id.openActivity);
        openActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ARMainActivity.this, ARBasicActivity.class);
                ARMainActivity.this.startActivity(myIntent);
            }


        });


        screenshotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recording) {
                    deepAR.stopVideoRecording();
                    prepareViews();
                    Toast.makeText(getApplicationContext(), "Saved video to: " + recordingPath, Toast.LENGTH_LONG).show();
                    startTrimActivity();
               
                } else {
                    txtProgress.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    startCountdown();
//                    deepAR.startVideoRecording(recordingPath);
                    Toast.makeText(getApplicationContext(), "Started video recording!", Toast.LENGTH_SHORT).show();
                }
                recording = !recording;
            }
        });


        previousMask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentMask == 0) {
                    gotoNext();
                } else {
                    gotoPrevious();

                }
            }
        });

    }

    private void startTrimActivity() {


        MediaScannerConnection.scanFile(this, new String[] { recordingPath }, null,
                (path, uri) ->{
                    Log.i("TAG", "videooo "+uri.toString());

                    Intent intent = new Intent(this, VideoTrimmerActivity.class);
                    intent.putExtra(EXTRA_VIDEO_PATH, uri.toString());
                    System.out.println("videoo "+uri.toString());
                    startActivity(intent);
                } );


    }

    private void gotoNext() {
        currentMask = (currentMask + 1) % masks.size();
        deepAR.switchEffect("mask", getFilterPath(masks.get(currentMask)));
    }

    private void gotoPrevious() {
        currentMask = (currentMask - 1 + masks.size()) % masks.size();
        deepAR.switchEffect("mask", getFilterPath(masks.get(currentMask)));
    }


    private int getScreenOrientation() {
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int orientation;
        // if the device's natural orientation is portrait:
        if ((rotation == Surface.ROTATION_0
                || rotation == Surface.ROTATION_180) && height > width ||
                (rotation == Surface.ROTATION_90
                        || rotation == Surface.ROTATION_270) && width > height) {
            switch (rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_180:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                case Surface.ROTATION_270:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                default:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
            }
        }
        // if the device's natural orientation is landscape or if the device
        // is square:
        else {
            switch (rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_180:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                case Surface.ROTATION_270:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                default:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
            }
        }

        return orientation;
    }

    private void initializeDeepAR() {
        deepAR = new DeepAR(this);
        deepAR.setLicenseKey("bd81c27ae5d8e5efd41eafeae67671fcdd936bd3471774f8db9777f31462e77fd9548a969daa98c1");
        deepAR.initialize(this, this);
        setupCamera();
    }

    private void setupCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindImageAnalysis(cameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindImageAnalysis(@NonNull ProcessCameraProvider cameraProvider) {
        CameraResolutionPreset cameraPreset = CameraResolutionPreset.P640x480;
        int width;
        int height;
        int orientation = getScreenOrientation();
        if (orientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE || orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            width = cameraPreset.getWidth();
            height = cameraPreset.getHeight();
        } else {
            width = cameraPreset.getHeight();
            height = cameraPreset.getWidth();
        }
        buffers = new ByteBuffer[NUMBER_OF_BUFFERS];
        for (int i = 0; i < NUMBER_OF_BUFFERS; i++) {
            buffers[i] = ByteBuffer.allocateDirect(width * height * 3);
            buffers[i].order(ByteOrder.nativeOrder());
            buffers[i].position(0);
        }

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder().setTargetResolution(new Size(width, height)).setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();
        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(@NonNull ImageProxy image) {
                //image.getImageInfo().getTimestamp();
                byte[] byteData;
                ByteBuffer yBuffer = image.getPlanes()[0].getBuffer();
                ByteBuffer uBuffer = image.getPlanes()[1].getBuffer();
                ByteBuffer vBuffer = image.getPlanes()[2].getBuffer();

                int ySize = yBuffer.remaining();
                int uSize = uBuffer.remaining();
                int vSize = vBuffer.remaining();

                byteData = new byte[ySize + uSize + vSize];

                //U and V are swapped
                yBuffer.get(byteData, 0, ySize);
                vBuffer.get(byteData, ySize, vSize);
                uBuffer.get(byteData, ySize + vSize, uSize);

                buffers[currentBuffer].put(byteData);
                buffers[currentBuffer].position(0);
                if (deepAR != null) {
                    deepAR.receiveFrame(buffers[currentBuffer],
                            image.getWidth(), image.getHeight(),
                            image.getImageInfo().getRotationDegrees(),
                            lensFacing == CameraSelector.LENS_FACING_FRONT,
                            DeepARImageFormat.YUV_420_888,
                            image.getPlanes()[1].getPixelStride()
                    );
                }
                currentBuffer = (currentBuffer + 1) % NUMBER_OF_BUFFERS;
                image.close();
            }
        });

        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(lensFacing).build();
        cameraProvider.unbindAll();
        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageAnalysis);

    }


    private String getFilterPath(String filterName) {
        if (filterName.equals("none")) {
            return null;
        }
        return "file:///android_asset/" + filterName;
    }


    @Override
    protected void onStop() {
        recording = false;
        ProcessCameraProvider cameraProvider = null;
        try {
            cameraProvider = cameraProviderFuture.get();
            cameraProvider.unbindAll();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        deepAR.release();
        deepAR = null;
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (deepAR == null) {
            return;
        }
        deepAR.setAREventListener(null);
        deepAR.release();
        deepAR = null;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // If we are using on screen rendering we have to set surface view where DeepAR will render
        deepAR.setRenderSurface(holder.getSurface(), width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (deepAR != null) {
            deepAR.setRenderSurface(null, 0, 0);
        }
    }

    @Override
    public void screenshotTaken(Bitmap bitmap) {
        CharSequence now = DateFormat.format("yyyy_MM_dd_hh_mm_ss", new Date());
        try {
            File imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/DeepAR_" + now + ".jpg");
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            MediaScannerConnection.scanFile(ARMainActivity.this, new String[]{imageFile.toString()}, null, null);
            Toast.makeText(ARMainActivity.this, "Screenshot saved", Toast.LENGTH_SHORT).show();
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    @Override
    public void videoRecordingStarted() {

    }

    @Override
    public void videoRecordingFinished() {

    }

    @Override
    public void videoRecordingFailed() {

    }

    @Override
    public void videoRecordingPrepared() {

    }

    @Override
    public void shutdownFinished() {

    }

    @Override
    public void initialized() {
        // Restore effect state after deepar release
        deepAR.switchEffect("mask", getFilterPath(masks.get(currentMask)));
    }

    @Override
    public void faceVisibilityChanged(boolean b) {

    }

    @Override
    public void imageVisibilityChanged(String s, boolean b) {

    }

    @Override
    public void frameAvailable(Image image) {

    }

    @Override
    public void error(ARErrorType arErrorType, String s) {

    }


    @Override
    public void effectSwitched(String s) {

    }
}

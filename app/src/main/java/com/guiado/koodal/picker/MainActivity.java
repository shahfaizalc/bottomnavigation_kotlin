package com.guiado.koodal.picker;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.guiado.koodal.R;
import com.guiado.koodal.listeners.EmptyResultListener;
import com.guiado.koodal.listeners.MultipleClickListener;
import com.guiado.koodal.model.EventStatus;
import com.guiado.koodal.model2.ImageEvents;
import com.guiado.koodal.network.FirbaseWriteHandler;
import com.guiado.koodal.util.GetGenericsKt;
import com.guiado.koodal.util.MultipleClickHandler;
import com.itravis.ticketexchange.listeners.DateListener;
import com.itravis.ticketexchange.utils.DatePickerEvent;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MultipleClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;
    private FirebaseAuth mAuth;

    ImageView imgProfile;
    ImageView imgPlus;
    Button uploadPhoto;
    ProgressBar progressBar;
    Uri uri;
    String imageId = "";
    TextView caption;
    EditText postDate;
    String dateStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainn);
        ButterKnife.bind(this);
        progressBar = findViewById(R.id.progress_bar);
        caption = findViewById(R.id.caption);
        postDate = findViewById(R.id.postDate);
        postDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!handleMultipleClicks()) {
                    new DatePickerEvent().onDatePickerClick(MainActivity.this, new DateListener() {
                        @Override
                        public void onDateSet(@NotNull String result) {
                            dateStr = result;
                            postDate.setText(result);
                        }
                    });
                }
            }
        });

        imgProfile = findViewById(R.id.img_profile);

        imgProfile.setOnClickListener(v -> imgAddClick());

        uploadPhoto = findViewById(R.id.loginButton);

        uploadPhoto.setOnClickListener(v -> uploadImage(uri));

        imgPlus = findViewById(R.id.img_plus);
        imgPlus.setOnClickListener(v -> imgAddClick());

        loadProfileDefault();
        mAuth = FirebaseAuth.getInstance();

        // Clearing older images from cache directory
        // don't call this line if you want to choose multiple images in the same activity
        // call this once the bitmap(s) usage is over
        ImagePickerActivity.clearCache(this);
    }
    private void imgAddClick() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }
    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);

        Picasso.get().load(url)
                .into(imgProfile);
        imgProfile.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }

    private void loadProfileDefault() {

        Picasso.get().load(R.drawable.placeholder_profile).into(imgProfile);

        imgProfile.setColorFilter(ContextCompat.getColor(this, R.color.profile_default_tint));
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(MainActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(MainActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    loadProfile(uri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadImage(Uri uri) {

        if(uri!= null && !uri.getPath().isEmpty()) {
            uploadPhoto.setVisibility(View.INVISIBLE);

            progressBar.setVisibility(View.VISIBLE);
            String postTime = String.valueOf(System.currentTimeMillis());


            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

            StorageReference riversRef = mStorageRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid() + "/"+postTime);

            riversRef.putFile(uri)
                    .addOnSuccessListener(taskSnapshot -> {

                        // Get a URL to the uploaded content
                        Task<Uri> downloadUrl = taskSnapshot.getMetadata().getReference()
                                .getDownloadUrl().addOnSuccessListener(uri1 ->{
                                    Log.d(TAG, "Image cache path: " + uri1);
                                    postEvent(uri1.toString(),postTime);


                                    });

                        Log.d(TAG, "Image cache path: " + downloadUrl);
                       // imageId = taskSnapshot.getUploadSessionUri().toString();
                    })
                    .addOnFailureListener(exception -> {
                        // Handle unsuccessful uploads
                        Log.d(TAG, "Image cache path: " + exception);
                        uploadPhoto.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    });
        } else {
            Toast.makeText(this,"Please upload image to post ",Toast.LENGTH_LONG).show();
        }

    }

    long onDatePickerClick(String dated )  {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = formatter.parse(dated);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }


    void postEvent(String id, String imageId){

        ImageEvents events = new ImageEvents();
        events.setImageId(id);
        events.setTitle(caption.getText().toString());
        events.setPostedBy(mAuth.getUid());
        events.setPostedDate(imageId);
        events.setStartDate(String.valueOf(onDatePickerClick(dateStr)));
        events.setEventState(EventStatus.SHOWING);
        events.setPostedByName(GetGenericsKt.getUserName(getApplicationContext(), FirebaseAuth.getInstance().getCurrentUser().getUid()).getName());

        FirbaseWriteHandler firbaseWriteHandler = new FirbaseWriteHandler();
        firbaseWriteHandler.updateEvents(events, new EmptyResultListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "DocumentSnapshot doPostEvents onFailure ");
                finish();

            }

            @Override
            public void onFailure(@NotNull Exception e) {
                Log.d(TAG, "DocumentSnapshot doPostEvents onFailure " + e.getLocalizedMessage());
                uploadPhoto.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    public boolean handleMultipleClicks() {
        return  MultipleClickHandler.Companion.handleMultipleClicks();
    }
}

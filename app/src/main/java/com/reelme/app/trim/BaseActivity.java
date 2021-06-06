package com.reelme.app.trim;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;


/**
 * Created by Deep Patel
 * (Sr. Android Developer)
 * on 2/6/2018
 */

public class BaseActivity extends AppCompatActivity {
    protected boolean shouldPerformDispatchTouch = true;

    public setPermissionListener permissionListener;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void showToastLong(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean ret = false;
        try {
            View view = getCurrentFocus();
            ret = super.dispatchTouchEvent(event);
            if (shouldPerformDispatchTouch) {
                if (view instanceof EditText) {
                    View w = getCurrentFocus();
                    int scrCords[] = new int[2];
                    if (w != null) {
                        w.getLocationOnScreen(scrCords);
                        float x = event.getRawX() + w.getLeft() - scrCords[0];
                        float y = event.getRawY() + w.getTop() - scrCords[1];

                        if (event.getAction() == MotionEvent.ACTION_UP
                                && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom())) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        }
                    }
                }
            }
            return ret;
        } catch (Exception e) {
            return ret;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                if (permissionListener != null) permissionListener.onPermissionGranted(requestCode);
                break;
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                if (permissionListener != null) permissionListener.onPermissionDenied(requestCode);
                break;
            } else {
                if (permissionListener != null)
                    permissionListener.onPermissionNeverAsk(requestCode);
                break;
            }
        }
    }

    public interface setPermissionListener {
        public void onPermissionGranted(int requestCode);

        public void onPermissionDenied(int requestCode);

        public void onPermissionNeverAsk(int requestCode);
    }
}

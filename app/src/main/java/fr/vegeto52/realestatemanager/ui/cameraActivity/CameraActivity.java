package fr.vegeto52.realestatemanager.ui.cameraActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.vegeto52.realestatemanager.R;
import fr.vegeto52.realestatemanager.databinding.ActivityCameraBinding;

public class CameraActivity extends AppCompatActivity {

    ActivityCameraBinding mBinding;
    private PreviewView mPreviewView;
    private FloatingActionButton mFabTakePhoto;
    private FloatingActionButton mFabBack;
    private Button mValidateButton;
    private Button mCancelButton;
    private ImageView mImageViewPreview;
    private ConstraintLayout mFrontLayout;
    private ListenableFuture<ProcessCameraProvider> mCameraProviderListenableFuture;
    private ImageCapture mImageCapture;
    private Uri mImageUri;
    private List<Uri> mCapturedPhotoUris = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityCameraBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        mPreviewView = view.findViewById(R.id.camera_preview_camera_activity);
        mFabTakePhoto = view.findViewById(R.id.fab_image_capture_camera_activity);
        mFabBack = view.findViewById(R.id.fab_back_camera_activity);
        mValidateButton = view.findViewById(R.id.validate_button_camera_activity);
        mCancelButton = view.findViewById(R.id.cancel_button_camera_activty);
        mImageViewPreview = view.findViewById(R.id.imageview_preview_camera_activity);
        mFrontLayout = view.findViewById(R.id.front_layout_camera_activity);

        if (savedInstanceState != null){
            mImageUri = savedInstanceState.getParcelable("ImageUri");
            mCapturedPhotoUris = savedInstanceState.getParcelableArrayList("CapturedPhotoUris");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);
        }
        initCamera();
        initButton();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("ImageUri", mImageUri);
        outState.putParcelableArrayList("CapturedPhotoUris", new ArrayList<>(mCapturedPhotoUris));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void initCamera(){
        mCameraProviderListenableFuture = ProcessCameraProvider.getInstance(this);
        mCameraProviderListenableFuture.addListener(() -> {
            ProcessCameraProvider cameraProvider = null;
            try {
                cameraProvider = mCameraProviderListenableFuture.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            bindPreview(cameraProvider);
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindPreview(ProcessCameraProvider cameraProvider){
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        preview.setSurfaceProvider(mPreviewView.getSurfaceProvider());
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            mImageCapture = new ImageCapture.Builder()
                    .setTargetResolution(new Size(1280, 720))
                    .build();
        } else {
            mImageCapture = new ImageCapture.Builder()
                    .setTargetResolution(new Size(720, 1280))
                    .build();
        }
        Camera camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, mImageCapture);
    }

    private void initButton(){
        mFabTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                String timestamp = String.valueOf(date.getTime());

                ContentResolver contentResolver = getContentResolver();
                Uri imageCollection;
                imageCollection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);

                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, timestamp + ".jpg");
                contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                Uri imageUri = contentResolver.insert(imageCollection, contentValues);

                OutputStream outputStream = null;
                try {
                    outputStream = contentResolver.openOutputStream(imageUri);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }

                assert  outputStream != null;
                ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(outputStream).build();

                mImageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(CameraActivity.this), new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        mImageUri = imageUri;
                        mPreviewView.setVisibility(View.INVISIBLE);
                        mFrontLayout.setVisibility(View.VISIBLE);
                        mFabTakePhoto.setEnabled(false);
                        mFabBack.setEnabled(false);
                        mValidateButton.setVisibility(View.VISIBLE);
                        mCancelButton.setVisibility(View.VISIBLE);

                        showPhotoPreview(mImageUri);
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        mImageUri = null;
                    }
                });
            }
        });

        mFabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putParcelableArrayListExtra("capturedPhotoUris", new ArrayList<>(mCapturedPhotoUris));
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFrontLayout.setVisibility(View.INVISIBLE);
                mImageUri = null;
                mPreviewView.setVisibility(View.VISIBLE);
                mFabTakePhoto.setEnabled(true);
                mFabBack.setEnabled(true);
                mValidateButton.setVisibility(View.INVISIBLE);
                mCancelButton.setVisibility(View.INVISIBLE);
            }
        });

        mValidateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mImageUri != null){
                    mCapturedPhotoUris.add(mImageUri);
                }

                mFrontLayout.setVisibility(View.INVISIBLE);
                mImageUri = null;
                mPreviewView.setVisibility(View.VISIBLE);
                mFabTakePhoto.setEnabled(true);
                mFabBack.setEnabled(true);
                mValidateButton.setVisibility(View.INVISIBLE);
                mCancelButton.setVisibility(View.INVISIBLE);


            }
        });
    }

    private void showPhotoPreview(Uri file){
        mImageViewPreview.setImageURI(file);
    }
}
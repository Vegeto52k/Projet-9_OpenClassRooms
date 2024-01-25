package fr.vegeto52.realestatemanager.ui.cameraActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.vegeto52.realestatemanager.databinding.ActivityCameraBinding;

/**
 * The CameraActivity class extends AppCompatActivity and provides functionality for capturing photos using the device camera.
 * It includes UI elements for camera preview, photo capture, validation, and cancellation.
 */
public class CameraActivity extends AppCompatActivity {

    // View binding for the activity
    ActivityCameraBinding mBinding;

    // UI elements
    private PreviewView mPreviewView;
    private FloatingActionButton mFabTakePhoto;
    private FloatingActionButton mFabBack;
    private Button mValidateButton;
    private Button mCancelButton;
    private ImageView mImageViewPreview;
    private ConstraintLayout mFrontLayout;

    // Camera-related variables
    private ListenableFuture<ProcessCameraProvider> mCameraProviderListenableFuture;
    private ImageCapture mImageCapture;
    private Uri mImageUri;
    private List<Uri> mCapturedPhotoUris = new ArrayList<>();

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           then this Bundle contains the data it most recently supplied in onSaveInstanceState.
     *                           Note: Otherwise, it is null.
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate and set the content view for the activity
        mBinding = ActivityCameraBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        // Initialize UI elements from the binding
        mPreviewView = mBinding.cameraPreviewCameraActivity;
        mFabTakePhoto = mBinding.fabImageCaptureCameraActivity;
        mFabBack = mBinding.fabBackCameraActivity;
        mValidateButton = mBinding.validateButtonCameraActivity;
        mCancelButton = mBinding.cancelButtonCameraActivty;
        mImageViewPreview = mBinding.imageviewPreviewCameraActivity;
        mFrontLayout = mBinding.frontLayoutCameraActivity;

        // Restore saved state or request camera permission
        if (savedInstanceState != null) {
            mImageUri = savedInstanceState.getParcelable("ImageUri");
            mCapturedPhotoUris = savedInstanceState.getParcelableArrayList("CapturedPhotoUris");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);
        }
        // Initialize camera and buttons
        initCamera();
        initButton();
    }

    /**
     * Called to retrieve per-instance state from an activity before being killed so that the state can be restored.
     *
     * @param outState Bundle in which to place your saved state.
     */
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

    /**
     * Initialize camera-related components.
     */
    private void initCamera() {
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

    /**
     * Bind camera preview to the UI.
     *
     * @param cameraProvider ProcessCameraProvider instance for managing the camera's lifecycle.
     */
    private void bindPreview(ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        preview.setSurfaceProvider(mPreviewView.getSurfaceProvider());

        // Set target resolution based on device orientation
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mImageCapture = new ImageCapture.Builder()
                    .setTargetResolution(new Size(1280, 720))
                    .build();
        } else {
            mImageCapture = new ImageCapture.Builder()
                    .setTargetResolution(new Size(720, 1280))
                    .build();
        }
        cameraProvider.bindToLifecycle(this, cameraSelector, preview, mImageCapture);
    }

    /**
     * Initialize button click listeners and actions.
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void initButton() {
        // Take photo button click listener
        mFabTakePhoto.setOnClickListener(view -> {
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
                assert imageUri != null;
                outputStream = contentResolver.openOutputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            assert outputStream != null;
            ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(outputStream).build();

            // Capture the image and handle the result
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
        });

        // Back button click listener
        mFabBack.setOnClickListener(view -> {
            // Return captured photo URIs to the calling activity
            Intent resultIntent = new Intent();
            resultIntent.putParcelableArrayListExtra("capturedPhotoUris", new ArrayList<>(mCapturedPhotoUris));
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        // Cancel button click listener
        mCancelButton.setOnClickListener(view -> {
            mFrontLayout.setVisibility(View.INVISIBLE);
            mImageUri = null;
            mPreviewView.setVisibility(View.VISIBLE);
            mFabTakePhoto.setEnabled(true);
            mFabBack.setEnabled(true);
            mValidateButton.setVisibility(View.INVISIBLE);
            mCancelButton.setVisibility(View.INVISIBLE);
        });

        // Validate button click listener
        mValidateButton.setOnClickListener(view -> {
            // Add the captured photo URI to the list and reset UI
            if (mImageUri != null) {
                mCapturedPhotoUris.add(mImageUri);
            }

            mFrontLayout.setVisibility(View.INVISIBLE);
            mImageUri = null;
            mPreviewView.setVisibility(View.VISIBLE);
            mFabTakePhoto.setEnabled(true);
            mFabBack.setEnabled(true);
            mValidateButton.setVisibility(View.INVISIBLE);
            mCancelButton.setVisibility(View.INVISIBLE);
        });
    }

    /**
     * Display the captured photo preview in the UI.
     *
     * @param file Uri of the captured photo.
     */
    private void showPhotoPreview(Uri file) {
        mImageViewPreview.setImageURI(file);
    }
}
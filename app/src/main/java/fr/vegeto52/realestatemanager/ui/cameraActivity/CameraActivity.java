package fr.vegeto52.realestatemanager.ui.cameraActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;

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
    private ListenableFuture<ProcessCameraProvider> mCameraProviderListenableFuture;
    private ImageCapture mImageCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityCameraBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);

        mPreviewView = view.findViewById(R.id.camera_preview_camera_activity);
        mFabTakePhoto = view.findViewById(R.id.fab_image_capture_camera_activity);
        mFabBack = view.findViewById(R.id.fab_back_camera_activity);
        mValidateButton = view.findViewById(R.id.validate_button_camera_activity);
        mCancelButton = view.findViewById(R.id.cancel_button_camera_activty);
        mImageViewPreview = view.findViewById(R.id.imageview_preview_camera_activity);

        initCamera();
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
}
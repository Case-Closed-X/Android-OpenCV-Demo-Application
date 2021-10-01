package com.x.edu.opencv;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.x.edu.opencv.databinding.Activity29CameraBinding;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Mat;

public class Activity29Camera extends AppCompatActivity {

    private Activity29CameraBinding binding;

    private Mat rgba;
    private Boolean isFrontCamera = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = Activity29CameraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        binding.javaCamera2View29.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_BACK);
        binding.javaCamera2View29.enableView();

        binding.button29Camera.setOnClickListener(v -> {
            if (isFrontCamera) {
                binding.javaCamera2View29.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_BACK);
                isFrontCamera = false;
            } else {
                binding.javaCamera2View29.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_FRONT);
                isFrontCamera = true;
            }

            binding.javaCamera2View29.disableView();

            binding.javaCamera2View29.enableView();
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            binding.javaCamera2View29.setCameraPermissionGranted();
        }

        binding.javaCamera2View29.setCvCameraViewListener(new CameraBridgeViewBase.CvCameraViewListener2() {
            @Override
            public void onCameraViewStarted(int width, int height) {
                rgba = new Mat();
            }

            @Override
            public void onCameraViewStopped() {
                rgba.release();
            }

            @Override
            public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
                rgba = inputFrame.rgba();
                return rgba;
            }
        });
    }

    private void init() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}
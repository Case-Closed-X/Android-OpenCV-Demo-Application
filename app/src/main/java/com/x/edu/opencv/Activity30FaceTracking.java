package com.x.edu.opencv;

import android.Manifest;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.x.edu.opencv.databinding.Activity30FaceTrackingBinding;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Activity30FaceTracking extends AppCompatActivity {

    private Activity30FaceTrackingBinding binding;

    private Mat rgba;
    private Boolean isFrontCamera = false;
    private CascadeClassifier faceDectector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=Activity30FaceTrackingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();//Landscape横屏也可以在清单文件中声明

        binding.imageCameraChange.setOnClickListener(v -> {
            if(isFrontCamera){
                binding.javaCamera2View30.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_BACK);
                isFrontCamera = false;
            }
            else{
                binding.javaCamera2View30.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_FRONT);
                isFrontCamera = true;
            }
            //使用视图绑定则无须断言其是否为空：assert javaCamera2View30 != null;
            binding.javaCamera2View30.disableView();

            binding.javaCamera2View30.enableView();
        });

        binding.imagePhotograph.setOnClickListener(v -> {
            String name = System.currentTimeMillis() + ".jpg";
            FileOutputStream fileOutputStream;
            try {
                fileOutputStream = openFileOutput(name,MODE_PRIVATE);
                Bitmap bitmap = Bitmap.createBitmap(rgba.width(),rgba.height(),Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(rgba,bitmap);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        binding.javaCamera2View30.setCvCameraViewListener(new CameraBridgeViewBase.CvCameraViewListener2() {
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
                //在这里实现人脸检测的功能
                FaceTracking();
                return rgba;
            }

            private void FaceTracking() {
                float biLi = 0.2f;
                int chiCun = Math.round(rgba.rows() * biLi);
                MatOfRect faces = new MatOfRect();
                faceDectector.detectMultiScale(rgba,faces,1.1,2,2,new Size(chiCun,chiCun),new Size());
                List<Rect> faceList = faces.toList();
                Scalar faceRectColor = new Scalar(255,255,255,255);
                if(faceList.size() > 0){
                    for(Rect rect : faceList){
                        Imgproc.rectangle(rgba,rect.tl(),rect.br(),faceRectColor,10,8,0);
                    }
                }
            }
        });

    }

    private void init() {
        //代码设置横屏，也可以用清单文件中的android:screenOrientation="landscape"以及全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //动态申请相机权限
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
        }
        else {
            binding.javaCamera2View30.setCameraPermissionGranted();
        }

        //设置显示后置摄像头
        binding.javaCamera2View30.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_BACK);
        binding.javaCamera2View30.enableView();

        //初始化人脸检测
        InputStream inputStream = getResources().openRawResource(R.raw.lbpcascade_frontalface_improved);
        File cascaseDir = this.getDir("cascade", Context.MODE_PRIVATE);
        File file = new File(cascaseDir.getAbsolutePath(),"lbpcascade_frontalface_improved.xml");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            byte[] buff = new byte[1024];
            int len;
            while((len = inputStream.read(buff)) != -1){
                fileOutputStream.write(buff,0,len);
            }
            inputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        faceDectector = new CascadeClassifier(file.getAbsolutePath());
        file.delete();
        cascaseDir.delete();
    }
}
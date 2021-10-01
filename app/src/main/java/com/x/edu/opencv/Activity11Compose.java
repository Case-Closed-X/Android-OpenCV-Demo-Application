package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity11ComposeBinding;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;

public class Activity11Compose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity11ComposeBinding binding = Activity11ComposeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.orange));
        }

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.x2);
        binding.image111.setImageBitmap(bitmap1);
        Mat mat1 = new Mat();
        Utils.bitmapToMat(bitmap1, mat1);

        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.s2);
        binding.image112.setImageBitmap(bitmap2);
        Mat mat2 = new Mat();
        Utils.bitmapToMat(bitmap2, mat2);

        binding.compose.setOnClickListener(v -> {
            Mat mat = new Mat();

            Core.add(mat1, mat2, mat);//叠加操作
            //拼接操作
            /*Mat matCut1=mat1.submat(0,mat1.height(),0,mat1.width()/2);
            Mat matCut2=mat2.submat(0,mat2.height(),mat2.width()/2,mat2.width());
            List<Mat> matList = new ArrayList<>();
            matList.add(matCut1);
            matList.add(matCut2);
            Core.hconcat(matList,mat);*/


            Bitmap bitmap = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat, bitmap);

            binding.imageCompose.setImageBitmap(bitmap);

            mat.release();
        });

        /*Mat mat1=Mat.zeros(500,500, CvType.CV_8UC1);
        Imgproc.circle(mat1,new Point(100,100),50,new Scalar(255,255,255),
                -1,8,0);
        Bitmap bitmap1=Bitmap.createBitmap(500,500, Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat1,bitmap1);
        binding.image111.setImageBitmap(bitmap1);

        Mat mat2=Mat.zeros(500,500, CvType.CV_8UC1);
        Imgproc.circle(mat2,new Point(400,100),50,new Scalar(255,255,255),
                -1,8,0);
        Bitmap bitmap2=Bitmap.createBitmap(500,500, Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat2,bitmap2);
        binding.image112.setImageBitmap(bitmap2);


        binding.compose.setOnClickListener(v -> {
            Mat mat=new Mat();

            Core.add(mat1,mat2,mat);

            Bitmap bitmap=Bitmap.createBitmap(mat.width(),mat.height(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat,bitmap);

            binding.imageCompose.setImageBitmap(bitmap);

            mat.release();
        });*/

    }
}
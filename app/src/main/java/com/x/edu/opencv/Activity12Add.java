package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity12AddBinding;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Activity12Add extends AppCompatActivity {

    private Activity12AddBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=Activity12AddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.purple_500));
        }

        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.bg1);
        binding.image12.setImageBitmap(bitmap);

        binding.add.setOnClickListener(v -> {
            Mat mat=new Mat();
            Utils.bitmapToMat(bitmap,mat);
            Imgproc.cvtColor(mat,mat,Imgproc.COLOR_BGRA2BGR);

            //图像亮度
            Core.add(mat,new Scalar(-10,-10,-10),mat);

            //图像的对比度调整为
            Core.multiply(mat,new Scalar(1.5,1.5,1.5),mat);

            Bitmap bitmap2=Bitmap.createBitmap(mat.width(),mat.height(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat,bitmap2);
            binding.imageAdd.setImageBitmap(bitmap2);

            mat.release();
        });
    }
}
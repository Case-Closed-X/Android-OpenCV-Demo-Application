package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity9ChannelBinding;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class Activity9Channel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity9ChannelBinding binding = Activity9ChannelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(Color.parseColor("#ff0099cc"));
        }

        /*setSupportActionBar(binding.toolbar);//toolBar相关操作
        binding.toolbar.setTitleTextColor(Color.WHITE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.microsoft);
        binding.image9.setImageBitmap(bitmap);

        binding.channel.setOnClickListener(v -> {
            Mat mat = new Mat();
            Utils.bitmapToMat(bitmap, mat);
            Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGRA2BGR);

            int width = mat.width();
            int height = mat.height();
            Bitmap.Config config = Bitmap.Config.ARGB_8888;

            Bitmap bitmapB = Bitmap.createBitmap(width, height, config);
            Bitmap bitmapG = Bitmap.createBitmap(width, height, config);
            Bitmap bitmapR = Bitmap.createBitmap(width, height, config);

            List<Mat> matList = new ArrayList<>();
            Core.split(mat, matList);

            Utils.matToBitmap(matList.get(0), bitmapB);
            Utils.matToBitmap(matList.get(1), bitmapG);
            Utils.matToBitmap(matList.get(2), bitmapR);

            binding.imageChannelB.setImageBitmap(bitmapB);
            binding.imageChannelG.setImageBitmap(bitmapG);
            binding.imageChannelR.setImageBitmap(bitmapR);

            mat.release();
        });
    }
}
package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity17MedianBinding;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Activity17Median extends AppCompatActivity {

    private Activity17MedianBinding medianBinding;
    private Mat mat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        medianBinding = Activity17MedianBinding.inflate(getLayoutInflater());
        setContentView(medianBinding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.blue));
        }

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.jiaoyan);
        medianBinding.imageView17.setImageBitmap(bitmap1);

        mat = new Mat();
        Utils.bitmapToMat(bitmap1, mat);
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGRA2GRAY);

        medianBinding.button17Median.setOnClickListener(v -> {
            Imgproc.medianBlur(mat, mat, 17);//必须设置ksize为奇数，否则会出现crash

            Bitmap bitmap2 = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat,bitmap2);

            medianBinding.imageView17Median.setImageBitmap(bitmap2);
        });
    }
}
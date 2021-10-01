package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity16BlurBinding;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class Activity16Blur extends AppCompatActivity {

    private Activity16BlurBinding blurBinding;
    private Mat mat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        blurBinding = Activity16BlurBinding.inflate(getLayoutInflater());
        setContentView(blurBinding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.purple_200));
        }

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.microsoft);
        blurBinding.imageView16.setImageBitmap(bitmap1);

        mat = new Mat();
        Utils.bitmapToMat(bitmap1, mat);

        blurBinding.button16Blur.setOnClickListener(v -> {
            Imgproc.blur(mat, mat, new Size(90, 90), new Point(-1, -1), Core.BORDER_DEFAULT);

            Bitmap bitmap2 = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat, bitmap2);
            blurBinding.image16Blur.setImageBitmap(bitmap2);
        });
    }
}
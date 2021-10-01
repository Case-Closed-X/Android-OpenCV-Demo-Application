package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity22HoughCircleBinding;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class Activity22HoughCircle extends AppCompatActivity {

    Activity22HoughCircleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = Activity22HoughCircleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CommonMethod.setStatusBarLight(getWindow().getDecorView(), true);//设置状态栏颜色为灰色

        CommonMethod.setTextViewStyles(binding.textView22);//设置文字渐变
        CommonMethod.setTextViewStyles(binding.button22HoughCircle);//Button文字也可以渐变

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.coin);
        Mat mat = new Mat();
        Utils.bitmapToMat(bitmap, mat);

        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGRA2GRAY);
        binding.CardImage22.setImageBitmap(bitmap);

        binding.button22HoughCircle.setOnClickListener(v -> {//记得要用Service重写
            Imgproc.GaussianBlur(mat, mat, new Size(3, 3), 0);

            Mat circles = new Mat();
            Imgproc.HoughCircles(mat, circles, Imgproc.HOUGH_GRADIENT, 1, 200,
                    150, 30, 950, 1200);

            for (int i = 0; i < circles.cols(); i++) {
                float[] circle = new float[3];
                circles.get(0, i, circle);
                Imgproc.circle(mat, new Point((int) circle[0], (int) circle[1]), (int) circle[2],
                        new Scalar(255, 0, 0), 10, 8, 0);
            }
            Bitmap bitmapHough = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat, bitmapHough);
            binding.CardImage22HoughCircle.setImageBitmap(bitmapHough);
        });
    }
}
package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity5DrawBinding;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class Activity5Draw extends AppCompatActivity {

    private Activity5DrawBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBinding = Activity5DrawBinding.inflate(getLayoutInflater());

        setContentView(viewBinding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#ffaa66cc"));
        }

        viewBinding.draw.setOnClickListener(v -> {
            Mat mat = new Mat(1000, 1000, CvType.CV_8UC3, new Scalar(255, 255, 255));
            //mat.setTo(new Scalar(255,255,255));
            Imgproc.ellipse(mat, new Point(500, 600), new Size(150, 100),
                    360, 0, 360, new Scalar(255, 255, 0), 10);

            Imgproc.putText(mat, "OpenCV", new Point(40, 300), Imgproc.FONT_HERSHEY_SIMPLEX,
                    8.0, new Scalar(255, 0, 0), 10);

            Rect rect = new Rect();
            rect.x = 100;
            rect.y = 500;
            rect.width = 200;
            rect.height = 200;
            Imgproc.rectangle(mat, rect.tl(), rect.br(), new Scalar(0, 0, 255), 10);

            Imgproc.circle(mat, new Point(800, 600), 100, new Scalar(0, 255, 255), 10);

            Imgproc.line(mat, new Point(0, 400), new Point(999, 400), new Scalar(0, 0, 0), 10);

            Bitmap bitmap = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat, bitmap);

            viewBinding.imageDraw.setImageBitmap(bitmap);

            mat.release();
        });
    }
}
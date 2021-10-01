package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity24MatchingBinding;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Activity24Matching extends AppCompatActivity {

    private Activity24MatchingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = Activity24MatchingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.orange));
        }

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.lena1);
        binding.image241.setImageBitmap(bitmap1);
        Mat mat1 = new Mat();
        Utils.bitmapToMat(bitmap1, mat1);
        //Imgproc.cvtColor(mat1, mat1, Imgproc.COLOR_BGRA2GRAY);

        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.lena);
        binding.image242.setImageBitmap(bitmap2);
        Mat mat2 = new Mat();
        Utils.bitmapToMat(bitmap2, mat2);
        //Imgproc.cvtColor(mat2, mat2, Imgproc.COLOR_BGRA2GRAY);

        binding.button24Matching.setOnClickListener(v -> {
            int height = mat2.height() - mat1.height();
            int width = mat2.width() - mat1.width();
            Mat result = new Mat(height, width, CvType.CV_32FC1);

            //模板匹配
            Imgproc.matchTemplate(mat2, mat1, result, Imgproc.TM_CCOEFF_NORMED);
            Core.MinMaxLocResult minMaxLocResult = Core.minMaxLoc(result);
            Point maxloc = minMaxLocResult.maxLoc;

            Imgproc.rectangle(mat2, maxloc, new Point(maxloc.x + mat1.cols(), maxloc.y + mat1.rows())
                    , new Scalar(255, 255, 255), 10, 8, 0);

            Bitmap bitmap = Bitmap.createBitmap(mat2.width(), mat2.height(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat2, bitmap);
            binding.image24Matching.setImageBitmap(bitmap);
        });
    }
}
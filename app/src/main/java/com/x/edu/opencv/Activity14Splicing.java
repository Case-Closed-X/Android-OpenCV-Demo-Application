package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity14SplicingBinding;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;

public class Activity14Splicing extends AppCompatActivity {

    Activity14SplicingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = Activity14SplicingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.yellow));
        }

        Mat mat1 = new Mat();
        Mat mat2 = new Mat();
        Mat mat = new Mat();
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.x);
        binding.image141.setImageBitmap(bitmap1);
        Utils.bitmapToMat(bitmap1, mat1);

        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.s);
        binding.image142.setImageBitmap(bitmap2);
        Utils.bitmapToMat(bitmap2, mat2);

        //otherWays();//拼接的另一种实现方法
        binding.splicing.setOnClickListener(v -> {
            List<Mat> matList = new ArrayList<>();
            matList.add(mat1);
            matList.add(mat2);
            Core.hconcat(matList, mat);//拼接

            Bitmap bitmap = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat, bitmap);

            binding.imageSplicing.setImageBitmap(bitmap);

            /*mat1.release();
            mat2.release();
            mat.release();
            //lambda外定义的变量不应在里面释放，否则二次点击会造成crash
            //千万不要bitmap.recycle()，否则会造成crash
            */
        });
    }

    /*private void otherWays() {
        Mat mat1 = Mat.zeros(400, 400, CvType.CV_8UC1);

        byte[] data = new byte[400 * 400];

        mat1.get(0, 0, data);

        for (int row = 100; row < 300; row++) {
            for (int col = 100; col < 300; col++) {
                int index = row * 400 + col;
                data[index] = (byte) 255;
            }
        }

        mat1.put(0, 0, data);

        Bitmap bitmap1 = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat1, bitmap1);

        binding.image141.setImageBitmap(bitmap1);

        Mat mat2 = Mat.zeros(400, 400, CvType.CV_8UC1);

        byte[] data2 = new byte[400 * 400];

        mat2.get(0, 0, data2);

        for (int row = 150; row < 250; row++) {
            for (int col = 150; col < 250; col++) {
                int index = row * 400 + col;
                data2[index] = (byte) 255;
            }
        }

        mat2.put(0, 0, data2);

        Bitmap bitmap2 = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat2, bitmap2);

        binding.image142.setImageBitmap(bitmap2);

        binding.splicing.setOnClickListener(v -> {
            //拼接的另一种实现
            Mat mat = Mat.zeros(400, 800, CvType.CV_8UC1);

            Rect rect = new Rect();
            rect.x = 0;
            rect.y = 0;
            rect.height = 400;
            rect.width = 400;
            mat1.copyTo(mat.submat(rect));

            rect.x = 400;
            mat2.copyTo(mat.submat(rect));

            Bitmap bitmap = Bitmap.createBitmap(800, 400, Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat, bitmap);
            binding.imageSplicing.setImageBitmap(bitmap);
        });
    }*/
}
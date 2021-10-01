package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity13LogicBinding;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class Activity13Logic extends AppCompatActivity {

    private Mat mat1;
    private Mat mat2;
    Activity13LogicBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = Activity13LogicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.orange));
        }

        initImage1();
        initImage2();

        binding.logic.setOnClickListener(v -> {
            Core.bitwise_not(mat2, mat2);//取反
            Core.bitwise_and(mat1, mat2, mat1);//按位与

            Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat1, bitmap);

            binding.imageLogic.setImageBitmap(bitmap);
        });

        /*binding.logic.setOnClickListener(v -> {
            Bitmap bitmap1= BitmapFactory.decodeResource(getResources(),R.drawable.x2);
            Bitmap bitmap2= BitmapFactory.decodeResource(getResources(),R.drawable.s2);
            Mat mat1=new Mat();
            Mat mat2=new Mat();
            Utils.bitmapToMat(bitmap1,mat1);
            Utils.bitmapToMat(bitmap2,mat2);
            Mat mat=new Mat();

            //Core.bitwise_not(mat2,mat2);//按位取反
            Core.bitwise_and(mat1, mat2, mat);

            Bitmap bitmap = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat, bitmap);

            binding.imageLogic.setImageBitmap(bitmap);

            mat1.release();
            mat2.release();
            mat.release();
        });*/
    }

    private void initImage1() {
        mat1 = Mat.zeros(400, 400, CvType.CV_8UC1);

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

        binding.image131.setImageBitmap(bitmap1);
    }

    private void initImage2() {
        mat2 = Mat.zeros(400, 400, CvType.CV_8UC1);

        byte[] data = new byte[400 * 400];
        mat2.get(0, 0, data);

        for (int row = 150; row < 250; row++) {
            for (int col = 150; col < 250; col++) {
                int index = row * 400 + col;
                data[index] = (byte) 255;
            }
        }

        mat2.put(0, 0, data);

        Bitmap bitmap2 = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat2, bitmap2);

        binding.image132.setImageBitmap(bitmap2);
    }
}
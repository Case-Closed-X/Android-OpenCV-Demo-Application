package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity8Read3Binding;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Activity8Read3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity8Read3Binding binding = Activity8Read3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.teal_700));
        }

        //Bitmap内存申请过大导致崩溃
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.bg2, options);
        int w = options.outWidth;
        int h = options.outHeight;
        int inSample = 1;
        if (w > 1000 || h > 1000) {
            while (Math.max(w / inSample, h / inSample) > 1000) {
                inSample *= 2;
            }
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSample;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg2, options);
        binding.image8.setImageBitmap(bitmap);

        binding.read3.setOnClickListener(v -> {
            Mat mat = new Mat();
            Utils.bitmapToMat(bitmap, mat);
            Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGRA2BGR);

            int channels = mat.channels();
            int width = mat.width();
            int height = mat.height();

            byte[] bytes = new byte[channels * width * height];

            int pv = 0;//pixel value

            mat.get(0, 0, bytes);//一次性读取所有数据

            for (int i = 0; i < bytes.length; i++) {
                pv = bytes[i] & 0xff;
                pv = 255 - pv;

                bytes[i] = (byte) pv;
            }
            mat.put(0, 0, bytes);

            Bitmap bitmapRead3 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat, bitmapRead3);
            binding.imageRead3.setImageBitmap(bitmapRead3);

            mat.release();
        });
    }
}
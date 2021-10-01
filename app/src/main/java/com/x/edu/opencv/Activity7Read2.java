package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity7Read2Binding;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Activity7Read2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity7Read2Binding viewBinding = Activity7Read2Binding.inflate(getLayoutInflater());

        setContentView(viewBinding.getRoot());

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
        viewBinding.image7.setImageBitmap(bitmap);

        viewBinding.read2.setOnClickListener(v -> {

            viewBinding.progressBar2.setVisibility(View.VISIBLE);

            Handler mHandler = new Handler();//创建Handler，接收子线程数据更新UI
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //耗时操作
                    Mat mat = new Mat();
                    Utils.bitmapToMat(bitmap, mat);

                    Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGRA2BGR);

                    int channels = mat.channels();
                    int width = mat.width();
                    int height = mat.height();

                    byte[] data = new byte[channels * width];//BGR BGR BGR...

                    int pv = 0;

                    for (int row = 0; row < height; row++) {
                        mat.get(row, 0, data);

                        for (int col = 0; col < data.length; col++) {
                            pv = data[col] & 0xff;

                            pv = 255 - pv;

                            data[col] = (byte) pv;
                        }
                        mat.put(row, 0, data);
                    }
                    Bitmap bitmapRead2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(mat, bitmapRead2);

                    mat.release();

                    //放在主线程或新开线程都可以
                    Runnable mRunnable0 = new Runnable() {
                        @Override
                        public void run() {
                            //更新UI
                            viewBinding.progressBar2.setVisibility(View.GONE);

                            viewBinding.imageRead2.setImageBitmap(bitmapRead2);
                        }
                    };

                    //在线程中
                    mHandler.post(mRunnable0);
                }
            }).start();
        });
    }
}
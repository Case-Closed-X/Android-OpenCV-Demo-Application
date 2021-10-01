package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity6Read1Binding;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Activity6Read1 extends AppCompatActivity {

    Activity6Read1Binding viewBinding;

    private ExecutorService executorService;//java线程池（AsyncTask已于Android 11废弃）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBinding = Activity6Read1Binding.inflate(getLayoutInflater());

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
        viewBinding.image6.setImageBitmap(bitmap);

        viewBinding.read1.setOnClickListener(v -> {

            viewBinding.progressBar.setVisibility(View.VISIBLE);

            executorService = Executors.newFixedThreadPool(1);
            doAsyncCode(bitmap);

            /*new Thread(new Runnable(){
                @Override
                public void run() {

                }
            }).start();*/


        });
    }

    private void doAsyncCode(Bitmap bitmap) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                //执行耗时操作代码
                Mat mat = new Mat();
                Utils.bitmapToMat(bitmap, mat);

                int channels = mat.channels();//通道

                byte[] data = new byte[channels];

                int b = 0, g = 0, r = 0;

                for (int row = 0; row < mat.height(); row++) {
                    for (int column = 0; column < mat.width(); column++) {
                        mat.get(row, column, data);

                        b = data[0] & 0xff;//二进制高位清零，低位保留
                        g = data[1] & 0xff;
                        r = data[2] & 0xff;

                        b = 255 - b;
                        g = 255 - g;
                        r = 255 - r;

                        data[0] = (byte) b;
                        data[1] = (byte) g;
                        data[2] = (byte) r;

                        mat.put(row, column, data);
                    }
                }
                Bitmap bitmapRead1 = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(mat, bitmapRead1);


                mat.release();

                doOnUiCode(bitmapRead1);
            }
        });
    }

    private void doOnUiCode(Bitmap bitmapRead1) {
        Handler uiThread = new Handler(Looper.getMainLooper());
        uiThread.post(new Runnable() {
            @Override
            public void run() {
                //Android子线程无法更新UI，故在此更新UI
                viewBinding.progressBar.setVisibility(View.GONE);

                viewBinding.imageRead1.setImageBitmap(bitmapRead1);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        //如果在子线程尚在运行时销毁此Activity，待线程池处理完仍会执行shutdownNow()，最终导致crash，故此注释掉这两行代码
        /*if (!executorService.isShutdown()) {
            executorService.shutdownNow();
        }*/
    }

}
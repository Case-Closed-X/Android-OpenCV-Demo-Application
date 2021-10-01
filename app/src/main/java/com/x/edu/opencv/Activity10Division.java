package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity10DivisionBinding;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.imgproc.Imgproc;

public class Activity10Division extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity10DivisionBinding binding = Activity10DivisionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.purple_500));
        }

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg1);
        Mat mat = new Mat();
        Utils.bitmapToMat(bitmap, mat);
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGRA2GRAY);

        int width = mat.width();
        int height = mat.height();

        Bitmap bitmapGray = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat, bitmapGray);

        binding.image10.setImageBitmap(bitmapGray);

        Bitmap bitmapDivision = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        //利用均值分割图像
        binding.division.setOnClickListener(v -> {
            MatOfDouble means = new MatOfDouble();//均值
            MatOfDouble stddev = new MatOfDouble();//标准差

            Core.meanStdDev(mat, means, stddev);//写入均值和标准差

            double[] mean = means.toArray();

            byte[] data = new byte[width * height];//缓冲区，通道为1不用乘

            mat.get(0, 0, data);

            int pv = 0;
            int meanValue = (int) mean[0];//均值

            for (int i = 0; i < data.length; i++) {
                pv = data[i] & 0xff;

                data[i] = (byte) (pv > meanValue ? 255 : 0);//255白色，0黑色
            }

            mat.put(0, 0, data);

            Utils.matToBitmap(mat, bitmapDivision);
            binding.imageDivision.setImageBitmap(bitmapDivision);
        });
        //mat.release();//mat即使在此处释放，也会影响到监听器，即会造成二次点击crash
    }
}
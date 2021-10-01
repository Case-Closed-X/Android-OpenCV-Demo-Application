package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity15NormalizationBinding;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Activity15Normalization extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity15NormalizationBinding binding = Activity15NormalizationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.design_default_color_error));
        }

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg1);

        Mat mat = new Mat();
        Utils.bitmapToMat(bitmap, mat);
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGRA2GRAY);//灰度化

        Core.add(mat, new Scalar(-50, -50, -50), mat);//亮度

        Core.multiply(mat, new Scalar(0.5, 0.5, 0.5), mat);//对比度

        Utils.matToBitmap(mat, bitmap);
        binding.image15.setImageBitmap(bitmap);

        binding.normalization.setOnClickListener(v -> {
            //图像归一化，参数：输入、输出、归一化最小值、归一化最大值、归一化类型、种类（负数时输入输出种类相同，否则只是通道相同）、遮罩范围（处理范围）
            Core.normalize(mat, mat, 0, 255, Core.NORM_MINMAX, -1, new Mat());

            Bitmap bitmap2 = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat, bitmap2);
            binding.imageNormalization.setImageBitmap(bitmap2);
        });
    }
}
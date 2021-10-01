package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity20ThresholdingBinding;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Activity20Thresholding extends AppCompatActivity {

    Activity20ThresholdingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=Activity20ThresholdingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CommonMethod.setStatusBarLight(getWindow().getDecorView(), true);//设置状态栏颜色为灰色

        CommonMethod.setTextViewStyles(binding.textView20);//设置文字渐变
        CommonMethod.setTextViewStyles(binding.button20Thresholding);//Button文字也可以渐变

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bg1);
        Mat mat = new Mat();
        Utils.bitmapToMat(bitmap,mat);
        Imgproc.cvtColor(mat,mat,Imgproc.COLOR_BGR2GRAY);
        Utils.matToBitmap(mat,bitmap);
        binding.CardImage20.setImageBitmap(bitmap);

        binding.button20Thresholding.setOnClickListener(v -> {
            Imgproc.threshold(mat,mat,127,255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
            //Imgproc.threshold(mat,mat,127,255, Imgproc.THRESH_BINARY | Imgproc.THRESH_TRIANGLE);
            Bitmap bitmapThresholding = Bitmap.createBitmap(mat.width() , mat.height() , Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat,bitmapThresholding);
            binding.CardImage20Thresholding.setImageBitmap(bitmapThresholding);
        });
    }
}
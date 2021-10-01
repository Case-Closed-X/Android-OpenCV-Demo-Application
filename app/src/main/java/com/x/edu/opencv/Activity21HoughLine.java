package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity21HoughLineBinding;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class Activity21HoughLine extends AppCompatActivity {

    Activity21HoughLineBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=Activity21HoughLineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CommonMethod.setStatusBarLight(getWindow().getDecorView(), true);//设置状态栏颜色为灰色

        CommonMethod.setTextViewStyles(binding.textView21);//设置文字渐变
        CommonMethod.setTextViewStyles(binding.button21HoughLine);//Button文字也可以渐变

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bg3);
        Mat mat = new Mat();
        Utils.bitmapToMat(bitmap,mat);
        Imgproc.cvtColor(mat,mat,Imgproc.COLOR_BGR2GRAY);
        //Utils.matToBitmap(mat,bitmap);
        binding.CardImage21.setImageBitmap(bitmap);

        binding.button21HoughLine.setOnClickListener(v -> {
            Imgproc.GaussianBlur(mat,mat,new Size(3,3),0);
            Imgproc.Canny(mat , mat,50,150,3,true);
            Mat lines = new Mat ();
            Imgproc.HoughLinesP(mat,lines,1,Math.PI/180.0,200,10,10);
            for (int i = 0 ; i< lines.rows(); i++){
                int[] line = new int[4];
                lines.get(i,0,line);
                Imgproc.line(mat,new Point(line[0] ,line[1]), new Point(line[2] , line[3]),
                        new Scalar(255,0,0),10,8,0);

            }
            Bitmap bitmapHoughLine = Bitmap.createBitmap(mat.width(),mat.height(),Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat,bitmapHoughLine);
            binding.CardImage21HoughLine.setImageBitmap(bitmapHoughLine);
        });
    }
}
package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity26TomasiBinding;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Activity26Tomasi extends AppCompatActivity {

    private Activity26TomasiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=Activity26TomasiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CommonMethod.setStatusBarLight(getWindow().getDecorView(), true);//设置状态栏颜色为灰色

        CommonMethod.setTextViewStyles(binding.textView26);//设置文字渐变
        CommonMethod.setTextViewStyles(binding.button26Tomasi);//Button文字也可以渐变

        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.jiaodian);
        binding.CardImage26.setImageBitmap(bitmap);
        Mat mat=new Mat();
        Utils.bitmapToMat(bitmap,mat);

        binding.button26Tomasi.setOnClickListener(v -> {
            Mat gray=new Mat(mat.rows(),mat.cols(), CvType.CV_8SC1);

            Imgproc.cvtColor(mat,gray,Imgproc.COLOR_BGRA2GRAY);

            MatOfPoint corners=new MatOfPoint();
            Imgproc.goodFeaturesToTrack(gray,corners,63,.01,20);

            Imgproc.cvtColor(mat,mat,Imgproc.COLOR_BGRA2BGR);

            Point[] point=corners.toArray();
            for (int i=0;i<point.length;i++)
            {
                Imgproc.circle(mat,point[i],30,new Scalar(255,0,0),
                        10,8,0);

            }
            Bitmap bitmapTomasi=Bitmap.createBitmap(mat.width(),mat.height(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat,bitmapTomasi);
            binding.CardImage26Tomasi.setImageBitmap(bitmapTomasi);
        });
    }
}
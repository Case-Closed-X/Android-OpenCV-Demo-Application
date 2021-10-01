package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity23HistogramBinding;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class Activity23Histogram extends AppCompatActivity {

    private Activity23HistogramBinding binding;

    private Mat mat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=Activity23HistogramBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.purple_500));
        }

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.round);
        mat = new Mat();
        Utils.bitmapToMat(bitmap,mat);
        Imgproc.cvtColor(mat,mat,Imgproc.COLOR_BGRA2GRAY);

        binding.image23.setImageBitmap(bitmap);

        Histogram();

        binding.button23Equalization.setOnClickListener(v -> Equalization());
    }

    private void Equalization() {
        Imgproc.equalizeHist(mat,mat);

        //实现直方图均衡化之后图像的显示
        Bitmap bitmap=Bitmap.createBitmap(mat.width(),mat.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat,bitmap);
        binding.image23Equalization.setImageBitmap(bitmap);

        //实现直方图的显示
        List<Mat> images = new ArrayList<>();
        images.add(mat);
        Mat mask = Mat.ones(mat.size(), CvType.CV_8UC1);
        Mat hist=new Mat();

        Imgproc.calcHist(images,new MatOfInt(0),mask,hist,new MatOfInt(256),new MatOfFloat(0,255));

        Core.normalize(hist,hist,0,255,Core.NORM_MINMAX);
        int height=hist.rows();

        Mat dst=new Mat();
        dst.create(400,400,mat.type());
        dst.setTo(new Scalar(200,200,200));
        float[] histdata=new float[256];

        hist.get(0,0,histdata);
        int offsetx=50;
        int offsety=350;

        Imgproc.line(dst,new Point(offsetx,0),new Point(offsetx,offsety),new Scalar(0,0,0));
        Imgproc.line(dst,new Point(offsetx,offsety),new Point(400,offsety),new Scalar(0,0,0));
        for(int i=0;i<height-1;i++){
            int y1= (int) histdata[i];

            Rect rect = new Rect();
            rect.x=offsetx+i;
            rect.y=offsety-y1;
            rect.width=1;
            rect.height=y1;

            Imgproc.rectangle(dst,rect.tl(),rect.br(),new Scalar(15,15,15));
        }

        Bitmap bitmapEqualizationHistogram=Bitmap.createBitmap(dst.width(),dst.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(dst,bitmapEqualizationHistogram);
        binding.image23EqualizationHistogram.setImageBitmap(bitmapEqualizationHistogram);
    }

    private void Histogram() {
        List<Mat> images = new ArrayList<>();
        images.add(mat);
        Mat mask = Mat.ones(mat.size(), CvType.CV_8UC1);
        Mat hist=new Mat();

        Imgproc.calcHist(images,new MatOfInt(0),mask,hist,new MatOfInt(256),new MatOfFloat(0,255));

        Core.normalize(hist,hist,0,255,Core.NORM_MINMAX);
        int height=hist.rows();

        Mat dst=new Mat();
        dst.create(400,400,mat.type());
        dst.setTo(new Scalar(200,200,200));
        float[] histdata=new float[256];

        hist.get(0,0,histdata);
        int offsetx=50;
        int offsety=350;

        Imgproc.line(dst,new Point(offsetx,0),new Point(offsetx,offsety),new Scalar(0,0,0));
        Imgproc.line(dst,new Point(offsetx,offsety),new Point(400,offsety),new Scalar(0,0,0));
        for(int i=0;i<height-1;i++){
            int y1= (int) histdata[i];

            Rect rect = new Rect();
            rect.x=offsetx+i;
            rect.y=offsety-y1;
            rect.width=1;
            rect.height=y1;

            Imgproc.rectangle(dst,rect.tl(),rect.br(),new Scalar(15,15,15));
        }

        Bitmap bitmap=Bitmap.createBitmap(dst.width(),dst.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(dst,bitmap);
        binding.image23Histogram.setImageBitmap(bitmap);
    }
}
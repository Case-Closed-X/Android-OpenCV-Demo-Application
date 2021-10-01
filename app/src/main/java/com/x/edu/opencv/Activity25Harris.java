package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity25HarrisBinding;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Activity25Harris extends AppCompatActivity {

    private Activity25HarrisBinding binding;
    private final float threshold=200.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=Activity25HarrisBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CommonMethod.setStatusBarLight(getWindow().getDecorView(), true);//设置状态栏颜色为灰色

        CommonMethod.setTextViewStyles(binding.textView25);//设置文字渐变
        CommonMethod.setTextViewStyles(binding.button25Harris);//Button文字也可以渐变

        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.jiaodian);
        binding.CardImage25.setImageBitmap(bitmap);
        Mat mat=new Mat();
        Utils.bitmapToMat(bitmap,mat);

        binding.button25Harris.setOnClickListener(v -> {
            Mat gray=new Mat(mat.rows(),mat.cols(), CvType.CV_8SC1);
            Mat response=new Mat();
            Mat response_norm=new Mat();

            Imgproc.cvtColor(mat,gray,Imgproc.COLOR_BGRA2GRAY);

            Imgproc.cornerHarris(gray,response,2,3,0.04);
            Core.normalize(response,response_norm,0,255,Core.NORM_MINMAX, CvType.CV_32F);

            Imgproc.cvtColor(mat,mat,Imgproc.COLOR_BGRA2BGR);

            float[] data=new float[1];
            for (int row=0;row<response_norm.rows();row++)
            {
                for (int col=0;col<response_norm.cols();col++)
                {
                    response_norm.get(row,col,data);

                    if(data[0]>threshold){
                        Imgproc.circle(mat,new Point(col,row),30,new Scalar(255,0,0),
                                10,8,0);
                    }
                }
            }
            Bitmap bitmapHarris=Bitmap.createBitmap(mat.width(),mat.height(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat,bitmapHarris);
            binding.CardImage25Harris.setImageBitmap(bitmapHarris);
        });
    }
}

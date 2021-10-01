package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity27sifiBinding;

import org.opencv.android.Utils;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.SIFT;

import java.util.ArrayList;

public class Activity27SIFI extends AppCompatActivity {

    private Activity27sifiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=Activity27sifiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getColor(R.color.purple_700));
        }

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.lena1);
        Mat mat1 = new Mat();
        Utils.bitmapToMat(bitmap1,mat1);
        //Imgproc.cvtColor(mat1,mat1, Imgproc.COLOR_BGRA2GRAY);
        binding.image271.setImageBitmap(bitmap1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.lena);
        Mat mat2 = new Mat();
        Utils.bitmapToMat(bitmap2,mat2);
        //Imgproc.cvtColor(mat2,mat2,Imgproc.COLOR_BGRA2GRAY);
        binding.image272.setImageBitmap(bitmap2);

        binding.button27SIFI.setOnClickListener(v -> {
            //SIFT特征检测算法的逻辑代码
            SIFT sift_detector = SIFT.create();
            //检测关键点
            MatOfKeyPoint keyPoint1 = new MatOfKeyPoint();
            MatOfKeyPoint keyPoint2 = new MatOfKeyPoint();
            sift_detector.detect(mat1,keyPoint1);
            sift_detector.detect(mat2,keyPoint2);
            //获取描述子
            Mat descriptor1 = new Mat();
            Mat descriptor2 = new Mat();
            sift_detector.compute(mat1,keyPoint1,descriptor1);
            sift_detector.compute(mat2,keyPoint2,descriptor2);
            //寻找匹配点
            MatOfDMatch matches = new MatOfDMatch();
            DescriptorMatcher descriptorMatcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_SL2);
            descriptorMatcher.match(descriptor1,descriptor2,matches);
            //通过阈值发确定好的关键点
            DMatch[] dm_arrays = matches.toArray();
            ArrayList<DMatch> goodMathes = new ArrayList<>();
            double threshold = 10000;//此处调整阈值，可以匹配得到不同的匹配点
            for(int i = 0;i < dm_arrays.length;i++){
                if(dm_arrays[i].distance <= threshold){
                    goodMathes.add(dm_arrays[i]);
                }
            }
            Mat dst = new Mat();
            //Features2d.drawMatches(mat1,keyPoint1,mat2,keyPoint2,new MatOfDMatch(goodMathes.toArray(new DMatch[0])),dst,Scalar.all(-1),Scalar.all(-1),new MatOfByte(),Features2d.DrawMatchesFlags_NOT_DRAW_SINGLE_POINTS);
            Features2d.drawMatches(mat1,keyPoint1,mat2,keyPoint2,new MatOfDMatch(goodMathes.toArray(new DMatch[0])),dst);
            Bitmap bitmap = Bitmap.createBitmap(dst.width(),dst.height(),Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(dst,bitmap);
            binding.image27SIFI.setImageBitmap(bitmap);
        });
    }
}
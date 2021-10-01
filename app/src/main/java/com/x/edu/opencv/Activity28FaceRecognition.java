package com.x.edu.opencv;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity28FaceRecognitionBinding;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Activity28FaceRecognition extends AppCompatActivity {

    private Activity28FaceRecognitionBinding binding;
    private CascadeClassifier faceDectector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=Activity28FaceRecognitionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CommonMethod.setStatusBarLight(getWindow().getDecorView(), true);//设置状态栏颜色为灰色

        CommonMethod.setTextViewStyles(binding.textView28);//设置文字渐变
        CommonMethod.setTextViewStyles(binding.button28FaceRecognition);//Button文字也可以渐变

        initFaceRecognition();

        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.lena);
        binding.CardImage28.setImageBitmap(bitmap);
        Mat src=new Mat();
        Utils.bitmapToMat(bitmap,src);
        //Imgproc.cvtColor(src,src,Imgproc.COLOR_BGRA2GRAY);

        binding.button28FaceRecognition.setOnClickListener(v -> {
            //人脸检测的逻辑代码
            MatOfRect faces = new MatOfRect();
            faceDectector.detectMultiScale(src,faces);
            //回执Box
            List<Rect> faceList = faces.toList();
            if(faceList.size() > 0){
                for(Rect rect : faceList){
                    Imgproc.rectangle(src,rect.tl(),rect.br(),new Scalar(255,255,255),10,8,0);
                }
            }
            Bitmap bitmapFaceRecognition = Bitmap.createBitmap(src.width(), src.height(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(src,bitmapFaceRecognition);
            binding.CardImage28FaceRecognition.setImageBitmap(bitmapFaceRecognition);
        });
    }

    private void initFaceRecognition() {
        InputStream inputStream=getResources().openRawResource(R.raw.lbpcascade_frontalface_improved);

        File cascaseDir=this.getDir("cascade", Context.MODE_PRIVATE);
        File file=new File(cascaseDir.getAbsolutePath(),"lbpcascade_frontalface_improved.xml");

        FileOutputStream fileOutputStream;

        try {
            fileOutputStream=new FileOutputStream(file);
            byte[] buff=new byte[1024];

            int len=0;
            while ((len=inputStream.read(buff))!=-1){
                fileOutputStream.write(buff,0,len);
            }
            inputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        faceDectector = new CascadeClassifier(file.getAbsolutePath());
        file.delete();
        cascaseDir.delete();
    }
}
package com.x.edu.opencv;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.ExamActivity4DivisionBinding;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class ExamActivity4Division extends AppCompatActivity {

    ExamActivity4DivisionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ExamActivity4DivisionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        //放置关键Code处
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.table);
        ImageView imageView=findViewById(R.id.imageView4Division);
        //imageView.setImageResource(R.drawable.table);

        Mat mat=new Mat();
        Utils.bitmapToMat(bitmap,mat);

        Imgproc.cvtColor(mat,mat, Imgproc.COLOR_BGRA2BGR);
        //Imgproc.cvtColor(mat,mat, Imgproc.COLOR_BGR2HSV);

        Mat mat2=new Mat();
        Core.inRange(mat,new Scalar(90,0,0),new Scalar(255, 50, 50),mat2);
        Imgproc.cvtColor(mat2,mat2, Imgproc.COLOR_BGRA2BGR);

        Core.bitwise_and(mat, mat2,mat2);

        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,new Size(95,95),new Point(-1,-1));//宽高为奇数
        Imgproc.morphologyEx(mat2, mat2, Imgproc.MORPH_CLOSE, kernel);
        Bitmap bitmap1=Bitmap.createBitmap(mat2.width(),mat2.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat2,bitmap1);
        imageView.setImageBitmap(bitmap1);
        /*Imgproc.cvtColor(mat,mat,Imgproc.COLOR_BGR2GRAY);
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,new Size(15,15),new Point(-1,-1));//宽高为奇数


        Mat mat3 = new Mat();
        Utils.bitmapToMat(bitmap,mat3);
        Imgproc.cvtColor(mat3,mat3,Imgproc.COLOR_BGRA2BGR);

        int width=mat3.width();
        int height=mat3.height();
        Bitmap.Config config=Bitmap.Config.ARGB_8888;

        //Bitmap bitmapR = Bitmap.createBitmap(width,height,config);

        List<Mat> matList = new ArrayList<>();
        Core.split(mat3,matList);


        //色彩分割
        Mat mat1=new Mat();
        Imgproc.morphologyEx(matList.get(1),mat1,Imgproc.MORPH_DILATE,kernel);

        Bitmap bitmap1=Bitmap.createBitmap(mat.width(),mat.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat1,bitmap1);
        imageView.setImageBitmap(bitmap1);*/


        /*
        Mat mat3 = new Mat();
        Utils.bitmapToMat(bitmap,mat3);
        Imgproc.cvtColor(mat3,mat3,Imgproc.COLOR_BGRA2BGR);

        int width=mat3.width();
        int height=mat3.height();
        Bitmap.Config config=Bitmap.Config.ARGB_8888;

        Bitmap bitmapR = Bitmap.createBitmap(width,height,config);

        List<Mat> matList = new ArrayList<>();
        Core.split(mat3,matList);




        Mat mat=new Mat();
        Utils.bitmapToMat(bitmap,mat);

        Mat mat22=new Mat();

        Imgproc.threshold(matList.get(0),mat22,0,255,Imgproc.THRESH_OTSU);
        //Imgproc.adaptiveThreshold(matList.get(0),mat22,255,0,Imgproc.THRESH_BINARY_INV,7,8);


        Utils.matToBitmap(mat22,bitmap);
        imageView.setImageBitmap(bitmap);

        //Mat mat33=new Mat(mat.width(),mat.height(), CvType.CV_8UC1);
        // Imgproc.cvtColor(mat,mat33,Imgproc.COLOR_BGRA2GRAY);
        //Imgproc.adaptiveThreshold(mat33,mat33,255,0,Imgproc.THRESH_BINARY_INV,7,8);

        //Utils.matToBitmap(mat33,bitmap);
        //ImageView imageView2=findViewById(R.id.imageview1912);
        //imageView2.setImageBitmap(bitmap);
        //分离红通道
        Mat mat3 = new Mat();
        Utils.bitmapToMat(bitmap,mat3);
        Imgproc.cvtColor(mat3,mat3,Imgproc.COLOR_BGRA2BGR);

        int width=mat3.width();
        int height=mat3.height();
        Bitmap.Config config=Bitmap.Config.ARGB_8888;

        Bitmap bitmapR = Bitmap.createBitmap(width,height,config);

        List<Mat> matList = new ArrayList<>();
        Core.split(mat3,matList);

        //
        Mat mat=matList.get(0);
        //Imgproc.cvtColor(mat,mat,Imgproc.COLOR_BGRA2GRAY);
        Imgproc.blur(mat, mat, new Size(25,25), new Point(-1,-1), Core.BORDER_DEFAULT);


        Mat kernel=Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE,new Size(15,15),new Point(-1,-1));;
        Mat mat1=new Mat();
        Imgproc.morphologyEx(mat,mat1,Imgproc.MORPH_GRADIENT,kernel);

        Bitmap bitmap1=Bitmap.createBitmap(mat.width(),mat.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat1,bitmap1);
        imageView.setImageBitmap(bitmap1);


        Mat mat2=new Mat();
        Imgproc.morphologyEx(mat1,mat2,Imgproc.MORPH_DILATE,kernel);

        Bitmap bitmap2=Bitmap.createBitmap(mat.width(),mat.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat2,bitmap2);
        ImageView imageView2=findViewById(R.id.imageview1912);
        imageView2.setImageBitmap(bitmap2);*/


        //膨胀
        /*Mat kernel=Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE,new Size(15,15),new Point(-1,-1));;
        Mat mat1=new Mat();
        Imgproc.morphologyEx(matList.get(0),mat1,Imgproc.MORPH_DILATE,kernel);

        Bitmap bitmap1=Bitmap.createBitmap(mat.width(),mat.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat1,bitmap1);

        imageView.setImageBitmap(bitmap1);



        Mat mat2=new Mat();
        Imgproc.morphologyEx(matList.get(1),mat2,Imgproc.MORPH_ERODE,kernel);

        Bitmap bitmap2=Bitmap.createBitmap(mat.width(),mat.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat2,bitmap2);
        ImageView imageView2=findViewById(R.id.imageview1912);
        imageView2.setImageBitmap(bitmap2);*/




        //imageviewLayout09R.setImageBitmap(bitmapR);


        //imageView.setImageBitmap(bitmap);
        //放置关键Code处

        code();
    }

    @SuppressLint("SetTextI18n")
    private void code() {
        binding.textView4Division.setText(
                getString(R.string.describe4) + "\n\n"+
                        "        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.table);\n" +
                        "        ImageView imageView=findViewById(R.id.imageView4Division);\n" +
                        "\n" +
                        "        Mat mat=new Mat();\n" +
                        "        Utils.bitmapToMat(bitmap,mat);\n" +
                        "\n" +
                        "        Imgproc.cvtColor(mat,mat, Imgproc.COLOR_BGRA2BGR);\n" +
                        "\n" +
                        "        Mat mat2=new Mat();\n" +
                        "        Core.inRange(mat,new Scalar(90,0,0),new Scalar(255, 50, 50),mat2);\n" +
                        "        Imgproc.cvtColor(mat2,mat2, Imgproc.COLOR_BGRA2BGR);\n" +
                        "\n" +
                        "        Core.bitwise_and(mat, mat2,mat2);\n" +
                        "\n" +
                        "        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,new Size(95,95),new Point(-1,-1));//宽高为奇数\n" +
                        "        Imgproc.morphologyEx(mat2, mat2, Imgproc.MORPH_CLOSE, kernel);\n" +
                        "        Bitmap bitmap1=Bitmap.createBitmap(mat2.width(),mat2.height(), Bitmap.Config.ARGB_8888);\n" +
                        "        Utils.matToBitmap(mat2,bitmap1);\n" +
                        "        imageView.setImageBitmap(bitmap1);"
                        );
        //binding.textView3Montage.setMovementMethod(ScrollingMovementMethod.getInstance());

        binding.division4.setOnClickListener(v -> {
            binding.textView4Division.setVisibility(View.VISIBLE);

        });
        binding.cancel.setOnClickListener(v -> {
            binding.textView4Division.setVisibility(View.GONE);
            ;
        });

    }

    private void init() {
        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= 23) {//View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR设置状态栏文字为灰色（默认为白色View.SYSTEM_UI_FLAG_LAYOUT_STABLE）
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setTextViewStyles(binding.textViewExam4);

        //binding.textViewDescribe3.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    private void setTextViewStyles(TextView textView) {
        LinearGradient mLinearGradient = new LinearGradient(0, 0, textView.getPaint().getTextSize() * textView.getText().length(), 0, Color.parseColor("#FF5722"), Color.parseColor("#FF6200EE"), Shader.TileMode.CLAMP);
        //LinearGradient mLinearGradient = new LinearGradient(0,0,textView.getPaint().getTextSize() * textView.getText().length(),0,new int[]{R.color.purple_500,R.color.purple_200,R.color.orange},null, Shader.TileMode.CLAMP);

        textView.getPaint().setShader(mLinearGradient);
        textView.invalidate();
    }
}
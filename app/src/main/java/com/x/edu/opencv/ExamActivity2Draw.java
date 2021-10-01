package com.x.edu.opencv;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.ExamActivity2DrawBinding;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class ExamActivity2Draw extends AppCompatActivity {

    ExamActivity2DrawBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ExamActivity2DrawBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        //放置关键Code处
        Mat mat = new Mat(500, 500, CvType.CV_8UC3, new Scalar(0, 0, 0));

        Rect rect = new Rect();
        rect.x = 150;
        rect.y = 150;
        rect.width = 200;
        rect.height = 200;
        Imgproc.rectangle(mat, rect.tl(), rect.br(), new Scalar(0, 255, 0), 100);

        Imgproc.line(mat, new Point(0, 0), new Point(500, 500), new Scalar(255, 0, 0));
        Imgproc.line(mat, new Point(500, 0), new Point(0, 500), new Scalar(255, 0, 0));

        Bitmap bitmap = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat, bitmap);

        ImageView imageView = findViewById(R.id.imageView2Draw);
        imageView.setImageBitmap(bitmap);

        mat.release();
        //放置关键Code处

        code();
    }

    @SuppressLint("SetTextI18n")
    private void code() {
        binding.textView2Draw.setText(
                getString(R.string.describe2) + "\n\n" +
                        "        Mat mat=new Mat(500,500, CvType.CV_8UC3,new Scalar(0,0,0));\n" +
                        "\n" +
                        "        Rect rect=new Rect();\n" +
                        "        rect.x=150;\n" +
                        "        rect.y=150;\n" +
                        "        rect.width=200;\n" +
                        "        rect.height=200;\n" +
                        "        Imgproc.rectangle(mat,rect.tl(),rect.br(),new Scalar(0,255,0),100);\n" +
                        "\n" +
                        "        Imgproc.line(mat,new Point(0,0),new Point(500,500),new Scalar(255,0,0));\n" +
                        "        Imgproc.line(mat,new Point(500,0),new Point(0,500),new Scalar(255,0,0));\n" +
                        "\n" +
                        "        Bitmap bitmap=Bitmap.createBitmap(mat.width(),mat.height(), Bitmap.Config.ARGB_8888);\n" +
                        "        Utils.matToBitmap(mat,bitmap);\n" +
                        "\n" +
                        "        ImageView imageView=findViewById(R.id.imageView2Draw);\n" +
                        "        imageView.setImageBitmap(bitmap);\n" +
                        "        mat.release();");
        //binding.textView2Draw.setMovementMethod(ScrollingMovementMethod.getInstance());

        binding.code2.setOnClickListener(v -> {
            binding.textView2Draw.setVisibility(View.VISIBLE);

        });
        binding.cancel.setOnClickListener(v -> {
            binding.textView2Draw.setVisibility(View.GONE);
            ;
        });

    }

    private void init() {
        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= 23) {//View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR设置状态栏文字为灰色（默认为白色View.SYSTEM_UI_FLAG_LAYOUT_STABLE）
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setTextViewStyles(binding.textViewExam2);

        //binding.textViewDescribe2.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    private void setTextViewStyles(TextView textView) {
        LinearGradient mLinearGradient = new LinearGradient(0, 0, textView.getPaint().getTextSize() * textView.getText().length(), 0, Color.parseColor("#FF5722"), Color.parseColor("#FF6200EE"), Shader.TileMode.CLAMP);
        //LinearGradient mLinearGradient = new LinearGradient(0,0,textView.getPaint().getTextSize() * textView.getText().length(),0,new int[]{R.color.purple_500,R.color.purple_200,R.color.orange},null, Shader.TileMode.CLAMP);

        textView.getPaint().setShader(mLinearGradient);
        textView.invalidate();
    }

}
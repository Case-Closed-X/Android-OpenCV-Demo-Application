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

import com.x.edu.opencv.databinding.ExamActivity3MontageBinding;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;

public class ExamActivity3Montage extends AppCompatActivity {

    ExamActivity3MontageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ExamActivity3MontageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        //放置关键Code处
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.x);
        Mat mat1 = new Mat();
        Utils.bitmapToMat(bitmap1, mat1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.s);
        Mat mat2 = new Mat();
        Utils.bitmapToMat(bitmap2, mat2);

        Mat mat = new Mat();
        Mat matCut1 = mat1.submat(0, mat1.height(), 0, mat1.width() / 2);
        Mat matCut2 = mat2.submat(0, mat2.height(), mat2.width() / 2, mat2.width());
        List<Mat> matList = new ArrayList<>();
        matList.add(matCut1);
        matList.add(matCut2);
        Core.hconcat(matList, mat);//拼接操作

        Bitmap bitmap = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat, bitmap);

        ImageView imageView = findViewById(R.id.imageView3Montage);
        imageView.setImageBitmap(bitmap);

        mat1.release();
        mat2.release();
        mat.release();
        matCut1.release();
        matCut2.release();
        //千万不要bitmap.recycle()，否则会造成crash
        //放置关键Code处

        code();
    }

    @SuppressLint("SetTextI18n")
    private void code() {
        binding.textView3Montage.setText(
                getString(R.string.describe3) + "\n\n" +
                        "        Bitmap bitmap1= BitmapFactory.decodeResource(getResources(),R.drawable.x);\n" +
                        "        Mat mat1=new Mat();\n" +
                        "        Utils.bitmapToMat(bitmap1,mat1);\n" +
                        "        Bitmap bitmap2= BitmapFactory.decodeResource(getResources(),R.drawable.s);\n" +
                        "        Mat mat2=new Mat();\n" +
                        "        Utils.bitmapToMat(bitmap2,mat2);\n" +
                        "\n" +
                        "        Mat mat=new Mat();\n" +
                        "        Mat matCut1=mat1.submat(0,mat1.height(),0,mat1.width()/2);\n" +
                        "        Mat matCut2=mat2.submat(0,mat2.height(),mat2.width()/2,mat2.width());\n" +
                        "        List<Mat> matList = new ArrayList<>();\n" +
                        "        matList.add(matCut1);\n" +
                        "        matList.add(matCut2);\n" +
                        "        Core.hconcat(matList,mat);//拼接操作\n" +
                        "\n" +
                        "        Bitmap bitmap=Bitmap.createBitmap(mat.width(),mat.height(), Bitmap.Config.ARGB_8888);\n" +
                        "        Utils.matToBitmap(mat,bitmap);\n" +
                        "\n" +
                        "        ImageView imageView=findViewById(R.id.imageView3Montage);\n" +
                        "        imageView.setImageBitmap(bitmap);\n" +
                        "\n" +
                        "        mat1.release();\n" +
                        "        mat2.release();\n" +
                        "        mat.release();\n" +
                        "        matCut1.release();\n" +
                        "        matCut2.release();");
        //binding.textView3Montage.setMovementMethod(ScrollingMovementMethod.getInstance());

        binding.code3.setOnClickListener(v -> {
            binding.textView3Montage.setVisibility(View.VISIBLE);

        });
        binding.cancel.setOnClickListener(v -> {
            binding.textView3Montage.setVisibility(View.GONE);
            ;
        });

    }

    private void init() {
        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= 23) {//View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR设置状态栏文字为灰色（默认为白色View.SYSTEM_UI_FLAG_LAYOUT_STABLE）
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setTextViewStyles(binding.textViewExam3);

        //binding.textViewDescribe3.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    private void setTextViewStyles(TextView textView) {
        LinearGradient mLinearGradient = new LinearGradient(0, 0, textView.getPaint().getTextSize() * textView.getText().length(), 0, Color.parseColor("#FF5722"), Color.parseColor("#FF6200EE"), Shader.TileMode.CLAMP);
        //LinearGradient mLinearGradient = new LinearGradient(0,0,textView.getPaint().getTextSize() * textView.getText().length(),0,new int[]{R.color.purple_500,R.color.purple_200,R.color.orange},null, Shader.TileMode.CLAMP);

        textView.getPaint().setShader(mLinearGradient);
        textView.invalidate();
    }
}
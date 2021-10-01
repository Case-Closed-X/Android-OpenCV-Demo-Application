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

import com.x.edu.opencv.databinding.ExamActivity1ShowBinding;

public class ExamActivity1Show extends AppCompatActivity {

    ExamActivity1ShowBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ExamActivity1ShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        //放置关键Code处
        ImageView imageView = findViewById(R.id.imageView1Show);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.conan);
        imageView.setImageBitmap(bitmap);
        //放置关键Code处

        code();
    }

    @SuppressLint("SetTextI18n")
    private void code() {
        binding.textView1Show.setText(
                getString(R.string.describe1) + "\n\n" +
                        "        ImageView imageView=findViewById(R.id.imageView1Show);\n\n" +
                        "        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.conan);\n\n" +
                        "        imageView.setImageBitmap(bitmap);");
        //binding.textView1Show.setMovementMethod(ScrollingMovementMethod.getInstance());
        //使用xml属性android:textIsSelectable="true"代替

        binding.code1.setOnClickListener(v -> binding.textView1Show.setVisibility(View.VISIBLE));
        binding.cancel.setOnClickListener(v -> binding.textView1Show.setVisibility(View.GONE));

    }

    private void init() {
        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= 23) {//View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR设置状态栏文字为灰色（默认为白色View.SYSTEM_UI_FLAG_LAYOUT_STABLE）
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setTextViewStyles(binding.textViewExam1);

        //binding.textViewDescribe1.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    private void setTextViewStyles(TextView textView) {
        LinearGradient mLinearGradient = new LinearGradient(0, 0, textView.getPaint().getTextSize() * textView.getText().length(), 0, Color.parseColor("#FF5722"), Color.parseColor("#FF6200EE"), Shader.TileMode.CLAMP);
        //LinearGradient mLinearGradient = new LinearGradient(0,0,textView.getPaint().getTextSize() * textView.getText().length(),0,new int[]{R.color.purple_500,R.color.purple_200,R.color.orange},null, Shader.TileMode.CLAMP);

        textView.getPaint().setShader(mLinearGradient);
        textView.invalidate();
    }

}
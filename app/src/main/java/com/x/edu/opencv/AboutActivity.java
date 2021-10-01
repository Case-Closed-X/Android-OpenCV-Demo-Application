package com.x.edu.opencv;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {

    private ActivityAboutBinding binding;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CommonMethod.setStatusBarLight(getWindow().getDecorView(), true);//设置状态栏颜色为灰色

        CommonMethod.setTextViewStyles(binding.cardTextAbout);//设置文字渐变

        //待项目开源后，改变此处url为新项目的地址
        String urlGithub = "https://github.com/Case-Closed-X/Android-OpenCV-Demo-Application";
        String urlOutlook = "mailto:CaseClosedX@outlook.com";
        String urlOpenCV = "https://opencv.org";

        binding.cardViewAbout.setOnClickListener(v -> Toast.makeText(this, "当前版本：v1.0", Toast.LENGTH_SHORT).show());

        binding.cardViewGithub.setOnClickListener(v -> {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlGithub));
            startActivity(intent);
        });

        binding.cardViewEmail.setOnClickListener(v -> {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlOutlook));
            startActivity(intent);
        });

        binding.cardViewOpenCV.setOnClickListener(v -> {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlOpenCV));
            startActivity(intent);
        });
    }
}
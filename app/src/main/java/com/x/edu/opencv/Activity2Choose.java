package com.x.edu.opencv;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity2ChooseBinding;

import java.io.FileNotFoundException;

public class Activity2Choose extends AppCompatActivity {

    private Activity2ChooseBinding viewBinding;

    private Bitmap bitmap;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBinding = Activity2ChooseBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        getWindow().setStatusBarColor(Color.parseColor("#ffff8800"));

        initButton();
    }

    private void initButton() {
        //LAMBDA表达式写法
        viewBinding.choose.setOnClickListener(v -> choose());
    }

    private void choose() {
        //Intent intent = new Intent();
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(intent, 2);
        //startActivityForResult(Intent.createChooser(intent,"图像选择"),2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 2 && data != null) {
            Uri uri = data.getData();

            ContentResolver contentResolver = this.getContentResolver();

            try {
                bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            viewBinding.imageChoose.setImageBitmap(bitmap);
        }
    }
}
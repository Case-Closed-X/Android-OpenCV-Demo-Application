package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity1GrayBinding;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Activity1Gray extends AppCompatActivity {

    private Activity1GrayBinding viewBinding;//ActivityMainBinding就是上面activity_main.xml生成的绑定文件

    private Bitmap bitmapOriginal;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBinding = Activity1GrayBinding.inflate(getLayoutInflater());//viewBinding代替findViewById()
        setContentView(viewBinding.getRoot());

        //setContentView(R.layout.activity_gray1);

        getWindow().setStatusBarColor(Color.parseColor("#FF5722"));

        initBitMapOriginal();

        initButton();
    }

    private void initBitMapOriginal() {
        bitmapOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.bg1);
        viewBinding.imageOriginal.setImageBitmap(bitmapOriginal);
    }

    private void initButton() {
        //接口中只有一个待实现方法，即可使用函数式API
        viewBinding.gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //对图像进行灰度化
                gray();
            }
        });
    }

    private void gray() {
        Mat matOriginal = new Mat();
        Mat matGray = new Mat();

        Utils.bitmapToMat(bitmapOriginal, matOriginal);

        Imgproc.cvtColor(matOriginal, matGray, Imgproc.COLOR_BGR2GRAY);//opencv图像灰度化

        Bitmap bitmapGray = Bitmap.createBitmap(matGray.width(), matGray.height(), Bitmap.Config.ARGB_8888);

        Utils.matToBitmap(matGray, bitmapGray);

        viewBinding.imageGray.setImageBitmap(bitmapGray);

        matOriginal.release();
        matGray.release();
    }

}
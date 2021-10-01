package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity4CutBinding;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

public class Activity4Cut extends AppCompatActivity {

    private Activity4CutBinding viewBinding;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBinding = Activity4CutBinding.inflate(getLayoutInflater());

        setContentView(viewBinding.getRoot());

        getWindow().setStatusBarColor(Color.parseColor("#03A9F4"));

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg3);
        viewBinding.image4.setImageBitmap(bitmap);

        viewBinding.cut.setOnClickListener(v -> {
            Mat mat = new Mat();
            Utils.bitmapToMat(bitmap, mat);

            Mat matCut = new Mat();
            matCut = mat.submat(mat.height() / 4, mat.height() * 3 / 4, mat.width() / 4, mat.width() * 3 / 4);

            Bitmap bitmapCut = Bitmap.createBitmap(matCut.width(), matCut.height(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(matCut, bitmapCut);

            viewBinding.imageCut.setImageBitmap(bitmapCut);

            mat.release();
            matCut.release();
        });

    }
}
package com.x.edu.opencv;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.ExamActivity5CropBinding;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ExamActivity5Crop extends AppCompatActivity {

    ExamActivity5CropBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ExamActivity5CropBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        //放置关键Code处
        Mat mat = new Mat();
        Mat lt;
        Mat rt;
        Mat ld;
        Mat rd;

        Bitmap bitmapbefore;

        bitmapbefore = BitmapFactory.decodeResource(getResources(), R.drawable.exam5crop);
        Utils.bitmapToMat(bitmapbefore, mat);

        Mat result = new Mat();
        int width = mat.width() / 2 + 60;
        int height = mat.height() / 2 - 20;
        //裁剪成四张图片
        lt = mat.submat(0, height,
                0, width);

        rt = mat.submat(0, height,
                width, mat.width());

        ld = mat.submat(height, mat.height(),
                0, width);

        rd = mat.submat(height, mat.height(),
                width, mat.width());

        //更改四张图片的对比度和亮度
        Imgproc.cvtColor(lt, lt, Imgproc.COLOR_BGRA2BGR);
        //亮度
        Core.add(lt, new Scalar(+15, +15, +15), lt);
        //对比度
        Core.multiply(lt, new Scalar(0.9, 0.9, 0.9), lt);
        Imgproc.cvtColor(lt, lt, result.type());

        Imgproc.cvtColor(rt, rt, Imgproc.COLOR_BGRA2RGB);
        Core.add(rt, new Scalar(+15, +15, +15), rt);
        Core.multiply(rt, new Scalar(1.1, 1.1, 1.1), rt);
        Imgproc.cvtColor(rt, rt, result.type());

        Imgproc.cvtColor(ld, ld, Imgproc.COLOR_BGRA2RGB);
        Core.add(ld, new Scalar(-5, -5, -5), ld);
        Core.multiply(ld, new Scalar(0.9, 0.9, 0.9), ld);
        Imgproc.cvtColor(ld, ld, result.type());

        Imgproc.cvtColor(rd, rd, Imgproc.COLOR_BGRA2RGB);
        Core.add(rd, new Scalar(0, 0, 0), rd);
        Core.multiply(rd, new Scalar(0.82, 0.82, 0.82), rd);
        Imgproc.cvtColor(rd, rd, result.type());

        //合并图片
        List<Mat> matList1 = new ArrayList<>();
        matList1.add(lt);
        matList1.add(rt);
        Mat mat1 = new Mat();
        //水平合并
        Core.hconcat(matList1, mat1);
        List<Mat> matList2 = new ArrayList<>();
        matList2.add(ld);
        matList2.add(rd);
        Mat mat2 = new Mat();
        Core.hconcat(matList2, mat2);
        List<Mat> matList3 = new ArrayList<>();
        matList3.add(mat1);
        matList3.add(mat2);
        //垂直合并
        Core.vconcat(matList3, result);


        Bitmap bitmap = Bitmap.createBitmap(result.width(), result.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(result, bitmap);

        //放置关键Code处

        AtomicBoolean origin = new AtomicBoolean(true);
        binding.cardViewExam5.setOnClickListener(v -> {
            if (origin.get()) {
                binding.imageViewExam5Crop.setImageBitmap(bitmap);
                origin.set(false);
            } else {
                binding.imageViewExam5Crop.setImageBitmap(bitmapbefore);
                origin.set(true);
            }

        });

        code();
    }

    @SuppressLint("SetTextI18n")
    private void code() {
        binding.textViewExam5Crop.setText("\n" +
                getString(R.string.describe5) + "\n\n" +
                "        Mat mat = new Mat();\n" +
                "        Mat lt;\n" +
                "        Mat rt;\n" +
                "        Mat ld;\n" +
                "        Mat rd;\n" +
                "\n" +
                "        Bitmap bitmapbefore;\n" +
                "\n" +
                "        bitmapbefore = BitmapFactory.decodeResource(getResources(), R.drawable.exam5crop);\n" +
                "        Utils.bitmapToMat(bitmapbefore, mat);\n" +
                "\n" +
                "        Mat result = new Mat();\n" +
                "        int width = mat.width() / 2 + 60;\n" +
                "        int height = mat.height() / 2 - 20;\n" +
                "        //裁剪成四张图片\n" +
                "        lt = mat.submat(0, height,\n" +
                "                0, width);\n" +
                "\n" +
                "        rt = mat.submat(0, height,\n" +
                "                width, mat.width());\n" +
                "\n" +
                "        ld = mat.submat(height, mat.height(),\n" +
                "                0, width);\n" +
                "\n" +
                "        rd = mat.submat(height, mat.height(),\n" +
                "                width, mat.width());\n" +
                "\n" +
                "        //更改四张图片的对比度和亮度\n" +
                "        Imgproc.cvtColor(lt,lt,Imgproc.COLOR_BGRA2BGR);\n" +
                "        //亮度\n" +
                "        Core.add(lt,new Scalar(+15,+15,+15),lt);\n" +
                "        //对比度\n" +
                "        Core.multiply(lt,new Scalar(0.9,0.9,0.9),lt);\n" +
                "        Imgproc.cvtColor(lt,lt,result.type());\n" +
                "\n" +
                "        Imgproc.cvtColor(rt,rt,Imgproc.COLOR_BGRA2RGB);\n" +
                "        Core.add(rt,new Scalar(+15,+15,+15),rt);\n" +
                "        Core.multiply(rt,new Scalar(1.1,1.1,1.1),rt);\n" +
                "        Imgproc.cvtColor(rt,rt,result.type());\n" +
                "\n" +
                "        Imgproc.cvtColor(ld,ld,Imgproc.COLOR_BGRA2RGB);\n" +
                "        Core.add(ld,new Scalar(-5,-5,-5),ld);\n" +
                "        Core.multiply(ld,new Scalar(0.9,0.9,0.9),ld);\n" +
                "        Imgproc.cvtColor(ld,ld,result.type());\n" +
                "\n" +
                "        Imgproc.cvtColor(rd,rd,Imgproc.COLOR_BGRA2RGB);\n" +
                "        Core.add(rd,new Scalar(0,0,0),rd);\n" +
                "        Core.multiply(rd,new Scalar(0.82,0.82,0.82),rd);\n" +
                "        Imgproc.cvtColor(rd,rd,result.type());\n" +
                "\n" +
                "        //合并图片\n" +
                "        List<Mat> matList1 = new ArrayList<>();\n" +
                "        matList1.add(lt);\n" +
                "        matList1.add(rt);\n" +
                "        Mat mat1 = new Mat();\n" +
                "        //水平合并\n" +
                "        Core.hconcat(matList1, mat1);\n" +
                "        List<Mat> matList2 = new ArrayList<>();\n" +
                "        matList2.add(ld);\n" +
                "        matList2.add(rd);\n" +
                "        Mat mat2 = new Mat();\n" +
                "        Core.hconcat(matList2, mat2);\n" +
                "        List<Mat> matList3 = new ArrayList<>();\n" +
                "        matList3.add(mat1);\n" +
                "        matList3.add(mat2);\n" +
                "        //垂直合并\n" +
                "        Core.vconcat(matList3, result);\n" +
                "\n" +
                "\n" +
                "        Bitmap bitmap = Bitmap.createBitmap(result.width(),result.height(), Bitmap.Config.ARGB_8888);\n" +
                "        Utils.matToBitmap(result,bitmap);\n" +
                "\n" +
                "        binding.imageViewExam5Crop.setImageBitmap(bitmap);"
        );
        //binding.textView3Montage.setMovementMethod(ScrollingMovementMethod.getInstance());

        binding.code5.setOnClickListener(v -> {
            binding.textViewExam5Crop.setVisibility(View.VISIBLE);

        });
        binding.cancel.setOnClickListener(v -> {
            binding.textViewExam5Crop.setVisibility(View.GONE);
            ;
        });

    }

    private void init() {
        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= 23) {//View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR设置状态栏文字为灰色（默认为白色View.SYSTEM_UI_FLAG_LAYOUT_STABLE）
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        CommonMethod.setTextViewStyles(binding.textViewExam5);
        //binding.textViewDescribe3.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}
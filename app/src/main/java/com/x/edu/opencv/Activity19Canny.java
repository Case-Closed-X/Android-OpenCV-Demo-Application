package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity19CannyBinding;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class Activity19Canny extends AppCompatActivity {

    Activity19CannyBinding binding;//视图绑定

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = Activity19CannyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CommonMethod.setStatusBarLight(getWindow().getDecorView(),true);//设置状态栏颜色为灰色

        CommonMethod.setTextViewStyles(binding.textView19);//设置文字渐变
        CommonMethod.setTextViewStyles(binding.button19Canny);//Button文字也可以渐变

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.microsoft);
        binding.CardImage19.setImageBitmap(bitmap);

        Mat mat=new Mat();
        Utils.bitmapToMat(bitmap,mat);
        Imgproc.cvtColor(mat,mat,Imgproc.COLOR_BGRA2GRAY);//灰度不灰度似乎没区别

        binding.button19Canny.setOnClickListener(v -> {
            Mat mat1=new Mat();
            Imgproc.GaussianBlur(mat,mat1,new Size(3,3),0);

            //输入，输出，低域值，高域值，边缘连接的尺寸模板大小通常为3，图像梯度的计算方法
            Imgproc.Canny(mat,mat1,50,150,3,true);

            Bitmap bitmap1 = Bitmap.createBitmap(mat.width(),mat.height(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat1,bitmap1);
            binding.CardImage19Canny.setImageBitmap(bitmap1);
        });
    }

    /*private void setTextViewStyles(TextView textView) {//也许我应当把这些常用的方法定义为类中的静态方法，省的复制粘贴
        LinearGradient mLinearGradient = new LinearGradient(0, 0, textView.getPaint().getTextSize() * textView.getText().length(), 0, Color.parseColor("#FF5722"), Color.parseColor("#FF6200EE"), Shader.TileMode.CLAMP);
        //LinearGradient mLinearGradient = new LinearGradient(0,0,textView.getPaint().getTextSize() * textView.getText().length(),0,new int[]{R.color.purple_500,R.color.purple_200,R.color.orange},null, Shader.TileMode.CLAMP);

        textView.getPaint().setShader(mLinearGradient);
        textView.invalidate();
    }*/
}
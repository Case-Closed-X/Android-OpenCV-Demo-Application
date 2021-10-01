package com.x.edu.opencv;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.Activity3SaveBinding;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class Activity3Save extends AppCompatActivity {

    private Activity3SaveBinding viewBinding;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBinding = Activity3SaveBinding.inflate(getLayoutInflater());

        setContentView(viewBinding.getRoot());

        getWindow().setStatusBarColor(Color.parseColor("#FF6200EE"));

        //lambda
        viewBinding.save.setOnClickListener(v -> save());
    }

    private void save() {
        /*File fileDir=new File("pictures","com.qqhru.edu.opencv");
        if(!fileDir.exists()){
            if (fileDir.mkdir()) {
                Toast.makeText(this, "目录pictures创建成功", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "目录pictures创建失败", Toast.LENGTH_LONG).show();
            }
        }*/

        /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.microsoft);

        Mat mat=new Mat();
        Utils.bitmapToMat(bitmap,mat);

        String saveName = System.currentTimeMillis() + ".jpg";

        File file=new File(fileDir.getAbsolutePath()+File.separator,saveName);

        Mat matConvert = new Mat();
        Imgproc.cvtColor(mat,matConvert,Imgproc.COLOR_BGR2RGB);

        Imgcodecs.imwrite(file.getAbsolutePath(),matConvert);

        Toast.makeText(this, "图像保存成功，文件名为"+saveName, Toast.LENGTH_LONG).show();

        viewBinding.imageSave.setImageBitmap(bitmap);*/
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.microsoft);
        addBitmapToAlbum(bitmap, String.valueOf(System.currentTimeMillis()), "image/jpeg", Bitmap.CompressFormat.JPEG);
        viewBinding.imageSave.setImageBitmap(bitmap);
    }

    //因Android10已无法访问Data目录，故采用Android作用域存储
    public final void addBitmapToAlbum(Bitmap bitmap, String displayName, String mimeType, Bitmap.CompressFormat compressFormat) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName);
        values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/OpenCV");
        } else {
            values.put(MediaStore.MediaColumns.DATA, "${Environment.getExternalStorageDirectory().path}/${Environment.DIRECTORY_PICTURES}/OpenCV/$displayName");
        }

        Uri uri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        if (uri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = this.getContentResolver().openOutputStream(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (outputStream != null) {
                bitmap.compress(compressFormat, 100, outputStream);

                Toast toast = Toast.makeText(getApplicationContext(), "图像保存成功，文件目录为" + Environment.DIRECTORY_PICTURES + "/OpenCV/" + displayName + ".jpg", Toast.LENGTH_LONG);
                //toast.setGravity(Gravity.CENTER,0,0);
                toast.show();

                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
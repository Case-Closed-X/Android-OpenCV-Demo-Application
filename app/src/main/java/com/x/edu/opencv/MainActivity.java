package com.x.edu.opencv;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.ActivityMainBinding;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding viewBinding;//ActivityMainBinding就是上面activity_main.xml生成的绑定文件

    private Intent intent;

    View decorView;
    private int shortAnimationDuration;

    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());//viewBinding代替findViewById()
        setContentView(viewBinding.getRoot());

        //setContentView(R.layout.activity_main);
        decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= 23) {
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;//View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR设置状态栏文字为灰色（默认为白色View.SYSTEM_UI_FLAG_LAYOUT_STABLE）
            decorView.setSystemUiVisibility(option);
            //themes已经设置透明导航栏（小白条）
            //getWindow().setNavigationBarColor(Color.TRANSPARENT);
            //getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        // Retrieve and cache the system's default "short" animation time.
        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            viewBinding.scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY >= 50) {
                        //decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//状态栏灰色
                        //viewBinding.designBy.setVisibility(View.INVISIBLE);

                        /*if(viewBinding.designBy.getVisibility()==View.VISIBLE)
                        {
                            viewBinding.designBy.setAlpha(1f);
                        }*/

                        viewBinding.designBy.animate()//淡出动画
                                .alpha(0f)
                                .setDuration(shortAnimationDuration)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        //viewBinding.designBy.setVisibility(View.INVISIBLE);
                                        if (scrollY >= 100) {
                                            viewBinding.button1Gray.animate()//淡出动画
                                                    .alpha(0f)
                                                    .setDuration(shortAnimationDuration)
                                                    .setListener(new AnimatorListenerAdapter() {
                                                        @Override
                                                        public void onAnimationEnd(Animator animation) {
                                                            //viewBinding.designBy.setVisibility(View.INVISIBLE);
                                                        }
                                                    });
                                        }

                                    }
                                });

                    } else {
                        //decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);//状态栏白色
                        //viewBinding.designBy.setVisibility(View.VISIBLE);

                        /*if(viewBinding.designBy.getVisibility()==View.INVISIBLE)
                        {
                            viewBinding.designBy.setAlpha(0f);
                        }*/

                        viewBinding.designBy.animate()
                                .alpha(1f)
                                .setDuration(shortAnimationDuration)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        //viewBinding.designBy.setVisibility(View.VISIBLE);
                                        viewBinding.button1Gray.animate()//淡入动画
                                                .alpha(1f)
                                                .setDuration(shortAnimationDuration)
                                                .setListener(new AnimatorListenerAdapter() {
                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        //viewBinding.designBy.setVisibility(View.INVISIBLE);
                                                    }
                                                });
                                    }
                                });
                    }
                }
            });
        }

       /* private void crossfade() {

            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            contentView.setAlpha(0f);
            contentView.setVisibility(View.VISIBLE);

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.
            contentView.animate()
                    .alpha(1f)
                    .setDuration(shortAnimationDuration)
                    .setListener(null);

            // Animate the loading view to 0% opacity. After the animation ends,
            // set its visibility to GONE as an optimization step (it won't
            // participate in layout passes, etc.)
            loadingView.animate()
                    .alpha(0f)
                    .setDuration(shortAnimationDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            loadingView.setVisibility(View.GONE);
                        }
                    });
        }*/


        //getWindow().setBackgroundDrawableResource(R.drawable.magic);

        /*//状态栏透明
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);*/
        //getWindow().setNavigationBarColor(Color.TRANSPARENT);

        initOpenCV();

        initActivityButton();

        setTextViewStyles(viewBinding.designBy);


    }

    boolean visibility = true;
    Bitmap bitmap;

    private void initActivityButton() {

        decorView = getWindow().getDecorView();

        viewBinding.imageButtonOpenCV.setOnClickListener(v -> {
            if (!visibility) {

                viewBinding.background.animate().alpha(1f).setDuration(shortAnimationDuration);
                //viewBinding.background.setVisibility(View.VISIBLE);

                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

                if (Build.VERSION.SDK_INT >= 23) {
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    //View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR设置状态栏文字为灰色（默认为白色View.SYSTEM_UI_FLAG_LAYOUT_STABLE）
                }

                visibility = true;
            } else {

                viewBinding.background.animate().alpha(0f).setDuration(shortAnimationDuration);

                //viewBinding.background.setVisibility(View.INVISIBLE);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo2);
                //setContentView(R.layout.activity_main);

                if (Build.VERSION.SDK_INT >= 23) {
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    //View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR设置状态栏文字为灰色（默认为白色View.SYSTEM_UI_FLAG_LAYOUT_STABLE）
                }
                visibility = false;
            }
            viewBinding.imageButtonOpenCV.setImageBitmap(bitmap);
        });

        viewBinding.designBy.setOnClickListener(v -> {
            intent = new Intent(this, RecyclerViewActivity.class);
            startActivity(intent);
        });

        //接口中只有一个待实现方法，即可使用函数式API写法
        viewBinding.button1Gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, Activity1Gray.class);
                startActivity(intent);
            }
        });

        //lambda匿名函数写法
        viewBinding.button2Choose.setOnClickListener(v -> {
            intent = new Intent(MainActivity.this, Activity2Choose.class);
            startActivity(intent);
        });

        //lambda写法
        viewBinding.button3Save.setOnClickListener(v -> {
            intent = new Intent(MainActivity.this, Activity3Save.class);
            startActivity(intent);
        });

        viewBinding.button4Cut.setOnClickListener(v -> {
            intent = new Intent(MainActivity.this, Activity4Cut.class);
            startActivity(intent);
        });

        viewBinding.button5Draw.setOnClickListener(v -> {
            intent = new Intent(MainActivity.this, Activity5Draw.class);
            startActivity(intent);
        });

        viewBinding.button6Read1.setOnClickListener(v -> {
            intent = new Intent(MainActivity.this, Activity6Read1.class);
            startActivity(intent);
        });

        viewBinding.button7Read2.setOnClickListener(v -> {
            intent = new Intent(MainActivity.this, Activity7Read2.class);
            startActivity(intent);
        });

        viewBinding.button8Read3.setOnClickListener(v -> {
            intent = new Intent(MainActivity.this, Activity8Read3.class);
            startActivity(intent);
        });

        viewBinding.button9Channel.setOnClickListener(v -> {
            intent = new Intent(MainActivity.this, Activity9Channel.class);
            startActivity(intent);
        });

        viewBinding.button10Division.setOnClickListener(v -> {
            intent = new Intent(MainActivity.this, Activity10Division.class);
            startActivity(intent);
        });

        viewBinding.button11Compose.setOnClickListener(v -> {
            intent = new Intent(MainActivity.this, Activity11Compose.class);
            startActivity(intent);
        });
    }

    private void initOpenCV() {
        boolean success = OpenCVLoader.initDebug();

        if (success) {
            Toast.makeText(this, "初始化成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "初始化失败", Toast.LENGTH_LONG).show();
        }
    }

    private void setTextViewStyles(TextView textView) {
        LinearGradient mLinearGradient = new LinearGradient(0, 0, textView.getPaint().getTextSize() * textView.getText().length(), 0, Color.parseColor("#FF5722"), Color.parseColor("#FF6200EE"), Shader.TileMode.CLAMP);
        //LinearGradient mLinearGradient = new LinearGradient(0,0,textView.getPaint().getTextSize() * textView.getText().length(),0,new int[]{R.color.purple_500,R.color.purple_200,R.color.orange},null, Shader.TileMode.CLAMP);

        textView.getPaint().setShader(mLinearGradient);
        textView.invalidate();
    }
}
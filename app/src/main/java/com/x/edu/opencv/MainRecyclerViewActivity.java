package com.x.edu.opencv;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.x.edu.opencv.databinding.ActivityMainRecyclerViewBinding;

import org.opencv.android.OpenCVLoader;

import java.util.ArrayList;
import java.util.List;

public class MainRecyclerViewActivity extends AppCompatActivity {

    private ActivityMainRecyclerViewBinding binding;

    private Intent intent;

    private RecyclerView mRecycleView;
    private MainRecycleViewAdapter mAdapter;//适配器
    private LinearLayoutManager mLinearLayoutManager;//布局管理器
    //private List<String> mList;
    private final List<MainItem> mList = new ArrayList<MainItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainRecyclerViewBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        OpenCVLoader.initDebug();//OpenCV初始化后才能使用Mat等对象

        View decorView = getWindow().getDecorView();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;//View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR设置状态栏文字为灰色（默认为白色View.SYSTEM_UI_FLAG_LAYOUT_STABLE）
            decorView.setSystemUiVisibility(option);
            //themes已经设置透明导航栏（小白条）
            //getWindow().setNavigationBarColor(Color.TRANSPARENT);
            //getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setTextViewStyles(binding.textViewMainOpenCV);


        //创建通知
        //createNotificationChannel();
        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ico2)
                .setContentTitle("Thank you")
                .setContentText("Thank you for using my application")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);*/

        // Create an explicit intent for an Activity in your app
        createNotificationChannel();//一定一定要有ChannelID的创建，与下方输入的Channel有关，否则不会显示通知
        Intent intentNotify = new Intent(this, Activity18MorphologyRecyclerView.class);
        intentNotify.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// | Intent.FLAG_ACTIVITY_CLEAR_TASK
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentNotify, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ico42)
                .setContentTitle("推荐功能")
                .setContentText("图像形态学运算")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)//PRIORITY_HIGH
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        //显示通知
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());


        mRecycleView = findViewById(R.id.MainRecyclerView);
        //初始化数据
        initData(mList);
        //创建布局管理器，垂直设置LinearLayoutManager.VERTICAL，水平设置LinearLayoutManager.HORIZONTAL
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //创建适配器，将数据传递给适配器
        mAdapter = new MainRecycleViewAdapter(mList);
        //设置布局管理器
        mRecycleView.setLayoutManager(mLinearLayoutManager);
        //设置适配器adapter
        mRecycleView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MainRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(getApplicationContext(), "第" + position + "条数据", Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        intent = new Intent(MainRecyclerViewActivity.this, AboutActivity.class);
                        break;
                    case 1:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity1Gray.class);
                        break;
                    case 2:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity2Choose.class);
                        break;
                    case 3:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity3Save.class);
                        break;
                    case 4:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity4Cut.class);
                        break;
                    case 5:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity5Draw.class);
                        break;
                    case 6:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity6Read1.class);
                        break;
                    case 7:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity7Read2.class);
                        break;
                    case 8:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity8Read3.class);
                        break;
                    case 9:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity9Channel.class);
                        break;
                    case 10:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity10Division.class);
                        break;
                    case 11:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity11Compose.class);
                        break;
                    case 12:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity12Add.class);
                        break;
                    case 13:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity13Logic.class);
                        break;
                    case 14:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity14Splicing.class);
                        break;
                    case 15:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity15Normalization.class);
                        break;
                    case 16:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity16Blur.class);
                        break;
                    case 17:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity17Median.class);
                        break;
                    case 18:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity18MorphologyRecyclerView.class);
                        break;
                    case 19:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity19Canny.class);
                        break;
                    case 20:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity20Thresholding.class);
                        break;
                    case 21:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity21HoughLine.class);
                        break;
                    case 22:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity22HoughCircle.class);
                        break;
                    case 23:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity23Histogram.class);
                        break;
                    case 24:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity24Matching.class);
                        break;
                    case 25:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity25Harris.class);
                        break;
                    case 26:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity26Tomasi.class);
                        break;
                    case 27:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity27SIFI.class);
                        break;
                    case 28:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity28FaceRecognition.class);
                        break;
                    case 29:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity29Camera.class);
                        break;
                    case 30:
                        intent = new Intent(MainRecyclerViewActivity.this, Activity30FaceTracking.class);
                        break;
                    default:
                        intent = new Intent(MainRecyclerViewActivity.this, RecyclerViewActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });

        binding.textViewMainOpenCV.setOnClickListener(v -> {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    public void initData(List<MainItem> itemList) {
        MainItem designBy = new MainItem(getString(R.string.about), R.drawable.home);
        itemList.add(designBy);

        MainItem activity1Gray = new MainItem("图像灰度化", // 名字
                R.drawable.ico1); // 图片id
        itemList.add(activity1Gray); // 增加到链表

        MainItem activity2Choose = new MainItem("图像选择", R.drawable.ico2);
        itemList.add(activity2Choose);

        MainItem activity3Save = new MainItem("图像保存", R.drawable.ico3);
        itemList.add(activity3Save);

        MainItem activity4Cut = new MainItem("图像裁剪", R.drawable.ico4);
        itemList.add(activity4Cut);

        MainItem activity5Draw = new MainItem("图像绘制", R.drawable.ico5);
        itemList.add(activity5Draw);

        MainItem activity6Read1 = new MainItem("图像线程池中取反", R.drawable.ico6);
        itemList.add(activity6Read1);

        MainItem activity7Read2 = new MainItem("图像多线程取反", R.drawable.ico7);
        itemList.add(activity7Read2);

        MainItem activity8Read3 = new MainItem("图像直接取反", R.drawable.ico8);
        itemList.add(activity8Read3);

        MainItem activity9Channel = new MainItem("图像通道分离与合并", R.drawable.ico9);
        itemList.add(activity9Channel);

        MainItem activity10Division = new MainItem("图像均值分割", R.drawable.ico10);
        itemList.add(activity10Division);

        MainItem activity11Compose = new MainItem("图像叠加", R.drawable.ico11);
        itemList.add(activity11Compose);

        MainItem activity12 = new MainItem("图像亮度与对比度", R.drawable.ico12);
        itemList.add(activity12);

        MainItem activity13 = new MainItem("图像逻辑操作", R.drawable.ico13);
        itemList.add(activity13);

        MainItem activity14 = new MainItem("图像拼接", R.drawable.ico14);
        itemList.add(activity14);

        MainItem activity15 = new MainItem("图像归一化", R.drawable.ico15);
        itemList.add(activity15);

        MainItem activity16 = new MainItem("图像模糊", R.drawable.ico16);
        itemList.add(activity16);

        MainItem activity17 = new MainItem("图像中值滤波", R.drawable.ico17);
        itemList.add(activity17);

        MainItem activity18 = new MainItem(getString(R.string.Activity18Morphology), R.drawable.ico18);
        itemList.add(activity18);

        MainItem activity19 = new MainItem(getString(R.string.Activity19Canny), R.drawable.ico19);
        itemList.add(activity19);

        MainItem activity20 = new MainItem(getString(R.string.Activity20Thresholding), R.drawable.ico20);
        itemList.add(activity20);

        MainItem activity21 = new MainItem(getString(R.string.Activity21HoughLine), R.drawable.ico21);
        itemList.add(activity21);

        MainItem activity22 = new MainItem(getString(R.string.Activity22HoughCircle), R.drawable.ico22);
        itemList.add(activity22);

        MainItem activity23 = new MainItem(getString(R.string.Activity23Histogram), R.drawable.ico23);
        itemList.add(activity23);

        MainItem activity24 = new MainItem(getString(R.string.Activity24Matching), R.drawable.ico24);
        itemList.add(activity24);

        MainItem activity25 = new MainItem(getString(R.string.Activity25Harris), R.drawable.ico25);
        itemList.add(activity25);

        MainItem activity26 = new MainItem(getString(R.string.Activity26Tomasi), R.drawable.ico26);
        itemList.add(activity26);

        MainItem activity27 = new MainItem(getString(R.string.Activity27SIFI), R.drawable.ico27);
        itemList.add(activity27);

        MainItem activity28 = new MainItem(getString(R.string.Activity28FaceRecognition), R.drawable.ico28);
        itemList.add(activity28);

        itemList.add(new MainItem(getString(R.string.Activity29Camera),R.drawable.ico29));

        itemList.add(new MainItem(getString(R.string.Activity30FaceTracking),R.drawable.ico30));

        MainItem activityExam = new MainItem(getString(R.string.examination), R.drawable.ico0);
        itemList.add(activityExam);
    }

    private void setTextViewStyles(TextView textView) {
        LinearGradient mLinearGradient = new LinearGradient(0, 0, textView.getPaint().getTextSize() * textView.getText().length(), 0, Color.parseColor("#FF5722"), Color.parseColor("#FF6200EE"), Shader.TileMode.CLAMP);
        //LinearGradient mLinearGradient = new LinearGradient(0,0,textView.getPaint().getTextSize() * textView.getText().length(),0,new int[]{R.color.purple_500,R.color.purple_200,R.color.orange},null, Shader.TileMode.CLAMP);

        textView.getPaint().setShader(mLinearGradient);
        textView.invalidate();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}


class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder> {

    private final List<MainItem> mList;//数据源

    private OnItemClickListener onItemClickListener;

    /**
     * 供外部调用设置监听
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 自定义的接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    //通过方法提供的ViewHolder，将数据绑定到ViewHolder中
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        MainItem list = mList.get(position);
        holder.imageView.setImageResource(list.getImageId());
        holder.textView.setText(list.getName());
        //holder.textView.setText(mList.get(position).toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, holder.getAdapterPosition());
                }
            }
        });
    }

    MainRecycleViewAdapter(List<MainItem> list) {
        mList = list;
    }

    //创建ViewHolder并返回，后续item布局里控件都是从ViewHolder中取出，即加载布局
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //将我们自定义的item布局转换为View
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.intent_item_main_recycler, parent, false);
        //将view传递给我们自定义的ViewHolder
        //返回这个ViewHolder实体
        return new ViewHolder(view);
    }

    //获取数据源总的条数
    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 自定义的ViewHolder
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewIntentMainRecycler);
            imageView = itemView.findViewById(R.id.imageViewIntentMainRecycler);
        }
    }

}

class MainItem {

    //Item的名称
    private final String name;

    //Item对应图片的资源id，在drawble里
    private final int imageId;

    //构造函数
    public MainItem(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    //get名字
    public String getName() {
        return name;
    }

    //get图片id
    public int getImageId() {
        return imageId;
    }
}
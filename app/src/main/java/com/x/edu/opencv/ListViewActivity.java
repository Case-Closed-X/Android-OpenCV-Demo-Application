package com.x.edu.opencv;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.x.edu.opencv.databinding.ActivityListViewBinding;

import org.opencv.android.OpenCVLoader;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    private ActivityListViewBinding binding;
    private Intent intent;

    private final List<MyItem> itemList = new ArrayList<MyItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListViewBinding.inflate(getLayoutInflater());
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

        setTextViewStyles(binding.textViewOpenCV);

        initItems();
        ItemAdapter adapter = new ItemAdapter(this,
                R.layout.intent_item, itemList);

        binding.listView.setAdapter(adapter);
        //setListViewHeight(binding.listView);

        binding.listView.setOnItemClickListener((parent, view, position, id) -> {
            /*MyItem myitem= itemList.get(position);
            Toast.makeText(this,myitem.getName(),Toast.LENGTH_SHORT).show();*/
            switch (position) {
                case 0:
                    intent = new Intent(this, MainActivity.class);
                    break;
                case 1:
                    intent = new Intent(this, Activity1Gray.class);
                    break;
                case 2:
                    intent = new Intent(this, Activity2Choose.class);
                    break;
                case 3:
                    intent = new Intent(this, Activity3Save.class);
                    break;
                case 4:
                    intent = new Intent(this, Activity4Cut.class);
                    break;
                case 5:
                    intent = new Intent(this, Activity5Draw.class);
                    break;
                case 6:
                    intent = new Intent(this, Activity6Read1.class);
                    break;
                case 7:
                    intent = new Intent(this, Activity7Read2.class);
                    break;
                case 8:
                    intent = new Intent(this, Activity8Read3.class);
                    break;
                case 9:
                    intent = new Intent(this, Activity9Channel.class);
                    break;
                case 10:
                    intent = new Intent(this, Activity10Division.class);
                    break;
                case 11:
                    intent = new Intent(this, Activity11Compose.class);
                    break;
                case 12:
                    intent = new Intent(this, Activity12Add.class);
                    break;
                case 13:
                    intent = new Intent(this, Activity13Logic.class);
                    break;
                case 14:
                    intent = new Intent(this, Activity14Splicing.class);
                    break;
                case 15:
                    intent = new Intent(this, Activity15Normalization.class);
                    break;
                case 16:
                    intent = new Intent(this, Activity16Blur.class);
                    break;
                case 17:
                    intent = new Intent(this, Activity17Median.class);
                    break;
                default:
                    intent = new Intent(this, RecyclerViewActivity.class);
                    break;
            }
            startActivity(intent);
        });

        binding.textViewOpenCV.setOnClickListener(v -> {
            intent = new Intent(this, RecyclerViewActivity.class);
            startActivity(intent);
        });

    }
    /*public void setListViewHeight(ListView listView) {
//获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {//listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView); //获得每个子item的视图
            listItem.measure(0, 0); //先判断写入的widthMeasureSpec和heightMeasureSpec是否和当前的值相等，如果不等，重新调用onMeasure()，计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); //累加不解释，统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)); //加上每个item之间的距离，listView.getDividerHeight()获取子项间分隔符占用的高度
        listView.setLayoutParams(params);//params.height最后得到整个ListView完整显示需要的高度
    }*/

    private void setTextViewStyles(TextView textView) {
        LinearGradient mLinearGradient = new LinearGradient(0, 0, textView.getPaint().getTextSize() * textView.getText().length(), 0, Color.parseColor("#FF5722"), Color.parseColor("#FF6200EE"), Shader.TileMode.CLAMP);
        //LinearGradient mLinearGradient = new LinearGradient(0,0,textView.getPaint().getTextSize() * textView.getText().length(),0,new int[]{R.color.purple_500,R.color.purple_200,R.color.orange},null, Shader.TileMode.CLAMP);

        textView.getPaint().setShader(mLinearGradient);
        textView.invalidate();
    }

    private void initItems() {
        MyItem designBy = new MyItem("旧版主页", R.drawable.home);
        itemList.add(designBy);

        MyItem activity1Gray = new MyItem("图像灰度化",
                R.drawable.ico1);
        itemList.add(activity1Gray);

        MyItem activity2Choose = new MyItem("图像选择", R.drawable.ico2);
        itemList.add(activity2Choose);

        MyItem activity3Save = new MyItem("图像保存", R.drawable.ico3);
        itemList.add(activity3Save);

        MyItem activity4Cut = new MyItem("图像裁剪", R.drawable.ico4);
        itemList.add(activity4Cut);

        MyItem activity5Draw = new MyItem("图像绘制", R.drawable.ico5);
        itemList.add(activity5Draw);

        MyItem activity6Read1 = new MyItem("图像线程池中取反", R.drawable.ico6);
        itemList.add(activity6Read1);

        MyItem activity7Read2 = new MyItem("图像多线程取反", R.drawable.ico7);
        itemList.add(activity7Read2);

        MyItem activity8Read3 = new MyItem("图像直接取反", R.drawable.ico8);
        itemList.add(activity8Read3);

        MyItem activity9Channel = new MyItem("图像通道分离与合并", R.drawable.ico9);
        itemList.add(activity9Channel);

        MyItem activity10Division = new MyItem("图像均值分割", R.drawable.ico10);
        itemList.add(activity10Division);

        MyItem activity11Compose = new MyItem("图像叠加", R.drawable.ico11);
        itemList.add(activity11Compose);

        MyItem activity12 = new MyItem("图像亮度与对比度", R.drawable.ico12);
        itemList.add(activity12);

        MyItem activity13 = new MyItem("图像逻辑操作", R.drawable.ico13);
        itemList.add(activity13);

        MyItem activity14 = new MyItem("图像拼接", R.drawable.ico14);
        itemList.add(activity14);

        MyItem activity15 = new MyItem("图像归一化", R.drawable.ico15);
        itemList.add(activity15);

        MyItem activity16 = new MyItem("图像模糊", R.drawable.ico16);
        itemList.add(activity16);

        MyItem activity17 = new MyItem("图像中值滤波", R.drawable.ico17);
        itemList.add(activity17);

        MyItem activityExam = new MyItem("Examination", R.drawable.ico0);
        itemList.add(activityExam);
    }
}

class ItemAdapter extends ArrayAdapter<MyItem> {

    private final int resourceId;

    public ItemAdapter(Context context,         //context上下文
                       int textViewResourceId,
                       List<MyItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    //重写getView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //获取当前项的实例
        MyItem myItem = getItem(position);
        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            //inflate出子项布局，实例化其中的图片控件和文本控件
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);

            viewHolder = new ViewHolder();
            viewHolder.fruitImage = (ImageView) view.findViewById(R.id.imageViewIntent);
            viewHolder.fruitName = (TextView) view.findViewById(R.id.textViewIntent);
            //缓存图片控件和文本控件的实例
            view.setTag(viewHolder);
        } else {
            view = convertView;
            //取出缓存
            viewHolder = (ViewHolder) view.getTag();
        }

        //直接使用缓存中的图片控件和文本控件的实例
        //图片控件设置图片资源
        viewHolder.fruitImage.setImageResource(myItem.getImageId());
        //文本控件设置文本内容
        viewHolder.fruitName.setText(myItem.getName());

        return view;
    }

    //内部类
    static class ViewHolder {
        ImageView fruitImage;
        TextView fruitName;
    }
}

class MyItem {
    private final String name;

    private final int imageId;

    public MyItem(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}

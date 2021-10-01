package com.x.edu.opencv;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.x.edu.opencv.databinding.ActivityRecyclerViewBinding;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private ActivityRecyclerViewBinding binding;

    private Intent intent;

    private RecyclerView mRecycleView;
    private MyRecycleViewAdapter mAdapter;//适配器
    private LinearLayoutManager mLinearLayoutManager;//布局管理器
    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecyclerViewBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= 23) {
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;//View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR设置状态栏文字为灰色（默认为白色View.SYSTEM_UI_FLAG_LAYOUT_STABLE）
            decorView.setSystemUiVisibility(option);
            //themes已经设置透明导航栏（小白条）
            //getWindow().setNavigationBarColor(Color.TRANSPARENT);
            //getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setTextViewStyles(binding.textViewRecycler);


        mList = new ArrayList<String>();
        mRecycleView = findViewById(R.id.recyclerView);
        //初始化数据
        initData(mList);
        //创建布局管理器，垂直设置LinearLayoutManager.VERTICAL，水平设置LinearLayoutManager.HORIZONTAL
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //创建适配器，将数据传递给适配器
        mAdapter = new MyRecycleViewAdapter(mList);
        //设置布局管理器
        mRecycleView.setLayoutManager(mLinearLayoutManager);
        //设置适配器adapter
        mRecycleView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MyRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(getApplicationContext(), "第" + position + "条数据", Toast.LENGTH_SHORT).show();

                switch (position) {
                    case 1:
                        intent = new Intent(RecyclerViewActivity.this, ExamActivity1Show.class);
                        break;
                    case 2:
                        intent = new Intent(RecyclerViewActivity.this, ExamActivity2Draw.class);
                        break;
                    case 3:
                        intent = new Intent(RecyclerViewActivity.this, ExamActivity3Montage.class);
                        break;
                    case 4:
                        intent = new Intent(RecyclerViewActivity.this, ExamActivity4Division.class);
                        break;
                    case 5:
                        intent = new Intent(RecyclerViewActivity.this, ExamActivity5Crop.class);
                        break;
                    default:
                        intent = new Intent(RecyclerViewActivity.this, ListViewActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });

    }

    public void initData(List<String> list) {
        list.add("图像显示");
        list.add("图像绘制");
        list.add("图像拼接");
        list.add("图像分割");
        list.add(getString(R.string.exam_05));
        //list.add("图像开运算");
    }

    private void setTextViewStyles(TextView textView) {
        LinearGradient mLinearGradient = new LinearGradient(0, 0, textView.getPaint().getTextSize() * textView.getText().length(), 0, Color.parseColor("#FF5722"), Color.parseColor("#FF6200EE"), Shader.TileMode.CLAMP);
        //LinearGradient mLinearGradient = new LinearGradient(0,0,textView.getPaint().getTextSize() * textView.getText().length(),0,new int[]{R.color.purple_500,R.color.purple_200,R.color.orange},null, Shader.TileMode.CLAMP);

        textView.getPaint().setShader(mLinearGradient);
        textView.invalidate();
    }
}

class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyHolder> {

    private List<String> mList;//数据源

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
    public void onBindViewHolder(final MyHolder holder, int position) {
        holder.textView.setText(mList.get(position).toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, holder.getAdapterPosition() + 1);
                }
            }
        });
    }

    MyRecycleViewAdapter(List<String> list) {
        mList = list;
    }

    //创建ViewHolder并返回，后续item布局里控件都是从ViewHolder中取出
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //将我们自定义的item布局转换为View
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.intent_item_recycler, parent, false);
        //将view传递给我们自定义的ViewHolder
        MyHolder holder = new MyHolder(view);
        //返回这个MyHolder实体
        return holder;
    }

    //获取数据源总的条数
    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 自定义的ViewHolder
     */
    static class MyHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewIntentRecycler);
        }
    }
}
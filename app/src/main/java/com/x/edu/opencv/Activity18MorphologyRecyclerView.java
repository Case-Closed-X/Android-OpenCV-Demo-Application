package com.x.edu.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.x.edu.opencv.databinding.Activity18MorphologyRecyclerViewBinding;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class Activity18MorphologyRecyclerView extends AppCompatActivity {

    private Activity18MorphologyRecyclerViewBinding binding;//视图绑定，替代findViewById()

    private RecyclerView mRecycleView;//RecyclerView
    private MorphologyRecycleViewAdapter mAdapter;//适配器
    private LinearLayoutManager mLinearLayoutManager;//布局管理器

    private final List<MorphologyItem> mList = new ArrayList<>();//传入的Item

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = Activity18MorphologyRecyclerViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mRecycleView = binding.Activity18RecyclerView;

        //初始化数据
        initData(mList);
        //创建布局管理器，垂直设置LinearLayoutManager.VERTICAL，水平设置LinearLayoutManager.HORIZONTAL
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //创建适配器，将数据传递给适配器
        mAdapter = new MorphologyRecycleViewAdapter(mList);
        //设置布局管理器
        mRecycleView.setLayoutManager(mLinearLayoutManager);
        //设置适配器adapter
        mRecycleView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((view, position) -> {
            switch (position + 1) {
                case 1:
                    Morphology(1, BitmapFactory.decodeResource(getResources(), R.drawable.bg1), "膨胀", "求高亮部分区域最大值");
                    break;
                case 2:
                    Morphology(2, BitmapFactory.decodeResource(getResources(), R.drawable.bg3), "腐蚀", "求高亮部分区域最小值");
                    break;
                case 3:
                    Morphology(3, BitmapFactory.decodeResource(getResources(), R.drawable.sera), "开运算", "腐蚀后膨胀，清除噪点");
                    break;
                case 4:
                    Morphology(4, BitmapFactory.decodeResource(getResources(), R.drawable.conan), "闭运算", "膨胀后腐蚀，融合连接");
                    break;
                case 5:
                    Morphology(5, BitmapFactory.decodeResource(getResources(), R.drawable.round), "黑帽", "突出更暗的区域");
                    break;
                case 6:
                    Morphology(6, BitmapFactory.decodeResource(getResources(), R.drawable.sun), "顶帽", "突出更亮的区域");
                    break;
                case 7:
                    Morphology(7, BitmapFactory.decodeResource(getResources(), R.drawable.microsoft), "梯度", "保留边缘轮廓");
                    break;
            }
        });

        mAdapter.setOnLongClickListener((view, position) -> {//长按重置Item
            switch (position +1){
                case 1:
                    showLayoutDialog(1, BitmapFactory.decodeResource(getResources(), R.drawable.bg1), "膨胀", "求高亮部分区域最大值");
                    break;
                case 2:
                    showLayoutDialog(2, BitmapFactory.decodeResource(getResources(), R.drawable.bg3), "腐蚀", "求高亮部分区域最小值");
                    break;
                case 3:
                    showLayoutDialog(3, BitmapFactory.decodeResource(getResources(), R.drawable.sera), "开运算", "腐蚀后膨胀，清除噪点");
                    break;
                case 4:
                    showLayoutDialog(4, BitmapFactory.decodeResource(getResources(), R.drawable.conan), "闭运算", "膨胀后腐蚀，融合连接");
                    break;
                case 5:
                    showLayoutDialog(5, BitmapFactory.decodeResource(getResources(), R.drawable.round), "黑帽", "突出更暗的区域");
                    break;
                case 6:
                    showLayoutDialog(6, BitmapFactory.decodeResource(getResources(), R.drawable.sun), "顶帽", "突出更亮的区域");
                    break;
                case 7:
                    showLayoutDialog(7, BitmapFactory.decodeResource(getResources(), R.drawable.microsoft), "梯度", "保留边缘轮廓");
                    break;
            }
        });
    }

    //图像形态学处理
    private void Morphology(int position, Bitmap bitmap, String CardTextTitle, String CardTextContent) {
        Mat mat = new Mat();
        Utils.bitmapToMat(bitmap, mat);
        //Imgproc.cvtColor(mat,mat,Imgproc.COLOR_BGR2GRAY);//无需灰度化

        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(15, 15), new Point(-1, -1));//内核，宽高必须是奇数

        switch (position) {
            case 1:
                Imgproc.morphologyEx(mat, mat, Imgproc.MORPH_DILATE, kernel);//膨胀
                break;
            case 2:
                Imgproc.morphologyEx(mat, mat, Imgproc.MORPH_ERODE, kernel);//腐蚀
                break;
            case 3:
                Imgproc.morphologyEx(mat, mat, Imgproc.MORPH_OPEN, kernel);//开运算
                break;
            case 4:
                Imgproc.morphologyEx(mat, mat, Imgproc.MORPH_CLOSE, kernel);//闭运算
                break;
            case 5:
                Imgproc.morphologyEx(mat, mat, Imgproc.MORPH_BLACKHAT, kernel);//黑帽
                break;
            case 6:
                Imgproc.morphologyEx(mat, mat, Imgproc.MORPH_TOPHAT, kernel);//顶帽
                break;
            case 7:
                Imgproc.morphologyEx(mat, mat, Imgproc.MORPH_GRADIENT, kernel);//梯度
                break;
        }

        Utils.matToBitmap(mat, bitmap);
        mAdapter.changeData(position - 1, bitmap, CardTextTitle, CardTextContent);
    }

    private void showLayoutDialog(int position, Bitmap bitmap, String CardTextTitle, String CardTextContent) {
        /*// Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定重置"+CardTextTitle+"运算吗？")
                .setPositiveButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.dialog_ok, (dialog, id) -> mAdapter.changeData(position - 1, bitmap, CardTextTitle, CardTextContent));
        builder.create().show();*/

        //加载布局并初始化组件
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_reset,null);
        TextView dialogReset = (TextView) dialogView.findViewById(R.id.textViewDialogReset);
        //TextView dialogDelete = (TextView) dialogView.findViewById(R.id.textViewDialogDelete);
        TextView dialogCancel = (TextView) dialogView.findViewById(R.id.textViewDialogCancel);
        final AlertDialog.Builder layoutDialog = new AlertDialog.Builder(this,R.style.TransparentDialog);
        //layoutDialog.setTitle(getString(R.string.dialog_custom_layout_text));
        //layoutDialog.setIcon(R.mipmap.ic_launcher_round);
        layoutDialog.setView(dialogView);
        AlertDialog dialog=layoutDialog.create();
        //设置组件
        dialogReset.setOnClickListener(v ->{
                mAdapter.changeData(position - 1, bitmap, CardTextTitle, CardTextContent);
                dialog.dismiss();
        });
        //dialogDelete.setOnClickListener(v -> mAdapter.removeData(position-1));
        dialogCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    public void initData(List<MorphologyItem> itemList) {//初始化数据
        MorphologyItem item1 = new MorphologyItem(BitmapFactory.decodeResource(getResources(), R.drawable.bg1), "膨胀", "求高亮部分区域最大值");
        itemList.add(item1);

        MorphologyItem item2 = new MorphologyItem(BitmapFactory.decodeResource(getResources(), R.drawable.bg3), "腐蚀", "求高亮部分区域最小值");
        itemList.add(item2);

        MorphologyItem item3 = new MorphologyItem(BitmapFactory.decodeResource(getResources(), R.drawable.sera), "开运算", "腐蚀后膨胀，清除噪点");
        itemList.add(item3);

        MorphologyItem item4 = new MorphologyItem(BitmapFactory.decodeResource(getResources(), R.drawable.conan), "闭运算", "膨胀后腐蚀，融合连接");
        itemList.add(item4);

        MorphologyItem item5 = new MorphologyItem(BitmapFactory.decodeResource(getResources(), R.drawable.round), "黑帽", "突出更暗的区域");//闭运算结果与原图像之差
        itemList.add(item5);

        MorphologyItem item6 = new MorphologyItem(BitmapFactory.decodeResource(getResources(), R.drawable.sun), "顶帽", "突出更亮的区域");//原图像与开运算的结果图之差
        itemList.add(item6);

        MorphologyItem item7 = new MorphologyItem(BitmapFactory.decodeResource(getResources(), R.drawable.microsoft), "梯度", "保留边缘轮廓");//膨胀图与腐蚀图之差
        itemList.add(item7);
    }
}

class MorphologyRecycleViewAdapter extends RecyclerView.Adapter<MorphologyRecycleViewAdapter.ViewHolder> {

    private final List<MorphologyItem> mList;//数据源

    private OnItemClickListener onItemClickListener;//点击监听器
    private OnLongClickListener onLongClickListener;//长按监听器

    //设置点击监听
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener){
        this.onLongClickListener = onLongClickListener;
    }

    //自定义点击监听接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnLongClickListener {
        void onLongClick(View view, int position);
    }

    //通过方法提供的ViewHolder，将数据绑定到ViewHolder中
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        MorphologyItem list = mList.get(position);
        holder.CardImageView.setImageBitmap(list.getCardImage());
        holder.CardTextTitleView.setText(list.getCardTextTitle());
        holder.CardTextContentView.setText(list.getCardTextContent());

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, holder.getAdapterPosition());
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (onLongClickListener != null) {
                onLongClickListener.onLongClick(v, holder.getAdapterPosition());
            }
            return false;
        });
    }

    MorphologyRecycleViewAdapter(List<MorphologyItem> list) {
        mList = list;
    }

    //创建ViewHolder并返回，后续item布局里控件都是从ViewHolder中取出，即加载布局
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //将自定义的item布局转换为View
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler18, parent, false);
        //将view传递给我们自定义的ViewHolder，即返回这个ViewHolder实体
        return new ViewHolder(view);
    }

    //获取数据源总的条数
    @Override
    public int getItemCount() {
        return mList.size();
    }

    //自定义ViewHolder，绑定布局中的id
    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView CardImageView;
        TextView CardTextTitleView;
        TextView CardTextContentView;

        public ViewHolder(View itemView) {
            super(itemView);
            CardImageView = itemView.findViewById(R.id.CardImage);
            CardTextTitleView = itemView.findViewById(R.id.cardTextAbout);
            CardTextContentView = itemView.findViewById(R.id.CardTextContent);
        }
    }

    //移除数据
    public void removeData(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    //新增数据
    public void addData(int position, Bitmap CardImage, String CardTextTitle, String CardTextContent) {
        mList.add(position, new MorphologyItem(CardImage, CardTextTitle, CardTextContent));
        notifyItemInserted(position);
    }

    //更改某个位置的数据
    public void changeData(int position, Bitmap CardImage, String CardTextTitle, String CardTextContent) {
        mList.set(position, new MorphologyItem(CardImage, CardTextTitle, CardTextContent));
        notifyItemChanged(position);
    }
}

class MorphologyItem {

    //Item对应图片的资源id，在drawable中
    private final Bitmap CardImage;

    //Item的TextView内容
    private final String CardTextTitle;
    private final String CardTextContent;

    //构造函数，适用于初始化、添加或更改数据
    public MorphologyItem(Bitmap CardImage, String CardTextTitle, String CardTextContent) {
        this.CardImage = CardImage;
        this.CardTextTitle = CardTextTitle;
        this.CardTextContent = CardTextContent;
    }

    //getter
    public Bitmap getCardImage() {
        return CardImage;
    }

    public String getCardTextTitle() {
        return CardTextTitle;
    }

    public String getCardTextContent() {
        return CardTextContent;
    }
}
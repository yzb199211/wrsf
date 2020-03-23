package com.yyy.wrsf.view.popwin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.view.recycle.RecyclerViewDivider;
import com.yyy.yyylibrary.wheel.interfaces.IPickerViewData;

import java.util.List;

public class Popwin<T extends IPickerViewData> extends PopupWindow {
    private Context context;
    private List<T> list;
    private final View view;
    private RecyclerView recyclerView;
    private SelectAdapter adapter;
    private OnItemClickListener onItemClickListener;
    private int width = -1;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public Popwin(Context context, List<T> list) {
        super(context);
        this.list = list;
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.popwin, null);//alt+ctrl+f
        setContentView(view);
        initView();
    }

    public Popwin(Context context, List<T> list, int width) {
        super(context);
        this.list = list;
        this.context = context;
        this.width = width;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.popwin, null);//alt+ctrl+f
        setContentView(view);
        initView();
    }

    private void initView() {
        initMainView();
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new RecyclerViewDivider(context, LinearLayout.VERTICAL));
        initAdapter();
    }

    private void initMainView() {
        this.setWidth(width == -1 ? context.getResources().getDimensionPixelOffset(R.dimen.dp_80) : width);
        // 设置弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置弹出窗体可点击()
        this.setFocusable(true);
        this.setOutsideTouchable(true);
    }

    private void initAdapter() {
        adapter = new SelectAdapter(context, list);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(pos);
                    Popwin.this.dismiss();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }
}

package com.yyy.wrsf.mine.notice;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.beans.NoticeB;
import com.yyy.wrsf.mine.notice.view.INoticeDetailV;
import com.yyy.wrsf.view.topview.TopView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeDetailActivity extends BaseActivity implements INoticeDetailV {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_detail)
    TextView tvDetail;

    private NoticeB noticeB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        noticeB = new Gson().fromJson(getIntent().getStringExtra("data"), new TypeToken<List<NoticeB>>() {
        }.getType());
        tvTitle.setText(TextUtils.isEmpty(noticeB.getTitle()) ? "" : noticeB.getTitle());
        tvDate.setText(TextUtils.isEmpty(noticeB.getModifyDate()) ? "" : noticeB.getModifyDate());
        tvDetail.setText(TextUtils.isEmpty(noticeB.getNoticeContent()) ? "" : noticeB.getNoticeContent());
    }

    @Override
    public String getId() {
        return noticeB.getId();
    }

    @Override
    public void startLoading() {

    }

    @Override
    public void finishLoading(@Nullable String s) {

    }

    @Override
    public void toast(String s) {

    }
}

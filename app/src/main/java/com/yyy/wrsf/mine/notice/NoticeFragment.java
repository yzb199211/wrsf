package com.yyy.wrsf.mine.notice;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yyy.wrsf.R;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.beans.NoticeB;
import com.yyy.wrsf.beans.filter.NoticeFilterB;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.NetUtil;
import com.yyy.wrsf.utils.net.net.PagerRequestBean;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.net.ResponseListener;
import com.yyy.wrsf.utils.net.net.Result;
import com.yyy.wrsf.utils.net.notice.NoticeUrl;
import com.yyy.wrsf.view.recycle.RecyclerViewDivider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeFragment extends Fragment implements XRecyclerView.LoadingListener {


    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;

    SharedPreferencesHelper preferencesHelper;
    List<NoticeB> notices = new ArrayList<>();
    NoticeAdpter noticeAdpter;
    private PagerRequestBean<NoticeFilterB> pager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferencesHelper = new SharedPreferencesHelper(getActivity(), getString(R.string.preferenceCache));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
        ButterKnife.bind(this, view);
        init();
        getData();
        return view;
    }


    private void init() {
        initRecycle();
        initPager();
    }

    private void initPager() {
        pager = new PagerRequestBean();
        pager.setPageIndex(0);
        pager.setPageSize(500);
        NoticeFilterB filterModel = new NoticeFilterB();
        filterModel.setMemberId((Integer) preferencesHelper.getSharedPreference("recNo", 0));
        pager.setQueryParam(filterModel);
    }

    private void initRecycle() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        recyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
        recyclerView.getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingMoreEnabled(false);
        recyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setLoadingListener(this);
    }

    private void getData() {
        new NetUtil(getParams(), NetConfig.address + NoticeUrl.getNotice, RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                Log.e(this.getClass().getName(), "data:" + string);
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        List<NoticeB> list = new Gson().fromJson(result.getData(), new TypeToken<List<NoticeB>>() {
                        }.getType());
                        if (list != null) {
                            notices.clear();
                            notices.addAll(list);
                            refrishList();
                        }
                    } else {
                        LoadingFinish(result.getMsg());
                        Log.e(NoticeFragment.class.getName(), result.getMsg());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LoadingFinish(e.getMessage());
                }

            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();
                LoadingFinish(e.getMessage());
            }
        });
    }

    private void refrishList() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (noticeAdpter == null) {
                    noticeAdpter = new NoticeAdpter(getActivity(), notices);
                    noticeAdpter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int pos) {
                        }
                    });
                    recyclerView.setAdapter(noticeAdpter);
                } else {
                    noticeAdpter.notifyDataSetChanged();
                }
            }
        });
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("param", new Gson().toJson(pager)));
        return params;
    }



    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onResume() {
        super.onResume();
        //注册EventBus,注意参数是this，传入activity会报错
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        //取消EventBus注册
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(String s) {
        getData();
    }

    private void LoadingFinish(String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (StringUtil.isNotEmpty(msg)) {
                    Toast(msg);
                }
                LoadingDialog.cancelDialogForLoading();
            }
        });
    }

    private void Toast(String msg) {
        Toasts.showShort(getActivity(), msg);
    }

}

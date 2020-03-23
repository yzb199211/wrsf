package com.yyy.wrsf.mine.shipping;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.R;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.model.PublicArray;
import com.yyy.wrsf.model.PublicModel;
import com.yyy.wrsf.model.filter.PublicFilterModel;
import com.yyy.wrsf.utils.PublicCode;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.utils.net.NetConfig;
import com.yyy.wrsf.utils.net.NetParams;
import com.yyy.wrsf.utils.net.NetUtil;
import com.yyy.wrsf.utils.net.RequstType;
import com.yyy.wrsf.utils.net.ResponseListener;
import com.yyy.wrsf.utils.net.Result;
import com.yyy.wrsf.utils.net.publics.PublicUrl;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.textselect.TextMenuItem;
import com.yyy.wrsf.view.topview.TopView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShippingGoodsActivity extends AppCompatActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.tmi_goods_name)
    TextMenuItem tmiGoodsName;
    @BindView(R.id.ecv_weight)
    EditClearView ecvWeight;
    @BindView(R.id.ecv_volume)
    EditClearView ecvVolume;
    @BindView(R.id.ecv_density)
    EditClearView ecvDensity;
    @BindView(R.id.ecv_num)
    EditClearView ecvNum;
    @BindView(R.id.tmi_delivery)
    TextMenuItem tmiDelivery;
    @BindView(R.id.tmi_trans)
    TextMenuItem tmiTrans;
    @BindView(R.id.tmi_send)
    TextMenuItem tmiSend;

    private List<PublicModel> goods = new ArrayList<>();
    private List<PublicModel> send = new ArrayList<>();
    private List<PublicModel> trans = new ArrayList<>();
    private List<PublicModel> delivery = new ArrayList<>();

    private PublicFilterModel publicFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_goods);
        ButterKnife.bind(this);
        init();
        getData();
    }

    private void init() {
        initTop();
        initData();
    }

    private void initData() {
        initPublicFilter();
    }

    private void initTop() {
        topView.setOnLeftClickListener(() -> {
            finish();
        });
    }

    private void initPublicFilter() {
        publicFilter = new PublicFilterModel();
        List<Integer> list = new ArrayList<>();
        list.add(PublicCode.CarType);
        list.add(PublicCode.CarStatus);
        publicFilter.setPublicCodes(list);
    }

    private void getData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(), NetConfig.address + PublicUrl.getPublic, RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        List<PublicArray> list = new Gson().fromJson(result.getData(), new TypeToken<List<PublicArray>>() {
                        }.getType());
                        setList(list);
                        LoadingFinish(null);

                    } else {
                        LoadingFinish(result.getMsg());
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

    private void setList(List<PublicArray> list) {
        for (PublicArray array : list) {
            if (array.equals(PublicCode.GoodsType) && array.getPlantPublicDetails() != null) {
                goods.addAll(array.getPlantPublicDetails());
            } else if (array.equals(PublicCode.SendType) && array.getPlantPublicDetails() != null) {
                send.addAll(array.getPlantPublicDetails());
            } else if (array.equals(PublicCode.DeliveryType) && array.getPlantPublicDetails() != null) {
                delivery.addAll(array.getPlantPublicDetails());
            } else if (array.equals(PublicCode.transType) && array.getPlantPublicDetails() != null) {
                trans.addAll(array.getPlantPublicDetails());
            }
        }
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("params", new Gson().toJson(publicFilter)));
        return params;
    }

    @OnClick({R.id.tmi_goods_name, R.id.tmi_delivery, R.id.tmi_trans, R.id.tmi_send, R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tmi_goods_name:
                break;
            case R.id.tmi_delivery:
                break;
            case R.id.tmi_trans:
                break;
            case R.id.tmi_send:
                break;
            case R.id.btn_add:

                break;
            default:
                break;
        }
    }

    private void LoadingFinish(String msg) {
        runOnUiThread(new Runnable() {
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
        Toasts.showShort(this, msg);
    }
}

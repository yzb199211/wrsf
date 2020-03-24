package com.yyy.wrsf.mine.shipping;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.R;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.model.PublicArray;
import com.yyy.wrsf.model.PublicModel;
import com.yyy.wrsf.model.ShipGoodsModel;
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
import com.yyy.wrsf.view.popwin.Popwin;
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
    private Popwin popGoods;
    private Popwin popSend;
    private Popwin popTrans;
    private Popwin popDelivery;
    private ShipGoodsModel goodsModel;

    private boolean isEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_goods);
        ButterKnife.bind(this);
        init();
        getData();
    }

    private void initTop() {
        topView.setOnLeftClickListener(() -> {
            finish();
        });
    }

    private void init() {
        initTop();
        initdensity();
        initData();
    }


    private void initdensity() {
        setWeightListener();
        setVolumeListener();
    }


    private void setWeightListener() {
        ecvWeight.setOnTextChangeAfter(() -> {
            if (ecvWeight.getText().length() > 0 && Integer.parseInt(ecvWeight.getText()) > 0 & TextUtils.isEmpty(ecvVolume.getText()) && Integer.parseInt(ecvVolume.getText()) > 0) {
                ecvDensity.setText(Integer.parseInt(ecvWeight.getText()) / (1000 * Integer.parseInt(ecvVolume.getText())) + "");
            }
        });
    }

    private void setVolumeListener() {
        ecvVolume.setOnTextChangeAfter(() -> {
            if (ecvWeight.getText().length() > 0 && Integer.parseInt(ecvWeight.getText()) > 0 & TextUtils.isEmpty(ecvVolume.getText()) && Integer.parseInt(ecvVolume.getText()) > 0) {
                ecvDensity.setText(Integer.parseInt(ecvWeight.getText()) / (1000 * Integer.parseInt(ecvVolume.getText())) + "");
            }
        });
    }

    private void initData() {
        initPublicFilter();
        initGoodsModel();
    }

    private void initGoodsModel() {
        String data = getIntent().getStringExtra("data");
        isEmpty = TextUtils.isEmpty(data);
        goodsModel = isEmpty ? new ShipGoodsModel() : new Gson().fromJson(data, new TypeToken<List<ShipGoodsModel>>() {
        }.getType());
        setGoodsView();
    }

    private void setGoodsView() {
        tmiGoodsName.setText(goodsModel.getGoodsName());

    }

    private void initPublicFilter() {
        publicFilter = new PublicFilterModel();
        List<Integer> list = new ArrayList<>();
        list.add(PublicCode.GoodsType);
        list.add(PublicCode.SendType);
        list.add(PublicCode.DeliveryType);
        list.add(PublicCode.transType);
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
                initGoods();
                break;
            case R.id.tmi_delivery:
                initDelivery();
                break;
            case R.id.tmi_trans:
                initTrans();
                break;
            case R.id.tmi_send:
                initSend();
                break;
            case R.id.btn_add:

                break;
            default:
                break;
        }
    }

    private void initGoods() {
        if (popGoods == null) {
            popGoods = new Popwin(this, goods, tmiGoodsName.getTextView().getWidth());
            popGoods.showAsDropDown(tmiGoodsName.getTextView());
            popGoods.setOnItemClickListener((int pos) -> {
                String type = goods.get(pos).getPickerViewText();
                tmiGoodsName.setText(type);
                goodsModel.setGoodsId(goods.get(pos).getRecNo());
                goodsModel.setGoodsName(goods.get(pos).getPickerViewText());
            });
        } else {
            popGoods.showAsDropDown(tmiGoodsName.getTextView());
        }
    }

    private void initDelivery() {
        if (popDelivery == null) {
            popDelivery = new Popwin(this, delivery, tmiDelivery.getTextView().getWidth());
            popDelivery.showAsDropDown(tmiDelivery.getTextView());
            popDelivery.setOnItemClickListener((int pos) -> {
                String type = delivery.get(pos).getPickerViewText();
                tmiDelivery.setText(type);
                goodsModel.setDeliveryId(delivery.get(pos).getRecNo());
                goodsModel.setDeliveryName(delivery.get(pos).getPickerViewText());
            });
        } else {
            popDelivery.showAsDropDown(tmiDelivery.getTextView());
        }
    }

    private void initSend() {
        if (popSend == null) {
            popSend = new Popwin(this, send, tmiSend.getTextView().getWidth());
            popSend.showAsDropDown(tmiSend.getTextView());
            popSend.setOnItemClickListener((int pos) -> {
                String type = send.get(pos).getPickerViewText();
                tmiSend.setText(type);
                goodsModel.setSendId(send.get(pos).getRecNo());
                goodsModel.setSendName(send.get(pos).getPickerViewText());
            });
        } else {
            popSend.showAsDropDown(tmiSend.getTextView());
        }
    }

    private void initTrans() {
        if (popTrans == null) {
            popTrans = new Popwin(this, trans, tmiTrans.getTextView().getWidth());
            popTrans.showAsDropDown(tmiTrans.getTextView());
            popTrans.setOnItemClickListener((int pos) -> {
                String type = trans.get(pos).getPickerViewText();
                tmiTrans.setText(type);
                goodsModel.setTransId(trans.get(pos).getRecNo());
                goodsModel.setTransName(trans.get(pos).getPickerViewText());
            });
        } else {
            popTrans.showAsDropDown(tmiTrans.getTextView());
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

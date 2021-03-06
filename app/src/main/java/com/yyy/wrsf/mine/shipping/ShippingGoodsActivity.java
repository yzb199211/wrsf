package com.yyy.wrsf.mine.shipping;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.beans.filter.PublicFilterB;
import com.yyy.wrsf.beans.price.PriceCalB;
import com.yyy.wrsf.beans.publicm.PublicArray;
import com.yyy.wrsf.beans.publicm.PublicBean;
import com.yyy.wrsf.beans.ship.ShipGoodsB;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.enums.TransTypeEnum;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.PublicCode;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.NetUtil;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.net.ResponseListener;
import com.yyy.wrsf.utils.net.net.Result;
import com.yyy.wrsf.utils.net.publics.PublicUrl;
import com.yyy.wrsf.utils.net.ship.ShipUrl;
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

public class ShippingGoodsActivity extends BaseActivity {

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
    @BindView(R.id.ecv_goods_name)
    EditClearView ecvGoodsName;
    @BindView(R.id.btn_add)
    TextView btnAdd;

    private List<PublicBean> goods = new ArrayList<>();
    private List<PublicBean> send = new ArrayList<>();
    private List<PublicBean> trans = new ArrayList<>();
    private List<PublicBean> delivery = new ArrayList<>();

    private PublicFilterB publicFilter;
    private Popwin popGoods;
    private Popwin popSend;
    private Popwin popTrans;
    private Popwin popDelivery;
    private ShipGoodsB goodsModel;

    private PriceCalB priceCal;
    private boolean isEmpty;
    private boolean isConfirm = false;

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
        ecvWeight.setOnTextChangeAfter((Editable s) -> {
            String s1 = s.toString();
            if (s1.length() > 0 && Long.parseLong(s1) > 0 && StringUtil.isNotEmpty(ecvVolume.getText()) && Long.parseLong(ecvVolume.getText()) > 0) {
                ecvDensity.setText(ShipUtil.getDensity(Double.parseDouble(s1), Double.parseDouble(ecvVolume.getText())) + "");
            } else {
                ecvDensity.setText("0");
            }
        });
    }

    private void setVolumeListener() {
        ecvVolume.setOnTextChangeAfter((Editable s) -> {
            String s1 = s.toString();
            if (s1.length() > 0 && Double.parseDouble(s1) > 0 && StringUtil.isNotEmpty(ecvWeight.getText()) && Long.parseLong(ecvWeight.getText()) > 0) {
                ecvDensity.setText(ShipUtil.getDensity(Double.parseDouble(ecvWeight.getText()), Double.parseDouble(s1)) + "");
            } else {
                ecvDensity.setText("0");
            }
        });
    }

    private void initData() {
        initPublicFilter();
        initGoodsModel();
        initPriceCal();
        initTransData();

    }

    private void initGoodsModel() {
        String data = getIntent().getStringExtra("data");
        isEmpty = TextUtils.isEmpty(data);
        goodsModel = isEmpty ? new ShipGoodsB() : new Gson().fromJson(data, ShipGoodsB.class);
        setGoodsView();

    }

    private void initSelect() {
        runOnUiThread(()->{
            if (goods != null && goods.size() > 0)
                initSelectGood();
            if (send!=null && send.size()>0)
                initSelectSend();
            if (delivery!=null && delivery.size()>0){
                initSelectDeliver();
            }
            if (trans!=null&& trans.size()>0){
                initSelectTrans();
            }
        });
    }

    private void initSelectTrans() {
        String type = trans.get(0).getPickerViewText();
        tmiTrans.setText(type);
        goodsModel.setTransId(trans.get(0).getDetailCode());
        goodsModel.setTransName(trans.get(0).getPickerViewText());
    }

    private void initSelectDeliver() {
        String type = delivery.get(0).getPickerViewText();
        tmiDelivery.setText(type);
        goodsModel.setDeliveryId(delivery.get(0).getDetailCode());
        goodsModel.setDeliveryName(delivery.get(0).getPickerViewText());
    }

    private void initSelectGood() {
        tmiGoodsName.setText(goods.get(0).getPickerViewText());
        goodsModel.setGoodsId(goods.get(0).getDetailCode());
        goodsModel.setGoodsName(goods.get(0).getPickerViewText());
        if (goods.get(0).getPickerViewText().equals(getString(R.string.send_good_other))) {
            ecvGoodsName.setVisibility(View.VISIBLE);
        } else {
            ecvGoodsName.setText("");
            ecvGoodsName.setVisibility(View.GONE);
        }
    }
    private void initSelectSend() {
        String type = send.get(0).getPickerViewText();
        tmiSend.setText(type);
        goodsModel.setSendId(send.get(0).getDetailCode());
        goodsModel.setSendName(send.get(0).getPickerViewText());
    }

    private void initPriceCal() {
        priceCal = new PriceCalB();
        priceCal.setRecRegion(getIntent().getIntExtra("receiveRec", 0));
        priceCal.setSendRegion(getIntent().getIntExtra("sendRec", 0));
        priceCal.setTransCompanyRecNo(getIntent().getIntExtra("company", 0));
        priceCal.setTransShopRecNo(getIntent().getIntExtra("sendShop", 0));
        priceCal.setTransRecShopRecNo(getIntent().getIntExtra("recShop", 0));
    }

    private void initTransData() {
        switch (getIntent().getStringExtra("type")) {
            case "1":
                trans.add(new PublicBean(TransTypeEnum.PRICE_COMMON.getTransType(), TransTypeEnum.PRICE_COMMON.getDesc()));
                break;
            case "2":
                trans.add(new PublicBean(TransTypeEnum.PRICE_TWO.getTransType(), TransTypeEnum.PRICE_TWO.getDesc()));
                break;
            default:
                trans.add(new PublicBean(TransTypeEnum.PRICE_COMMON.getTransType(), TransTypeEnum.PRICE_COMMON.getDesc()));
                trans.add(new PublicBean(TransTypeEnum.PRICE_TWO.getTransType(), TransTypeEnum.PRICE_TWO.getDesc()));
                break;
        }
    }

    private void setGoodsView() {
        tmiGoodsName.setText(goodsModel.getGoodsName());
        tmiTrans.setText(goodsModel.getTransName());
        tmiSend.setText(goodsModel.getSendName());
        tmiDelivery.setText(goodsModel.getDeliveryName());
        ecvWeight.setText(goodsModel.getWeight() == 0 ? "" : goodsModel.getWeight() + "");
        ecvNum.setText(goodsModel.getNum() == 0 ? "" : goodsModel.getNum() + "");
        ecvVolume.setText(goodsModel.getVolume() == 0 ? "" : goodsModel.getVolume() + "");
        ecvDensity.setText(goodsModel.getDensity() == 0 ? "" : goodsModel.getDensity() + "");
    }

    private void initPublicFilter() {
        publicFilter = new PublicFilterB();
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
            }
//            else if (array.equals(PublicCode.transType) && array.getPlantPublicDetails() != null) {
//                trans.addAll(array.getPlantPublicDetails());
//            }
        }
        if (isEmpty) initSelect();
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
                setGoods();
                save();
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
                goodsModel.setGoodsId(goods.get(pos).getDetailCode());
                goodsModel.setGoodsName(goods.get(pos).getPickerViewText());
                if (goods.get(pos).getPickerViewText().equals(getString(R.string.send_good_other))) {
                    ecvGoodsName.setVisibility(View.VISIBLE);
                } else {
                    ecvGoodsName.setText("");
                    ecvGoodsName.setVisibility(View.GONE);
                }
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
                goodsModel.setDeliveryId(delivery.get(pos).getDetailCode());
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
                goodsModel.setSendId(send.get(pos).getDetailCode());
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
                goodsModel.setTransId(trans.get(pos).getDetailCode());
                goodsModel.setTransName(trans.get(pos).getPickerViewText());
            });
        } else {
            popTrans.showAsDropDown(tmiTrans.getTextView());
        }
    }

    private void save() {

        if (goodsModel.isEmpty()) {
            LoadingFinish(getString(R.string.send_goods_empty));
            return;
        }
        if (!isConfirm) {
            go2confirm();
            return;
        }
//        setResult(CodeUtil.ShipGoods, new Intent().putExtra("data", new Gson().toJson(goodsModel)));
//        finish();
    }

    private void go2confirm() {
        startActivityForResult(new Intent().setClass(this, ShipPayNoticeActivity.class), CodeUtil.CONFIRM);
    }

    private void getPrice() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getPriceCalParams(), NetConfig.address + ShipUrl.getPriceByContractInfo, RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    LoadingFinish(null);
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        back(result.getData());
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

    private List<NetParams> getPriceCalParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("params", new Gson().toJson(priceCal)));
        return params;
    }

    private void setPriceCal() {
        priceCal.setSize(goodsModel.getVolume());
        priceCal.setWeight(goodsModel.getWeight());
        priceCal.setSendType(goodsModel.getSendId());
        priceCal.setDeliverType(goodsModel.getDeliveryId());
        priceCal.setTransType(goodsModel.getTransId());
    }


    private void setGoods() {
        goodsModel.setWeight(TextUtils.isEmpty(ecvWeight.getText()) ? 0 : Integer.parseInt(ecvWeight.getText()));
        goodsModel.setVolume(TextUtils.isEmpty(ecvVolume.getText()) ? 0 : Double.parseDouble(ecvVolume.getText()));
        goodsModel.setNum(TextUtils.isEmpty(ecvNum.getText()) ? 0 : Integer.parseInt(ecvNum.getText()));
        goodsModel.setDensity(TextUtils.isEmpty(ecvDensity.getText()) ? 0 : Double.parseDouble(ecvDensity.getText()));
    }

    private void back(String price) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (StringUtil.isNotEmpty(ecvGoodsName.getText())) {
                    goodsModel.setGoodsName(ecvGoodsName.getText());
                }
                setResult(CodeUtil.ShipGoods,
                        new Intent()
                                .putExtra("price", price)
                                .putExtra("data", new Gson().toJson(goodsModel)));
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CodeUtil.CONFIRM) {
            setPriceCal();
            getPrice();
        }
    }
}

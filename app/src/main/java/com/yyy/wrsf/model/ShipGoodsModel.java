package com.yyy.wrsf.model;

import android.text.TextUtils;

import com.yyy.wrsf.utils.StringUtil;

public class ShipGoodsModel {
    private int goodsId;
    private String goodsName;
    private long weight;
    private long volume;
    private long num;
    private int deliveryId;
    private String deliveryName;
    private int sendId;
    private String sendName;
    private int transId;
    private String transName;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return TextUtils.isEmpty(goodsName) ? "" : goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getDeliveryName() {
        return TextUtils.isEmpty(deliveryName) ? "" : deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public int getSendId() {
        return sendId;
    }

    public void setSendId(int sendId) {
        this.sendId = sendId;
    }

    public String getSendName() {
        return TextUtils.isEmpty(sendName) ? "" : sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public int getTransId() {
        return transId;
    }

    public void setTransId(int transId) {
        this.transId = transId;
    }

    public String getTransName() {
        return TextUtils.isEmpty(transName) ? "" : transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public boolean isEmpty() {
        if (goodsId == 0 || weight == 0 || volume == 0 || num == 0 || deliveryId == 0 || sendId == 0 || transId == 0) {
            return true;
        }
        return false;
    }

    public String getData() {
        String s = "";
        if (StringUtil.isNotEmpty(goodsName)) {
            s = s + goodsName;
        }
        if (weight > 0) {
            s = TextUtils.isEmpty(s) ? s + weight + "KG" : s + "\\" + weight + "KG";
        }
        if (volume > 0) {
            s = TextUtils.isEmpty(s) ? s + volume + "m³" : s + "\\" + volume + "m³";
        }
        if (num > 0) {
            s = TextUtils.isEmpty(s) ? s + num + "件" : s + "\\" + num + "件";
        }
        return s;

    }
}

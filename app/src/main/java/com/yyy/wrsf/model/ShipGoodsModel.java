package com.yyy.wrsf.model;

import android.text.TextUtils;

public class ShipGoodsModel {
    private int goodsId;
    private String goodsName;
    private int weight;
    private int volume;
    private int num;
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
        return TextUtils.isEmpty(goodsName)?"":goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getDeliveryName() {
        return TextUtils.isEmpty(deliveryName)?"":deliveryName;
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
        return TextUtils.isEmpty(sendName)?"":sendName;
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
        return TextUtils.isEmpty(transName)?"":transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public boolean isEmpty() {
        if (goodsId == 0 || weight == 0 || volume == 0 || num == 0 || deliveryId == 0 || sendId == 0 || transId == 0) {
            return false;
        }
        return true;
    }
}

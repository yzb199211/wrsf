package com.yyy.wrsf.beans.ship;

public  class ShipRegionB {
        /**
         * recNo : 8
         * transCompanyRecno : 13
         * shopCode : DQ01
         * shopName : 丁桥网点
         * detailAdd : 丁桥园区
         * sendTel : 17858956195
         * recTel : 17858956195
         * recQu : ,1444
         * person : {"recNo":45,"memberName":"测试0331","memberTel":"17858956333","memberSex":"男","memberPetname":"哈哈","passWord":"1406900ba9403d11849de12bca99f4fb","mail":"219213@qq.com","stopYesno":0,"password":"1406900ba9403d11849de12bca99f4fb","username":"17858956333","admin":false}
         * remark : 测试
         * shopStatus : 1
         */

        private int recNo;
        private int transCompanyRecno;
        private String shopCode;
        private String shopName;
        private String detailAdd;
        private String sendTel;
        private String recTel;
        private String recQu;
        private ShipPersonB person;
        private String remark;
        private int shopStatus;

        public int getRecNo() {
            return recNo;
        }

        public void setRecNo(int recNo) {
            this.recNo = recNo;
        }

        public int getTransCompanyRecno() {
            return transCompanyRecno;
        }

        public void setTransCompanyRecno(int transCompanyRecno) {
            this.transCompanyRecno = transCompanyRecno;
        }

        public String getShopCode() {
            return shopCode;
        }

        public void setShopCode(String shopCode) {
            this.shopCode = shopCode;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getDetailAdd() {
            return detailAdd;
        }

        public void setDetailAdd(String detailAdd) {
            this.detailAdd = detailAdd;
        }

        public String getSendTel() {
            return sendTel;
        }

        public void setSendTel(String sendTel) {
            this.sendTel = sendTel;
        }

        public String getRecTel() {
            return recTel;
        }

        public void setRecTel(String recTel) {
            this.recTel = recTel;
        }

        public String getRecQu() {
            return recQu;
        }

        public void setRecQu(String recQu) {
            this.recQu = recQu;
        }

        public ShipPersonB getPerson() {
            return person;
        }

        public void setPerson(ShipPersonB person) {
            this.person = person;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getShopStatus() {
            return shopStatus;
        }

        public void setShopStatus(int shopStatus) {
            this.shopStatus = shopStatus;
        }

    }
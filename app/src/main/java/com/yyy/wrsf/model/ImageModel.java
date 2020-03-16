package com.yyy.wrsf.model;

public class ImageModel {


        /**
         * id : b9fd781fd72b1b3e26a75d6b8d921aec
         * contentType : image/*
         * size : 1003939
         * path : d:/files/b9fd781fd72b1b3e26a75d6b8d921aec.jpg
         * url : /b9fd781fd72b1b3e26a75d6b8d921aec.jpg
         * type : 1
         * showUrl : /image/b9fd781fd72b1b3e26a75d6b8d921aec.jpg
         * createTime : 1584338915421
         * updateTime : 1584338915421
         */

        private String id;
        private String contentType;
        private int size;
        private String path;
        private String url;
        private int type;
        private String showUrl;
        private long createTime;
        private long updateTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getShowUrl() {
            return showUrl;
        }

        public void setShowUrl(String showUrl) {
            this.showUrl = showUrl;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

}

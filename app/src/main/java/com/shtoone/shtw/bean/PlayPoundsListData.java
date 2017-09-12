package com.shtoone.shtw.bean;

import java.io.Serializable;
import java.util.List;

public class PlayPoundsListData {

    /**
     * data : [{"banhezhanminchen":"1号拌合站地磅","cailiaoname":"碎石(16-31.5mm)","chuchangshijian":"2017-06-14 15:42:52","gongyingshangname":"安徽祥实商贸有限公司","guobangleibie":"1","id":"30036","jinchangshijian":"2017-06-14 15:31:44","jingzhong":"34.16","maozhong":"50.64","qianchepai":"皖LA4996","remark":"调拨","sibangyuan":"董振鹏"},{"banhezhanminchen":"1号拌合站地磅","cailiaoname":"河砂(中砂)","chuchangshijian":"2017-06-02 17:21:43","gongyingshangname":"全椒润尧商贸有限公司","guobangleibie":"1","id":"30035","jinchangshijian":"2017-06-02 17:21:18","jingzhong":"","maozhong":"0.00","qianchepai":"皖LA4996","remark":"调拨","sibangyuan":"董振鹏"},{"banhezhanminchen":"1号拌合站地磅","cailiaoname":"碎石(16-31.5mm)","chuchangshijian":"2017-06-02 16:21:27","gongyingshangname":"安徽祥实商贸有限公司","guobangleibie":"1","id":"30034","jinchangshijian":"2017-06-02 16:03:51","jingzhong":"39.94","maozhong":"56.26","qianchepai":"皖LA4996","remark":"调拨","sibangyuan":"董振鹏"},{"banhezhanminchen":"1号拌合站地磅","cailiaoname":"减水剂","chuchangshijian":"2017-05-02 14:08:43","gongyingshangname":"四川恒泽建材有限公司","guobangleibie":"0","id":"10030","jinchangshijian":"2017-05-02 14:08:26","jingzhong":"","maozhong":"0.00","qianchepai":"皖B16236","remark":"不合格出场","sibangyuan":"董振鹏"},{"banhezhanminchen":"1号拌合站地磅","cailiaoname":"减水剂","chuchangshijian":"2017-04-26 09:58:08","gongyingshangname":"四川恒泽建材有限公司","guobangleibie":"0","id":"29","jinchangshijian":"2017-04-26 08:47:44","jingzhong":"6.24","maozhong":"9.62","qianchepai":"皖BY8582","remark":"不合格出场","sibangyuan":"董振鹏"},{"banhezhanminchen":"1号拌合站地磅","cailiaoname":"河砂(中砂)","chuchangshijian":"2017-03-18 14:30:56","gongyingshangname":"全椒润尧商贸有限公司","guobangleibie":"1","id":"25","jinchangshijian":"2017-03-18 14:23:17","jingzhong":"3.70","maozhong":"19.64","qianchepai":"001","remark":"调拨","sibangyuan":"董振鹏"},{"banhezhanminchen":"1号拌合站地磅","cailiaoname":"碎石(16-31.5mm)","chuchangshijian":"2017-03-17 14:18:47","gongyingshangname":"安徽祥实商贸有限公司","guobangleibie":"0","id":"23","jinchangshijian":"2017-03-17 14:01:45","jingzhong":"34.26","maozhong":"48.10","qianchepai":"皖A85433","remark":"不合格出场","sibangyuan":"董振鹏"},{"banhezhanminchen":"1号拌合站地磅","cailiaoname":"碎石(16-31.5mm)","chuchangshijian":"2017-03-17 14:11:05","gongyingshangname":"安徽祥实商贸有限公司","guobangleibie":"0","id":"24","jinchangshijian":"2017-03-17 14:00:19","jingzhong":"37.04","maozhong":"51.00","qianchepai":"皖A85499","remark":"不合格出场","sibangyuan":"董振鹏"},{"banhezhanminchen":"1号拌合站地磅","cailiaoname":"碎石(16-31.5mm)","chuchangshijian":"2017-03-17 13:42:43","gongyingshangname":"安徽祥实商贸有限公司","guobangleibie":"0","id":"22","jinchangshijian":"2017-03-17 13:42:29","jingzhong":"","maozhong":"0.00","qianchepai":"皖C33925","remark":"不合格出场","sibangyuan":"董振鹏"},{"banhezhanminchen":"1号拌合站地磅","cailiaoname":"碎石(16-31.5mm)","chuchangshijian":"2017-03-17 13:41:57","gongyingshangname":"四川恒泽建材有限公司","guobangleibie":"0","id":"21","jinchangshijian":"2017-03-17 13:41:41","jingzhong":"","maozhong":"0.00","qianchepai":"asfdsf","remark":"不合格出场","sibangyuan":"董振鹏"}]
     * success : true
     */

    private boolean success;
    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * banhezhanminchen : 1号拌合站地磅
         * cailiaoname : 碎石(16-31.5mm)
         * chuchangshijian : 2017-06-14 15:42:52
         * gongyingshangname : 安徽祥实商贸有限公司
         * guobangleibie : 1
         * id : 30036
         * jinchangshijian : 2017-06-14 15:31:44
         * jingzhong : 34.16
         * maozhong : 50.64
         * qianchepai : 皖LA4996
         * remark : 调拨
         * sibangyuan : 董振鹏
         */

        private String banhezhanminchen;
        private String cailiaoname;
        private String chuchangshijian;
        private String gongyingshangname;
        private String guobangleibie;
        private String id;
        private String jinchangshijian;
        private String jingzhong;
        private String maozhong;
        private String qianchepai;
        private String remark;
        private String sibangyuan;

        public String getBanhezhanminchen() {
            return banhezhanminchen;
        }

        public void setBanhezhanminchen(String banhezhanminchen) {
            this.banhezhanminchen = banhezhanminchen;
        }

        public String getCailiaoname() {
            return cailiaoname;
        }

        public void setCailiaoname(String cailiaoname) {
            this.cailiaoname = cailiaoname;
        }

        public String getChuchangshijian() {
            return chuchangshijian;
        }

        public void setChuchangshijian(String chuchangshijian) {
            this.chuchangshijian = chuchangshijian;
        }

        public String getGongyingshangname() {
            return gongyingshangname;
        }

        public void setGongyingshangname(String gongyingshangname) {
            this.gongyingshangname = gongyingshangname;
        }

        public String getGuobangleibie() {
            return guobangleibie;
        }

        public void setGuobangleibie(String guobangleibie) {
            this.guobangleibie = guobangleibie;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJinchangshijian() {
            return jinchangshijian;
        }

        public void setJinchangshijian(String jinchangshijian) {
            this.jinchangshijian = jinchangshijian;
        }

        public String getJingzhong() {
            return jingzhong;
        }

        public void setJingzhong(String jingzhong) {
            this.jingzhong = jingzhong;
        }

        public String getMaozhong() {
            return maozhong;
        }

        public void setMaozhong(String maozhong) {
            this.maozhong = maozhong;
        }

        public String getQianchepai() {
            return qianchepai;
        }

        public void setQianchepai(String qianchepai) {
            this.qianchepai = qianchepai;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getSibangyuan() {
            return sibangyuan;
        }

        public void setSibangyuan(String sibangyuan) {
            this.sibangyuan = sibangyuan;
        }
    }
}

package com.shtoone.shtw.bean;

import java.io.Serializable;
import java.util.List;

public class EnterPoundsListData {

    /**
     * data : [{"banhezhanminchen":"1号拌合站地磅","cailiaoname":"河砂(中砂)","chuchangshijian":"2017-07-04 21:03:52","gongyingshangname":"全椒润尧商贸有限公司","id":"184974","jinchangshijian":"2017-07-04 20:59:24","jingzhong":"49.53","maozhong":"65.90","qianchepai":"皖B45657","sibangyuan":"董振鹏"},{"banhezhanminchen":"1号拌合站地磅","cailiaoname":"粉煤灰(F类Ⅱ级)","chuchangshijian":"2017-07-02 12:45:46","gongyingshangname":"新余美索贸易有限公司","id":"184969","jinchangshijian":"2017-07-02 12:02:43","jingzhong":"37.22","maozhong":"57.38","qianchepai":"皖B50101","sibangyuan":"董振鹏"},{"banhezhanminchen":"1号拌合站地磅","cailiaoname":"粉煤灰(F类Ⅱ级)","chuchangshijian":"2017-07-01 17:02:47","gongyingshangname":"新余美索贸易有限公司","id":"184964","jinchangshijian":"2017-07-01 15:54:13","jingzhong":"42.48","maozhong":"62.36","qianchepai":"皖B12360","sibangyuan":"董振鹏"},{"banhezhanminchen":"1号拌合站地磅","cailiaoname":"减水剂","chuchangshijian":"2017-07-01 10:23:08","gongyingshangname":"四川恒泽建材有限公司","id":"184963","jinchangshijian":"2017-07-01 09:25:26","jingzhong":"6.30","maozhong":"9.70","qianchepai":"皖BY8582","sibangyuan":"董振鹏"},{"banhezhanminchen":"1号拌合站地磅","cailiaoname":"河砂(中砂)","chuchangshijian":"2017-07-01 06:07:04","gongyingshangname":"全椒润尧商贸有限公司","id":"184962","jinchangshijian":"2017-07-01 06:01:21","jingzhong":"53.19","maozhong":"75.76","qianchepai":"皖B199D3","sibangyuan":"董振鹏"},{"banhezhanminchen":"1号拌合站地磅","cailiaoname":"河砂(中砂)","chuchangshijian":"2017-07-01 06:05:16","gongyingshangname":"全椒润尧商贸有限公司","id":"184961","jinchangshijian":"2017-07-01 06:00:05","jingzhong":"48.67","maozhong":"71.04","qianchepai":"皖A80742","sibangyuan":"董振鹏"},{"banhezhanminchen":"1号拌合站地磅","cailiaoname":"河砂(中砂)","chuchangshijian":"2017-07-01 05:54:59","gongyingshangname":"全椒润尧商贸有限公司","id":"184960","jinchangshijian":"2017-07-01 05:50:39","jingzhong":"36.43","maozhong":"52.12","qianchepai":"晋K68062","sibangyuan":"董振鹏"},{"banhezhanminchen":"1号拌合站地磅","cailiaoname":"河砂(中砂)","chuchangshijian":"2017-07-01 05:45:46","gongyingshangname":"全椒润尧商贸有限公司","id":"184959","jinchangshijian":"2017-07-01 05:42:07","jingzhong":"36.16","maozhong":"53.02","qianchepai":"晋H50704","sibangyuan":"董振鹏"},{"banhezhanminchen":"1号拌合站地磅","cailiaoname":"河砂(中砂)","chuchangshijian":"2017-07-01 05:33:02","gongyingshangname":"全椒润尧商贸有限公司","id":"184958","jinchangshijian":"2017-07-01 05:27:53","jingzhong":"49.63","maozhong":"72.50","qianchepai":"皖B25798","sibangyuan":"董振鹏"},{"banhezhanminchen":"1号拌合站地磅","cailiaoname":"河砂(中砂)","chuchangshijian":"2017-07-01 05:24:03","gongyingshangname":"全椒润尧商贸有限公司","id":"184957","jinchangshijian":"2017-07-01 05:16:21","jingzhong":"51.35","maozhong":"73.22","qianchepai":"皖B13180","sibangyuan":"董振鹏"}]
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
         * cailiaoname : 河砂(中砂)
         * chuchangshijian : 2017-07-04 21:03:52
         * gongyingshangname : 全椒润尧商贸有限公司
         * id : 184974
         * jinchangshijian : 2017-07-04 20:59:24
         * jingzhong : 49.53
         * maozhong : 65.90
         * qianchepai : 皖B45657
         * sibangyuan : 董振鹏
         */

        private String banhezhanminchen;
        private String cailiaoname;
        private String chuchangshijian;
        private String gongyingshangname;
        private String id;
        private String jinchangshijian;
        private String jingzhong;
        private String maozhong;
        private String qianchepai;
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

        public String getSibangyuan() {
            return sibangyuan;
        }

        public void setSibangyuan(String sibangyuan) {
            this.sibangyuan = sibangyuan;
        }
    }
}

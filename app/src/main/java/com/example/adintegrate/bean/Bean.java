package com.example.adintegrate.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dell on 2019/11/28 11:36
 * Description:
 * Emain: 1187278976@qq.com
 */
public class Bean {


    /**
     * taskstatus : 0
     * data : {}
     */

    private int taskstatus;
    private DataBean data;

    public int getTaskstatus() {
        return taskstatus;
    }

    public void setTaskstatus(int taskstatus) {
        this.taskstatus = taskstatus;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {

        /**
         * tid : 12338
         * exec_type : imp_url
         * exec_code : http://www.baidu.com
         * exec_times : 6
         * stay_time : 60
         * referer : http://g.cn
         * ua :
         * device_id : {"imei":"902376503427590432","idfa":"idfa2393-0983248-394903-32948"}
         * app_name : mojitianqi
         * resolution : {"w":720,"h":1080}
         * replace : {"{ip}":"127.0.0.1","IMEI":"393294761327934839274"}
         */

        private int tid;
        private String exec_type;
        private String exec_code;
        private int exec_times;
        private int stay_time;
        private String referer;
        private String ua;
        private DeviceIdBean device_id;
        private String app_name;
        private ResolutionBean resolution;
        private ReplaceBean replace;

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

        public String getExec_type() {
            return exec_type;
        }

        public void setExec_type(String exec_type) {
            this.exec_type = exec_type;
        }

        public String getExec_code() {
            return exec_code;
        }

        public void setExec_code(String exec_code) {
            this.exec_code = exec_code;
        }

        public int getExec_times() {
            return exec_times;
        }

        public void setExec_times(int exec_times) {
            this.exec_times = exec_times;
        }

        public int getStay_time() {
            return stay_time;
        }

        public void setStay_time(int stay_time) {
            this.stay_time = stay_time;
        }

        public String getReferer() {
            return referer;
        }

        public void setReferer(String referer) {
            this.referer = referer;
        }

        public String getUa() {
            return ua;
        }

        public void setUa(String ua) {
            this.ua = ua;
        }

        public DeviceIdBean getDevice_id() {
            return device_id;
        }

        public void setDevice_id(DeviceIdBean device_id) {
            this.device_id = device_id;
        }

        public String getApp_name() {
            return app_name;
        }

        public void setApp_name(String app_name) {
            this.app_name = app_name;
        }

        public ResolutionBean getResolution() {
            return resolution;
        }

        public void setResolution(ResolutionBean resolution) {
            this.resolution = resolution;
        }

        public ReplaceBean getReplace() {
            return replace;
        }

        public void setReplace(ReplaceBean replace) {
            this.replace = replace;
        }

        public static class DeviceIdBean {
            /**
             * imei : 902376503427590432
             * idfa : idfa2393-0983248-394903-32948
             */

            private String imei;
            private String idfa;

            public String getImei() {
                return imei;
            }

            public void setImei(String imei) {
                this.imei = imei;
            }

            public String getIdfa() {
                return idfa;
            }

            public void setIdfa(String idfa) {
                this.idfa = idfa;
            }
        }

        public static class ResolutionBean {
            /**
             * w : 720
             * h : 1080
             */

            private int w;
            private int h;

            public int getW() {
                return w;
            }

            public void setW(int w) {
                this.w = w;
            }

            public int getH() {
                return h;
            }

            public void setH(int h) {
                this.h = h;
            }
        }

        public static class ReplaceBean {
            @SerializedName("{ip}")
            private String _$Ip143; // FIXME check this code
            private String IMEI;

            public String get_$Ip143() {
                return _$Ip143;
            }

            public void set_$Ip143(String _$Ip143) {
                this._$Ip143 = _$Ip143;
            }

            public String getIMEI() {
                return IMEI;
            }

            public void setIMEI(String IMEI) {
                this.IMEI = IMEI;
            }
        }
    }
}

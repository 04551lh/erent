package com.example.adintegrate.bean;

/**
 * Created by dell on 2019/11/29 14:57
 * Description:
 * Emain: 1187278976@qq.com
 */
public class TestBean {

    /**
     * taskstatus : 0
     * data : {"status":200,"message":{"exec_type":"imp_url","exec_code":"http://test.gogo-cd.com/i.html?c=ghip","ua":"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4091.2 Safari/537.36","exec_times":"6","tid":"2","resolution":{"h":1080,"w":1920},"stay_time":"60","app_name":"","referer":"https://www.sogou.com"}}
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

    public static class DataBean {
        /**
         * status : 200
         * message : {"exec_type":"imp_url","exec_code":"http://test.gogo-cd.com/i.html?c=ghip","ua":"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4091.2 Safari/537.36","exec_times":"6","tid":"2","resolution":{"h":1080,"w":1920},"stay_time":"60","app_name":"","referer":"https://www.sogou.com"}
         */

        private int status;
        private MessageBean message;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public MessageBean getMessage() {
            return message;
        }

        public void setMessage(MessageBean message) {
            this.message = message;
        }

        public static class MessageBean {
            /**
             * exec_type : imp_url
             * exec_code : http://test.gogo-cd.com/i.html?c=ghip
             * ua : Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4091.2 Safari/537.36
             * exec_times : 6
             * tid : 2
             * resolution : {"h":1080,"w":1920}
             * stay_time : 60
             * app_name :
             * referer : https://www.sogou.com
             */

            private String exec_type;
            private String exec_code;
            private String ua;
            private String exec_times;
            private String tid;
            private ResolutionBean resolution;
            private String stay_time;
            private String app_name;
            private String referer;

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

            public String getUa() {
                return ua;
            }

            public void setUa(String ua) {
                this.ua = ua;
            }

            public String getExec_times() {
                return exec_times;
            }

            public void setExec_times(String exec_times) {
                this.exec_times = exec_times;
            }

            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }

            public ResolutionBean getResolution() {
                return resolution;
            }

            public void setResolution(ResolutionBean resolution) {
                this.resolution = resolution;
            }

            public String getStay_time() {
                return stay_time;
            }

            public void setStay_time(String stay_time) {
                this.stay_time = stay_time;
            }

            public String getApp_name() {
                return app_name;
            }

            public void setApp_name(String app_name) {
                this.app_name = app_name;
            }

            public String getReferer() {
                return referer;
            }

            public void setReferer(String referer) {
                this.referer = referer;
            }

            public static class ResolutionBean {
                /**
                 * h : 1080
                 * w : 1920
                 */

                private int h;
                private int w;

                public int getH() {
                    return h;
                }

                public void setH(int h) {
                    this.h = h;
                }

                public int getW() {
                    return w;
                }

                public void setW(int w) {
                    this.w = w;
                }
            }
        }
    }
}

package org.plutodjava.matchmaker.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "matchmaker.notify")
public class NotifyProperties {
    private Sms sms;

    public Sms getSms() {
        return sms;
    }

    public void setSms(Sms sms) {
        this.sms = sms;
    }

    public static class Sms {
        private String sign;
        private Aliyun aliyun;
        private List<Map<String, String>> template = new ArrayList<>();

        public List<Map<String, String>> getTemplate() {
            return template;
        }

        public void setTemplate(List<Map<String, String>> template) {
            this.template = template;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public Aliyun getAliyun() {
            return aliyun;
        }

        public void setAliyun(Aliyun aliyun) {
            this.aliyun = aliyun;
        }

        public static class Aliyun {
            private String regionId;
            private String accessKeyId;
            private String accessKeySecret;

            public String getRegionId() {
                return regionId;
            }

            public void setRegionId(String regionId) {
                this.regionId = regionId;
            }

            public String getAccessKeyId() {
                return accessKeyId;
            }

            public void setAccessKeyId(String accessKeyId) {
                this.accessKeyId = accessKeyId;
            }

            public String getAccessKeySecret() {
                return accessKeySecret;
            }

            public void setAccessKeySecret(String accessKeySecret) {
                this.accessKeySecret = accessKeySecret;
            }
        }
    }

}

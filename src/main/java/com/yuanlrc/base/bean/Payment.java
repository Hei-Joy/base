package com.yuanlrc.base.bean;


public enum Payment {

    AliPay(true, "支付宝"),
    WxPay(false, "微信");

    private boolean enable;

    private String name;

    Payment(boolean enable, String name) {
        this.enable = enable;
        this.name = name;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

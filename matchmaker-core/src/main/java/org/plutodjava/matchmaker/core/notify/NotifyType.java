package org.plutodjava.matchmaker.core.notify;

public enum NotifyType {
    ALLOW("allow"),
    REJECT("reject"),
    CAPTCHA("captcha");

    private String type;

    NotifyType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}

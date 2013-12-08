package com.github.jamesnorris.flow.yaml;

public class YAMLValue {
    private String value, comment;
    private Object setting;

    public YAMLValue(String value, Object setting) {
        this(value, setting, null);
    }

    public YAMLValue(String value, Object setting, String comment) {
        this.value = value;
        this.setting = setting;
        this.comment = comment;
    }

    public void set(Object setting) {
        this.setting = setting;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getValue() {
        return value;
    }

    public String getComment() {
        return comment;
    }

    public Object get() {
        return setting;
    }
}

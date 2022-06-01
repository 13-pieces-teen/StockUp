package com.example.stockup.entity;

public class wholeObject {
    private int ID;
    private String OB_name;//物品名称
    private String OB_guarantee_day;//保质天数
    private String OB_json;//json数据
    private String OB_uri;//图片uri

    public wholeObject() {
        super();
    }
    public wholeObject(int ID, String OB_json, String OB_name, String OB_guarantee_day, String OB_uri) {
        super();
        this.ID = ID;
        this.OB_name = OB_name;
        this.OB_guarantee_day = OB_guarantee_day;
        this.OB_json = OB_json;
        this.OB_uri = OB_uri;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getOB_name() {
        return OB_name;
    }

    public void setOB_name(String OB_name) {
        this.OB_name = OB_name;
    }

    public String getOB_guarantee_day() {
        return OB_guarantee_day;
    }

    public void setOB_guarantee_day(String OB_guarantee_day) {
        this.OB_guarantee_day = OB_guarantee_day;
    }

    public String getOB_json() {
        return OB_json;
    }

    public void setOB_json(String OB_json) {
        this.OB_json = OB_json;
    }

    public String getOB_uri() {
        return OB_uri;
    }

    public void setOB_uri(String OB_uri) {
        this.OB_uri = OB_uri;
    }

    @Override
    public String toString() {
        return "wholeObject{" +
                "ID=" + ID +
                ", OB_name='" + OB_name + '\'' +
                ", OB_guarantee_day='" + OB_guarantee_day + '\'' +
                ", OB_json='" + OB_json + '\'' +
                ", OB_uri='" + OB_uri + '\'' +
                '}';
    }
}

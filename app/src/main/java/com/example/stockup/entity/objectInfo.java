package com.example.stockup.entity;

public class objectInfo {
    //物品名称，物品类别，保质天数，生产日期，过期日期，开封日期，备注，产品数量，间隔天数
    private String OB_name;//物品名称
    private String OB_type;//物品类别
    private String OB_guarantee_day;//保质天数
    private String OB_produce_date;//生产日期
    private String OB_after_date;//过期日期
    private String OB_open_date;//开封日期
    private int OB_amount;//物品数量
    private String OB_remarks;//物品备注
    private int betweenDays;//间隔天数

    public objectInfo() {
        super();
    }
    public objectInfo(String OB_name, String OB_type, String OB_guarantee_day, String OB_after_date,
                      String OB_produce_date, String OB_open_date,String OB_remarks, int OB_amount, int betweenDays) {
        super();
        this.OB_name = OB_name;
        this.OB_type = OB_type;
        this.OB_guarantee_day = OB_guarantee_day;
        this.OB_after_date = OB_after_date;
        this.OB_produce_date = OB_produce_date;
        this.OB_open_date = OB_open_date;
        this.OB_remarks = OB_remarks;
        this.OB_amount = OB_amount;
        this.betweenDays = betweenDays;
    }



    public String getOB_name() {
        return OB_name;
    }

    public void setOB_name(String OB_name) {
        this.OB_name = OB_name;
    }

    public String getOB_type() {
        return OB_type;
    }

    public void setOB_type(String OB_type) {
        this.OB_type = OB_type;
    }

    public String getOB_guarantee_day() {
        return OB_guarantee_day;
    }

    public void setOB_guarantee_day(String OB_guarantee_day) {
        this.OB_guarantee_day = OB_guarantee_day;
    }

    public String getOB_produce_date() {
        return OB_produce_date;
    }

    public void setOB_produce_date(String OB_produce_date) {
        this.OB_produce_date = OB_produce_date;
    }

    public String getOB_after_date() {
        return OB_after_date;
    }

    public void setOB_after_date(String OB_after_date) {
        this.OB_after_date = OB_after_date;
    }

    public String getOB_open_date() {
        return OB_open_date;
    }

    public void setOB_open_date(String OB_open_date) {
        this.OB_open_date = OB_open_date;
    }

    public int getOB_amount() {
        return OB_amount;
    }

    public void setOB_amount(int OB_amount) {
        this.OB_amount = OB_amount;
    }

    public String getOB_remarks() {
        return OB_remarks;
    }

    public void setOB_remarks(String OB_remarks) {
        this.OB_remarks = OB_remarks;
    }

    public int getBetweenDays() {
        return betweenDays;
    }

    public void setBetweenDays(int betweenDays) {
        this.betweenDays = betweenDays;
    }

    @Override
    public String toString() {
        return "objectInfo{" +
                "OB_name='" + OB_name + '\'' +
                ", OB_type='" + OB_type + '\'' +
                ", OB_guarantee_day='" + OB_guarantee_day + '\'' +
                ", OB_produce_date='" + OB_produce_date + '\'' +
                ", OB_after_date='" + OB_after_date + '\'' +
                ", OB_open_date='" + OB_open_date + '\'' +
                ", OB_amount=" + OB_amount +
                ", OB_remarks='" + OB_remarks + '\'' +
                ", betweenDays=" + betweenDays +
                '}';
    }
}

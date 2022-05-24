package Object;

public class Object {
    private String OB_name;//物品名称
    private String OB_guarantee_date;//保质期
    private String OB_after_date;//过期日期
    private String OB_produce_date;//生产日期
    private String OB_open_date;//开封日期
    private String OB_remarks;//物品备注
    private int OB_amount;//物品数量

    public Object() {
        super();
    }
        public Object(String OB_name, String OB_guarantee_date, String OB_after_date,
                      String OB_produce_date, String OB_open_date,String OB_remarks, int OB_amount) {
        super();
        this.OB_name = OB_name;
        this.OB_guarantee_date = OB_guarantee_date;
        this.OB_after_date = OB_after_date;
        this.OB_produce_date = OB_produce_date;
        this.OB_open_date = OB_open_date;
        this.OB_remarks = OB_remarks;
        this.OB_amount = OB_amount;
    }

    public String getOB_name() {return OB_name;}
    public void setOB_name(String OB_name) {this.OB_name = OB_name;}
    public String getOB_guarantee_date() {return OB_guarantee_date;}
    public void setOB_guarantee_date(String OB_guarantee_date) {this.OB_guarantee_date = OB_guarantee_date;}
    public String getOB_after_date() {return OB_after_date;}
    public void setOB_after_date(String OB_after_date) {this.OB_after_date = OB_after_date;}
    public String getOB_produce_date() {return OB_produce_date;}
    public void setOB_produce_date(String OB_produce_date) {this.OB_produce_date = OB_produce_date;}
    public String getOB_open_date() {return OB_open_date;}
    public void setOB_open_date(String OB_open_date) {this.OB_open_date = OB_open_date;}
    public String getOB_remarks() {return OB_remarks;}
    public void setOB_remarks(String OB_remarks) {this.OB_remarks = OB_remarks;}
    public int getOB_amount() {return OB_amount;}
    public void setOB_amount(int OB_amount) {this.OB_amount = OB_amount;}

}

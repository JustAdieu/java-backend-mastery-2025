package demo7;

public class VipUser extends User{
    private String vipLevel;
    private String vipId;
    private double vipDiscount;

    public VipUser(String name, int age, String vipLevel, String vipId, double vipDiscount) {
        super(name, age);
        this.vipLevel = vipLevel;
        this.vipId = vipId;
        this.vipDiscount = vipDiscount;
    }

    public VipUser(String vipLevel, String vipId, double vipDiscount) {
        this.vipLevel = vipLevel;
        this.vipId = vipId;
        this.vipDiscount = vipDiscount;
    }

    public VipUser(String name, int age) {
        super(name, age);
    }

    public VipUser() {
    }


    @Override
    public String toString() {
        return "VipUser{" +
                "vipLevel='" + vipLevel + '\'' +
                ", vipId='" + vipId + '\'' +
                ", vipDiscount=" + vipDiscount +
                '}';
    }

    public String getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(String vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
    }

    public double getVipDiscount() {
        return vipDiscount;
    }

    public void setVipDiscount(double vipDiscount) {
        this.vipDiscount = vipDiscount;
    }
}

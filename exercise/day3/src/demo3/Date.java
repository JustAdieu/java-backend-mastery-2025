package demo3;

public class Date implements Cloneable{
    String date;
    public Date(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    @Override
    public Date clone(){
        try {
            return (Date)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

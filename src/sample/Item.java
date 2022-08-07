package sample;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item {
    private final SimpleIntegerProperty srNo;
    private final SimpleStringProperty desc;
    private final SimpleDoubleProperty qty;
    private final SimpleStringProperty po1;
    private final SimpleDoubleProperty tp;
    private final SimpleDoubleProperty price;
    private final SimpleDoubleProperty stitch;

    public Item(int a, String b, double c, String d, double e,double f,double g) {
        this.srNo = new SimpleIntegerProperty(a);
        this.desc = new SimpleStringProperty(b);
        this.qty = new SimpleDoubleProperty(c);
        this.po1 = new SimpleStringProperty(d);
        this.tp = new SimpleDoubleProperty(e);
        this.price = new SimpleDoubleProperty(f);
        this.stitch = new SimpleDoubleProperty(g);
    }

    public int getSrNo() {
        return srNo.get();
    }
    public void setSrNo(int a) {
        srNo.set(a);
    }

    public String getDesc() {
        return desc.get();
    }
    public void setDesc(String b) {
        desc.set(b);
    }

    public double getQty() {
        return qty.get();
    }
    public void setQty(double fName) {
        qty.set(fName);
    }

    public String getPo1() {
        return po1.get();
    }
    public void setPo1(String fName) {
        po1.set(fName);
    }
    public double getTp() {
        return tp.get();
    }
    public void setTp(double fName) {
        tp.set(fName);
    }

    public double getPrice() {
        return price.get();
    }
    public void setPrice(double fName) {
        price.set(fName);
    }
    public double getStitch() {
        return stitch.get();
    }
    public void setStitch(double fName) {
        stitch.set(fName);
    }
}
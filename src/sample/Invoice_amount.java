package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Invoice_amount {
    private final SimpleStringProperty desc;
    private final SimpleStringProperty qty;
    private final SimpleDoubleProperty po1;
    private final SimpleStringProperty mno;
    private final SimpleStringProperty ddate;
    private final SimpleDoubleProperty adv;
    private final SimpleDoubleProperty sO;

    public Invoice_amount(String b, String c, double d,String e,String  f,double g,double h) {
        this.desc = new SimpleStringProperty(b);
        this.qty = new SimpleStringProperty(c);
        this.po1 = new SimpleDoubleProperty(d);
        this.mno = new SimpleStringProperty(e);
        this.ddate = new SimpleStringProperty(f);
        this.adv = new SimpleDoubleProperty(g);
        this.sO = new SimpleDoubleProperty(h);
    }

    public String getDesc() {
        return desc.get();
    }
    public void setDesc(String b) {
        desc.set(b);
    }

    public String getQty() {
        return qty.get();
    }
    public void setQty(String fName) {
        qty.set(fName);
    }

    public double getPo1() {
        return po1.get();
    }
    public void setPo1(double fName) {
        po1.set(fName);
    }

    public String getMno() {
        return mno.get();
    }
    public void setMno(String b) {mno.set(b);}

    public String getDdate() {
        return ddate.get();
    }
    public void setDdate(String fName) {
        ddate.set(fName);
    }

    public double getAdv() {
        return adv.get();
    }
    public void setAdv(double fName) {
        adv.set(fName);
    }

    public double getSO() {
        return sO.get();
    }
    public void setSO(double fName) {
        sO.set(fName);
    }
}
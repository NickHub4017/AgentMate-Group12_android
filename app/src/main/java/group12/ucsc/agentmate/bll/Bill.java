package group12.ucsc.agentmate.bll;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by NRV on 8/23/2014.
 */
public class Bill implements Serializable {
    String BillID;
    String venderID;
    String BillDate;
    String PayDate;
    double Total;
    double Discount;

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    boolean sync;

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public String getBillID() {
        return BillID;
    }

    public void setBillID(String billID) {
        BillID = billID;
    }

    public String getVenderID() {
        return venderID;
    }

    public void setVenderID(String venderID) {
        this.venderID = venderID;
    }

    public String getBillDate() {
        return BillDate;
    }

    public void setBillDate(String billDate) {
        BillDate = billDate;
    }

    public String getPayDate() {
        return PayDate;
    }

    public void setPayDate(String payDate) {
        PayDate = payDate;
    }

    public Bill(Vendor vendor,Representative rep){
    venderID=vendor.getVenderNo();
    createBillNo(rep);
        sync=false;

}
    public void createBillNo(Representative rep){
        Calendar c = Calendar.getInstance();
        this.BillID = rep.getEmp_id()+"BLL"+c.getTime().toString();

    }


}

package group12.ucsc.agentmate.bll;

import java.io.Serializable;

/**
 * Created by NRV on 9/9/2014.
 */
public class SellItem implements Serializable {
    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getStoreQty() {
        return StoreQty;
    }

    public void setStoreQty(int storeQty) {
        StoreQty = storeQty;
    }

    public double getCompanyDiscount() {
        return CompanyDiscount;
    }

    public void setCompanyDiscount(double companyDiscount) {
        CompanyDiscount = companyDiscount;
    }

    public String getMinUnit() {
        return MinUnit;
    }

    public void setMinUnit(String minUnit) {
        MinUnit = minUnit;
    }

    public String getMinOrderUnit() {
        return MinOrderUnit;
    }

    public void setMinOrderUnit(String minOrderUnit) {
        MinOrderUnit = minOrderUnit;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public boolean isSync() {
        return Sync;
    }

    public void setSync(boolean sync) {
        Sync = sync;
    }

    String ItemID;
    String ItemName;
    double Price;
    int StoreQty;
    double CompanyDiscount;
    String MinUnit;
    String MinOrderUnit;
    String CategoryID;
    boolean Sync;

}

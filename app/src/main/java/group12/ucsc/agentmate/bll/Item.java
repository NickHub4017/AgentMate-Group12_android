package group12.ucsc.agentmate.bll;

import java.io.Serializable;

/**
 * Created by NRV on 8/23/2014.
 */
public class Item implements Serializable {
    String ItemID;
    String ItemName;
    float Price;
    int StoreQty;
    float CompanyDiscount;
    String MinUnit;
    String MinOrderUnit;
    String CategoryID;
    boolean Sync;
    Discount discount_list[];

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

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getStoreQty() {
        return StoreQty;
    }

    public void setStoreQty(int storeQty) {
        StoreQty = storeQty;
    }

    public float getCompanyDiscount() {
        return CompanyDiscount;
    }

    public void setCompanyDiscount(float companyDiscount) {
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

    public Discount[] getDiscount_list() {
        return discount_list;
    }

    public void setDiscount_list(Discount[] discount_list) {
        this.discount_list = discount_list;
    }

    //TODO add change discount method which change the discount of the item.


}

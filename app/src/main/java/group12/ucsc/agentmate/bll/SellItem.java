package group12.ucsc.agentmate.bll;

import java.io.Serializable;

/**
 * Created by NRV on 9/9/2014.
 */
public class SellItem implements Serializable {

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

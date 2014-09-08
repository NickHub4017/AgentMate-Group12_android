package group12.ucsc.agentmate.bll;

import java.io.Serializable;

/**
 * Created by NRV on 8/23/2014.
 */
public class Bill implements Serializable {
    String BillID;
    String venderID;
    String BillDate;
    String PayDate;
    
    String create_billTable_query = "CREATE TABLE bill (BillID TEXT PRIMARY KEY, venorderID TEXT , " +
            "Billing_date datetime default current_timestamp,paid_date datetime default not_yet TEXT,paid_amount FLOAT,full_amount FLOAT)";
}

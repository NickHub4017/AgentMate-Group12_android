package group12.ucsc.agentmate.bll;

import java.io.Serializable;

/**
 * Created by NRV on 8/23/2014.
 */
public class Payment implements Serializable {
    String BillID;
    Bill obj_bill;
    float amount;
    String Date;
    String Type;

}

package group12.ucsc.agentmate.bll;

import java.io.Serializable;

/**
 * Created by NRV on 8/23/2014.
 */
public class Discount implements Serializable{
    public int min_amount;
    public int max_amount;
    public String unit_of_measure;
    public float discount;
}

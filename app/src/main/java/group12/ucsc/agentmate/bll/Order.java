package group12.ucsc.agentmate.bll;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by NRV on 9/20/2014.
 */
public class Order implements Serializable {
    int position;
    String VenOrderID;
    public ArrayList<SellItem> list = new ArrayList<SellItem>();
    boolean sync;

    public String getVenOrderID() {
        return VenOrderID;
    }

    public void setVenOrderID(String venOrderID) {
        VenOrderID = venOrderID;
    }

    public void addItem(SellItem item){list.add(item);}

    public int findById(String itemID){
        for (int i=0;i<list.size();i++){
            if (list.get(i).getItemID().equals(itemID)){
                return i;
            }

        }
        return  -1;

    }

    public SellItem findByIdObj(int i){
       return list.get(i);
    }


    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }
}
//ToDo write self database update code to all items from this class.
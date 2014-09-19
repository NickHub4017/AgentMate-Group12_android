package group12.ucsc.agentmate.bll;

/**
 * Created by NRV on 9/19/2014.
 */
public class UnitMap {
    String unit;
    int QtyMap;

    public UnitMap(int qtyMap, String unit) {
        QtyMap = qtyMap;
        this.unit = unit;
    }

    public String getUnit() {
        return unit;

    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getQtyMap() {
        return QtyMap;
    }

    public void setQtyMap(int qtyMap) {
        QtyMap = qtyMap;
    }
}

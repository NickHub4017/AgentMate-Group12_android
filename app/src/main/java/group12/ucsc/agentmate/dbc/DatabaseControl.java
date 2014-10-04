package group12.ucsc.agentmate.dbc;
/**
 * Created by NRV on 8/23/2014.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;

import group12.ucsc.agentmate.bll.Complain;
import group12.ucsc.agentmate.bll.SellItem;
import group12.ucsc.agentmate.bll.UnitMap;
import group12.ucsc.agentmate.bll.Vendor;

public class DatabaseControl extends SQLiteOpenHelper{
    SQLiteDatabase dbase;
    Context con;
    public DatabaseControl(Context context) {

        super(context, "datacollection.db", null, 1);
        con=context;
        //con.deleteFile("datacollection.db");


    }

    @Override
    public void onCreate(SQLiteDatabase database) {

//Create login table
        String create_login_query = "CREATE TABLE login (EmpId VARCHAR(4),UserName VARCHAR(10) PRIMARY KEY,Password TEXT,Question VARCHAR(50),Answer VARCHAR(20),LastUpdate datetime default current_timestamp)";
        database.execSQL(create_login_query);
        dbase=database;
//Create vendor table
        String create_vendorTable_query = "CREATE TABLE vendor (venderno VARCHAR(6) PRIMARY KEY,ShopName VARCHAR(20) ,VenderName VARCHAR(30) , " +
                "Address VARCHAR(100),TelNoShop VARCHAR(10),TelNoConfirm VARCHAR(10),Overdue FLOAT,Confirm BOOLEAN)";
        database.execSQL(create_vendorTable_query);

//Create item table
        String create_itemTable_query = "CREATE TABLE item (ItemID VARCHAR(5) PRIMARY KEY, ItemName VARCHAR(20) , " +
                "Price FLOAT,StoreQty INTEGER ,CompanyDiscount FLOAT,MinUnit VARCHAR(3),MinOrderUnit VARCHAR(3),CategoryID VARCHAR(2),Sync BOOLEAN)";
        database.execSQL(create_itemTable_query);

//Create venorder table
        String create_venOrderTable_query = "CREATE TABLE venOrder (VenOrderID VARCHAR(16) PRIMARY KEY, VendorNo VARCHAR(6) , " +
                "OrderDate datetime default current_timestamp,DeliverDate datetime,Sync BOOLEAN)";
        database.execSQL(create_venOrderTable_query);

//Create discount table
        String create_discount_Table_query = "CREATE TABLE discount (ItemID VARCHAR(5), MinQty INTEGER, " +
                "MaxQty INTEGER,Discount FLOAT,Sync BOOLEAN,PRIMARY KEY (ItemID, MinQty, MaxQty))";
        database.execSQL(create_discount_Table_query);

        String create_unit_mapping_Table_query = "CREATE TABLE measure (ItemID VARCHAR(5), unit VARCHAR(3),MapQty INTEGER,PRIMARY KEY (ItemID,unit))";
        database.execSQL(create_unit_mapping_Table_query);

        String create_complain_Table_query = "CREATE TABLE complain (ComplainID VARCHAR(17) PRIMARY KEY, ItemID VARCHAR(5),Complain TEXT,VendorNo VARCHAR(6),Sync BOOLEAN)";
        database.execSQL(create_complain_Table_query);

        String create_bill_Table_query = "CREATE TABLE bill (BillID VARCHAR(17) PRIMARY KEY, VenOrderID VARCHAR(16),BillDate date,Total INTEGER,Sync BOOLEAN)";
        database.execSQL(create_bill_Table_query);



        Toast.makeText(con,"DONE",Toast.LENGTH_SHORT).show();




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
    public void k(){
        //SQLiteDatabase database = this.getWritableDatabase();
        //String create_unit_mapping_Table_query = "CREATE TABLE measure (ItemID VARCHAR(5), unit VARCHAR(3),MapQty INTEGER,PRIMARY KEY (ItemID,unit))";
        //database.execSQL(create_unit_mapping_Table_query);
        //Toast.makeText(con, "id ", Toast.LENGTH_SHORT).show();

        //String create_complain_Table_query = "CREATE TABLE complain (ComplainID VARCHAR(12) PRIMARY KEY, ItemID VARCHAR(5),Complain TEXT,VendorNo VARCHAR(6),Sync BOOLEAN)";
        //database.execSQL(create_complain_Table_query);

       // String create_bill_Table_query = "CREATE TABLE bill (BillID VARCHAR(17) PRIMARY KEY, VenOrderID VARCHAR(16),BillDate date,Total INTEGER,Sync BOOLEAN)";
        //database.execSQL(create_bill_Table_query);

        /*ContentValues values = new ContentValues();
        values.put("ItemID","123");
        values.put("unit","pkt");
        values.put("MapQty",15);

        database.insert("measure",null, values);

        ContentValues values2 = new ContentValues();
        values2.put("ItemID","123");
        values2.put("unit","box");
        values2.put("MapQty",180);

        database.insert("measure",null, values2);

        ContentValues values3 = new ContentValues();
        values3.put("ItemID","123");
        values3.put("unit","bulk");
        values3.put("MapQty",50);

        database.insert("measure",null, values3);
        Toast.makeText(con, "****//*///*//**** ", Toast.LENGTH_SHORT).show();*/
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("StoreQty", 80);
        database.update("item", values,"ItemID"+" = ?",new String[] {"123"});
    }

    public void insertToLogin(String EmpId_ins,String username_ins,String encpassword_ins,String Question_ins,String enc_Ans_ins){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("EmpId", EmpId_ins);
        values.put("UserName", username_ins);
        values.put("Password", encpassword_ins);
        values.put("Question", Question_ins);
        values.put("Answer", enc_Ans_ins);


        if(this.getLoginInfo(username_ins).getCount()!=0){
            database.update("login", values,"EmpId"+" = ?",new String[] {EmpId_ins});
            //If already exsits member login do update
        }
        else{

            database.insert("login",null, values);
            database.close(); //Query if member id does not exsists insert.
        }
    }

    public Cursor getLoginInfo(String Username_ins) {
        HashMap<String, String> LoginMap = new HashMap<String, String>();



        SQLiteDatabase database = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM login  where UserName='"+Username_ins+"'";

        Cursor cursor = database.rawQuery(selectQuery,null);
        //Toast.makeText(con, "no of rows", Toast.LENGTH_SHORT).show();
        //Toast.makeText(con, ""+ cursor.getCount(), Toast.LENGTH_SHORT).show();
        return cursor;
    }

    public String password_encoder (String password){////To encode the password.
        int base=10;									///Get the password and encode it.
        //char at position will add as a string if int value exceed margin
        //Then that value save as string and do the remaining part.
        int margin=99999;
        int cur_val;
        int cur_tot=0;
        String final_value="";
        for (int i=0;i<password.length();i++){
            cur_val=password.charAt(i);
            cur_tot=(int) (cur_tot+cur_val*Math.pow(base,i));
            if (cur_tot>=99999){
                final_value=final_value+cur_tot;
                cur_tot=0;
            }
        }
        final_value=final_value+cur_tot;
        return final_value;

    }

    public void change_Password(String UserName,String new_Password){
        SQLiteDatabase database = this.getWritableDatabase();
        Calendar cal = Calendar.getInstance();
        int seconds = cal.get(Calendar.DATE);
        String now_time=""+cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.DATE)+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
        ContentValues values = new ContentValues();
        values.put("Password", password_encoder(new_Password));
        //Toast.makeText(con,"---> "+seconds+"  "+new_Password, Toast.LENGTH_SHORT).show();
        values.put("LastUpdate",now_time);//////SET TO GET THE DATE

        ///Set the date of the update when this happens

        database.update("login", values,"UserName"+" = ?",new String[] {UserName});

        Cursor c=getLoginInfo(UserName);
        c.moveToFirst();
        String pw_in_db2=c.getString(c.getColumnIndex("Password"));
        if (pw_in_db2.equals(password_encoder(new_Password))){
            //Updated correctly
            Toast.makeText(con,"Updated succefully "+UserName, Toast.LENGTH_SHORT).show();
        }
        else{
            //Not updated
            Toast.makeText(con,"Updating process failed", Toast.LENGTH_SHORT).show();

        }
    }

    public void confirm_data(Vendor vendor_conf){
        String ven_id=vendor_conf.getVenderNo();
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Confirm", vendor_conf.isConfirm());
        database.update("vendor", values, "venderno" + " = ?", new String[]{ven_id});
    }

  public void addVendor(Vendor new_vendor){
      SQLiteDatabase database = this.getWritableDatabase();
      ContentValues values = new ContentValues();
      values.put("venderno",new_vendor.getVenderNo());
      values.put("ShopName",new_vendor.getShopName());
      values.put("VenderName",new_vendor.getVenderName());
      values.put("Address",new_vendor.getAddress());
      values.put("Overdue",new_vendor.getOverdue());
      values.put("TelNoShop",new_vendor.getTelNoShop());
      values.put("TelNoConfirm",new_vendor.getTelNoConfm());
      values.put("Confirm",false);
      database.insert("vendor",null, values);
  }

    public Cursor findVendor(String ven_id){
        SQLiteDatabase database = this.getReadableDatabase();

        String select_ven_id_Query = "SELECT * FROM vendor " +
                "WHERE ShopName LIKE '"+ven_id+"%'";

        //Toast.makeText(con,select_ven_id_Query,Toast.LENGTH_SHORT).show();
        Cursor cursor = database.rawQuery(select_ven_id_Query,null);
        return cursor;
    }

    public void editVendor(Vendor editVendor,Context con){
        String edited_venderno=editVendor.getVenderNo();

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ShopName",editVendor.getShopName());
        values.put("VenderName",editVendor.getVenderName());
        values.put("Address",editVendor.getAddress());
        values.put("TelNoShop",editVendor.getTelNoShop());
        values.put("TelNoConfirm",editVendor.getTelNoConfm());
        values.put("Overdue",editVendor.getOverdue());
        values.put("Confirm",false);
        int updted_rows=database.update("vendor", values, "venderno" + " = ?", new String[]{edited_venderno});
        if (updted_rows==1){
            Toast.makeText(con,"Update Successfull",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(con,"Update Error",Toast.LENGTH_SHORT).show();
        }

    }

    public Cursor getVendorTable(){
        SQLiteDatabase database = this.getReadableDatabase();
        String select_ven_name_Query = "SELECT * FROM vendor";
        Cursor cursor = database.rawQuery(select_ven_name_Query,null);
        return cursor;

    }


    public void add_complain(String CompID_ins,String ItemID_ins,String Complain_ins,String VendorID_ins,Boolean synced_ins){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ComplainID",CompID_ins);
        values.put("ItemID",ItemID_ins);
        values.put("Complain",Complain_ins);
        values.put("VendorID",VendorID_ins);
        values.put("Synced",synced_ins);
        database.insert("complain",null, values);
    }

    public void add_bill(String BillID_ins,String VenorderID_ins,String BillDate_ins,String paid_Date_ins,float paid_amount_ins,float full_amount_ins){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("BillID",BillID_ins);
        values.put("venorderID",VenorderID_ins);
        values.put("Billing_date",BillDate_ins);
        values.put("paid_date",paid_Date_ins);
        values.put("paid_amount",paid_amount_ins);
        values.put("full_amount",full_amount_ins);
        database.insert("bill",null, values);
    }

    //public void add_payment(String BillID)

public void like(){

    SQLiteDatabase database = this.getReadableDatabase();
    String selectQuery = "SELECT * FROM login " +
            "WHERE UserName LIKE 'pq%'";
    Cursor cursor = database.rawQuery(selectQuery,null);
    //Toast.makeText(con, "no of rows", Toast.LENGTH_SHORT).show();
    //Toast.makeText(con, ""+ cursor.getCount(), Toast.LENGTH_SHORT).show();
}



public Cursor findComplain(){
    SQLiteDatabase database = this.getReadableDatabase();

    String select_item_id_Query = "SELECT * FROM complain";
    Cursor cursor = database.rawQuery(select_item_id_Query,null);
    return cursor;
}

public Cursor findComplainByID(String comp_id_ins){
        SQLiteDatabase database = this.getReadableDatabase();

        String select_item_id_Query = "SELECT * FROM complain WHERE ComplainID='"+comp_id_ins+"'";
        Cursor cursor = database.rawQuery(select_item_id_Query,null);
        return cursor;
    }

public void AddItem (SellItem item){
    SQLiteDatabase database = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    Toast.makeText(con,item.getItemID(),Toast.LENGTH_SHORT).show();
    values.put("ItemID",item.getItemID());
    values.put("ItemName",item.getItemName());
    values.put("Price",item.getPrice());
    values.put("StoreQty",item.getStoreQty());
    values.put("CompanyDiscount",item.getCompanyDiscount());
    values.put("MinUnit",item.getMinUnit());
    values.put("MinOrderUnit",item.getMinOrderUnit());
    values.put("CategoryID",item.getCategoryID());
    values.put("Sync",item.isSync());

    database.insert("item",null, values);


}


public Cursor getExactItemByID(String ItemID){
    SQLiteDatabase database = this.getReadableDatabase();
    String select_exact_item_id_Query = "SELECT * FROM item where ItemID='"+ItemID+"'";
    Cursor cursor = database.rawQuery(select_exact_item_id_Query,null);
    return cursor;

}

public Cursor getAllItemByName (){
        SQLiteDatabase database = this.getReadableDatabase();
        String select_item_id_Query = "SELECT ItemID,ItemName FROM item";

        Cursor cursor = database.rawQuery(select_item_id_Query,null);
        return cursor;
    }

public void setStateItem(SellItem itemEdit){
    SQLiteDatabase database = this.getWritableDatabase();
    ContentValues values = new ContentValues();

    values.put("StoreQty",itemEdit.getStoreQty());
    values.put("Sync",itemEdit.isSync());

    database.update("item", values,"ItemID"+" = ?",new String[] {itemEdit.getItemID()});

}

public Cursor getAllDiscounts(String itemID){
    SQLiteDatabase database = this.getReadableDatabase();
    String select_discount_id_Query = "SELECT * FROM discount where ItemID='"+itemID+"'";

    Cursor cursor = database.rawQuery(select_discount_id_Query,null);
    return cursor;
}

public void AddDiscount (String id,int max,int min,double disc) {
    SQLiteDatabase database = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    //Toast.makeText(con,item.getItemID(),Toast.LENGTH_SHORT).show();
    values.put("ItemID", id);
    values.put("MinQty", min);
    values.put("MaxQty", max);
    values.put("Discount", disc);

    database.insert("discount", null, values);
}

    public void test(){
        String create_discount_Table_query = "CREATE TABLE discount (ItemID VARCHAR(5), MinQty INTEGER, " +
                "MaxQty INTEGER,Discount FLOAT,Sync BOOLEAN,PRIMARY KEY (ItemID, MinQty, MaxQty))";
        dbase.execSQL(create_discount_Table_query);
        Toast.makeText(con,"DONE DONE",Toast.LENGTH_SHORT).show();
    }

    public UnitMap[] findQtyMap(String itemID){
        SQLiteDatabase database = this.getReadableDatabase();
        String select_discount_id_Query = "SELECT * FROM measure where ItemID='"+itemID+"' order by MapQty desc";

        Cursor cursor = database.rawQuery(select_discount_id_Query,null);
        UnitMap[] mapset=new UnitMap[cursor.getCount()];
        int i=0;
        if (cursor.moveToFirst()) {
            do {
                mapset[i]=new UnitMap(cursor.getInt(cursor.getColumnIndex("MapQty")),cursor.getString(cursor.getColumnIndex("unit")));
                i++;
            } while (cursor.moveToNext());
            return mapset;
        }
        return null;
    }

    public void addToComplain(Complain cmp){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ComplainID",cmp.getComplainID());
        values.put("ItemID",cmp.getItemID());
        values.put("Complain",cmp.getComplain());
        values.put("VendorNo",cmp.getVendorNo());
        values.put("Sync",cmp.getSync());
        database.insert("complain",null, values);

    }
    public void getComplainID(){
        SQLiteDatabase database = this.getReadableDatabase();
        String select_id_complain_Query = "SELECT * FROM complain";
        Cursor cursor = database.rawQuery(select_id_complain_Query,null);

        if (cursor.moveToFirst() && cursor.getCount() != 0) {
            do {

                Toast.makeText(con,cursor.getString(cursor.getColumnIndex("ComplainID"))+"-->"+cursor.getString(cursor.getColumnIndex("ItemID"))+"-->"+cursor.getString(cursor.getColumnIndex("Complain"))+"-->"+cursor.getString(cursor.getColumnIndex("VendorNo")),Toast.LENGTH_SHORT).show();


            } while (cursor.moveToNext());

        }

    }
    public Cursor findBillByID(String BillID_ins){
        SQLiteDatabase database = this.getReadableDatabase();
        String select_discount_id_Query = "SELECT * FROM bill where BillID='"+BillID_ins+"' order by name desc";

        Cursor cursor = database.rawQuery(select_discount_id_Query,null);
        return cursor;
    }



}

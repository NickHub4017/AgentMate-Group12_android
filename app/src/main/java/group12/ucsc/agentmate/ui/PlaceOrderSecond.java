package group12.ucsc.agentmate.ui;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import group12.ucsc.agentmate.R;
import group12.ucsc.agentmate.bll.Representative;
import group12.ucsc.agentmate.bll.SellItem;
import group12.ucsc.agentmate.bll.Vendor;
import group12.ucsc.agentmate.dbc.DatabaseControl;

/**
 * Created by NRV on 9/7/2014.
 */
public class PlaceOrderSecond extends Activity {
    DatabaseControl dbc=new DatabaseControl(this);
    AutoCompleteTextView itemID_edit_auto;
    AutoCompleteTextView itemName_edit_auto;
    Cursor itm_cur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_order_item_add);
        itemID_edit_auto=(AutoCompleteTextView)findViewById(R.id.auto_comp_item_id);
        itemName_edit_auto=(AutoCompleteTextView)findViewById(R.id.auto_comp_item_name);
        final Representative logged_rep=(Representative)getIntent().getExtras().getSerializable("logged_user");
        final Vendor sel_vendor=(Vendor)getIntent().getExtras().getSerializable("vendor");
        TextView logged_vendor_tv=(TextView)findViewById(R.id.txt_vname_order_b);
        logged_vendor_tv.setText("Selected Vendor is :- "+sel_vendor.getShopName());
        //TableLayout t1;


        TableLayout tl = (TableLayout) findViewById(R.id.selected_table1);
        final TableRow tr_head = new TableRow(this);
        tr_head.setId(10);
        tr_head.setBackgroundColor(Color.BLACK);
        tr_head.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        TextView label_Item_ID = new TextView(this);
        label_Item_ID.setId(20);
        label_Item_ID.setText("Item ID");
        label_Item_ID.setTextColor(Color.WHITE);
        label_Item_ID.setPadding(5, 5, 5, 5);
        tr_head.addView(label_Item_ID);// add the column to the table row here

        TextView label_Item_Name = new TextView(this);
        label_Item_Name.setId(21);// define id that must be unique
        label_Item_Name.setText("Item Name"); // set the text for the header
        label_Item_Name.setTextColor(Color.WHITE); // set the color
        label_Item_Name.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_Item_Name); // add the column to the table row here

        TextView label_Qty = new TextView(this);
        label_Qty.setId(22);// define id that must be unique
        label_Qty.setText("Qty"); // set the text for the header
        label_Qty.setTextColor(Color.WHITE); // set the color
        label_Qty.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_Qty); // add the column to the table row here

        TextView label_Discount = new TextView(this);
        label_Discount.setId(23);// define id that must be unique
        label_Discount.setText("Discount"); // set the text for the header
        label_Discount.setTextColor(Color.WHITE); // set the color
        label_Discount.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_Discount); // add the column to the table row here

        TextView label_Price = new TextView(this);
        label_Price.setId(24);// define id that must be unique
        label_Price.setText("Price"); // set the text for the header
        label_Price.setTextColor(Color.WHITE); // set the color
        label_Price.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_Price); // add the column to the table row here

        tl.addView(tr_head, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        itm_cur=dbc.getAllItemByName();

        final String[] str_arry_item_id=new String[itm_cur.getCount()];
        final String[] str_arry_item_name=new String[itm_cur.getCount()];
        int j=0;
        if (itm_cur.moveToFirst()&& itm_cur.getCount()!=0){
            do{
                str_arry_item_id[j]=itm_cur.getString(itm_cur.getColumnIndex("ItemID"));
                str_arry_item_name[j]=itm_cur.getString(itm_cur.getColumnIndex("ItemName"));
                j++;
            }while(itm_cur.moveToNext());
        }

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,str_arry_item_id);
        itemID_edit_auto.setAdapter(adapter);
        itemID_edit_auto.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String selection = (String)adapterView.getItemAtPosition(position);
                //int real_id_pos=getReal(selection,str_arry_item_id);
                //itm_cur.moveToPosition(real_id_pos);
                Cursor cur=dbc.getExactItemByID(selection);
                cur.moveToFirst();

                RowCreator(selection);
                SellItem currentItem=new SellItem(selection,PlaceOrderSecond.this);
                //Toast.makeText(getApplicationContext(),si.getItemID()+" "+si.getDiscount()[0].getDiscount()+" "+si.getDiscount()[1].getDiscount(),Toast.LENGTH_SHORT).show();
            ///TODO write an constructor for item class which retrieve its data in database when the ItemId gives as constructor parameter.

            }
        });
        ArrayAdapter adapter2 = new ArrayAdapter(this,android.R.layout.simple_list_item_1,str_arry_item_name);
        itemName_edit_auto.setAdapter(adapter2);
        //itemName_edit_auto.setOnItemClickListener(new );

    }

    public int getReal(String key,String[] array){
       for (int i=0;i<array.length;i++){
           if(array[i].equals(key)){
               return i;
           }
       }
       return -1;
    }

    public void RowCreator(String p){
        TableLayout tl = (TableLayout) findViewById(R.id.selected_table1);
        Integer count=0;
        int i=0;

            i++;
            String date = p;// get the first variable
            Double weight_kg =2.5;// get the second variable
// Create the table row
            TableRow tr = new TableRow(this);
            if(count%2!=0) tr.setBackgroundColor(Color.GRAY);
            tr.setId(100+count);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

//Create two columns to add as table data
            // Create a TextView to add date
            TextView labelDATE = new TextView(this);
            labelDATE.setId(200+count);
            labelDATE.setText(date);
            labelDATE.setPadding(2, 0, 5, 0);
            labelDATE.setTextColor(Color.BLACK);
            tr.addView(labelDATE);

            TextView labelWEIGHT = new TextView(this);
            labelWEIGHT.setId(200+count);
            labelWEIGHT.setText(weight_kg.toString());
            labelWEIGHT.setTextColor(Color.BLACK);
            tr.addView(labelWEIGHT);

            TextView labelItem = new TextView(this);
            labelItem.setId(200+count);
            labelItem.setText("Name");
            labelItem.setTextColor(Color.BLACK);
            tr.addView(labelItem);

            TextView labelItem2 = new TextView(this);
            labelItem2.setId(200+count);
            labelItem2.setText("Name");
            labelItem2.setTextColor(Color.BLACK);
            tr.addView(labelItem2);

            TextView labelItem3 = new TextView(this);
            labelItem3.setId(200+count);
            labelItem3.setText("Name");
            labelItem3.setTextColor(Color.BLACK);
            tr.addView(labelItem3);

// finally add this to the table row
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            count++;

    }


}




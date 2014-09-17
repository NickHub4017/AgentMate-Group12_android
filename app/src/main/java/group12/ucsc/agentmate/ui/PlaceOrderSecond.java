package group12.ucsc.agentmate.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
    SellItem currentItem;
    int count=0;
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


        table_hdr();
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

                //RowCreator(currentItem);
                currentItem=new SellItem(selection,PlaceOrderSecond.this);
                showDialog(1);


            ///TODO write an constructor for item class which retrieve its data in database when the ItemId gives as constructor parameter.

            }
        });
        ArrayAdapter adapter2 = new ArrayAdapter(this,android.R.layout.simple_list_item_1,str_arry_item_name);
        itemName_edit_auto.setAdapter(adapter2);
        //itemName_edit_auto.setOnItemClickListener(new );

        Button b2=(Button)findViewById(R.id.button_testing);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(1);
            }
        });
    }

    public int getReal(String key,String[] array){
       for (int i=0;i<array.length;i++){
           if(array[i].equals(key)){
               return i;
           }
       }
       return -1;
    }

public void RowCreator(SellItem item){
        //item.setQty(10);
        //ToDo remove the hardcoded qty via dialobox.
        TableLayout tl = (TableLayout) findViewById(R.id.selected_table1);

// Create the table row
            TableRow tr = new TableRow(this);
            if(count%2!=0) tr.setBackgroundColor(Color.GRAY);
            tr.setId(100+count);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
///TODO must write a method to get qty
//Create two columns to add as table data
            // Create a TextView to add date
            TextView labelID = new TextView(this);
        labelID.setId(200+count);
        labelID.setText(item.getItemID());
        labelID.setPadding(2, 0, 5, 0);
        labelID.setTextColor(Color.BLACK);
            tr.addView(labelID);

        TextView labelName = new TextView(this);
        labelName.setId(300+count);
        labelName.setText(String.valueOf(item.getItemName()));
        labelName.setTextColor(Color.BLACK);
        tr.addView(labelName);

            TextView labelQty = new TextView(this);
        labelQty.setId(400+count);
        labelQty.setText(String.valueOf(item.getQty()));
        labelQty.setTextColor(Color.BLACK);
            tr.addView(labelQty);

            TextView labelDiscount = new TextView(this);
        labelDiscount.setId(500+count);
        labelDiscount.setText(String.valueOf(item.getRelavantDiscount(item.getQty())));
        labelDiscount.setTextColor(Color.BLACK);
            tr.addView(labelDiscount);

            TextView labelPrice = new TextView(this);
        labelPrice.setId(600+count);
        double value=(100-item.getRelavantDiscount(item.getQty()))*item.getPrice()*item.getQty();
        labelPrice.setText(String.valueOf(value/100));
        labelPrice.setTextColor(Color.BLACK);
            tr.addView(labelPrice);

// finally add this to the table row
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            count++;

    }

public void table_hdr(){
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

 }

public Dialog onCreateDialog(int a){
    AlertDialog.Builder builder;//=new AlertDialog.Builder(this);
    LayoutInflater inflater;//=getLayoutInflater();
    builder=new AlertDialog.Builder(this);
    inflater=getLayoutInflater();
    builder.setView(inflater.inflate(R.layout.item_load_dialog, null))
            .setTitle("First Dialog Box" )
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Dialog dlg=(Dialog)arg0;
                    EditText etxtName=(EditText)dlg.findViewById(R.id.txt_Qty);
                    if(etxtName.getText().toString().length()>0)
                    {
                        String name=etxtName.getText().toString();
                        Toast.makeText(getApplicationContext(), "I am , " + name, Toast.LENGTH_LONG).show();
                        currentItem.setQty(Integer.parseInt(name));
                        RowCreator(currentItem);
                    }
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "You clicked on Cancel", Toast.LENGTH_LONG).show();
                }
            });
    return builder.create();
}

}

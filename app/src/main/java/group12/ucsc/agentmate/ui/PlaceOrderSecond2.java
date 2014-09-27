package group12.ucsc.agentmate.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

/**
 * Created by NRV on 9/27/2014.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import group12.ucsc.agentmate.R;
import group12.ucsc.agentmate.bll.Order;
import group12.ucsc.agentmate.bll.Representative;
import group12.ucsc.agentmate.bll.SellItem;
import group12.ucsc.agentmate.bll.UnitMap;
import group12.ucsc.agentmate.bll.Vendor;
import group12.ucsc.agentmate.dbc.DatabaseControl;
import group12.ucsc.agentmate.bll.mapper;
import group12.ucsc.agentmate.ui.DialogGetQty.GetQtyCommunicator;
/**
 * Created by NRV on 9/27/2014.
 */
public class PlaceOrderSecond2 extends Activity implements GetQtyCommunicator{
    Order new_order=new Order(); //Create new Order
    DatabaseControl dbc = new DatabaseControl(this);
    UnitMap[] map;
    mapper mpUnitnames=null;
    AutoCompleteTextView itemID_edit_auto;
    AutoCompleteTextView itemName_edit_auto;
    SellItem currentItem;
    boolean select_exsist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_order_item_add);

        itemID_edit_auto = (AutoCompleteTextView) findViewById(R.id.auto_comp_item_id);
        itemName_edit_auto = (AutoCompleteTextView) findViewById(R.id.auto_comp_item_name);

        final Representative logged_rep = (Representative) getIntent().getExtras().getSerializable("logged_user");
        final Vendor sel_vendor = (Vendor) getIntent().getExtras().getSerializable("vendor");

        TextView logged_vendor_tv = (TextView) findViewById(R.id.txt_vname_order_b);
        logged_vendor_tv.setText("Selected Vendor is :- " + sel_vendor.getShopName());

        table_hdr();///Draw headers of the tables
        demand_table_hdr();

        Cursor itm_cur = dbc.getAllItemByName();

        final String[] str_arry_item_id = new String[itm_cur.getCount()];
        final String[] str_arry_item_name = new String[itm_cur.getCount()];
        int j = 0;
        if (itm_cur.moveToFirst() && itm_cur.getCount() != 0) {
            do {
                str_arry_item_id[j] = itm_cur.getString(itm_cur.getColumnIndex("ItemID"));
                str_arry_item_name[j] = itm_cur.getString(itm_cur.getColumnIndex("ItemName"));
                j++;
            } while (itm_cur.moveToNext());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, str_arry_item_id);
        itemID_edit_auto.setAdapter(adapter);
        itemID_edit_auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String selection = (String) adapterView.getItemAtPosition(position);//
                mpUnitnames=new mapper(getApplicationContext(),selection);//GET the unit maps
                int pos = new_order.findById(selection);


                if (pos != -1) {
                    currentItem = new_order.findByIdObj(pos);
                    select_exsist=true;
                }
                else{
                    select_exsist=false;
                    currentItem = new SellItem(selection, PlaceOrderSecond2.this);
                }

                FragmentManager fm = getFragmentManager();
                DialogGetQty md = new DialogGetQty();
                Bundle args = new Bundle();

                args.putInt("qty", currentItem.getStoreQty());
                args.putString("itemid", currentItem.getItemID());
                args.putSerializable("umapname", mpUnitnames);
                md.setArguments(args);
                md.show(fm, "dialog2");



            }

        });

        Button btset=(Button)findViewById(R.id.button_testing);
        btset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    dbc.k();
            }
        });
    }

    public void table_hdr() {
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

    public void demand_table_hdr() {
        TableLayout tl = (TableLayout) findViewById(R.id.demanded_table);
        final TableRow tr_head = new TableRow(this);
        tr_head.setId(10);
        tr_head.setBackgroundColor(Color.BLACK);
        tr_head.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        TextView label_Item_ID = new TextView(this);
        label_Item_ID.setId(30);
        label_Item_ID.setText("Item ID");
        label_Item_ID.setTextColor(Color.WHITE);
        label_Item_ID.setPadding(5, 5, 5, 5);
        tr_head.addView(label_Item_ID);// add the column to the table row here

        TextView label_Item_Name = new TextView(this);
        label_Item_Name.setId(31);// define id that must be unique
        label_Item_Name.setText("Item Name"); // set the text for the header
        label_Item_Name.setTextColor(Color.WHITE); // set the color
        label_Item_Name.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_Item_Name); // add the column to the table row here

        TextView label_Qty = new TextView(this);
        label_Qty.setId(32);// define id that must be unique
        label_Qty.setText("Qty"); // set the text for the header
        label_Qty.setTextColor(Color.WHITE); // set the color
        label_Qty.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_Qty); // add the column to the table row here

        TextView label_Discount = new TextView(this);
        label_Discount.setId(33);// define id that must be unique
        label_Discount.setText("Date"); // set the text for the header
        label_Discount.setTextColor(Color.WHITE); // set the color
        label_Discount.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_Discount); // add the column to the table row here


        tl.addView(tr_head, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

    }


    @Override
    public void onGetData(int qty, int demandQty) {
        Toast.makeText(getApplicationContext(),"//////////////"+qty+" "+demandQty+" "+" " ,Toast.LENGTH_SHORT).show();
    }
}




package group12.ucsc.agentmate.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import group12.ucsc.agentmate.R;
import group12.ucsc.agentmate.bll.Representative;
import group12.ucsc.agentmate.bll.Vendor;

/**
 * Created by NRV on 9/7/2014.
 */
public class PlaceOrderSecond extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_order_item_add);
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
        label_Qty.setId(21);// define id that must be unique
        label_Qty.setText("Qty"); // set the text for the header
        label_Qty.setTextColor(Color.WHITE); // set the color
        label_Qty.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_Qty); // add the column to the table row here

        TextView label_Discount = new TextView(this);
        label_Discount.setId(21);// define id that must be unique
        label_Discount.setText("Discount"); // set the text for the header
        label_Discount.setTextColor(Color.WHITE); // set the color
        label_Discount.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_Discount); // add the column to the table row here

        TextView label_Price = new TextView(this);
        label_Price.setId(21);// define id that must be unique
        label_Price.setText("Price"); // set the text for the header
        label_Price.setTextColor(Color.WHITE); // set the color
        label_Price.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_Price); // add the column to the table row here

        tl.addView(tr_head, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

    }
}




package group12.ucsc.agentmate.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import group12.ucsc.agentmate.R;
import group12.ucsc.agentmate.dbc.DatabaseControl;

/**
 * Created by NRV on 9/7/2014.
 */
public class PlaceOrderFirst extends Activity {
    DatabaseControl dbc=new DatabaseControl(this);
    Cursor cursor_ven_id;

    AutoCompleteTextView vno_edit_auto;
    AutoCompleteTextView shname_edit_auto;
    TextView txt_venaddr;
    TextView txt_venmngr;
    TextView txt_due_amnt;
    Button btn_cntd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_order_ven_select);

        vno_edit_auto=(AutoCompleteTextView)findViewById(R.id.edit_auto_venid_order);
        shname_edit_auto=(AutoCompleteTextView)findViewById(R.id.edit_shopname_auto_order);
        txt_venaddr=(TextView)findViewById(R.id.txt_venaddr_order);
        txt_venmngr=(TextView)findViewById(R.id.txt_mngr_nm_order);
        txt_due_amnt=(TextView)findViewById(R.id.txt_due_amnt_order);
        btn_cntd=(Button)findViewById(R.id.btn_contd_order_a);
        btn_cntd.setEnabled(false);

        cursor_ven_id=dbc.getVendorTable();
        final String[] str_arry_vno=new String[cursor_ven_id.getCount()];
        final String[] str_arry_shname=new String[cursor_ven_id.getCount()];
        int i=0;
        if (cursor_ven_id.moveToFirst()){
            do{
                str_arry_vno[i]=cursor_ven_id.getString(cursor_ven_id.getColumnIndex("venderno"));
                str_arry_shname[i]=cursor_ven_id.getString(cursor_ven_id.getColumnIndex("ShopName"));

                i++;
            }while(cursor_ven_id.moveToNext());
        }

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,str_arry_vno);
        vno_edit_auto.setAdapter(adapter);
        vno_edit_auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String selection = (String)adapterView.getItemAtPosition(position);
                int real_pos=Edit_Customer.getArrayPosition(selection,str_arry_vno);
                cursor_ven_id.moveToPosition(real_pos);
                //Toast.makeText(getApplicationContext(), selection, Toast.LENGTH_SHORT).show();
                setDataField(cursor_ven_id);

            }
        });

        ArrayAdapter adapter_shname = new ArrayAdapter(this,android.R.layout.simple_list_item_1,str_arry_shname);

        shname_edit_auto.setAdapter(adapter_shname);
        shname_edit_auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String selection = (String)adapterView.getItemAtPosition(position);
                int real_pos=Edit_Customer.getArrayPosition(selection,str_arry_shname);
                cursor_ven_id.moveToPosition(real_pos);
                //Toast.makeText(getApplicationContext(),""+cursor_ven_id.getString(cursor_ven_id.getColumnIndex("ShopName"))+"Shop name"+selection,Toast.LENGTH_SHORT).show();
                setDataField(cursor_ven_id);

            }
        });


        btn_cntd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (btn_cntd.isEnabled()){
                        cursor_ven_id.close();
                        Intent next=new Intent(PlaceOrderFirst.this,PlaceOrderSecond.class);
                        startActivity(next);
                    }
                }
            });





    }

   public void setDataField(Cursor cur){
       vno_edit_auto.setText(cur.getString(cur.getColumnIndex("venderno")));
        shname_edit_auto.setText(cur.getString(cur.getColumnIndex("ShopName")));

       txt_venmngr.setText("\t"+cur.getString(cur.getColumnIndex("VenderName")));
       txt_venaddr.setText("\t"+cur.getString(cur.getColumnIndex("Address")));
       txt_due_amnt.setText("\t \t"+cur.getString(cur.getColumnIndex("Overdue")));
       txt_due_amnt.setTextColor(Color.parseColor("#F505E5"));

       btn_cntd.setEnabled(true);


    }


}

package group12.ucsc.agentmate.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import group12.ucsc.agentmate.R;
import group12.ucsc.agentmate.bll.SellItem;

/**
 * Created by NRV on 9/20/2014.
 */
public class DialogEditQty extends DialogFragment{

    EditComm ecm;
    String[] itemid;
    SellItem toEditItem;
    int select_pos;
    EditText etx_qty;
    TextView txt_cur_qty;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ecm = (EditComm)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.select_list_edit, null);
        AutoCompleteTextView auto_edit_itm_id=(AutoCompleteTextView)view.findViewById(R.id.editauto_item_id);
        AutoCompleteTextView auto_edit_itm_name=(AutoCompleteTextView)view.findViewById(R.id.editauto_item_name);
        txt_cur_qty=(TextView)view.findViewById(R.id.txt_edit_order2);
        etx_qty=(EditText)view.findViewById(R.id.edit_qty_order);

        itemid=getData(0);
        ArrayAdapter adapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,itemid );
        auto_edit_itm_id.setAdapter(adapter);
        auto_edit_itm_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                select_pos=pos;
                toEditItem=PlaceOrderSecond.new_order.list.get(GetRealPos((String) adapterView.getItemAtPosition(pos)));
                txt_cur_qty.setText("Enter Qty you want current is "+toEditItem.getQty()+" has total "+(toEditItem.getQty()+toEditItem.getStoreQty()));
                //Toast.makeText(getActivity().getApplicationContext(),toEditItem.getStoreQty(),Toast.LENGTH_SHORT).show();
                //Log.d("DialogEditQty",String.valueOf(toEditItem.getStoreQty()));
            }
        });


        ArrayAdapter adapter2 = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, getData(1));
        auto_edit_itm_name.setAdapter(adapter2);

        Button btn_delete=(Button)view.findViewById(R.id.btn_order_edit_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlaceOrderSecond.new_order.list.remove(toEditItem);
                itemid=getData(0);
                ecm.onEditMessage();
                dismiss();
            }
        });

        Button btn_edit=(Button)view.findViewById(R.id.btn_order_edit_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toEditItem.resetStoreQty();
                int temp_qty=Integer.parseInt(etx_qty.getText().toString());
                if (temp_qty>toEditItem.getStoreQty()){
                    show_warning();
                }
                else {
                    toEditItem.setQty(temp_qty);
                    toEditItem.setStoreQty(toEditItem.getStoreQty() - toEditItem.getQty());
                    ecm.onEditMessage();
                }
                dismiss();

            }
        });
        /*btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toEditItem.setStoreQty(toEditItem.getStoreQty()+toEditItem.getQty());
                int qty=Integer.parseInt(etx_qty.getText().toString());
                toEditItem.setQty(qty);
                toEditItem.setStoreQty(toEditItem.getStoreQty()-qty);
                ecm.onEditMessage();
            }
        });*/

        return view;
    }

    interface EditComm{
        public void onEditMessage();
    }


    public String[] getData(int dataid){
        int len=PlaceOrderSecond.new_order.list.size();
        String[] reslt_list=new String[len];
        for (int i=0;i<len;i++){
            if (dataid==0){
                reslt_list[i]=PlaceOrderSecond.new_order.list.get(i).getItemID();
            }
            else{
                reslt_list[i]=PlaceOrderSecond.new_order.list.get(i).getItemName();
            }
        }
        return reslt_list;


    }

    public int GetRealPos(String key){
        for (int i=0;i<itemid.length;i++){
            if (itemid[i].equals(key)){
                return i;
            }
        }
        return -1;
    }

    public void show_warning(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setTitle("Quantity limit exceed")
                .setMessage("Quantity limit exceeds Maximum is "+(toEditItem.getStoreQty()))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Yes button clicked, do something
                    }
                })						//Do nothing on no
                .show();

    }


}

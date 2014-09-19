package group12.ucsc.agentmate.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import group12.ucsc.agentmate.ui.PlaceOrderSecond;
import group12.ucsc.agentmate.R;
import group12.ucsc.agentmate.bll.SellItem;

/**
 * Created by NRV on 9/17/2014.
 */
public class DialogGetQty extends DialogFragment{
    Button btn_submit,btn_cancel,btn_submit_demand;
    Communicator cm;
    int entered_qty,entered_demand_qty;
    EditText txt_get_qty;
    EditText txt_get_dmnd_qty;
    Spinner unit_spinner,unit_demand_spinner;
    SellItem s_item;
    String[] unitSet;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        cm=(Communicator)activity;
        //s_item=(SellItem)activity.getIntent().getSerializableExtra("cur_item");



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.item_load_dialog, null);
        btn_submit=(Button)view.findViewById(R.id.btn_sbmt_dialog);
        btn_cancel=(Button)view.findViewById(R.id.btn_cancel_dialog);
        btn_submit_demand=(Button)view.findViewById(R.id.btn_sbmt_with_demand_dialog);

        txt_get_qty=(EditText)view.findViewById(R.id.txt_Qty);
        txt_get_dmnd_qty=(EditText)view.findViewById(R.id.edit_demand_qty);

        TextView Remain_tv=(TextView)view.findViewById(R.id.text_remain);

        Remain_tv.setText("Remaining Stock for this product "+PlaceOrderSecond.cur_st_value);

        unit_spinner=(Spinner)view.findViewById(R.id.unit_spinner);
        unit_demand_spinner=(Spinner)view.findViewById(R.id.demand_unit_spinner);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                entered_qty=Integer.parseInt(txt_get_qty.getText().toString());
                if (entered_qty>PlaceOrderSecond.cur_st_value){
                    show_warning();
                }

                else{
                    cm.onDialogMessage(entered_qty,0,unit_spinner.getSelectedItemPosition(),0);//ToDo set arrays
                    dismiss();
                }

            }
        });
        btn_cancel=(Button)view.findViewById(R.id.btn_cancel_dialog);
        btn_cancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btn_submit_demand=(Button)view.findViewById(R.id.btn_sbmt_with_demand_dialog);
        btn_submit_demand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entered_qty=Integer.parseInt(txt_get_qty.getText().toString());
                entered_demand_qty=Integer.parseInt(txt_get_dmnd_qty.getText().toString());
                cm.onDialogMessage(entered_qty,entered_demand_qty,unit_spinner.getSelectedItemPosition(),unit_demand_spinner.getSelectedItemPosition());//ToDo set arrays
                dismiss();

            }
        });

        unitSet=PlaceOrderSecond.getStringUnits();
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,PlaceOrderSecond.getStringUnits());
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit_spinner.setAdapter(spinnerArrayAdapter);


        unitSet=PlaceOrderSecond.getStringUnits();
        final ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,PlaceOrderSecond.getStringUnits());
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit_demand_spinner.setAdapter(spinnerArrayAdapter2);

        return view;
    }

public void show_warning(){
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder
            .setTitle("Quantity limit exceed")
            .setMessage("Press yes if you want to revise input to demand list. Otherwise Select No(Only remain will bill)")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //Yes button clicked, do something
                    txt_get_qty.setText(String.valueOf(PlaceOrderSecond.cur_st_value));
                    txt_get_dmnd_qty.setText(String.valueOf(entered_qty-PlaceOrderSecond.cur_st_value));
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    cm.onDialogMessage(PlaceOrderSecond.cur_st_value,0,unit_spinner.getSelectedItemPosition(),0);//ToDo set arrays
                    dismiss();
                }
            })						//Do nothing on no
            .show();

}
interface Communicator{
        public void onDialogMessage(int qty,int demandQty,int qtyUnitindex,int demandQtyUnitIndex);
    }
//ToDo change number of parameteres in onDialogMessage method (to get qty in dif units),get demand qty,date for demanded values
}

//ToDO convert all qty to its minimum unit.
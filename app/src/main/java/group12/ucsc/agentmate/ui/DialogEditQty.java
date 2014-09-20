package group12.ucsc.agentmate.ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    Button btn_submit,btn_cancel,btn_submit_demand;
    EditComm Ecm;
    int entered_qty,entered_demand_qty,select_unit_index,demand_unit_index;
    EditText txt_get_qty;
    EditText txt_get_dmnd_qty;
    Spinner unit_spinner2,unit_demand_spinner;
    SellItem s_item;
    String[] unitSet;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.item_load_dialog, null);
        btn_submit=(Button)view.findViewById(R.id.btn_sbmt_dialog);
        btn_cancel=(Button)view.findViewById(R.id.btn_cancel_dialog);
        btn_submit_demand=(Button)view.findViewById(R.id.btn_sbmt_with_demand_dialog);
        txt_get_qty=(EditText)view.findViewById(R.id.txt_Qty);
        txt_get_dmnd_qty=(EditText)view.findViewById(R.id.edit_demand_qty);

        TextView Remain_tv=(TextView)view.findViewById(R.id.text_remain);//ToDO set store quantity to diplay
        Remain_tv.setText("Current Store has "+PlaceOrderSecond.t_selItem.getStoreQty()+" "+"You prevously selected"+PlaceOrderSecond.t_selItem.getQty()+" "+PlaceOrderSecond.t_selItem.getSelectedUnit());
        Log.d("EditQty",String.valueOf(PlaceOrderSecond.t_selItem==null));
        //+PlaceOrderSecond.t_selItem.getStoreQty()+" "+"You prevously selected"+PlaceOrderSecond.t_selItem.getQty()+" "+PlaceOrderSecond.t_selItem.getSelectedUnit()
        unitSet=PlaceOrderSecond.getStringUnits();
        Log.d("DialogEditQty",String.valueOf(unitSet.length)+unitSet[0]+unitSet[1]+unitSet[2]);
        //String[] p={"a","b","c"};
        ViewGroup unit_radio_group = (ViewGroup) view.findViewById(R.id.hour_radio_group);  // This is the id of the RadioGroup we defined
        for (int i = 0; i<unitSet.length; i++) {
            RadioButton button = new RadioButton(getActivity().getApplicationContext());
            button.setId(i+1000);
            button.setText(unitSet[i]);
            button.setTextColor(Color.BLACK);
             // Only select button with same index as currently selected number of hours
            if (PlaceOrderSecond.t_selItem.getSelectedUnit().equals(unitSet[i])){//ToDo set boollean with cyrrent unit
                button.setChecked(true);
                select_unit_index=i;
            }
            //button.setBackgroundResource(R.drawable.item_selector); // This is a custom button drawable, defined in XML
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((RadioGroup) view.getParent()).check(view.getId());
                    //Toast.makeText(getActivity().getApplicationContext(),String.valueOf(view.getId()),Toast.LENGTH_SHORT).show();
                    select_unit_index=view.getId()-1000;
                }
            });

            unit_radio_group.addView(button);
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //ToDO implement unit change conflict method for update..




        //Toast.makeText(getActivity().getApplicationContext(),PlaceOrderSecond.u_map.length,Toast.LENGTH_SHORT).show();
        /*ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,p);
        spinnerArrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit_spinner2.setAdapter(spinnerArrayAdapter3);*/
//////TODO CLEAR THIS
/*
        unitSet=PlaceOrderSecond.getStringUnits();
        final ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,PlaceOrderSecond.getStringUnits());
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit_demand_spinner.setAdapter(spinnerArrayAdapter2);*/


        return view;
    }

    interface EditComm{
        public void onEditMessage(int qty,int demandQty,int qtyUnitindex,int demandQtyUnitIndex);
    }
}

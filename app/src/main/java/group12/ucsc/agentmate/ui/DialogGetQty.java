package group12.ucsc.agentmate.ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
    int entered_qty;
    EditText txt_get_qty;
    EditText txt_get_dmnd_qty;
    SellItem s_item;
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
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entered_qty=Integer.parseInt(txt_get_qty.getText().toString());
                cm.onDialogMessage(""+entered_qty);
                dismiss();
            }
        });

        return view;
    }

    interface Communicator{
        public void onDialogMessage(String msg);
    }

}


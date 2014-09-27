package group12.ucsc.agentmate.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import group12.ucsc.agentmate.bll.UnitMap;
import group12.ucsc.agentmate.bll.mapper;
import group12.ucsc.agentmate.dbc.DatabaseControl;
import group12.ucsc.agentmate.ui.PlaceOrderSecond;
import group12.ucsc.agentmate.R;
import group12.ucsc.agentmate.bll.SellItem;

/**
 * Created by NRV on 9/17/2014.
 */
public class DialogGetQty extends DialogFragment {
    Button btn_submit, btn_cancel, btn_submit_demand;

    GetQtyCommunicator cm;
    static EditText txt_get_qty;
    EditText txt_get_dmnd_qty;
    TextView txt_item_reamin;

    String Selectedunit;
    String Selected_demndedunit;
    mapper Umaps;
    int cur_store_Qty;
    int mapped;
    int mappedDemand;
    int tempQty;
    String[] QtyArray,QtyArray_demand;
    EditText enterBox[],DemandBox[];
    int tot=0,demtot=0;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        cm=(GetQtyCommunicator)activity;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_load_dialog, null);
        cur_store_Qty=getArguments().getInt("qty");
        String cur_id=getArguments().getString("itemid");
        Umaps=(mapper)getArguments().getSerializable("umapname");
        //radioBtnCreator(view,Umaps.getUnitNames());
        //radioBtnCreator_demand(view,Umaps.getUnitNames());
        textboxCreator(view,Umaps.getUnitNames());
        textboxCreator_demand(view,Umaps.getUnitNames());
        btn_submit=(Button)view.findViewById(R.id.btn_sbmt_dialog);
        btn_cancel=(Button)view.findViewById(R.id.btn_cancel_dialog);
        btn_submit_demand=(Button)view.findViewById(R.id.btn_sbmt_with_demand_dialog);
        txt_get_qty=(EditText) view.findViewById(R.id.txt_Qty);
        txt_get_dmnd_qty=(EditText) view.findViewById(R.id.edit_demand_qty);
        txt_item_reamin=(TextView)view.findViewById(R.id.text_remain);
        txt_item_reamin.setText("Currently Only "+cur_store_Qty+" "+Umaps.getsmallUnit()+" are only available");
        QtyArray=new String[Umaps.getUnitNames().length];
        QtyArray_demand=new String[Umaps.getUnitNames().length];


        txt_get_qty.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                Log.d("KEY",txt_get_qty.getText().toString());
                return false;
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//ToDo set the method to avoid null inputs.

                for (int i=0;i<Umaps.u_map.length;i++){
                    try{
                     int x=Integer.parseInt(QtyArray[i]);
                     tot=tot+Umaps.getQtyInMinUnit(Umaps.u_map[i].getUnit(),x);
                    }
                    catch (Exception e){
                        tot=tot+0;

                    }
                }
                    Log.d("Array total",""+tot);
                if (tot>cur_store_Qty){
                    show_warning();
                }
                else{
                    cm.onGetData(tot,demtot);
                    dismiss();
                }

                //EditText tvt=(EditText)view.getRootView().findViewById(1801);
                //tvt.setText("jjgjgj");
                //Log.d("TExt",tvt.getText().toString());
                //tvt.setText("");

               /* tempQty = Integer.parseInt(txt_get_qty.getText().toString());
                int tempDemandQty = Integer.parseInt(txt_get_dmnd_qty.getText().toString());
                mapped = Umaps.getQtyInMinUnit(Selectedunit, tempQty);
                mappedDemand = Umaps.getQtyInMinUnit(Selected_demndedunit, tempDemandQty);
                if (mapped > cur_store_Qty) {
                    show_warning();
                } else {
                    cm.onGetData(tempQty, tempDemandQty, Selectedunit, Selected_demndedunit);
                    dismiss();
                }
                Log.d("MAPpped Qty", "" + mapped + " " + mappedDemand);*/

            }
        });

        btn_submit_demand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tot=0;
                for (int i=0;i<Umaps.u_map.length;i++){
                    try{
                        int x=Integer.parseInt(QtyArray[i]);
                        tot=tot+Umaps.getQtyInMinUnit(Umaps.u_map[i].getUnit(),x);
                    }
                    catch (Exception e){
                        tot=tot+0;
                    }
                }
                Log.d("Array total",""+tot);
                if (tot>cur_store_Qty){
                    show_warning();
                }
                else{
                    demtot=0;
                    for (int i=0;i<Umaps.u_map.length;i++){
                        try{
                            int x=Integer.parseInt(QtyArray_demand[i]);
                            demtot=demtot+Umaps.getQtyInMinUnit(Umaps.u_map[i].getUnit(),x);
                        }
                        catch (Exception e){
                            demtot=demtot+0;
                        }
                    }
                    cm.onGetData(tot,demtot);
                    dismiss();
                }

                //cm.onGetData(tot,demtot);



            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;

    }

    public void adjust(){//We need to give all the things to the endor
        int areas=tot-cur_store_Qty;
        demtot=areas;
        tot=cur_store_Qty;
        for (int i=0;i<Umaps.u_map.length;i++){
            EditText seletv=(EditText)getView().getRootView().findViewById(1800+i);
            EditText demandtv=(EditText)getView().getRootView().findViewById(1900+i);
            int result_for_demand=areas/Umaps.u_map[i].getQtyMap();
            int result_for_select=tot/Umaps.u_map[i].getQtyMap();
            if (result_for_demand==0){//we cannot give the areas by using this unit
                demandtv.setText(null);
            }
            else{
                demandtv.setText(String.valueOf(result_for_demand));
                QtyArray_demand[i]=String.valueOf(result_for_demand);
                areas=areas-(result_for_demand*Umaps.u_map[i].getQtyMap());

            }
            if (result_for_select==0){//we cannot give the areas by using this unit
                seletv.setText(null);
            }
            else{
                seletv.setText(String.valueOf(result_for_select));
                QtyArray[i]=String.valueOf(result_for_select);
                tot=tot-(result_for_select*Umaps.u_map[i].getQtyMap());
            }


        }
    }

    public void radioBtnCreator(View view,String[] Unitlist){
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.Radiolink1);
        layout.setBackgroundColor(Color.GRAY);
        RadioGroup radioGroup = new RadioGroup(getActivity().getApplicationContext());
        radioGroup.setOrientation(RadioGroup.HORIZONTAL);



        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT

        );


        layout.addView(radioGroup, p);

           for (int i=0;i<Unitlist.length;i++) {


               final RadioButton radioButtonView = new RadioButton(getActivity().getApplicationContext());
               radioButtonView.setText(Unitlist[i]);
               radioButtonView.setTextColor(Color.BLACK);
               radioButtonView.setId(i+1500);


               if (Umaps.getsmallUnit().equals(Unitlist[i])){
                   radioButtonView.setChecked(true);
                   Selectedunit=Umaps.getsmallUnit();
               }
               radioButtonView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Toast.makeText(getActivity().getApplicationContext(), radioButtonView.getText().toString()+"******", Toast.LENGTH_SHORT).show();
                       Selectedunit=radioButtonView.getText().toString();
                   }
               });
               radioGroup.addView(radioButtonView, 100,60);
           }

    }

    public void radioBtnCreator_demand(View view,String[] Unitlist){
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.radio_link_2);
        layout.setBackgroundColor(Color.GRAY);
        RadioGroup radioGroup = new RadioGroup(getActivity().getApplicationContext());
        radioGroup.setOrientation(RadioGroup.HORIZONTAL);



        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT

        );


        layout.addView(radioGroup, p);

        for (int i=0;i<Unitlist.length;i++) {


            final RadioButton radioButtonView = new RadioButton(getActivity().getApplicationContext());
            radioButtonView.setText(Unitlist[i]);
            radioButtonView.setTextColor(Color.BLACK);
            radioButtonView.setId(i+1600);
            radioButtonView.setBackgroundColor(Color.GRAY);

            if (Umaps.getsmallUnit().equals(Unitlist[i])){
                radioButtonView.setChecked(true);
                Selected_demndedunit=Umaps.getsmallUnit();
            }
            radioButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity().getApplicationContext(), radioButtonView.getText().toString()+"******", Toast.LENGTH_SHORT).show();
                    Selected_demndedunit=radioButtonView.getText().toString();
                }
            });
            radioGroup.addView(radioButtonView, 100,60);
        }

    }

    public void show_warning() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setTitle("Quantity limit exceed")
                .setMessage("Press yes if you want to revise input to demand list. Otherwise Select No(Only remain will bill)")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        adjust();

                        //Yes button clicked, do something
                        //txt_get_qty.setText(cur_store_Qty);
                        //txt_get_dmnd_qty.setText(String.valueOf(tempQty - Umaps.UnitConverter(Umaps.getsmallUnit(),cur_store_Qty,Selectedunit)));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cm.onGetData(tot, 0);//ToDo set arrays
                        dismiss();
                    }
                })                        //Do nothing on no
                .show();


    }

    public void textboxCreator(View view,String[] Unitlist){
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.Radiolink1);
        layout.setBackgroundColor(Color.LTGRAY);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(parms);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        layout.setGravity(Gravity.CLIP_VERTICAL);
        layout.setPadding(2, 2, 2, 2);

        for (int i=0;i<Unitlist.length;i++) {
            final EditText tv = new EditText(view.getContext());
            //tv.setText(Unitlist[i]);
            tv.setId(i+1800);
            tv.setPadding(5, 5, 5, 5);
            tv.setInputType(InputType.TYPE_CLASS_NUMBER);
            tv.setGravity(Gravity.CENTER);
            tv.setHint(Unitlist[i]);
            if((i%2)==0){
                tv.setBackgroundColor(Color.DKGRAY);
                tv.setTextColor(Color.LTGRAY);
            }
            tv.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {

                    QtyArray[view.getId()-1800]=tv.getText().toString();
                    Log.d("QtyArray",""+QtyArray[0]+" "+QtyArray[1]);
                    return false;
                }
            });
            layout.addView(tv, 100, 40);

        }


    }

    public void textboxCreator_demand(View view,String[] Unitlist){
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.radio_link_2);
        layout.setBackgroundColor(Color.LTGRAY);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(parms);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CLIP_VERTICAL);
        layout.setPadding(2, 2, 2, 2);

        for (int i=0;i<Unitlist.length;i++) {
            final EditText tv = new EditText(view.getContext());
            //tv.setText(Unitlist[i]);
            tv.setId(i+1900);
            tv.setPadding(5, 5, 5, 5);
            tv.setInputType(InputType.TYPE_CLASS_NUMBER);
            tv.setGravity(Gravity.CENTER);
            tv.setHint(Unitlist[i]);
            if((i%2)==0){
                tv.setBackgroundColor(Color.DKGRAY);
                tv.setTextColor(Color.LTGRAY);
            }
            tv.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {

                    QtyArray_demand[view.getId()-1900]=tv.getText().toString();
                    Log.d("QtyArray_demand",""+QtyArray_demand[0]+" "+QtyArray_demand[1]);
                    return false;
                }
            });
            layout.addView(tv, 100, 40);

        }


    }

    interface GetQtyCommunicator{
        public void onGetData(int qty,int demandQty);
    }


}


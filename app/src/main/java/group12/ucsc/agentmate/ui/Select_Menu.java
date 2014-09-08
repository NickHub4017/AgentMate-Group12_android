package group12.ucsc.agentmate.ui;

/**
 * Created by NRV on 8/23/2014.
 */


        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;


        import group12.ucsc.agentmate.R;
        import group12.ucsc.agentmate.bll.Representative;



public class Select_Menu extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intnt=getIntent();
        //intnt.get
        final Representative logged_rep=(Representative) intnt.getExtras().getSerializable("Logged_User");
        setContentView(R.layout.select_menu);

        Button btn_set_password=(Button)findViewById(R.id.btn_set_password);
        btn_set_password.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent set_password_intent=new Intent(Select_Menu.this, SetPassword.class);
                set_password_intent.putExtra("logged_user", logged_rep);
                startActivity(set_password_intent);

            }
        });

        Button btn_create_new_vendor=(Button)findViewById(R.id.btn_create_new_customer);
        btn_create_new_vendor.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent create_new_vendor_intent=new Intent(Select_Menu.this,CreateNewVendor.class);
                create_new_vendor_intent.putExtra("logged_user", logged_rep);
                startActivity(create_new_vendor_intent);
            }
        });

        Button btn_edit_vendor=(Button)findViewById(R.id.btn_edit_customer);
        btn_edit_vendor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent create_edit_vendor_intent=new Intent(Select_Menu.this,Edit_Customer.class);
                create_edit_vendor_intent.putExtra("logged_user", logged_rep);
                startActivity(create_edit_vendor_intent);
            }
        });


        Button btn_place_order=(Button)findViewById(R.id.btn_place_order);
        btn_place_order.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent place_order_first_intent=new Intent(Select_Menu.this,PlaceOrderFirst.class);
                place_order_first_intent.putExtra("logged_user", logged_rep);
                startActivity(place_order_first_intent);
            }
        });
    }


}



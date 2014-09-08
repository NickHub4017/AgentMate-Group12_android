package group12.ucsc.agentmate.ui;

/**
 * Created by NRV on 8/23/2014.
 */


        import android.app.Activity;
        import android.os.Bundle;
        import android.telephony.SmsManager;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import group12.ucsc.agentmate.R;
        import group12.ucsc.agentmate.dbc.DatabaseControl;

public class Recover extends  Activity{
    DatabaseControl dbc=new DatabaseControl(this);
    String ans,Eid,Encoded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.recover_window);

        String user=getIntent().getExtras().getString("Username_is");
        EditText ans_txt=(EditText)findViewById(R.id.edit_empid);
        ans_txt.setText("Your UserName is  "+(String)user);
        ans_txt.setEnabled(false);
        String qstn=getIntent().getExtras().getString("Question_is");

        TextView qsttv=(TextView)findViewById(R.id.lblqstn);
        qsttv.setText(qstn);
        ans=getIntent().getExtras().getString("Answer_is");
        Eid=getIntent().getExtras().getString("EmpId_is");
        Encoded=getIntent().getExtras().getString("Pwd_is");

        Button submit=(Button)findViewById(R.id.btn_submit);
        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText ans_txt=(EditText)findViewById(R.id.editanswer);
                String ent_ans=ans_txt.getText().toString();
                if (ans.equals(dbc.password_encoder(ent_ans))){

                    String phoneNo = "0712626607";
                    String msg = "Employee "+Eid+" Request for password change And his code is "+Encoded;
                    try {

                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNo, null, msg, null, null);
                        Toast.makeText(getApplicationContext(),"Your submission is send to head office. Please check your email/sms inbox." , Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(),
                                ex.getMessage().toString(),
                                Toast.LENGTH_LONG).show();
                        ex.printStackTrace();
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(),"Wrong answer. Try again. :0" , Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}

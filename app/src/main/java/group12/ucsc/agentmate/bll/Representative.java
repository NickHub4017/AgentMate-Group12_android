package group12.ucsc.agentmate.bll;

/**
 * Created by NRV on 8/23/2014.
 */


        import java.io.Serializable;

        import android.os.Parcel;
        import android.os.Parcelable;

public class Representative implements Serializable{



    public String Emp_id;
    public String UserName;
    public String enc_password;
    public String Question;
    public String Answer;

    public Representative(String empid_create,String username_create,String enc_pwd_create,String question_create,String Answer_create){
        //Naming convention "_create" stand for parameters.
        Emp_id=empid_create;
        UserName=username_create;
        enc_password=enc_pwd_create;
        Question=question_create;
        Answer=Answer_create;

    }
}

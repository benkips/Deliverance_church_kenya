package com.mabnets.dcuk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.TransactionType;

public class payment extends AppCompatActivity {
    private EditText  pymt;
    private EditText   phon;
    private Button sndd;
    Daraja daraja;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        pymt=(EditText)findViewById(R.id.amnt);
        phon=(EditText)findViewById(R.id.phoneno);
        sndd=(Button)findViewById(R.id.sndbtn);

        pd=new ProgressDialog(payment.this);
        pd.setMessage("processing...Transaction");

        daraja = Daraja.with("bnfcSXLOwiiqUcGATYLDDbZe9fgySG1M", "TOs57DZodcOtCJYm", new DarajaListener<AccessToken>() {
            @Override
            public void onResult(@NonNull AccessToken accessToken) {

                Log.i(payment.this.getClass().getSimpleName(), accessToken.getAccess_token());
                /*Toast.makeText(payment.this, "TOKEN : " + accessToken.getAccess_token(), Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onError(String error) {
                Log.e(payment.this.getClass().getSimpleName(), error);
            }
        });

        sndd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=phon.getText().toString().trim();
               String  amount=pymt.getText().toString().trim();
               pd.show();
                if (phone.equals("")) {
                    phon.setError("Please Provide a Phone Number");
                    return;
                }else if (amount.equals("")){
                    pymt.setError("Please enter amount");
                    return;
                }else if (!isphone(phone) || (phone.length() != 10 || !phone.startsWith("07"))) {
                    phon.setError("phone is invalid");
                    phon.requestFocus();
                    return;
                }
                LNMExpress lnmExpress = new LNMExpress(
                        "174379",
                        "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",  //https://developer.safaricom.co.ke/test_credentials
                        TransactionType.CustomerPayBillOnline, //CustomerBuyGoodsOnline TransactionType.CustomerPayBillOnline  <- Apply any of these two
                        amount,
                        "254708374149",
                        "174379",
                        phone,
                        "http://read.mabnets.com/mpesatestone.php",
                        "001ABC",
                        "Goods Payment"
                );

                daraja.requestMPESAExpress(lnmExpress, new DarajaListener<LNMResult>() {
                    @Override
                    public void onResult(@NonNull LNMResult lnmResult) {
                        pd.dismiss();
                        Log.i(payment.this.getClass().getSimpleName(), lnmResult.ResponseDescription);
                    }

                    @Override
                    public void onError(String error) {
                        Log.i(payment.this.getClass().getSimpleName(), error);
                    }
                });
            }
        });

    }
    public static boolean isphone(CharSequence target){
        return !TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches();
    }
}

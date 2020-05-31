package com.example.bookit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pedromassango.doubleclick.DoubleClick;
import com.pedromassango.doubleclick.DoubleClickListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private LinearLayout signIn_Content;
    private EditText email_edt;
    private EditText password_edt;
    private ProgressDialog loadingBar;

    private SharedPreferences sp;
    private SharedPreferences.Editor Ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp=getSharedPreferences("userLogin", MODE_PRIVATE);
        Ed=sp.edit();
        email_edt=findViewById(R.id.email_signIn);
        password_edt=findViewById(R.id.pass_signIn);
        FloatingActionButton signIn = findViewById(R.id.sign_in_btn);
        TextView signUp = findViewById(R.id.sign_up_btn);
        TextView frg_btn = findViewById(R.id.forgotPass_btn);
        signIn_Content=findViewById(R.id.sign_in_content);
        loadingBar=new ProgressDialog(this);



        SharedPreferences sp1=this.getSharedPreferences("userLogin", MODE_PRIVATE);

        String unm=sp1.getString("Unm", null);
        String pass = sp1.getString("Psw", null);


        // doubleClick is
        //"A android library lo handle double click on android Views components. You just need to call it on your view
        // in  https://github.com/pedromassango/doubleClick imp "
        signUp.setOnClickListener(this);
        frg_btn.setOnClickListener(this);

        signIn.setOnClickListener( new DoubleClick(new DoubleClickListener() {
            @Override
            public void onSingleClick(View view) {
                signIn_Content.setVisibility(View.VISIBLE);
//                Toast.makeText(getApplicationContext(),"Double Click to SignIn ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDoubleClick(View view) {
                String email_Str = email_edt.getText().toString();
                String password_Str = password_edt.getText().toString();
                if (!email_Str.isEmpty()&&!password_Str.isEmpty()){
                    signIn_Content.setVisibility(View.INVISIBLE);

                }
                else {
                    email_edt.setError("Email is required");
                    password_edt.setError("password is required");

                }

            }
        })  );



    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_up_btn:
                startActivity( new Intent(this, SignUpActivity.class));
                break;


            case R.id.forgotPass_btn:

                break;
        }
    }
}

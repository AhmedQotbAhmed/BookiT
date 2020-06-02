package com.example.bookit.UI.Activty;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText Fname, Lname, email, password, rePassword, mobile_num;
    private FloatingActionButton SignUp;

    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        loadingBar = new ProgressDialog(this);

        Fname = findViewById(R.id.fname);
        Lname = findViewById(R.id.lname);
        email = findViewById(R.id.email_signUp);
        password = findViewById(R.id.pass_signUp);
        rePassword = findViewById(R.id.repass_signUp);
        mobile_num = findViewById(R.id.mobile_signUp);
        SignUp = findViewById(R.id.sign_up_btn_act);
        SignUp.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up_btn_act:
                CreateAccount();

                break;

        }
    }

    //first step to check the requirement to Create Account
    private void CreateAccount() {
        String Fname_Str = Fname.getText().toString();
        String Lname_Str = Lname.getText().toString();
        String email_Str = email.getText().toString();
        String password_Str = password.getText().toString();
        String rePassword_Str = rePassword.getText().toString();
        String mobile_Str = mobile_num.getText().toString();

        if (password_Str.length() < 8) {
            password.setError(" Minimum length of Password is should be 8 ");
            password.requestFocus();

        } else if (email_Str.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();

        } else if (password_Str.isEmpty() || rePassword_Str.isEmpty()) {
            password.setError("Password is required");
            rePassword.setError("Password is required");
            password.requestFocus();

        } else if (!password_Str.equals(rePassword_Str)) {
            rePassword.setError("Password doesn't match");
            password.setError("Password doesn't match");
            rePassword.requestFocus();

        } else if (Lname_Str.isEmpty()) {
            Lname.setError("Lname is required");
            Lname.requestFocus();

        } else if (Fname_Str.isEmpty()) {
            Fname.setError("Fname is required");
            Fname.requestFocus();

        } else if (mobile_Str.isEmpty()) {
            mobile_num.setError("mobile is required");
            mobile_num.requestFocus();

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email_Str).matches()) {
            email.setError(" please Enter a valid email");
            email.requestFocus();

        } else {

            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
//            validationEmail(Fname_Str,Lname_Str,email_Str,password_Str,mobile_Str);
        }


    }
}

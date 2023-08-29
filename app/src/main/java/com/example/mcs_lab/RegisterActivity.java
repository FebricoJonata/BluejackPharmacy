package com.example.mcs_lab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mcs_lab.database.Database;
import com.example.mcs_lab.model.User;

import java.io.Serializable;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText name, email, password, confirmpassword, phone;
    Button login, register;
    Database db;

    void init(){
        name = findViewById(R.id.txtName);
        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        confirmpassword = findViewById(R.id.txtConfirmPassword);
        phone = findViewById(R.id.txtPhone);
        login = findViewById(R.id.loginBtn);
        register = findViewById(R.id.registerBtn);
        db = Database.getInstance(this);
    }

    void setEvent(){
        login.setOnClickListener(e->{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
        register.setOnClickListener(e->{
            String name = this.name.getText().toString();
            String email = this.email.getText().toString();
            String password = this.password.getText().toString();
            String confirmpassword = this.confirmpassword.getText().toString();
            String phone = this.phone.getText().toString();
            String errormsg = "";
            if(name.length()==0 || email.length()==0 || password.length()==0 || confirmpassword.length()==0 || phone.length()==0){
                Toast.makeText(this, "All Fields Must Be Filled", Toast.LENGTH_SHORT).show();
                errormsg = "All Fields Must Be Filled";
            }
            else if(name.length()<5){
                Toast.makeText(this, "Name Length Must At Least Five Characters", Toast.LENGTH_SHORT).show();
                errormsg = "Name Length Must At Least Five Characters";
            }
            else if(!email.endsWith(".com")){
                Toast.makeText(this, "Email Must End With .com", Toast.LENGTH_SHORT).show();
                errormsg = "Email Must End With .com";
            }
            else if(!Pattern.matches("^[a-zA-Z0-9]+$", password)){
                Toast.makeText(this, "Password Must Be Alphanumeric", Toast.LENGTH_SHORT).show();
                errormsg = "Password Must Be Alphanumeric";
            }
            else if(!password.equals(confirmpassword)){
                Toast.makeText(this, "Password and Confirm Password Doesn't Match", Toast.LENGTH_SHORT).show();
                errormsg = "Password and Confirm Password Doesn't Match";
            }
            else{
                db.addUser(name, email, password, phone, "false");
                User user = db.authUser(email, password);
                Intent intent = new Intent(this, VerificationActivity.class);
                intent.putExtra("userid", user.getUserId().toString());
                intent.putExtra("name", user.getName());
                intent.putExtra("email", user.getEmail());
                intent.putExtra("password", user.getPassword());
                intent.putExtra("phone", user.getPhone());
                intent.putExtra("isVerified", user.getIsVerified());

                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        init();
        setEvent();
    }
}
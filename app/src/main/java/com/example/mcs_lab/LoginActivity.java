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
import com.example.mcs_lab.helper.SessionManager;
import com.example.mcs_lab.model.User;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button login, register;
    Database db;

    void init(){
        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        login = findViewById(R.id.loginBtn);
        register = findViewById(R.id.registerBtn);
        db = Database.getInstance(this);
    }

    void setEvent(){
        register.setOnClickListener(e->{
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
        login.setOnClickListener(e->{
            String email = this.email.getText().toString();
            String password = this.password.getText().toString();
            if(email.length()==0 || password.length()==0){
                Toast.makeText(this, "All Fields Must Be Filled", Toast.LENGTH_SHORT).show();
            }
            else {
                User user = db.authUser(email, password);
                if (user != null && user.getIsVerified().equals("true")) {
                    SessionManager sm = SessionManager.getInstance(this);
                    sm.saveCredential(user);
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(user==null){
                    Toast.makeText(this, "Wrong Credential", Toast.LENGTH_SHORT).show();
                }
                else if (user.getIsVerified().equals("false")) {
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
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        init();
        setEvent();
    }
}
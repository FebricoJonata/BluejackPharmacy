package com.example.mcs_lab;

import android.Manifest;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mcs_lab.database.Database;
import com.example.mcs_lab.model.User;

import java.util.Random;

public class VerificationActivity extends AppCompatActivity implements View.OnKeyListener {

    EditText num1, num2, num3, num4, num5, num6;
    Button submit;
    TextView phone;
    SmsManager smsManager;
    Random random = new Random();
    String otp;
    Database db;
    Intent intent;

    String generateOTP(){
        Integer a = random.nextInt(10);
        Integer b = random.nextInt(10);
        Integer c =random.nextInt(10);
        Integer d = random.nextInt(10);
        Integer e = random.nextInt(10);
        Integer f = random.nextInt(10);
        otp = a.toString() + b.toString() + c.toString() + d.toString() + e.toString() + f.toString();
        System.out.println("otp = " + otp);
        return otp;
    }

    void init(){
        submit = findViewById(R.id.submit);
        phone = findViewById(R.id.phone);
        intent = getIntent();
        phone.setText(intent.getStringExtra("phone"));
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        num6 = findViewById(R.id.num6);
        db = Database.getInstance(this);
        smsManager = SmsManager.getDefault();
    }
    void setEvent(){
        submit.setOnClickListener(e->{
            String otp = num1.getText().toString() + num2.getText().toString() + num3.getText().toString() +
                    num4.getText().toString() + num5.getText().toString() + num6.getText().toString();
            System.out.println(otp);
            System.out.println(this.otp);
            if(otp.compareTo(this.otp.toString())!=0 || otp.length()<6){
                Toast.makeText(this, "Wrong OTP Number", Toast.LENGTH_SHORT).show();
            }
            else{
                db.verifiedUser(Integer.parseInt(intent.getStringExtra("userid")));
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });
        num1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(num1.getText().toString().length()==1){
                    num2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        num2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(num2.getText().toString().length()==1){
                    num3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        num3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(num3.getText().toString().length()==1){
                    num4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        num4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(num4.getText().toString().length()==1){
                    num5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        num5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(num5.getText().toString().length()==1){
                    num6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        num1.setOnKeyListener(this);
        num2.setOnKeyListener(this);
        num3.setOnKeyListener(this);
        num4.setOnKeyListener(this);
        num5.setOnKeyListener(this);
        num6.setOnKeyListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_verification);
        init();
        setEvent();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                    1);
            generateOTP();
//            String number = intent.getStringExtra("phone").replace("0", "+62");
//            smsManager.sendTextMessage(number, null, otp, null, null);
        }
        else{
            generateOTP();
            String number = intent.getStringExtra("phone").replace("0", "+62");
                smsManager.sendTextMessage(number, null, otp, null, null);
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(i== 67){
            if(num6.getText().toString().length()==0){
                num5.requestFocus();
            }
            if(num5.getText().toString().length()==0){
                num4.requestFocus();
            }
            if(num4.getText().toString().length()==0){
                num3.requestFocus();
            }
            if(num3.getText().toString().length()==0){
                num2.requestFocus();
            }
            if(num2.getText().toString().length()==0){
                num1.requestFocus();
            }
        }
        return false;
    }
}
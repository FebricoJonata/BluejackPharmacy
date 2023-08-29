package com.example.mcs_lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mcs_lab.database.Database;
import com.example.mcs_lab.helper.SessionManager;
import com.example.mcs_lab.model.User;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;

public class DetailActivity extends AppCompatActivity{

    ImageView imgv;
    TextView name, manu, desc, price;

    EditText quantity;
    Button btn;
    Intent intent;
    Database db;

    void init(){
        imgv = findViewById(R.id.imgv);
        name = findViewById(R.id.name);
        manu = findViewById(R.id.manu);
        desc = findViewById(R.id.desc);
        price = findViewById(R.id.price);
        quantity = findViewById(R.id.quantity);
        btn = findViewById(R.id.btn);
        intent = getIntent();
        Picasso.get().load(intent.getStringExtra("image")).into(imgv);
        name.setText(intent.getStringExtra("name"));
        manu.setText(intent.getStringExtra("manu"));
        desc.setText(intent.getStringExtra("desc"));
        price.setText(intent.getStringExtra("price"));
        db = Database.getInstance(this);

    }

    void setEvent(){
        btn.setOnClickListener(e->{
            String quantity = this.quantity.getText().toString();

            if(quantity.length()==0){
                Toast.makeText(this, "Quantity must be filled", Toast.LENGTH_SHORT).show();
            }
            else if(Integer.parseInt(quantity) < 1){
                Toast.makeText(this, "Quantity must be at least 1", Toast.LENGTH_SHORT).show();
            }
            else{
                SessionManager sm = SessionManager.getInstance(this);
                User user = sm.getUser();
                int userid = user.getUserId();
                LocalDate currentDate = LocalDate.now();
                db.addTransaction(Integer.parseInt(intent.getStringExtra("medicineid")), userid, currentDate.toString(), Integer.parseInt(quantity));
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        setEvent();
    }
}
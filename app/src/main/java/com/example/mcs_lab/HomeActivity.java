package com.example.mcs_lab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.mcs_lab.fragment.AboutUsFragment;
import com.example.mcs_lab.fragment.MedicineListFragment;
import com.example.mcs_lab.fragment.TransactionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    MedicineListFragment medicineListFragment = new MedicineListFragment();
    TransactionFragment transactionFragment = new TransactionFragment();
    AboutUsFragment aboutUsFragment = new AboutUsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomnav);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, medicineListFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, medicineListFragment).commit();
                        return true;
                    case R.id.transaction:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, transactionFragment).commit();
                        return true;
                    case R.id.aboutus:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, aboutUsFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}
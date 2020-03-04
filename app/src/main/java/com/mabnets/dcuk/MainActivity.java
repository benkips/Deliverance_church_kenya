package com.mabnets.dcuk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
private BottomNavigationView bottomNavigationView;
private FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation);

        Fragment fragmentmain=new index();
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fragmentmain).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Fragment fragmenthome=new index();
                        fm.beginTransaction().replace(R.id.framelayout,fragmenthome).addToBackStack(null).commit();
                        break;
                    case R.id.action_project:
                        Fragment fragmentproject=new project();
                        fm.beginTransaction().replace(R.id.framelayout,fragmentproject).addToBackStack(null).commit();
                        break;
                    case R.id.action_person:
                        Fragment fragmentaccount=new myaccount();
                        fm.beginTransaction().replace(R.id.framelayout,fragmentaccount).addToBackStack(null).commit();
                        break;
                }
                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {


        if (fm.getBackStackEntryCount() !=1){
            fm.beginTransaction().addToBackStack(null);
        }else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
        super.onBackPressed();
    }
}

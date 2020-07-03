package com.app.medisrout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.app.medisrout.databinding.ActivityMainBinding;
import com.app.medisrout.model.SharedData;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setUpView();
        loadUserName();
    }

    private void setUpView() {
        setSupportActionBar(activityMainBinding.appBar.toolbar);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_settings)
                .setOpenableLayout(activityMainBinding.drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(activityMainBinding.navView, navController);
        activityMainBinding.navView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_home);
                    break;
                case R.id.nav_settings:
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_settings);
                    break;
                case R.id.nav_share:
                    shareApp();
                    break;
            }
            return true;
        });
    }

    private void shareApp() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ahmedMansor@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Savics Android test");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Savics Android test \n I did good,like it!");
        emailIntent.setType("message/rfc822");
        try {
            startActivity(Intent.createChooser(emailIntent, "Share..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadUserName() {
        SharedData sharedData = new SharedData(this);
        String userName = sharedData.getValue(SharedData.ReturnValue.STRING, "UserName");
        if (!TextUtils.isEmpty(userName))
            Toast.makeText(this, "Hi," + userName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_settings);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                SharedData sharedData = new SharedData(MainActivity.this);
                sharedData.clear();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
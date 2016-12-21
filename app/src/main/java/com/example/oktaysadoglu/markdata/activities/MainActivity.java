package com.example.oktaysadoglu.markdata.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.oktaysadoglu.markdata.R;
import com.example.oktaysadoglu.markdata.controller.ClickableController;
import com.example.oktaysadoglu.markdata.fragments.SettingsFragment;
import com.example.oktaysadoglu.markdata.models.StackBundle;
import com.example.oktaysadoglu.markdata.fragments.StackFragment;
import com.example.oktaysadoglu.markdata.fragments.TextFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<StackBundle> stackWordsesBundles = new ArrayList<>();

    private ClickableController clickableController = new ClickableController(this);

    public static String article;

    public static int news_text_size = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adjustNavBar();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_article) {

            fragmentManager.beginTransaction().replace(R.id.content_frame,new TextFragment()).commit();

        } else if (id == R.id.nav_stack) {

            fragmentManager.beginTransaction().replace(R.id.content_frame,new StackFragment()).commit();

        } else if (id == R.id.nav_settings) {

            fragmentManager.beginTransaction().replace(R.id.content_frame,new SettingsFragment()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void adjustNavBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    public List<StackBundle> getStackWordsesBundles() {
        return stackWordsesBundles;
    }

    public ClickableController getClickableController() {
        return clickableController;
    }

    public static String getArticle() {
        return article;
    }

    public static void setArticle(String article) {
        MainActivity.article = article;
    }

    public void setStackWordsesBundles(List<StackBundle> stackWordsesBundles) {
        this.stackWordsesBundles = stackWordsesBundles;
    }

    public void setClickableController(ClickableController clickableController) {
        this.clickableController = clickableController;
    }

    public static boolean isConnected(Context context){
        NetworkInfo localNetworkInfo = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (localNetworkInfo == null) {
            return false;
        }
        else{
            //Check if only wifi is selected or wifi==1  Constant Value: 1 (0x00000001)
            if(((localNetworkInfo.getType() == 1)) || (localNetworkInfo.isConnected()) || (localNetworkInfo.isAvailable()))
                return true;
        }
        return false;
    }

}

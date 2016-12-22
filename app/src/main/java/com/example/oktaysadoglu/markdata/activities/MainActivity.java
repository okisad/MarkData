package com.example.oktaysadoglu.markdata.activities;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.oktaysadoglu.markdata.R;
import com.example.oktaysadoglu.markdata.controller.ClickableController;
import com.example.oktaysadoglu.markdata.fragments.SettingsFragment;
import com.example.oktaysadoglu.markdata.models.StackBundle;
import com.example.oktaysadoglu.markdata.fragments.StackFragment;
import com.example.oktaysadoglu.markdata.fragments.TextFragment;
import com.example.oktaysadoglu.markdata.preferences.ControlingWordsPreferences;
import com.example.oktaysadoglu.markdata.preferences.ControllingStackBundlePreferences;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<StackBundle> stackWordsesBundles;

    private ClickableController clickableController = new ClickableController(this);

    private MenuItem getNewsMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adjustNavBar();

        if (ControllingStackBundlePreferences.getStackBundles(this) != null){

            stackWordsesBundles = ControllingStackBundlePreferences.getStackBundles(this);

        }else {

            stackWordsesBundles = new ArrayList<StackBundle>();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_main_toolbar,menu);

        for (int i = 0 ; i < menu.size() ; i++){

            setGetNewsMenuItem(menu.getItem(i));

            Drawable drawable = getGetNewsMenuItem().getIcon();

            if (drawable != null){

                drawable.mutate();

                drawable.setColorFilter(ContextCompat.getColor(this,android.R.color.white), PorterDuff.Mode.SRC_ATOP);

            }

        }

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if (fragment == null)
            setFragment(new TextFragment());

        return true;
    }

    private void appearanceGetNewNewsButton(boolean appear){

        if (appear)
            getGetNewsMenuItem().setVisible(true);
        else
            getGetNewsMenuItem().setVisible(false);

    }

    private void setFragment(Fragment fragment){

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).commit();

        if (fragment instanceof TextFragment){
            appearanceGetNewNewsButton(true);
        }else if(fragment instanceof StackFragment){
            appearanceGetNewNewsButton(false);
        }else if (fragment instanceof SettingsFragment){
            appearanceGetNewNewsButton(false);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_get_news) {

            Log.e("my","bastı");

            TextFragment textFragment = (TextFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);

            if (textFragment != null){

                textFragment.getNewNews();

            }

            return true;
        }

        return super.onOptionsItemSelected(item);
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

    @Override
    protected void onStop() {
        super.onStop();

        Log.e("my"," girdi");

        if (getStackWordsesBundles().size() > 0){

            ControllingStackBundlePreferences.setStackBundles(this,getStackWordsesBundles());

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_article) {

            setFragment(new TextFragment());

        } else if (id == R.id.nav_stack) {

            setFragment(new StackFragment());

        } else if (id == R.id.nav_settings) {

            setFragment(new SettingsFragment());

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

    public MenuItem getGetNewsMenuItem() {
        return getNewsMenuItem;
    }

    public void setGetNewsMenuItem(MenuItem getNewsMenuItem) {
        this.getNewsMenuItem = getNewsMenuItem;
    }


}

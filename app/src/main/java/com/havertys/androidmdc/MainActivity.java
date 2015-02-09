package com.havertys.androidmdc;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.FragmentTransaction;
import android.view.Window;

import com.havertys.androidmdc.lp.receiving.LpReceivingActivity;
import com.havertys.androidmdc.signin.HvtMenu;

import com.havertys.androidmdc.signin.SignInFragment;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //requestWindowFeature(Window.FEATURE_ACTION_BAR);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sign In");
        //ColorDrawable colorDrawable = (ColorDrawable)getResources().getDrawable(R.color.signinactionbar);
        //actionBar.setBackgroundDrawable(colorDrawable);


        //setWallpaper();
        if (savedInstanceState == null) {

            SignInFragment signInFragment = new SignInFragment();
            getFragmentManager().beginTransaction().add(R.id.container, signInFragment).commit();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

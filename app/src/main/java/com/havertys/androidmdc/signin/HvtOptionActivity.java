package com.havertys.androidmdc.signin;

import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.havertys.androidmdc.R;

import java.util.ArrayList;

public class HvtOptionActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hvt_option);

        Bundle temp =  getIntent().getExtras();
        ArrayList<HvtMenu> tempObject = (ArrayList<HvtMenu>) temp.get("options");

        if (savedInstanceState == null) {
            HvtOptionFragment hvtOptionFragment = new HvtOptionFragment();
            Bundle args = new Bundle();
            args.putSerializable("options", tempObject);
            hvtOptionFragment.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.containerOption, hvtOptionFragment);
            transaction.commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_option, menu);
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

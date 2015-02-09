package com.havertys.androidmdc.signin;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.havertys.androidmdc.R;
import com.havertys.androidmdc.lp.LpInquiryActivity;
import com.havertys.androidmdc.lp.receiving.LpReceivingActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HvtOptionFragment extends ListFragment {


    public HvtOptionFragment() {
        // Required empty public constructor
    }


    ArrayAdapter<String> adapter;
    ArrayList<HvtOption> hvtOptionList;

    public void onViewCreated(View view, Bundle bundleState) {


        Bundle bundle = getArguments();

        hvtOptionList = (ArrayList<HvtOption>) bundle.getSerializable("options");

        ArrayList<String> options = new ArrayList<String>();

        for (int i = 0; i < hvtOptionList.size(); i++) {
            options.add(hvtOptionList.get(i).getName());
        }

        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, options);
        setListAdapter(adapter);
    }

    public void onActivityCreated(Bundle bundleState) {

        super.onActivityCreated(bundleState);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                View tempView = arg1;
                CharSequence viewText = ((TextView)arg1).getText();

                if (viewText.toString().equalsIgnoreCase("Receiving")) {

                    Intent intent = new Intent(HvtOptionFragment.this.getActivity(), LpReceivingActivity.class);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(HvtOptionFragment.this.getActivity(), LpInquiryActivity.class);
                    startActivity(intent);
                }
            }
        });


    }
}

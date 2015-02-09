package com.havertys.androidmdc.signin;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.havertys.androidmdc.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HvtMenuFragment extends ListFragment {


    public HvtMenuFragment() {
        // Required empty public constructor
    }

    ArrayAdapter<String> adapter;
    ArrayList<HvtMenu> hvtMenuList;

    public void onViewCreated(View view, Bundle bundleState) {

        Bundle bundle = getArguments();

        hvtMenuList = (ArrayList<HvtMenu>) bundle.getSerializable("menus");

        ArrayList<String> menus = new ArrayList<String>();

        for (int i = 0; i < hvtMenuList.size(); i++) {
            menus.add(hvtMenuList.get(i).getName());
        }

        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, menus);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hvt_menu, container, false);
    }

    public void onActivityCreated(Bundle bundleState) {

        super.onActivityCreated(bundleState);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                View tempView = arg1;
                CharSequence viewText = ((TextView)arg1).getText();
                HvtMenu hvtMenu = null;

                for (int i =0; i < hvtMenuList.size(); i ++) {

                    hvtMenu = hvtMenuList.get(i);

                    if (viewText.toString().equalsIgnoreCase(hvtMenu.getName())) {
                        break;
                    }
                }

                showOption(hvtMenu.getHvtOptionList());
            }
        });


    }

    public void showOption(ArrayList<HvtOption> optionList) {

        Intent intent = new Intent(HvtMenuFragment.this.getActivity(), HvtOptionActivity.class);
        intent.putExtra("options", optionList);
        startActivity(intent);
    }
}
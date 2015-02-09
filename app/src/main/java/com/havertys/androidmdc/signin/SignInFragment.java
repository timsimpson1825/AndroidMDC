package com.havertys.androidmdc.signin;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.havertys.androidmdc.R;
import com.havertys.androidmdc.common.Utility;

public class SignInFragment extends Fragment {

    public SignInFragment() {

    }

    Button buttonGo;
    EditText userName;
    EditText password;
    TextView errorMsg;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sign_in, container,false);

        password = (EditText) rootView.findViewById(R.id.editTextPassword);
        userName = (EditText) rootView.findViewById(R.id.editTextUsername);
        errorMsg = (TextView) rootView.findViewById(R.id.signInErrorMsg);
        errorMsg.setVisibility(TextView.INVISIBLE);

        buttonGo = (Button) rootView.findViewById(R.id.signInButton);

        buttonGo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                new SignInTask().execute(userName.getEditableText().toString(), password.getEditableText().toString());

            }
        });
        return rootView;
    }

    private class SignInTask extends AsyncTask<Object, Object, JSONObject> {

        //private final String URL = "http://xd56devlrg01.havertys.com:8080/restws/rest/lpInquiry";
        private final String URL = "http://intdevwebservices.havertys.com/wsrest/rest/signIn";
        private final int CONNECTION_TIMEOUT = 30000;
        private final int DATARETRIEVAL_TIMEOUT = 25000;

        protected void onPreExecute() {

//			imageButtonPicture.setVisibility(ImageButton.INVISIBLE);
//			errorMessageView.setText("");
//			itemView.setText("");
//		    itemDescriptionView.setText("");
//		    progressDialog.setMessage("Please wait");
//		    progressDialog.show();

        }

        @Override
        protected JSONObject doInBackground(Object... arg0) {

            HttpURLConnection urlConnection = null;
            JSONObject jsonObject = null;

            try {

                String username = (String)arg0[0];
                String password = (String)arg0[1];

                System.out.println("User:" + userName + ", Pass:" + password);

                URL urlToRequest = new URL(URL+"?username="+username+"&password="+password);
                urlConnection = (HttpURLConnection) urlToRequest.openConnection();
                urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                urlConnection.setReadTimeout(DATARETRIEVAL_TIMEOUT);

                int statusCode = urlConnection.getResponseCode();
                if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    //TODO: handle unauthorized (if service requires user login)
                } else if (statusCode != HttpURLConnection.HTTP_OK) {
                    //TODO: handle any other errors, like 404, 500,..
                }

                String responseText = Utility.getResponseText(urlConnection.getInputStream());
                System.out.println(responseText);

                jsonObject = new JSONObject(responseText);

            } catch(Exception e) {
                e.printStackTrace();
            }

            return jsonObject;
        }

		@Override
		protected void onPostExecute(JSONObject input) {

            try {

                String returnCode = input.getString("returnCode");

                if ("EDIT".equalsIgnoreCase(returnCode) || "ERROR".equalsIgnoreCase(returnCode) )  {
                    errorMsg.setVisibility(TextView.VISIBLE);
                    errorMsg.setText(input.getString("returnMessage"));
                } else {

                    JSONArray tempList = input.getJSONArray("menuList");
                    ArrayList<HvtMenu> hvtMenuList = new ArrayList<HvtMenu>();
                    for (int i = 0; i < tempList.length(); i++) {
                        JSONObject temp = (JSONObject) tempList.get(i);
                        HvtMenu hvtMenu = new HvtMenu();
                        hvtMenu.setName(temp.getString("menuName"));
                        hvtMenu.setId(temp.getString("menuId"));
                        hvtMenuList.add(hvtMenu);
                        ArrayList<HvtOption> hvtOptionList = new ArrayList<HvtOption>();
                        hvtMenu.setHvtOptionList(hvtOptionList);
                        JSONArray tempArray = temp.getJSONArray("optionList");
                        for (int j = 0; j < tempArray.length(); j++) {
                            JSONObject optionTemp = (JSONObject) tempArray.get(j);
                            HvtOption hvtOption = new HvtOption();
                            hvtOption.setId(optionTemp.getString("optionId"));
                            hvtOption.setName(optionTemp.getString("optionName"));
                            hvtOptionList.add(hvtOption);
                        }
                    }

                    Intent intent = new Intent(SignInFragment.this.getActivity(), HvtMenuActivity.class);
                    intent.putExtra("menus", hvtMenuList);
                    startActivity(intent);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }
    }
}

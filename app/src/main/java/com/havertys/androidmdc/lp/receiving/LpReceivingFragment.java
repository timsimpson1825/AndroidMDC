package com.havertys.androidmdc.lp.receiving;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.havertys.androidmdc.R;
import com.havertys.androidmdc.common.Constants;
import com.havertys.androidmdc.common.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;

public class LpReceivingFragment extends Fragment {

    Button buttonGo;
    EditText lp;
    EditText location;
    TextView locationView;
    TextView lpView;
    TextView errorMsgTextView;
    private String oldTime="";
    private String oldDate="";
    private String lastValidLocation="";
    private int lpScanCount = 0;
    private int totalCubes = 0;

    protected TextWatcher watcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {

        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
            // When No Password Entered
            //textViewPasswordStrengthIndiactor.setText("Not Entered");
        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            CharSequence temp = s;

            if (s.length() == 22) {
                System.out.println("s value:" + s + ", start:" + start + ", before:" + before + ", count:" + count);
                submitPage();
            }
        }

    };

    public void submitPage() {

        String tempLp = "";
        String tempLocation = "";

        if (lp != null && lp.getEditableText() != null && lp.getEditableText().toString().trim().length() > 0) {
            tempLp = lp.getEditableText().toString();
            Long resultLP = Utility.lpInputValidate(tempLp);
            lp.setText("" + tempLp);

            System.out.print(tempLp);
        }

        if (location != null && location.getEditableText() != null) {
            tempLocation = location.getEditableText().toString();
        }


        new lpReceivingTask().execute(tempLp, tempLocation);

    }

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lp_receiving, container,false);

        lp = (EditText) rootView.findViewById(R.id.lpReceivingLpEditText);
        location = (EditText) rootView.findViewById(R.id.lpReceivingLocationEditText);
        lpView = (TextView) rootView.findViewById(R.id.lpReceivingLpTextView);
        locationView = (TextView) rootView.findViewById(R.id.lpReceivingLocationTextView);
        errorMsgTextView = (TextView) rootView.findViewById(R.id.lpReceivingErrorMsg);

        errorMsgTextView.setVisibility(TextView.INVISIBLE);
        lp.setVisibility(EditText.INVISIBLE);
        lpView.setVisibility(TextView.INVISIBLE);
        //lp.addTextChangedListener(watcher);

        buttonGo = (Button) rootView.findViewById(R.id.lpReceivingGo);

        buttonGo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                submitPage();
            }
        });
        return rootView;
    }

    private class lpReceivingTask extends AsyncTask<Object, Object, JSONObject> {

        //private final String URL = "http://xd56devlrg01.havertys.com:8080/restws/rest/lpInquiry";
        private final String URL = "http://intdevwebservices.havertys.com/wsrest/rest/lpReceiving";
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
        protected JSONObject doInBackground(Object... args) {

            HttpURLConnection urlConnection = null;
            try {

                String lpScan = (String)args[0];
                String locationScan = (String)args[1];
                String source = null;

                if (lpScan != null && lpScan.trim().length() > 0) {
                    source = "LP";
                } else {
                    source = "LS";
                }

                if (lpScan.length() > 12) {
                    long tempLong = Utility.lpInputValidate(lpScan);
                    lpScan = "" + tempLong;
                }
                String parameters = "?sourceScreen="+source+
                        "&div=4&dc=10"+"&locationScanField="+locationScan+"&lpScanField="+lpScan+"&empInitials=tms&empNumber=55&empLastName=simpson&empFirstName=tim" +
                        "&terminal=N01&secondarySecurityFlag=N&lastValidLocation="+lastValidLocation+"&oldDate="+oldDate+"&oldTime="+oldTime+"&lpScanCount="+lpScanCount+"&totalCubes="+totalCubes;

                String fullURL = URL + parameters;

                java.net.URL urlToRequest = new java.net.URL(fullURL);
                urlConnection = (HttpURLConnection) urlToRequest.openConnection();
                urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                urlConnection.setReadTimeout(DATARETRIEVAL_TIMEOUT);

                int statusCode = urlConnection.getResponseCode();
                if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    //TODO: handle unauthorized (if service requires user login)
                } else if (statusCode != HttpURLConnection.HTTP_OK) {
                    //TODO: handle any other errors, like 404, 500,..
                }

                String responseText = getResponseText(urlConnection.getInputStream());
                System.out.println(responseText);
                JSONObject jsonObject = new JSONObject(responseText);


                return jsonObject;

            } catch(MalformedURLException mue) {
                mue.printStackTrace();
            } catch(IOException ioe) {
                ioe.printStackTrace();
            } catch(JSONException jsone) {
                jsone.printStackTrace();
            }

            return null;
        }

		@Override
		protected void onPostExecute(JSONObject jsonObject) {

            try {

                String returnCode = jsonObject.getString("returnCode");
                String errorMsg = (String) jsonObject.get("returnMessage");

                if (Constants.RETURN_CODE_WARNING.equalsIgnoreCase(returnCode) || Constants.RETURN_CODE_ERROR.equalsIgnoreCase(returnCode) )  {
                    errorMsgTextView.setVisibility(TextView.VISIBLE);
                    errorMsgTextView.setText(errorMsg.trim());
                } else {
                    errorMsgTextView.setVisibility(TextView.INVISIBLE);
                    errorMsgTextView.setText("");
                }

                Integer nextRequestedAction = (Integer) jsonObject.get("nextRequestedAction");

                if (0 == nextRequestedAction) {
                    // don't do anything
                    lp.setVisibility(EditText.VISIBLE);
                    lpView.setVisibility(TextView.VISIBLE);
                    lp.setText("");
                    oldDate = jsonObject.getString("oldDate");
                    oldTime = jsonObject.getString("oldTime");
                    lastValidLocation = jsonObject.getString("lastValidLocation");
                    totalCubes = totalCubes+ jsonObject.getInt("totalCubes");
                    lpScanCount = lpScanCount + jsonObject.getInt("lpScanCount");

                } if (10 == nextRequestedAction) {

                    location.setVisibility(EditText.VISIBLE);
                    locationView.setVisibility(TextView.VISIBLE);
                    location.setText("");

                    lp.setVisibility(EditText.INVISIBLE);
                    lpView.setVisibility(TextView.INVISIBLE);
                    lp.setText("");

                } else if (20 == nextRequestedAction) {
                    lp.setVisibility(EditText.VISIBLE);
                    lpView.setVisibility(TextView.VISIBLE);
                    lp.setText("");
                    location.setVisibility(EditText.INVISIBLE);
                    locationView.setVisibility(TextView.INVISIBLE);

                    oldDate = jsonObject.getString("oldDate");
                    oldTime = jsonObject.getString("oldTime");
                    lastValidLocation = jsonObject.getString("lastValidLocation");
                    totalCubes = totalCubes+ jsonObject.getInt("totalCubes");
                    lpScanCount = lpScanCount + jsonObject.getInt("lpScanCount");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private String getResponseText(InputStream in) {

            Scanner scanner = null;
            try {
                scanner = new Scanner(in);
                return scanner.useDelimiter("\\A").next();
            } finally {
                if(scanner != null)
                    scanner.close();
            }
        }
    }
}

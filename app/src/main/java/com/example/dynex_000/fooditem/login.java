package com.example.dynex_000.fooditem;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import customfonts.MyEditText;
import customfonts.MyTextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class login extends AppCompatActivity {

    TextView create;
    MyTextView signin;
    MyEditText email,password;
    JSONArray jArray;
    String user,pass;
    String pName,pImage,pArea,pVtype,pSrtype,pUname,pMobile,pWmobile,pMail_id,pDistrict,pState,pCountry,status;
    Typeface fonts1;
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        create = (TextView)findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(login.this, Registration.class);
                startActivity(it);

            }
        });




        fonts1 =  Typeface.createFromAsset(login.this.getAssets(),
                "fonts/Lato-Regular.ttf");


        status = NetCheck.getConnectivityStatusString(getBaseContext());

        TextView t4 =(TextView)findViewById(R.id.create);
        t4.setTypeface(fonts1);
//        web3 = (WebView) findViewById(R.id.fullscreen_content);
//        WebSettings webSettings = web3.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        web3.setWebViewClient(new WebViewClient());
//       // web3.loadUrl ("http://www.dynexoit.com/SlideShow/index.html");


//        if(status.compareTo("Not connected to Internet")==0) {
//
//            web3.loadUrl ("file:///android_asset/index.html");
//
//        }
//        else
//        {
//            web3.loadUrl("http://www.dynexoit.com/SlideShow/index.html");//main web view
//
//        }

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(login.this,MainActivity.class);
                startActivity(i);
            }
        });

        fab.setVisibility(View.GONE);

    }
//
    public void onClick(View v) {

        String status = NetCheck.getConnectivityStatusString(getBaseContext());
                    if(status.compareTo("Not connected to Internet")==0)
                    {

                        Toast.makeText(getApplicationContext(), "Your Internet connection is unstable, Please try again later ", Toast.LENGTH_LONG).show();


                    }
                    else {
                        signin = (MyTextView) findViewById(R.id.signin1);
                        email = (MyEditText) findViewById(R.id.email);
                        password = (MyEditText) findViewById(R.id.password);

                        user = email.getText().toString();
                        pass = password.getText().toString();

                        if(user.compareTo("")==0)
                        {
                            Toast.makeText(getApplicationContext(), "Please Enter your email ", Toast.LENGTH_LONG).show();
                        }
                        else if(pass.compareTo("")==0)
                        {
                            Toast.makeText(getApplicationContext(), "Please Enter your Password ", Toast.LENGTH_LONG).show();
                        }
                        else {

                            new LoginAsyncTask().execute();
                        }

                    }

    }

    public void onBackPressed() {
        super.onBackPressed();
//                Intent i = new Intent(login.this, MainActivity.class);
//                startActivity(i);
//                finish();
        }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }



   //........................LOGIN PROCESS.........ASYNC TASK...............................................................


    private class LoginAsyncTask extends AsyncTask<Void, Void, Void> {
        String responsefromserver = null;
        String responsefromserver2 = null;
        private ProgressDialog mProgressDialog;
        HashMap<String, String> nameValuePairs;
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(login.this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        protected Void doInBackground(Void... params) {
            try {
                try {
                    //System.out.println(userName+"   "+passWord);
                    nameValuePairs = new HashMap<String, String>();


                    nameValuePairs.put("username",user);
                    nameValuePairs.put("password",pass);
                    //nameValuePairs.put("phone",phn);

                    SendRequestServer req = new SendRequestServer();
                    String url1 = "android_login_foodcorner.php";
                    responsefromserver = req.requestSender(url1, nameValuePairs,login.this);



                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void unused) {
            mProgressDialog.dismiss();
            Log.e("Response", responsefromserver);
            if (responsefromserver == null) {

                Toast.makeText(login.this, "Network Error", Toast.LENGTH_SHORT).show();
                // logginFromMobile();
            } else if (responsefromserver.contains("<html>")) {
                //Log.e("Response", responsefromserver);

                Toast.makeText(login.this, "<html>", Toast.LENGTH_SHORT).show();
                //  logginFromMobile();
            } else if (responsefromserver.equals("success")) {


                new LoadData().execute();


             //   Toast.makeText(login.this, "Login Success", Toast.LENGTH_LONG).show();
               // new LoadData().execute();

            }

            else if (responsefromserver.contains("exist")) {
                Toast.makeText(login.this, "Invalid User", Toast.LENGTH_SHORT).show();


            }

        }}




//.............................LOAD PROFILE DATA TO LOCAL SERVER..................................................................

    private class LoadData extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        String responsefromserver = null;
        HashMap<String, String> nameValuePairs;
        @Override
        // can use UI thread here
        protected void onPreExecute() {

            this.progressDialog = ProgressDialog.show(
                    login.this, "", " Loading .....");

        }

        @Override
        protected void onPostExecute(Void unused) {
            this.progressDialog.dismiss();


            if (responsefromserver == null) {
                //serverDown();
            }
            else if (responsefromserver.contains("<html>")) {
                Log.e("ResponseLocation", responsefromserver);
                // serverDown();
            }
            else if (responsefromserver.equals("no_data")) {
                //	Toast.makeText(getApplicationContext(), "There is No Request Available....", Toast.LENGTH_LONG).show();

            }
            else{

                DynexoPrefManager D = new DynexoPrefManager();
               // D.saveImage(pImage,login.this);
                D.saveName(pName,login.this);
               // D.saveArea(pArea,login.this);
              //  D.saveVehicleType(pVtype,login.this);
              //  D.saveServiceTypes(pSrtype,login.this);
               // D.saveUserName(pUname,login.this);
                D.saveUserMobile(pMobile,login.this);
                D.saveMailId(pMail_id,login.this);
                D.saveDistrict(pDistrict,login.this);
                D.saveState(pState,login.this);
               // D.saveUserPassword(pass,login.this);

                Intent k = new Intent(login.this, MainActivity.class);
                startActivity(k);
                finish();

            }


        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // HTTP post

            try {
                try {


                    nameValuePairs = new HashMap<String, String>();
                    nameValuePairs.put("username",user);
                    nameValuePairs.put("password",pass);

                    SendRequestServer req = new SendRequestServer();
                    String url1 = "android_user_data.php";
                    responsefromserver = req.requestSender(url1, nameValuePairs,login.this);

//                    ArrayList<NameValuePair>namevaluePairs=new ArrayList<NameValuePair>();
//
//                    com.dynexo.SendRequestServer req = new com.dynexo.SendRequestServer();
//                    String url1 = "get_donor.php";
//                    responsefromserver = req.requestSender(url1, namevaluePairs);


                    try {
                        jArray = new JSONArray(responsefromserver);
                        JSONObject json_data = null;
                        for (int i = 0; i < jArray.length(); i++) {
                            json_data = jArray.getJSONObject(i);

                            pName = json_data.getString("contact_person");
                           // pImage=json_data.getString("photo");

                           // pArea=json_data.getString("area");
                            pMobile=json_data.getString("phone");
                           // pVtype=json_data.getString("vctype");
                           // pSrtype=json_data.getString("srtype");
                            pUname=json_data.getString("email");
                          //  pMobile = json_data.getString("mobile");
                            pMail_id = json_data.getString("email");
                           pDistrict = json_data.getString("password");
                            pState = json_data.getString("outlet_name");
                           // pCountry = json_data.getString("country");
                            // pWmobile= json_data.getString("w_mobile");




                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.toString(),
                                Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }





}

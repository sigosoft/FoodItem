package com.example.dynex_000.fooditem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import customfonts.MyEditText;
import customfonts.MyTextView;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Registration extends AppCompatActivity {




            MyTextView signup;

    EditText mobile;
   MyEditText name,outlet,address,email,password,cnfpassword;

    String nameS,outlet_name,mobileS,emailS,passwordS,cnfpasswordS,Saddress,emailPattern,status;



    DrawerLayout drawerLayout;
    Toolbar toolbar;
    Typeface fonts1;
    DrawerLayout mDrawerLayout;
    GravityCompat gravityCompat;
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

        setContentView(R.layout.activity_registration);


        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        name = (MyEditText)findViewById(R.id.name);
        outlet = (MyEditText)findViewById(R.id.outlet);
        address =(MyEditText)findViewById(R.id.address);
        mobile = (EditText)findViewById(R.id.mobile);
        email = (MyEditText)findViewById(R.id.email);
        password =(MyEditText)findViewById(R.id.password);
        cnfpassword = (MyEditText)findViewById(R.id.cnfpassword);
        signup = (MyTextView)findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nameS =name.getText().toString();
                outlet_name = outlet.getText().toString();
                mobileS = mobile.getText().toString();
                Saddress = address.getText().toString();
                emailS =email.getText().toString();
                passwordS = password.getText().toString();
                cnfpasswordS = cnfpassword.getText().toString();
                status = NetCheck.getConnectivityStatusString(getBaseContext());

                tst2();

            }
        });


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
                Intent i=new Intent(Registration.this,MainActivity.class);
                startActivity(i);


            }
        });


fab.setVisibility(View.GONE);


        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);



    }







    //...........................................................................................................................

    public void onBackPressed() {
        super.onBackPressed();
//        Intent i = new Intent(Registration.this, MainActivity.class);
//        startActivity(i);
//        finish();
    }

//................................................................................................................................


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




    public void tst2()
    {


        if( emailS.matches(emailPattern)) {


            if (nameS.compareTo("") == 0) {
                Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
            } else if (mobileS.compareTo("") == 0) {
                Toast.makeText(this, "PLease Enter Mobile Number", Toast.LENGTH_SHORT).show();
            }
            else if (outlet_name.compareTo("") == 0) {
                Toast.makeText(this, "PLease Enter Outlet Name", Toast.LENGTH_SHORT).show();
            }
            else if (emailS.compareTo("") == 0) {
                Toast.makeText(this, "PLease Enter email", Toast.LENGTH_SHORT).show();
            } else if (passwordS.compareTo("") == 0) {
                Toast.makeText(this, "PLease Enter Password", Toast.LENGTH_SHORT).show();
            } else if (passwordS.compareTo(cnfpasswordS) != 0) {
                Toast.makeText(this, "Password not matching", Toast.LENGTH_SHORT).show();
            } else if (passwordS.length() < 8) {
                Toast.makeText(Registration.this, "Your password should be minimum 8 character", Toast.LENGTH_SHORT).show();
            }  else if (status.compareTo("Not connected to Internet") == 0) {
                Toast.makeText(Registration.this, "Your Internet connection is unstable, Please try again later", Toast.LENGTH_SHORT).show();
            } else {


                new RegisterData().execute();
            }

        }
        else
        {
            Toast.makeText(this, "PLease Enter valid Mail id", Toast.LENGTH_SHORT).show();
        }





       // Toast.makeText(this,nameS , Toast.LENGTH_SHORT).show();
    }





    //...............................................SAVE DATA TO SERVER..................................................................................................



    private class RegisterData extends AsyncTask<Void, Void, Void> {
        String responsefromserver = null;
        String responsefromserver2 = null;
        private ProgressDialog mProgressDialog;
        HashMap<String, String> nameValuePairs;
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(Registration.this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        protected Void doInBackground(Void... params) {
            try {
                try {

                    nameValuePairs = new HashMap<String, String>();


                    nameValuePairs.put("name",nameS);
                    nameValuePairs.put("outlet_name",outlet_name);
                    nameValuePairs.put("phone",mobileS);
                    nameValuePairs.put("email",emailS);
                    nameValuePairs.put("address",Saddress);
                    nameValuePairs.put("password",passwordS);


                    SendRequestServer req = new SendRequestServer();
                    String url1 = "android_registration_food_corner.php";
                    responsefromserver = req.requestSender(url1, nameValuePairs,Registration.this);



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

                Toast.makeText(Registration.this, "Network Error", Toast.LENGTH_SHORT).show();
                // logginFromMobile();
            } else if (responsefromserver.contains("<html>")) {
                //Log.e("Response", responsefromserver);

                Toast.makeText(Registration.this, "<html>", Toast.LENGTH_SHORT).show();
                //  logginFromMobile();
            } else if (responsefromserver.equals("success")) {


                Toast.makeText(Registration.this, "Successfully Registered...", Toast.LENGTH_LONG).show();


            }

            else if (responsefromserver.contains("existnumber")) {
                Toast.makeText(Registration.this, "Invalid User / Phone number already exists", Toast.LENGTH_SHORT).show();


            }
            else if (responsefromserver.contains("existuser")) {
                Toast.makeText(Registration.this, "Invalid User / User name already exists", Toast.LENGTH_SHORT).show();


            }

        }}


}

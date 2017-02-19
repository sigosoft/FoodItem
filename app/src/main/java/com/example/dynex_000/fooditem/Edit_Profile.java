package com.example.dynex_000.fooditem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import customfonts.MyEditText;
import customfonts.MyTextView;

public class Edit_Profile extends AppCompatActivity {

    JSONArray jArray;
    ImageView savePhoto;
    String pName,pImage,pArea,pVtype,pSrtype,pUname,pMobile,pWmobile,pMail_id,pDistrict,pState,pCountry;
    MyEditText name,state,district,area,brandname,username,email,password,cnfpassword;
    String nameS,stateS,districtS,areaS,brandnameS,mobileS,usernameS,emailS,passwordS,cnfpasswordS,vtypeS,img_str,services,emailPattern;

    MyTextView signup;
    EditText mobile;
    RadioGroup radiotype;
    RadioButton bus, car, atrk,vtype;
    int selectedId;
    CheckBox taxi,driver;
    String userName,userPassword,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile);
        DynexoPrefManager D =new  DynexoPrefManager();


        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        signup = (MyTextView)findViewById(R.id.signup);
        name = (MyEditText)findViewById(R.id.name);
        state = (MyEditText)findViewById(R.id.state);
        district = (MyEditText)findViewById(R.id.district);
        area = (MyEditText)findViewById(R.id.area);
        brandname = (MyEditText)findViewById(R.id.brandname);
        mobile = (EditText)findViewById(R.id.mobile);
        username = (MyEditText)findViewById(R.id.username);
        email = (MyEditText)findViewById(R.id.email);
        password = (MyEditText)findViewById(R.id.password);
        cnfpassword = (MyEditText)findViewById(R.id.cnfpassword);

        taxi=(CheckBox)findViewById(R.id.taxi);
        driver=(CheckBox)findViewById(R.id.driver);

        radiotype = (RadioGroup) findViewById(R.id.radiotype);
        bus = (RadioButton) findViewById(R.id.bus);
        car = (RadioButton) findViewById(R.id.car);
        atrk = (RadioButton) findViewById(R.id.atrk);
        savePhoto=(ImageView)findViewById(R.id.savePhoto);

        status = NetCheck.getConnectivityStatusString(getBaseContext());

        userName = D.getSavedUserName(Edit_Profile.this);
        userPassword = D.getSavedUserPassword(Edit_Profile.this);


        new LoadData().execute();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String status = NetCheck.getConnectivityStatusString(getBaseContext());
                if(status.compareTo("Not connected to Internet")==0)
                {
                    Toast.makeText(Edit_Profile.this, "Your Internet connection is unstable, Please try again later", Toast.LENGTH_SHORT);


                }
                else
                {

                  //  passwordS = password.getText().toString();
                  //  cnfpasswordS = cnfpassword.getText().toString();


                    savePhoto.buildDrawingCache();
                    Bitmap bitmap = savePhoto.getDrawingCache();
                    ByteArrayOutputStream stream=new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                    byte[] image=stream.toByteArray();

                    img_str = Base64.encodeToString(image, 0);


                    nameS = name.getText().toString();
                    stateS = state.getText().toString();
                    districtS = district.getText().toString();
                    areaS = area.getText().toString();
                    brandnameS = brandname.getText().toString();
                    mobileS = mobile.getText().toString();
                  //  usernameS = username.getText().toString();
                    emailS = email.getText().toString();

                    if(taxi.isChecked())
                    {
                        if(driver.isChecked())
                        {
                            services = "Taxi,Driver";
                        }
                        else

                        {
                            services = "Taxi";
                        }
                    }

                    if(driver.isChecked())
                    {
                        if(taxi.isChecked())
                        {
                            services = "Taxi,Driver";
                        }

                        else {
                            services = "Driver";
                        }

                    }

                    tst2();

                }

            }
        });
    }


    public void setprofile() {
        name.setText(pName);
        state.setText(pState);
        district.setText(pDistrict);
        mobile.setText(pMobile);
        area.setText(pArea);



        if(pSrtype.compareTo("Taxi")==0)
        {
            taxi.setChecked(true);
        }
        else if(pSrtype.compareTo("Car")==0)
        {
            driver.setChecked(true);
        }
        else
        {
            taxi.setChecked(true);
            driver.setChecked(true);
        }


        if(pVtype.compareTo("Car")==0)
        {

            car.setChecked(true);
        }
        else if(pVtype.compareTo("Bus")==0)
        {

            bus.setChecked(true);
        }
        else
        {

            atrk.setChecked(true);
        }

        email.setText(pMail_id);
        username.setVisibility(View.GONE);
        brandname.setText(pCountry);

        String base=pImage;
        byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
        savePhoto.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length) );


    }



//.......................LOAD DATA FROM SERVER.....................................................................................


    private class LoadData extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        String responsefromserver = null;
        HashMap<String, String> nameValuePairs;
        @Override
        // can use UI thread here
        protected void onPreExecute() {

            this.progressDialog = ProgressDialog.show(
                    Edit_Profile.this, "", " Loading .....");

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
                // nodonor();
            }
            else{
                setprofile();

            }


        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // HTTP post

            try {
                try {


                    nameValuePairs = new HashMap<String, String>();
                    nameValuePairs.put("username",userName);
                    nameValuePairs.put("password",userPassword);

                    SendRequestServer req = new SendRequestServer();
                    String url1 = "android_view_profile.php";
                    responsefromserver = req.requestSender(url1, nameValuePairs,Edit_Profile.this);

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

                            pName = json_data.getString("name");
                            pImage=json_data.getString("photo");
                            pArea=json_data.getString("area");
                            pMobile=json_data.getString("mobile");
                            pVtype=json_data.getString("vctype");
                            pSrtype=json_data.getString("srtype");
                            pUname=json_data.getString("uname");
                            pMobile = json_data.getString("mobile");
                            pMail_id = json_data.getString("email");
                            pDistrict = json_data.getString("district");
                            pState = json_data.getString("state");
                            pCountry = json_data.getString("brandname");
                            passwordS = json_data.getString("password");
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

    public void tst2()
    {

        if (radiotype.getCheckedRadioButtonId() == -1)
        {

            Toast.makeText(this,"Please Select Vehicle type" , Toast.LENGTH_SHORT).show();
        }
        else
        {
            // one of the radio buttons is checked

            selectedId = radiotype.getCheckedRadioButtonId();
            vtype = (RadioButton) findViewById(selectedId);
            vtypeS = vtype.getText().toString();
        }
        if( emailS.matches(emailPattern)) {


            if (nameS.compareTo("") == 0) {
                Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
            } else if (stateS.compareTo("") == 0) {
                Toast.makeText(this, "PLease Enter District", Toast.LENGTH_SHORT).show();
            } else if (areaS.compareTo("") == 0) {
                Toast.makeText(this, "PLease Enter Area/Location", Toast.LENGTH_SHORT).show();
            } else if (brandnameS.compareTo("") == 0) {
                Toast.makeText(this, "PLease Enter Brand/Mdel", Toast.LENGTH_SHORT).show();
            } else if (mobileS.compareTo("") == 0) {
                Toast.makeText(this, "PLease Enter Mobile Number", Toast.LENGTH_SHORT).show();
//            } else if (usernameS.compareTo("") == 0) {
//                Toast.makeText(this, "PLease Enter User name", Toast.LENGTH_SHORT).show();
            } else if (emailS.compareTo("") == 0) {
                Toast.makeText(this, "PLease Enter email", Toast.LENGTH_SHORT).show();
//            } else if (passwordS.compareTo("") == 0) {
//                Toast.makeText(this, "PLease Enter Password", Toast.LENGTH_SHORT).show();
//            } else if (passwordS.compareTo(cnfpasswordS) != 0) {
//                Toast.makeText(this, "Password not matching", Toast.LENGTH_SHORT).show();
//            } else if (passwordS.length() < 9) {
//                Toast.makeText(Edit_Profile.this, "Your password should be minimum 8 character", Toast.LENGTH_SHORT).show();
           }
            else if (img_str.compareTo("") == 0) {
                Toast.makeText(this, "PLease Set profile photo", Toast.LENGTH_SHORT).show();
            } else if (services == null) {
                Toast.makeText(this, "PLease Set Your Available Services", Toast.LENGTH_SHORT).show();
            } else if (status.compareTo("Not connected to Internet") == 0) {
                Toast.makeText(Edit_Profile.this, "Your Internet connection is unstable, Please try again later", Toast.LENGTH_SHORT).show();
            } else {


                new UpdateData().execute();
            }

        }
        else
        {
            Toast.makeText(this, "PLease Enter valid Mail id", Toast.LENGTH_SHORT).show();
        }

    }


    //...............................................SAVE DATA TO SERVER..................................................................................................



    private class UpdateData extends AsyncTask<Void, Void, Void> {
        String responsefromserver = null;
        String responsefromserver2 = null;
        private ProgressDialog mProgressDialog;
        HashMap<String, String> nameValuePairs;
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(Edit_Profile.this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        protected Void doInBackground(Void... params) {
            try {
                try {

                    nameValuePairs = new HashMap<String, String>();


                    nameValuePairs.put("name",nameS);
                    nameValuePairs.put("state",stateS);
                    nameValuePairs.put("district",districtS);
                    nameValuePairs.put("area",areaS);
                    nameValuePairs.put("brandname",brandnameS);
                    nameValuePairs.put("mobile",mobileS);
                    nameValuePairs.put("username",pUname);
                    nameValuePairs.put("email",emailS);
                    nameValuePairs.put("password",passwordS);
                    nameValuePairs.put("vtype",vtypeS);
                    nameValuePairs.put("image",img_str);
                    nameValuePairs.put("services",services);


                    SendRequestServer req = new SendRequestServer();
                    String url1 = "update_profile.php";
                    responsefromserver = req.requestSender(url1, nameValuePairs,Edit_Profile.this);



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

                Toast.makeText(Edit_Profile.this, "Network Error", Toast.LENGTH_SHORT).show();
                // logginFromMobile();
            } else if (responsefromserver.contains("<html>")) {
                //Log.e("Response", responsefromserver);

                Toast.makeText(Edit_Profile.this, "<html>", Toast.LENGTH_SHORT).show();
                //  logginFromMobile();
            } else if (responsefromserver.equals("success")) {


                DynexoPrefManager D = new DynexoPrefManager();
                D.saveImage(img_str,Edit_Profile.this);
                D.saveName(nameS,Edit_Profile.this);
                D.saveArea(areaS,Edit_Profile.this);
                D.saveVehicleType(vtypeS,Edit_Profile.this);
                D.saveServiceTypes(services,Edit_Profile.this);
                D.saveUserName(pUname,Edit_Profile.this);
                D.saveUserMobile(mobileS,Edit_Profile.this);
                D.saveMailId(emailS,Edit_Profile.this);
                D.saveDistrict(districtS,Edit_Profile.this);
                D.saveState(stateS,Edit_Profile.this);
               // D.saveUserPassword(pass,Edit_Profile.this);

                Toast.makeText(Edit_Profile.this, "Successfully Updated...", Toast.LENGTH_LONG).show();
                Intent i=new Intent(Edit_Profile.this,MainActivity.class);
                startActivity(i);
                finish();
                // new LoadData().execute();

            }

            else if (responsefromserver.contains("existnumber")) {
                Toast.makeText(Edit_Profile.this, "Invalid User / Phone number already exists", Toast.LENGTH_SHORT).show();


            }
            else if (responsefromserver.contains("existuser")) {
                Toast.makeText(Edit_Profile.this, "Invalid User / User name already exists", Toast.LENGTH_SHORT).show();


            }

        }}
}

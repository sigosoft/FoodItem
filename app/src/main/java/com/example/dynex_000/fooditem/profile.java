package com.example.dynex_000.fooditem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import customfonts.MyTextView;

public class profile extends AppCompatActivity {

    private ImageView banar1,call;
    TextView service,area,name,uname,mobile,w_mobile,mail_id,address;

    JSONArray jArray;
    private LinearLayout contact;
    private LinearLayout album;
    ImageView ic_arrows;
    MyTextView edit;
    String username,password,pName,pImage,pArea,pVtype,pSrtype,pUname,pMobile,pWmobile,pMail_id,pDistrict,pState,pCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        Context context;
        service = (TextView)findViewById(R.id.service);
        area = (TextView)findViewById(R.id.area);
        name = (TextView)findViewById(R.id.name);
        banar1 = (ImageView)findViewById(R.id.banar1);
        call = (ImageView)findViewById(R.id.call);
        uname = (TextView)findViewById(R.id.uname);
        mobile = (TextView)findViewById(R.id.mobile);
        w_mobile = (TextView)findViewById(R.id.w_mobile) ;
        address = (TextView)findViewById(R.id.address);
       // edit = (MyTextView)findViewById(R.id.edit);

        mail_id =(TextView)findViewById(R.id.mail_id);

       // View headertoolbar = navigationView.inflateHeaderView(R.layout.toolbar);

        ic_arrows= (ImageView)findViewById(R.id.ic_arrows) ;

        ic_arrows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(profile.this, MainActivity.class);
                startActivity(k);
                finish();
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String status = NetCheck.getConnectivityStatusString(getBaseContext());
                if(status.compareTo("Not connected to Internet")==0)
                {
                    Toast.makeText(getApplicationContext(), "Network Error...", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent k = new Intent(profile.this, Edit_Profile.class);

//                    k.putExtra("userName", sState);
//                    k.putExtra("password", sDistrict);

                    startActivity(k);
                   // finish();
                }
            }
        });

        setprofile();

}


    @Override
    public void onBackPressed() {
      // super.onBackPressed();

        Intent k = new Intent(profile.this, MainActivity.class);
        startActivity(k);
        finish();
    }

    public void onClick(View v) {

//        Uri number = Uri.parse("tel:"+pMobile);
//        Intent intent = new Intent(Intent.ACTION_DIAL,number);
//        startActivity(intent);

        AlertDialog.Builder adb = new AlertDialog.Builder(profile.this);
        adb.setTitle("Exit").setMessage("Do you want to really Logout ?");
        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DynexoPrefManager f = new DynexoPrefManager();
                f.saveImage(null,profile.this);
                Intent i=new Intent(profile.this,MainActivity.class);
                startActivity(i);
                finish();
//                  Intent a = new Intent(Intent.ACTION_MAIN);
//                  a.addCategory(Intent.CATEGORY_HOME);
//                  a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                  startActivity(a);
//                  finish();
            }
        });
        adb.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = adb.create();
        alert.show();

    }

    public void onClick2(View v) {

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");


        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Call Driver");
        String sAux = "\nHey there! I am using Call Driver..\nLet me recommend you this application\n\n";
        sAux = sAux + "https://play.google.com/store/apps/details?id=com.dynexo.bloodBank";
        sharingIntent.putExtra(Intent.EXTRA_TEXT, sAux);
        //  startActivity(Intent.createChooser(sharingIntent, "choose one"));
        startActivity(Intent.createChooser(sharingIntent, "Share via"));


       // return true;

    }


    public void onClick3(View v) {

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + pMail_id));
        startActivity(intent);

    }

    public void setprofile()
    {

        DynexoPrefManager D = new DynexoPrefManager();

        pName = D.getSavedName(profile.this);
        pImage = D.getSavedImage(profile.this);
        pArea = D.getSavedArea(profile.this);
        pState =D.getSavedState(profile.this);
        pDistrict =D.getSavedDistrict(profile.this);
        pMobile = D.getSavedUserMobile(profile.this);
        pUname =D.getSavedUserName(profile.this);
        pMail_id = D.getSavedMailId(profile.this);
        pVtype = D.getSavedVehicleType(profile.this);
        pSrtype = D.getSavedServiceTypes(profile.this);
       // pPassword =D.getSavedUserPassword(profile.this)





       // Picasso.with(this).load(pImage).into(banar1);
        service.setText(pSrtype);
        name.setText(pName);
        area.setText(pArea);
        uname.setText(pUname);
        mobile.setText(pMobile);
        w_mobile.setText(pVtype+" ");
        mail_id.setText(pMail_id);
        address.setText(pArea+", "+pDistrict+", "+pState+", "+pCountry);

        String base=pImage;
        byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
        // ImageView ivsavedphoto = (ImageView)this.findViewById(R.id.usersavedphoto);
        banar1.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length) );

    }

    private void forCircleImage(ImageView imageView, int image){

        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),
                image);

        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);
        paint.setAntiAlias(true);
        Canvas c = new Canvas(circleBitmap);
        c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);

        imageView.setImageBitmap(circleBitmap);

        /*contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(profile.this, ListviewActivity.class);
                startActivity(it);
            }
        });

        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(profile.this, GridviewActivity.class);
                startActivity(it);
            }
        });*/

    }


    private class LoadData extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        String responsefromserver = null;
        HashMap<String, String> nameValuePairs;
        @Override
        // can use UI thread here
        protected void onPreExecute() {

            this.progressDialog = ProgressDialog.show(
                    profile.this, "", " Loading .....");

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
               // Picasso.with(this).load("http://dynexoit.com/calldriver/PhotoUpload/uploads/123456.png") .into(banar1);

            //    t1.setText(password);
             //   t.setText(namer);

                // displayListView();
            }


        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // HTTP post

            try {
                try {


                    nameValuePairs = new HashMap<String, String>();
                    nameValuePairs.put("username",username);
                    nameValuePairs.put("password",password);

                    SendRequestServer req = new SendRequestServer();
                    String url1 = "android_view_profile.php";
                    responsefromserver = req.requestSender(url1, nameValuePairs,profile.this);

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
                            pCountry = json_data.getString("country");
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

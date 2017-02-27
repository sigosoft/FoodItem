package com.example.dynex_000.fooditem;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import customfonts.MyTextView;

public class Single_view_Search_Result extends AppCompatActivity {

String id,name,image_url,price;
ImageView ic_arrows,ic_arrowsa,banar1;
MyTextView buy,Back;
    String  pMa,quandity;
    TextView p_name,pricet;
    EditText qnty;
    JSONArray jArray;
    Integer   f_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_single_view__search__result);


        Intent p = getIntent();

     //   id = p.getStringExtra("id");
        name =p.getStringExtra("name");
       image_url=p.getStringExtra("image");
//
       price = p.getStringExtra("price");

        qnty=(EditText)findViewById(R.id.qnty);
//
        p_name= (TextView)findViewById(R.id.p_name);
        pricet= (TextView)findViewById(R.id.price);
        p_name.setText(name);
       pricet.setText("Rs."+price);
        banar1 = (ImageView)findViewById(R.id.banar1);

       Picasso.with(this).load(image_url).into(banar1);

        buy = (MyTextView)findViewById(R.id.buy);
       Back = (MyTextView)findViewById(R.id.Back);
        ic_arrows =(ImageView)findViewById(R.id.ic_arrows);
        ic_arrowsa = (ImageView)findViewById(R.id.ic_arrowsa);

        ic_arrowsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(Single_view_Search_Result.this, Procnfm.class);
                startActivity(i);

            }
        });

        ic_arrows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         quandity =qnty.getText().toString();

                Integer n= Integer.decode(quandity);

                f_price = Integer.decode(price)*n;
               // Toast.makeText(Single_view_Search_Result.this,f_price , Toast.LENGTH_SHORT).show();

//

                DynexoPrefManager q = new DynexoPrefManager();
                pMa= q.getSavedMailId(Single_view_Search_Result.this);


                if(quandity.compareTo("")==0)
                {
                    Toast.makeText(Single_view_Search_Result.this, "Please Enter the Quantity first", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    new LoadDataph().execute();
                  //  Toast.makeText(Single_view_Search_Result.this, pMa, Toast.LENGTH_SHORT).show();
                }
//                Intent i = new Intent(Single_view_Search_Result.this, Procnfm.class);
//                startActivity(i);
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }




//.............................Load to shopping bag..................................................................

    public class LoadDataph extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        String responsefromserver = null;
        HashMap<String, String> nameValuePairs;
        @Override
        // can use UI thread here
        protected void onPreExecute() {

            this.progressDialog = ProgressDialog.show(
                    Single_view_Search_Result.this, "", " Loading .....");

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
            else if (responsefromserver.equals("exist")) {
                	Toast.makeText(getApplicationContext(), "Product already exist....", Toast.LENGTH_LONG).show();
            }
            else{

                Intent k = new Intent(Single_view_Search_Result.this, Procnfm.class);
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
                        nameValuePairs.put("user_mail", pMa);
                        nameValuePairs.put("image", image_url);
                        nameValuePairs.put("prod_name", name);
                        nameValuePairs.put("qnty", quandity);
                        nameValuePairs.put("price", f_price.toString());
                        nameValuePairs.put("status", "wait");

                        SendRequestServer req = new SendRequestServer();
                        String url1 = "android_insert_shopping_bag.php";
                        responsefromserver = req.requestSender(url1, nameValuePairs, Single_view_Search_Result.this);

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

//




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


package com.example.dynex_000.fooditem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class History extends AppCompatActivity {

    String pMa;

    ImageView ic_arrows,ic_arrowsa;

    private SpecialAdapter3 sp;
    private ListView listview;
    private ArrayList<BeanClassForListView3> beanClassArrayList;
    JSONArray jArray;

    static ArrayList<String> list = new ArrayList<String>();
    static ArrayList<String> listImage = new ArrayList<String>();
    static ArrayList<String> listTitle = new ArrayList<String>();
    static ArrayList<String> listMobile = new ArrayList<String>();
    static ArrayList<String> listVehicleType = new ArrayList<String>();
    static ArrayList<String> listBrandName = new ArrayList<String>();
    static ArrayList<String> listEmail = new ArrayList<String>();
    static ArrayList<String> listServiceType = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);

        listview = (ListView) findViewById(R.id.listview);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fab.setVisibility(View.GONE);

        DynexoPrefManager q = new DynexoPrefManager();
        pMa= q.getSavedMailId(History.this);

        ic_arrowsa= (ImageView)findViewById(R.id.ic_arrowsa);
        ic_arrowsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(History.this, Procnfm.class);
                startActivity(i);
            }
        });
        ic_arrows = (ImageView)findViewById(R.id.ic_arrows);

        ic_arrows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });

        new LoadData().execute();

    }



    public void setListview()
    {
        final String[] IMAGE = listImage.toArray(new String[listImage.size()]);
        final String[] DESCRIPTION = listVehicleType.toArray(new String[list.size()]);
        final String[] TITLE = listTitle.toArray(new String[listTitle.size()]);


        beanClassArrayList = new ArrayList<BeanClassForListView3>();


        for (int i = 0; i < TITLE.length; i++) {

            BeanClassForListView3 beanClass = new BeanClassForListView3(IMAGE[i], TITLE[i], DESCRIPTION[i]);
            beanClassArrayList.add(beanClass);

        }

        sp = new SpecialAdapter3(History.this, beanClassArrayList);

        listview.setAdapter(sp);

//
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//
//
//                Toast.makeText(Home.this, "Entered " +TITLE[position]  + " and password entered is", Toast.LENGTH_SHORT).show();
//                //w(id);
//
//
//                Intent i=new Intent(Home.this,Home.class);
////                i.putExtra("name", listTitle.get(position));
////                i.putExtra("mobile", listMobile.get(position));
////                i.putExtra("area",sArea );
////
////                i.putExtra("vType",listVehicleType.get(position));
////                i.putExtra("state", sState);
////                i.putExtra("district", sDistrict);
////                i.putExtra("serviceType",list.get(position) );
////
////                i.putExtra("brandName", listBrandName.get(position));
////                i.putExtra("email", listEmail.get(position));
////                i.putExtra("image",listImage.get(position));
//                startActivity(i);
//
//
//
//
//
//            }
//
//
//        });

    }



    //.............................LOAD History Data from Server..................................................................

    private class LoadData extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        String responsefromserver = null;
        HashMap<String, String> nameValuePairs;
        @Override
        // can use UI thread here
        protected void onPreExecute() {


            listTitle.clear();
            listImage.clear();
            listVehicleType.clear();

            this.progressDialog = ProgressDialog.show(
                    History.this, "", " Loading .....");

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

                setListview();
               // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

            }


        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // HTTP post

            try {
                try {


                    nameValuePairs = new HashMap<String, String>();
                    nameValuePairs.put("username",pMa);


                    SendRequestServer req = new SendRequestServer();
                    String url1 = "android_load_bill_history.php";
                    responsefromserver = req.requestSender(url1, nameValuePairs,History.this);


                    try {
                        jArray = new JSONArray(responsefromserver);
                        JSONObject json_data = null;
                        for (int i = 0; i < jArray.length(); i++) {
                            json_data = jArray.getJSONObject(i);

                            listImage.add(json_data.getString("price"));
                            listVehicleType.add(json_data.getString("date"));
                            listTitle.add(json_data.getString("bill_no"));



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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

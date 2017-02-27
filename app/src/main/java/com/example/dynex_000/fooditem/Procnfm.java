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
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import customfonts.MyTextView;
import customfonts.MyTextView1;

public class Procnfm extends AppCompatActivity {

    String message;
    Integer kl=0;

    JSONArray jArray;
    private SpecialAdapter2 sp;
    private ListView listview;
    private ArrayList<BeanClassForListView2> beanClassArrayList;
    String pMa,pro_name,user_mail,qnty,s_price,st;
    Integer tt=0;
   static MyTextView cnfm,ttl,add;

    String[] PRICE,QUANTITY,PRO_NAME;
    static ArrayList<String> list = new ArrayList<String>();
    static ArrayList<String> listImage = new ArrayList<String>();
    static ArrayList<String> listTitle = new ArrayList<String>();

    static ArrayList<String> listVehicleType = new ArrayList<String>();
    static ArrayList<String> listBrandName = new ArrayList<String>();
    static ArrayList<String> listEmail = new ArrayList<String>();
    static ArrayList<String> listServiceType = new ArrayList<String>();


    static ArrayList<String> listp_name = new ArrayList<String>();
    static ArrayList<String> listp_qnty = new ArrayList<String>();
    static ArrayList<String> listp_amnt = new ArrayList<String>();
    String[] DeletetedProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procnfm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);




        list.clear();
        listImage.clear();
        listTitle.clear();
        listVehicleType.clear();

        ttl = (MyTextView)findViewById(R.id.ttl);
        cnfm =(MyTextView)findViewById(R.id.cnfm);
        add =(MyTextView)findViewById(R.id.add);
        listview = (ListView) findViewById(R.id.listview);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               new LoadDataph().execute();

            }
        });

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
        pMa= q.getSavedMailId(Procnfm.this);

        cnfm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DynexoPrefManager u = new DynexoPrefManager();
                String pl = u.getDeleteProduct(Procnfm.this);
                //  u.saveDeleteProduct("",Procnfm.this);



                if(pl == null)
                {

                   // new LoadData().execute();
                    sendMail();


                }
                else
                {
                    DeletetedProducts = pl.split(",");
                    new DeleteProductBeforeCnfm().execute();
                    // Toast.makeText(this, DeletetedProducts[1], Toast.LENGTH_LONG).show();
                }



            }
        });
        DynexoPrefManager u = new DynexoPrefManager();
        String pl = u.getDeleteProduct(Procnfm.this);
      //  u.saveDeleteProduct("",Procnfm.this);



        if(pl == null)
        {
//            //
           new LoadData().execute();
//            Toast.makeText(this, "hi", Toast.LENGTH_LONG).show();
//        }
//        else
//        {
//
        }
        else
        {
            DeletetedProducts = pl.split(",");
            new DeleteProduct().execute();
           // Toast.makeText(this, DeletetedProducts[1], Toast.LENGTH_LONG).show();
        }
      //  new LoadData().execute();

    }

    public void setListview() {


        listp_name.clear();
        listp_qnty.clear();
        listp_amnt.clear();

        PRICE = listImage.toArray(new String[listImage.size()]);
        QUANTITY = listVehicleType.toArray(new String[list.size()]);
        PRO_NAME = listTitle.toArray(new String[listTitle.size()]);



        final String[] IMAGE = listImage.toArray(new String[listImage.size()]);
        final String[] DESCRIPTION = listVehicleType.toArray(new String[list.size()]);
        final String[] TITLE = listTitle.toArray(new String[listTitle.size()]);
        final String[] IMAGE_Org = listBrandName.toArray(new String[listBrandName.size()]);


        // Toast.makeText(Procnfm.this, "There is no Cart List Here...", Toast.LENGTH_SHORT).show();

//        if(listTitle.size()<1)
//        {
//            Toast.makeText(this, "There is no Cart List Here...", Toast.LENGTH_SHORT).show();
//           // onBackPressed();
//            finish();
//        }

//
//            Toast.makeText(this, TITLE.length, Toast.LENGTH_SHORT).show();
            beanClassArrayList = new ArrayList<BeanClassForListView2>();


            for (int i = 0; i < TITLE.length; i++) {



                BeanClassForListView2 beanClass = new BeanClassForListView2(IMAGE[i], TITLE[i], DESCRIPTION[i],IMAGE_Org[i]);
                beanClassArrayList.add(beanClass);

            }


        ttl.setText(tt.toString());

            sp = new SpecialAdapter2(Procnfm.this, beanClassArrayList);

            listview.setAdapter(sp);





    }



    //.............................LOAD List Items to Cnfm..................................................................

    public class LoadData extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        String responsefromserver = null;
        HashMap<String, String> nameValuePairs;
        @Override
        // can use UI thread here
        protected void onPreExecute() {
            list.clear();
            listImage.clear();
            listTitle.clear();
            listVehicleType.clear();

//            listTitle.clear();
//            listImage.clear();
//            listVehicleType.clear();

            this.progressDialog = ProgressDialog.show(
                    Procnfm.this, "", " Loading .....");

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
                	Toast.makeText(getApplicationContext(), "There is No Cart list available", Toast.LENGTH_LONG).show();

//                Intent i = new Intent(Procnfm.this, NoCunnectionPage.class);
//                startActivity(i);

                onBackPressed();
                finish();


            }
            else{
               // Toast.makeText(getApplicationContext(),listTitle.size() , Toast.LENGTH_LONG).show();

               setListview();
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
                    nameValuePairs.put("status","wait");

                    SendRequestServer req = new SendRequestServer();
                    String url1 = "android_list_for_cnfm.php";
                    responsefromserver = req.requestSender(url1, nameValuePairs,Procnfm.this);

                     if (responsefromserver.equals("no_data")) {
                        Toast.makeText(getApplicationContext(), "There is No Cart List here....", Toast.LENGTH_LONG).show();
                        onBackPressed();
                        finish();

                    }
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

//                            pro_name = json_data.getString("prod_name");
//                            user_mail=json_data.getString("user_mail");
//                            qnty=;
                          s_price = json_data.getString("price");

                            listImage.add(json_data.getString("price"));
                            listVehicleType.add(json_data.getString("qnty"));
                            listTitle.add(json_data.getString("prod_name"));
                            listBrandName.add(json_data.getString("image"));

                            tt =Integer.decode(s_price)+tt;


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
    //.............................Sending mail to Admin..................................................................

    private class LoadDataq extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        String responsefromserver = null;
        HashMap<String, String> nameValuePairs;
        @Override
        // can use UI thread here
        protected void onPreExecute() {


            this.progressDialog = ProgressDialog.show(
                    Procnfm.this, "", " Loading .....");

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

            else{

                Intent i=new Intent(Procnfm.this,History.class);
                startActivity(i);
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
                    nameValuePairs.put("email",pMa);
                    nameValuePairs.put("message",message);
                    nameValuePairs.put("price",st);


                    SendRequestServer req = new SendRequestServer();
                    String url1 = "android_send_food_order_to_mail.php";
                    responsefromserver = req.requestSender(url1, nameValuePairs,Procnfm.this);


                    try {
                        jArray = new JSONArray(responsefromserver);
                        JSONObject json_data = null;
                        for (int i = 0; i < jArray.length(); i++) {
                            json_data = jArray.getJSONObject(i);



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







    //.............................Load to shopping bag..................................................................

    public class LoadDataph extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        String responsefromserver = null;
        HashMap<String, String> nameValuePairs;
        @Override
        // can use UI thread here
        protected void onPreExecute() {

            this.progressDialog = ProgressDialog.show(
                    Procnfm.this, "", " Loading .....");

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


                onBackPressed();

//                Intent k = new Intent(Procnfm.this, Procnfm.class);
//                startActivity(k);
                finish();

            }


        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // HTTP post

            try {
                try {

                    int p;
                    for (p=0;p<=listp_name.size();p++) {


                        nameValuePairs = new HashMap<String, String>();
                        nameValuePairs.put("user_mail", pMa);
                        // nameValuePairs.put("image", image_url);
                        nameValuePairs.put("prod_name", listp_name.get(p));
                        nameValuePairs.put("qnty", listp_qnty.get(p));
                        nameValuePairs.put("price", listp_amnt.get(p));
                        nameValuePairs.put("status", "wait");

                        SendRequestServer req = new SendRequestServer();
                        String url1 = "android_update_shopping_bag.php";
                        responsefromserver = req.requestSender(url1, nameValuePairs, Procnfm.this);

//                    ArrayList<NameValuePair>namevaluePairs=new ArrayList<NameValuePair>();
//
//                    com.dynexo.SendRequestServer req = new com.dynexo.SendRequestServer();
//                    String url1 = "get_donor.php";
//                    responsefromserver = req.requestSender(url1, namevaluePairs);

                    }
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


//.............................Load to shopping bag..................................................................

    public class DeleteProduct extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        String responsefromserver = null;
        HashMap<String, String> nameValuePairs;
        @Override
        // can use UI thread here
        protected void onPreExecute() {

            this.progressDialog = ProgressDialog.show(
                    Procnfm.this, "", " Loading .....");

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
                DynexoPrefManager jk = new DynexoPrefManager();

                jk.saveDeleteProduct(null,Procnfm.this);
                Intent k = new Intent(Procnfm.this, Procnfm.class);
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

                   int o;

                   for(o =0;o<DeletetedProducts.length;o++) {

                       nameValuePairs = new HashMap<String, String>();
                       nameValuePairs.put("user_mail", pMa);
                       // nameValuePairs.put("image", image_url);
                       nameValuePairs.put("prod_name", DeletetedProducts[o]);
                       nameValuePairs.put("status", "wait");
                       SendRequestServer req = new SendRequestServer();
                       String url1 = "android_delete_shopping_bag.php";
                       responsefromserver = req.requestSender(url1, nameValuePairs, Procnfm.this);
                   }
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



    //.............................Delete Before Confirm..................................................................

    public class DeleteProductBeforeCnfm extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        String responsefromserver = null;
        HashMap<String, String> nameValuePairs;
        @Override
        // can use UI thread here
        protected void onPreExecute() {

            this.progressDialog = ProgressDialog.show(
                    Procnfm.this, "", " Loading .....");

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
                DynexoPrefManager jk = new DynexoPrefManager();

                jk.saveDeleteProduct(null,Procnfm.this);

               sendMail();
            }


        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            // HTTP post

            try {
                try {

                    int o;

                    for(o =0;o<DeletetedProducts.length;o++) {

                        nameValuePairs = new HashMap<String, String>();
                        nameValuePairs.put("user_mail", pMa);
                        // nameValuePairs.put("image", image_url);
                        nameValuePairs.put("prod_name", DeletetedProducts[o]);
                        nameValuePairs.put("status", "wait");
                        SendRequestServer req = new SendRequestServer();
                        String url1 = "android_delete_shopping_bag.php";
                        responsefromserver = req.requestSender(url1, nameValuePairs, Procnfm.this);
                    }
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




public void sendMail()
{


     st= ttl.getText().toString();


    DynexoPrefManager q = new DynexoPrefManager();
   String  name= q.getSavedName(Procnfm.this);
    String outlet =q.getSavedState(Procnfm.this);
    String mobile = q.getSavedUserMobile(Procnfm.this);
    message ="";

    for(int i=0;i<PRO_NAME.length;i++)
    {
        message =message+" \n"+ PRO_NAME[i]+"       "+QUANTITY[i]+"       Rs."+PRICE[i]+" \n";

    }

    message=  message+"\n"+" Total Amount: "+tt.toString();
    message = message+"\n"+"\n"+"\n"+"  "+"Name: "+name;
    message =message+"\n"+"  "+"Outlet: "+outlet;
    message = message+"\n"+"  "+"Mobile: "+mobile;

    new LoadDataq().execute();


}

}

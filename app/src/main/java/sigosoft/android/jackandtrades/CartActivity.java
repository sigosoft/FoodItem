package sigosoft.android.jackandtrades;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pooja on 4/26/2017.
 */

public class CartActivity extends AppCompatActivity {
    ListView listView;
    String outletName;
    DBHelper bn;
    List<BeanClassForListView4> items = new ArrayList<>();
    CartAdapter adapter;
    BeanClassForListView4 model;
    TextView submit;
    String title, price, qty, cid, id, unitprice;
    TextView total;
    JSONArray jArray;
    double totalprice = 0;
    String pMa, grandtotal;
    ImageView ic_arrows, ic_arrowsa;
    Context context;
    String LastInsertedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_procnfm);
        listView = (ListView) findViewById(R.id.listview);
        total = (TextView) findViewById(R.id.ttl);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);
        DynexoPrefManager q = new DynexoPrefManager();
        pMa = q.getSavedMailId(CartActivity.this);
        submit = (TextView) findViewById(R.id.cnfm);
        DynexoPrefManager dy = new DynexoPrefManager();
        outletName = dy.showSavedResponse("outname", this);
        bn = new DBHelper(this);
        items = bn.getOrders();
        if (items.size() == 0) {
            Toast.makeText(getApplicationContext(), "Cart is Empty", Toast.LENGTH_LONG).show();


        } else {
            adapter = new CartAdapter(CartActivity.this, items);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            totalprice = getTotalPrice(items);
            total.setText(String.valueOf(totalprice));
            grandtotal = String.valueOf(totalprice);

        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //    new DatatoServer().execute();
                AlertDialog.Builder alert = new AlertDialog.Builder(CartActivity.this);
                alert.setTitle("Warning!").setMessage("Do you wish to continue Submit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new DatatoServerGetLastID().execute();


                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
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


        ic_arrows = (ImageView) findViewById(R.id.ic_arrows);

        ic_arrows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ic_arrowsa = (ImageView) findViewById(R.id.ic_arrowsa);
        ic_arrowsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CartActivity.this, History2.class);
                startActivity(i);
            }
        });
    }

    private double getTotalPrice(List<BeanClassForListView4> items) {
        double totalcost = 0;
        for (int i = 0; i < items.size(); i++) {
            BeanClassForListView4 fitem = items.get(i);
            totalcost = totalcost + Double.parseDouble(fitem.getDescription());
        }
        return totalcost;
    }

    private class DatatoServer extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        String responsefromserver = "empty";

        HashMap<String, String> namevaluePairs = new HashMap<String, String>();

        protected void onPreExecute() {


            this.progressDialog = ProgressDialog.show(
                    CartActivity.this, "", " Loading .....");


        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                for (int i = 0; i < items.size(); i++) {
                    cid = items.get(i).getCart_id().toString();
                    id = items.get(i).getId();
                    title = items.get(i).getTitle();
                    price = items.get(i).getDescription();
                    qty = items.get(i).getQty();
                    unitprice = items.get(i).getUnitPrice();

                           /*  runOnUiThread(new Runnable(){

                                 @Override
                                 public void run(){
                                     //update ui here
                                     // display toast here
                                     Toast.makeText(CartActivity.this,title+cid+id+price+qty,Toast.LENGTH_LONG).show();
                                 }
                             });
*/
                    namevaluePairs.put("cid", cid);
                    Log.d("cid", cid);
                    namevaluePairs.put("id", id);
                    Log.d("id", id);
                    namevaluePairs.put("title", title);
                    Log.d("title", title);
                    namevaluePairs.put("price", price);
                    Log.d("price", price);
                    namevaluePairs.put("qty", qty);
                    namevaluePairs.put("userid", pMa);
                    namevaluePairs.put("total", grandtotal);


                    SendRequestServer req = new SendRequestServer();

                    String url = "bill_history.php";
                    responsefromserver = req.requestSender(url, namevaluePairs, CartActivity.this);
                    Log.d("response", responsefromserver);


                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }


        protected void onPostExecute(Void unused) {
            progressDialog.dismiss();

            if (responsefromserver == null) {

                Toast.makeText(CartActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                // logginFromMobile();
            } else if (responsefromserver.contains("<html>")) {
                //Log.e("Response", responsefromserver);

                Toast.makeText(CartActivity.this, "<html>", Toast.LENGTH_SHORT).show();
                //  logginFromMobile();
            } else if (responsefromserver.equals("success")) {

                //   Toast.makeText(CartActivity.this, "Success", Toast.LENGTH_LONG).show();

                new Sndmail().execute();


            } else if (responsefromserver.contains("exist")) {
                Toast.makeText(CartActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();


            }

        }
    }


    private class DatatoServerGetLastID extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        String responsefromserver = "empty";

        HashMap<String, String> namevaluePairs = new HashMap<String, String>();

        protected void onPreExecute() {


            this.progressDialog = ProgressDialog.show(
                    CartActivity.this, "", " Loading .....");


        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd   HH:mm:ss");
                String currentDateandTime = sdf.format(new Date());
                String Totalprice = String.valueOf(totalprice);
                for (int i = 0; i < items.size(); i++) {
                    cid = items.get(i).getCart_id().toString();
                    id = items.get(i).getId();
                    title = items.get(i).getTitle();
                    price = items.get(i).getDescription();
                    qty = items.get(i).getQty();
                    unitprice = items.get(i).getUnitPrice();


                    namevaluePairs.put("user", pMa);
                    namevaluePairs.put("date", currentDateandTime);
                    namevaluePairs.put("time", "564");
                    namevaluePairs.put("total", Totalprice);


                }
                SendRequestServer req = new SendRequestServer();
                String url = "bill_history_id_return.php";
//                    String url = "bill_history.php";
//                   responsefromserver = req.requestSender(url, namevaluePairs, CartActivity.this);
//                    Log.d("response", responsefromserver);

                responsefromserver = req.requestSender(url, namevaluePairs, CartActivity.this);

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }


        protected void onPostExecute(Void unused) {
            progressDialog.dismiss();
            LastInsertedID = responsefromserver;

            new DatatoBillDetails().execute();

        }
    }


    private class DatatoBillDetails extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        String responsefromserver = "empty";

        HashMap<String, String> namevaluePairs = new HashMap<String, String>();

        protected void onPreExecute() {


            this.progressDialog = ProgressDialog.show(
                    CartActivity.this, "", " Loading .....");


        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                int lastId = Integer.parseInt(LastInsertedID);

                for (int i = 0; i < items.size(); i++) {
                    cid = items.get(i).getCart_id().toString();
                    id = items.get(i).getId();
                    title = items.get(i).getTitle();
                    price = items.get(i).getDescription();
                    qty = items.get(i).getQty();
                    unitprice = items.get(i).getUnitPrice();

                    namevaluePairs.put("bill_id", LastInsertedID);
                    namevaluePairs.put("product_name", title);
                    namevaluePairs.put("qty", qty);
                    namevaluePairs.put("total", price);
//
                    SendRequestServer req = new SendRequestServer();
                    String url = "bill_history_details.php";
//                    String url = "bill_history.php";
                    responsefromserver = req.requestSender(url, namevaluePairs, CartActivity.this);

                }

                // Log.d("response", responsefromserver);

                //      responsefromserver = req.requestSender(url, namevaluePairs, CartActivity.this);

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }


        protected void onPostExecute(Void unused) {
            progressDialog.dismiss();
            String asdsdf = responsefromserver;

            new Sndmail().execute();

        }
    }


    private class Sndmail extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        String responsefromserver = "empty";

        HashMap<String, String> namevaluePairs = new HashMap<String, String>();

        protected void onPreExecute() {


            this.progressDialog = ProgressDialog.show(
                    CartActivity.this, "", " Loading .....");


        }

        @Override
        protected Void doInBackground(Void... voids) {

            ArrayList<String> a1 = new ArrayList<>();
            ArrayList<String> a2 = new ArrayList<>();
            ArrayList<String> a3 = new ArrayList<>();
            try {
                int priceSum = 0;
                for (int i = 0; i < items.size(); i++) {
                    cid = items.get(i).getCart_id().toString();
                    id = items.get(i).getId();
                    title = items.get(i).getTitle();
                    price = items.get(i).getDescription();
                    qty = items.get(i).getQty();
                    a1.add(title);
                    a2.add(price);
                    a3.add(qty);
                    priceSum = priceSum + Integer.parseInt(price);
                    String body = null;
                    String abc = null;
                    //String body = cid + id + title + price + qty;
                    //     String body = a3.get(0)+a1.get(0)+"=="+a2.get(0);
                    for (int j = 0; j < a1.size(); j++) {
                        body = a3.get(j)+"x" + a1.get(j) + "=" + a2.get(j) + "<br />";
                        abc = body + abc;
                    }
                    DynexoPrefManager dy = new DynexoPrefManager();
                    String lo = dy.showSavedResponse("location", CartActivity.this);
//                    DynexoPrefManager de = new DynexoPrefManager();
//                    String location = de.showSavedResponse("location", context);
                    String location = "cccccc";
                    String sum = abc.replaceAll("null", "");
                    //   String body =title+"<br />" +"price= "+ price +"<br />"+"Qty= "+ qty;
//                    namevaluePairs.put("qty", qty);
//                    namevaluePairs.put("userid", pMa);
//                    namevaluePairs.put("total", grandtotal);
                    namevaluePairs.put("name", "user");
                    namevaluePairs.put("email", pMa);
                    namevaluePairs.put("subject", "JackandTrade Sale");
                    //  namevaluePairs.put("body","Outlet:  "+outletName+"<br/>"+"Bill no:  "+LastInsertedID+"<br/>"+"<br/>"+ sum + "<br/>   Total Price = " + priceSum);

                    namevaluePairs.put("body", "Outlet:  " + outletName + "<br/>" + "Location: " + lo + "<br/>" + "Bill no:  " + LastInsertedID + "<br/>" + "<br/>" + sum + "<br/>   Total Price = " + priceSum +"<br/>"+"Thank you for the Business");

                }


                SendRequestServer req = new SendRequestServer();
                String url = "phpsendmail.php";
                responsefromserver = req.requestSender(url, namevaluePairs, CartActivity.this);

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }


        protected void onPostExecute(Void unused) {
            progressDialog.dismiss();

            if (responsefromserver == null) {

                Toast.makeText(CartActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                // logginFromMobile();
            } else if (responsefromserver.contains("<html>")) {
                //Log.e("Response", responsefromserver);

                Toast.makeText(CartActivity.this, "<html>", Toast.LENGTH_SHORT).show();
                //  logginFromMobile();
            } else if (responsefromserver.equals("Message has been sent")) {

                //    Toast.makeText(CartActivity.this, "EmailSent", Toast.LENGTH_LONG).show();
                AlertDialog.Builder alert = new AlertDialog.Builder(CartActivity.this);
                alert.setMessage("Your order has been placed.Thank you")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent in = new Intent(CartActivity.this, OutLetActivity.class);
                                finish();
                                startActivity(in);
                                bn.clearOrders();
                                // Toast.makeText(CartActivity.this, "No recent cart", Toast.LENGTH_SHORT).show();
                            }
                        }).create().show();

            } else if (responsefromserver.contains("exist")) {
                Toast.makeText(CartActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();


            }

        }
    }

    @Override
    public void onBackPressed() {
        finish();
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
    }
}










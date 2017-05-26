package sigosoft.android.jackandtrades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pooja on 5/3/2017.
 */

public class History2 extends AppCompatActivity {
    ListView listView;
    HistoryModel historyModel;
    ArrayList<HistoryModel> dataarray;
    JSONArray jsonArray;
    ImageView ic_arrows, ic_arrowsa;
    JSONArray jArray;
    String pMa;
    HistoryAdapter1 historyAdapter1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listView = (ListView) findViewById(R.id.listview);
        ic_arrowsa = (ImageView) findViewById(R.id.ic_arrowsa);
        ic_arrows = (ImageView) findViewById(R.id.ic_arrows);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);
        dataarray = new ArrayList<>();
        //   new LoadDataS().execute();
        new LoadHistory().execute();
        DynexoPrefManager q = new DynexoPrefManager();
        pMa = q.getSavedMailId(History2.this);
        historyAdapter1 = new HistoryAdapter1(History2.this, dataarray);

        ic_arrowsa = (ImageView) findViewById(R.id.ic_arrowsa);
        ic_arrowsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(History2.this, CartActivity.class);
                startActivity(i);
            }
        });
        ic_arrows = (ImageView) findViewById(R.id.ic_arrows);

        ic_arrows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private class LoadDataS extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        String responsefromserver = null;
        HashMap<String, String> namevaluePairs;

        protected void onPreExecute() {


            this.progressDialog = ProgressDialog.show(
                    History2.this, "", " Loading .....");


        }

        protected void onPostExecute(Void unused) {
            this.progressDialog.dismiss();


            if (responsefromserver == null) {
                //serverDown();
            } else if (responsefromserver.contains("<html>")) {
                Log.e("ResponseLocation", responsefromserver);
                // serverDown();
                Toast.makeText(getApplicationContext(), "Server down....", Toast.LENGTH_LONG).show();
            } else if (responsefromserver.equals("no_data")) {
                Toast.makeText(getApplicationContext(), "There is no Such product....", Toast.LENGTH_LONG).show();
                finish();

            } else {


                setListview();


            }
        }

        protected Void doInBackground(Void... voids) {
            try {

                try {
                    namevaluePairs = new HashMap<String, String>();
                    namevaluePairs.put("user_id", pMa);
                    SendRequestServer req = new SendRequestServer();
                    String url1 = "historyview.php";
                    responsefromserver = req.requestSender(url1, namevaluePairs, History2.this);

                    try {
                        jArray = new JSONArray(responsefromserver);
                        JSONObject json_data = null;
                        for (int i = 0; i < jArray.length(); i++) {
                            json_data = jArray.getJSONObject(i);
                            String cart_id = json_data.getString("cart_id");
                            String title = json_data.getString("title");
                            String price = json_data.getString("price");
                            HistoryModel historyModel = new HistoryModel(cart_id, title, price);
                            dataarray.add(historyModel);
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

    private class LoadHistory extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        String responsefromserver = null;
        HashMap<String, String> namevaluePairs;

        protected void onPreExecute() {


            this.progressDialog = ProgressDialog.show(
                    History2.this, "", " Loading .....");


        }


        protected Void doInBackground(Void... voids) {
            try {

                try {
                    namevaluePairs = new HashMap<String, String>();
                    namevaluePairs.put("user_id", pMa);
                    SendRequestServer req = new SendRequestServer();
                    String url1 = "historyview.php";
                    responsefromserver = req.requestSender(url1, namevaluePairs, History2.this);

                    try {
                        jArray = new JSONArray(responsefromserver);
                        JSONObject json_data = null;
                        for (int i = 0; i < jArray.length(); i++) {
                            json_data = jArray.getJSONObject(i);
                            String bill_id = json_data.getString("id");
                            String date = json_data.getString("date");
                            String time = json_data.getString("time");
                            String total = json_data.getString("total");
                            HistoryModel historyModel = new HistoryModel(bill_id, date, total);
                            dataarray.add(historyModel);
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

        protected void onPostExecute(Void unused) {
            this.progressDialog.dismiss();


            if (responsefromserver == null) {
                //serverDown();
            } else if (responsefromserver.contains("<html>")) {
                Log.e("ResponseLocation", responsefromserver);
                // serverDown();
                Toast.makeText(getApplicationContext(), "Server down....", Toast.LENGTH_LONG).show();
            } else if (responsefromserver.equals("no_data")) {
                Toast.makeText(getApplicationContext(), "There is no Such product....", Toast.LENGTH_LONG).show();
                finish();

            } else {


                setListview();


            }
        }

    }

    public void setListview() {
        listView.setAdapter(historyAdapter1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = dataarray.get(position).getCart_id();
                Intent in = new Intent(getApplicationContext(), HistoryDetailsActivity.class);
                in.putExtra("BillId", value);
                startActivity(in);
            }
        });

    }

}

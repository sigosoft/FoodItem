package sigosoft.android.jackandtrades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryDetailsActivity extends AppCompatActivity {
    String billId;
    ListView listView;
    HistoryDetailsAdapter adp;
    List<HistoryDetailsModel> items;
    static ArrayList<String> arr1 = new ArrayList<String>();
    static ArrayList<String> arr2 = new ArrayList<String>();
    static ArrayList<String> arr3 = new ArrayList<String>();
    static ArrayList<String> arr4 = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        items = new ArrayList<>();
        Intent p=getIntent();
        billId = p.getStringExtra("BillId");
        listView = (ListView) findViewById(R.id.billListview);

        new LoadHistoryFullDetails().execute();
        adp = new HistoryDetailsAdapter(HistoryDetailsActivity.this, items);
        listView.setAdapter(adp);
    }
    private class LoadHistoryFullDetails extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        String responsefromserver = null;
        HashMap<String, String> namevaluePairs;

        protected void onPreExecute() {


            this.progressDialog = ProgressDialog.show(
                    HistoryDetailsActivity.this, "", " Loading .....");


        }


        protected Void doInBackground(Void... voids) {
            try {

                try {
                    namevaluePairs = new HashMap<String, String>();
                    namevaluePairs.put("bill_id", billId);
                    SendRequestServer req = new SendRequestServer();
                    String url1 = "get_history_details.php";
                    responsefromserver = req.requestSender(url1, namevaluePairs, HistoryDetailsActivity.this);

                    try {
                        JSONArray jArray = new JSONArray(responsefromserver);
                        JSONObject json_data = null;
                        for (int i = 0; i < jArray.length(); i++) {
                            json_data = jArray.getJSONObject(i);
                            String id = json_data.getString("id");
                            String bill_id = json_data.getString("bill_id");
                            String product_name = json_data.getString("product_name");
                            String qty = json_data.getString("qty");
                            String total = json_data.getString("total");
                            HistoryDetailsModel historyModel = new HistoryDetailsModel(bill_id, product_name, qty,total);
                            items.add(historyModel);
//                            arr1.add(bill_id);
//                            arr1.add(product_name);
//                            arr1.add(qty);
//                            arr1.add(total);


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

            adp = new HistoryDetailsAdapter(HistoryDetailsActivity.this, items);
            listView.setAdapter(adp);
        }

    }

}

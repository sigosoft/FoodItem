package sigosoft.android.jackandtrades;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pooja on 3/28/2017.
 */

public class OutLetActivity extends AppCompatActivity {
    ListView outletList;
    //adapter class for outletactivity
    OutletAdapter1 adapter;
    //model class for outletactivity
    OutLetModal outLetModal;
    ArrayList<OutLetModal> outlets;
    //    ImageView ic_arrows;//image of backbutton
    JSONArray jArray;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // turning off the title at the top of the screen.
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.outlet_listview);
//        ic_arrows=(ImageView)findViewById(R.id.ic_arrows);
        outletList = (ListView) findViewById(R.id.listview);
        outlets = new ArrayList<>();
        new LoadDataS().execute();
        // Delayed display of UI elements
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

//.............................LOAD LIST DATA FOR COMMON SEARCH RESULT..................................................................

    private class LoadDataS extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        String responsefromserver = null;
        HashMap<String, String> nameValuePairs;

        // can use UI thread here
        protected void onPreExecute() {


            this.progressDialog = ProgressDialog.show(
                    OutLetActivity.this, "", " Loading .....");


        }

        protected Void doInBackground(Void... params) {

            try {
                try {


                    nameValuePairs = new HashMap<String, String>();
                    SendRequestServer req = new SendRequestServer();
                    String url1 = "outlets.php";

                    responsefromserver = req.requestSender(url1, nameValuePairs, OutLetActivity.this);


                    try {
                        jArray = new JSONArray(responsefromserver);
                        JSONObject json_data = null;
                        for (int i = 0; i < jArray.length(); i++) {
                            json_data = jArray.getJSONObject(i);
                            String name = json_data.getString("outlet_name");
                            int id = json_data.getInt("id");
                            String location = json_data.getString("location");
                            String image = json_data.getString("image");
                            //model class of outletlist
                            outLetModal = new OutLetModal(id, name, location, image);
                            outlets.add(outLetModal);

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
                //setting adapter
                adapter = new OutletAdapter1(OutLetActivity.this, outlets);
                outletList.setAdapter(adapter);

            }


        }
    }
    @Override
    public void onBackPressed() {
        finish();
        moveTaskToBack(true);
        super.onBackPressed();
    }

}






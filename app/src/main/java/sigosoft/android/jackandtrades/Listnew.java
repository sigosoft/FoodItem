package sigosoft.android.jackandtrades;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by pooja on 4/26/2017.
 */

public class Listnew extends Activity {
    ListView lv;
    ArrayList<BeanClassForListView4> dataArray;
    JSONArray jArray;
    ImageView imsearch, search_back, ic_arrowsa, ic_arrows;
    EditText Edsearch;
    String category;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_listview);
        lv = (ListView) findViewById(R.id.listview);
        imsearch = (ImageView) findViewById(R.id.search);
        search_back = (ImageView) findViewById(R.id.search_back);
        ic_arrowsa = (ImageView) findViewById(R.id.ic_arrowsa);
        ic_arrows = (ImageView) findViewById(R.id.ic_arrows);
        Edsearch = (EditText) findViewById(R.id.searched);
        add = (Button) findViewById(R.id.addcart);

        Edsearch.setVisibility(View.GONE);
        search_back.setVisibility(View.GONE);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Listnew.this, CartActivity.class);
                startActivity(i);
            }
        });

        Intent p = getIntent();
        category = p.getStringExtra("name");
        dataArray = new ArrayList<>();
        new LoadDataS().execute();


        ic_arrowsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Listnew.this, CartActivity.class);
                startActivity(i);
            }
        });
        ic_arrows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Edsearch.setVisibility(View.GONE);
                search_back.setVisibility(View.GONE);
                imsearch.setVisibility(View.VISIBLE);
                ic_arrows.setVisibility(View.VISIBLE);
                ic_arrowsa.setVisibility(View.VISIBLE);

            }
        });

        imsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edsearch.setVisibility(View.VISIBLE);
                search_back.setVisibility(View.VISIBLE);
                imsearch.setVisibility(View.GONE);
                ic_arrows.setVisibility(View.GONE);
                ic_arrowsa.setVisibility(View.GONE);
            }
        });


    }

    private class LoadDataS extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        String responsefromserver = null;
        HashMap<String, String> namevaluePairs;

        protected void onPreExecute() {


            this.progressDialog = ProgressDialog.show(
                    Listnew.this, "", " Loading .....");


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
                    namevaluePairs.put("category", category);

                    SendRequestServer req = new SendRequestServer();
                    String url1 = "android_list_products.php";
                    responsefromserver = req.requestSender(url1, namevaluePairs, Listnew.this);

                    try {
                        jArray = new JSONArray(responsefromserver);
                        JSONObject json_data = null;
                        for (int i = 0; i < jArray.length(); i++) {
                            json_data = jArray.getJSONObject(i);
                            String name = json_data.getString("name");
                            String price = json_data.getString("price");
                            String image = json_data.getString("image");
                            String id = json_data.getString("id");
                            BeanClassForListView4 beanClassForListView4 = new BeanClassForListView4(image, name, price, id);
                            dataArray.add(beanClassForListView4);
                            //   arr.add(name);
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

    public void setListview() {
        final ListnewAdapter listnewAdapter = new ListnewAdapter(Listnew.this, dataArray);
        lv.setAdapter(listnewAdapter);
        Edsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = Edsearch.getText().toString().toLowerCase(Locale.getDefault());
                listnewAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
    }


}

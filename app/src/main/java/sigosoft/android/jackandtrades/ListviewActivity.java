package sigosoft.android.jackandtrades;//package com.example.dynex_000.fooditem;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.design.widget.NavigationView;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//
//import customfonts.MyTextView;
//
//
//public class ListviewActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener,CustomButtonListener {
//
//    private ListView listview;
//    private ImageView imsearch,ic_arrows,ic_arrowsa,search_back;
//MyTextView add_to_cart;
//    AutoCompleteTextView Edsearch;
//
// //   ProgressDialog pd ;
////    int number = 1;
////    Toolbar toolbar;
////    Typeface fonts1;
////    private int image;
////    private String title;
////    private String description;
////    private String price;
//    String category,pMa;
//    String title,qtty;
//
//
//    private SpecialAdapter4 sp;
//
//    private ArrayList<BeanClassForListView4> beanClassArrayList;
//    JSONArray jArray;
//
//
//   // private listViewAdapter listViewAdapter;
//
//    static ArrayList<String> list = new ArrayList<String>();
//    static ArrayList<String> listImage = new ArrayList<String>();
//    static ArrayList<String> listTitle = new ArrayList<String>();
//    static ArrayList<String> listMobile = new ArrayList<String>();
//    static ArrayList<String> listVehicleType = new ArrayList<String>();
//    static ArrayList<String> listBrandName = new ArrayList<String>();
//    static ArrayList<String> listEmail = new ArrayList<String>();
//    static ArrayList<String> listServiceType = new ArrayList<String>();
//    static ArrayList<String> listQnty = new ArrayList<String>();
//
//    String product;
//
//    String[] languages;
//
//
//   public static HashSet<String> listKey = new HashSet<>();
//    public static ArrayList<String> listPrice = new ArrayList<String>();
//
//   // public static HashMap<String, String> productImage;
//
//    public static HashMap<String, String> productQnty;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.contact_listview);
//      //  DynexoPrefManager D = new DynexoPrefManager();
//
//
//        productQnty = new HashMap<String, String>();
//        Intent p = getIntent();
//        category = p.getStringExtra("name");
//        imsearch = (ImageView)findViewById(R.id.search);
//        search_back =(ImageView)findViewById(R.id.search_back);
//        ic_arrowsa =(ImageView)findViewById(R.id.ic_arrowsa);
//        ic_arrows = (ImageView)findViewById(R.id.ic_arrows);
//        Edsearch =(AutoCompleteTextView)findViewById(R.id.searched);
//        listview = (ListView) findViewById(R.id.listview);
//        add_to_cart = (MyTextView)findViewById(R.id.add_to_cart);
//        Edsearch.setVisibility(View.GONE);
//        search_back.setVisibility(View.GONE);
//
//        productQnty.clear();
//        listKey.clear();
//        listPrice.clear();
//
//
//        add_to_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//                DynexoPrefManager q = new DynexoPrefManager();
//                pMa= q.getSavedMailId(ListviewActivity.this);
//               // Toast.makeText(getApplicationContext(), pMa, Toast.LENGTH_LONG).show();
//
//                new LoadDataph().execute();
////
//
//            }
//        });
//
//        //pd = ProgressDialog.show(ListviewActivity.this, "", "Please wait...", true);
//
//        Edsearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                product = Edsearch.getText().toString();
//                new LoadSearchData().execute();
////
//            }
//        });
//
//
//        ic_arrowsa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(ListviewActivity.this, Procnfm.class);
//                startActivity(i);
//            }
//        });
//
//        ic_arrows.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//
//        search_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Edsearch.setVisibility(View.GONE);
//                search_back.setVisibility(View.GONE);
//                imsearch.setVisibility(View.VISIBLE);
//                ic_arrows.setVisibility(View.VISIBLE);
//                ic_arrowsa.setVisibility(View.VISIBLE);
//
//            }
//        });
//
//        imsearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Edsearch.setVisibility(View.VISIBLE);
//                search_back.setVisibility(View.VISIBLE);
//                imsearch.setVisibility(View.GONE);
//                ic_arrows.setVisibility(View.GONE);
//                ic_arrowsa.setVisibility(View.GONE);
//            }
//        });
//
//
//
////        fonts1 = Typeface.createFromAsset(ListviewActivity.this.getAssets(),
////                "fonts/Roboto-Regular.ttf");
//
//        listTitle.clear();
//        list.clear();
//        listImage.clear();
//        listMobile.clear();
//        listVehicleType.clear();
//        listBrandName.clear();
//        listEmail.clear();
//        listServiceType.clear();
//
//
//        new LoadDataS().execute();
//
//
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        return false;
//    }
//
//    @Override
//    public void onButtonClickListener(int position, EditText editText, int value) {
//
//
//        int quantity = Integer.parseInt(editText.getText().toString());
//        quantity = quantity *value;
//        if(quantity<0)
//            quantity=0;
//        editText.setText(quantity);
//
//
//
//    }
//
//
////.............................LOAD LIST DATA FOR COMMON SEARCH RESULT..................................................................
//
//    private class LoadDataS extends AsyncTask<Void, Void, Void> {
//        private ProgressDialog progressDialog;
//        String responsefromserver = null;
//        HashMap<String, String> nameValuePairs;
//
//        @Override
//        // can use UI thread here
//        protected void onPreExecute() {
//            listTitle.clear();
//            list.clear();
//            listImage.clear();
//            listMobile.clear();
//            listVehicleType.clear();
//            listBrandName.clear();
//            listEmail.clear();
//            listServiceType.clear();
//
//            this.progressDialog = ProgressDialog.show(
//                    ListviewActivity.this, "", " Loading .....");
//
//
//
//        }
//
//        @Override
//        protected void onPostExecute(Void unused) {
//            this.progressDialog.dismiss();
//
//
//            if (responsefromserver == null) {
//                //serverDown();
//            } else if (responsefromserver.contains("<html>")) {
//                Log.e("ResponseLocation", responsefromserver);
//                // serverDown();
//                Toast.makeText(getApplicationContext(), "Server down....", Toast.LENGTH_LONG).show();
//            } else if (responsefromserver.equals("no_data")) {
//                Toast.makeText(getApplicationContext(), "There is no Such product....", Toast.LENGTH_LONG).show();
//                finish();
//
//            } else {
//
//
//
//                setListview();
////
////
//            }
//
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            // TODO Auto-generated method stub
//            // HTTP post
//
//            try {
//                try {
//
//
//                    nameValuePairs = new HashMap<String, String>();
//                    nameValuePairs.put("category", category);
//
//                    SendRequestServer req = new SendRequestServer();
//                    String url1 = "android_list_products.php";
//                    responsefromserver = req.requestSender(url1, nameValuePairs, ListviewActivity.this);
//
//
//                    try {
//                        jArray = new JSONArray(responsefromserver);
//                        JSONObject json_data = null;
//                        for (int i = 0; i < jArray.length(); i++) {
//                            json_data = jArray.getJSONObject(i);
//
//
//
//
//                            listTitle.add(json_data.getString("name"));
//                            listImage.add(json_data.getString("image"));
//                           // list.add(json_data.getString("id"));
//                            listVehicleType.add(json_data.getString("price"));
//
//
//
//
//                        }
//                    } catch (JSONException e) {
//                        Toast.makeText(getApplicationContext(), e.toString(),
//                                Toast.LENGTH_LONG).show();
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//    }
//
//
//    public void setListview()
//{
//    final String[] IMAGE = listImage.toArray(new String[listImage.size()]);
//    final String[] DESCRIPTION = listVehicleType.toArray(new String[list.size()]);
//    final String[] TITLE = listTitle.toArray(new String[listTitle.size()]);
//   // final String[] ID = list.toArray(new String[listTitle.size()]);
//   // languages = TITLE;
//
//        ArrayAdapter adapter = new
//        ArrayAdapter(this,android.R.layout.simple_list_item_1,TITLE);
//
//    Edsearch.setAdapter(adapter);
//    Edsearch.setThreshold(1);
//
//
//
//    beanClassArrayList = new ArrayList<BeanClassForListView4>();
//
//
//    for (int i = 0; i < TITLE.length; i++) {
//
//        BeanClassForListView4 beanClass = new BeanClassForListView4(IMAGE[i], TITLE[i], DESCRIPTION[i]);
//        beanClassArrayList.add(beanClass);
//
//    }
//
//    sp = new SpecialAdapter4(ListviewActivity.this, beanClassArrayList);
//
//    listview.setAdapter(sp);
//
//
//    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view,
//                                int position, long id) {
//            title = beanClassArrayList.get(position).getTitle();
//            EditText et=(EditText)view.findViewById(R.id.txt);
//            qtty=et.getText().toString();
//            Toast.makeText(getApplicationContext(),title+qtty,Toast.LENGTH_LONG).show();
//
//
//
//
//
//        }
//
//
//    });
//
//
//}
//
//
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
//
//
//
//
//
//
//
//
//
//    //.............................LOAD LIST DATA FOR COMMON SEARCH RESULT..................................................................
//
//    private class LoadSearchData extends AsyncTask<Void, Void, Void> {
//        private ProgressDialog progressDialog;
//        String responsefromserver = null;
//        HashMap<String, String> nameValuePairs;
//
//        @Override
//        // can use UI thread here
//        protected void onPreExecute() {
//            listTitle.clear();
//            list.clear();
//            listImage.clear();
//            listMobile.clear();
//            listVehicleType.clear();
//            listBrandName.clear();
//            listEmail.clear();
//            listServiceType.clear();
//            listQnty.clear();
//
//            this.progressDialog = ProgressDialog.show(
//                    ListviewActivity.this, "", " Loading .....");
//
//
//
//        }
//
//        @Override
//        protected void onPostExecute(Void unused) {
//            this.progressDialog.dismiss();
//
//
//            if (responsefromserver == null) {
//                //serverDown();
//            } else if (responsefromserver.contains("<html>")) {
//                Log.e("ResponseLocation", responsefromserver);
//                // serverDown();
//            } else if (responsefromserver.equals("no_data")) {
//                Toast.makeText(getApplicationContext(), "There is No Such Products....", Toast.LENGTH_LONG).show();
//                finish();
//
//            } else {
//
//
//
//                setListview();
////
////
//            }
//
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            // TODO Auto-generated method stub
//            // HTTP post
//
//            try {
//                try {
//
//
//                    nameValuePairs = new HashMap<String, String>();
//                    nameValuePairs.put("category", category);
//                    nameValuePairs.put("product", product);
////                    nameValuePairs.put("area", sArea);
//
//                    SendRequestServer req = new SendRequestServer();
//                    String url1 = "android_list_products_by_search.php";
//                    responsefromserver = req.requestSender(url1, nameValuePairs, ListviewActivity.this);
//
//
//                    try {
//                        jArray = new JSONArray(responsefromserver);
//                        JSONObject json_data = null;
//                        for (int i = 0; i < jArray.length(); i++) {
//                            json_data = jArray.getJSONObject(i);
//
//
//
//
//                            listTitle.add(json_data.getString("name"));
//
//                            listImage.add(json_data.getString("image"));
//                            list.add(json_data.getString("id"));
//                            listVehicleType.add(json_data.getString("price"));
//                            listQnty.add("0");
//
//
//
//
//                        }
//                    } catch (JSONException e) {
//                        Toast.makeText(getApplicationContext(), e.toString(),
//                                Toast.LENGTH_LONG).show();
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//    }
//
//
//    public void call(String a,String b,String c)
//    {
//        Intent i=new Intent(ListviewActivity.this,Single_view_Search_Result.class);
//         i.putExtra("name", a);
//           i.putExtra("image",b);
//           i.putExtra("price",c);
//
//        startActivity(i);
//      //  finish();
//    }
//
//
//
//
//
//
//
//
//
////.............................Load to shopping bag..................................................................
//
//    public class LoadDataph extends AsyncTask<Void, Void, Void> {
//        private ProgressDialog progressDialog;
//        String responsefromserver = null;
//        HashMap<String, String> nameValuePairs;
//        @Override
//        // can use UI thread here
//        protected void onPreExecute() {
//
//            this.progressDialog = ProgressDialog.show(
//                    ListviewActivity.this, "", " Loading .....");
//
//        }
//
//        @Override
//        protected void onPostExecute(Void unused) {
//            this.progressDialog.dismiss();
//
//
//            if (responsefromserver == null) {
//                //serverDown();
//            }
//            else if (responsefromserver.contains("<html>")) {
//                Log.e("ResponseLocation", responsefromserver);
//                // serverDown();
//            }
//            else if (responsefromserver.equals("no_data")) {
//                //	Toast.makeText(getApplicationContext(), "There is No Request Available....", Toast.LENGTH_LONG).show();
//
//            }
//            else{
//
//                Intent k = new Intent(ListviewActivity.this, Procnfm.class);
//                startActivity(k);
//                finish();
//
//            }
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            // TODO Auto-generated method stub
//            // HTTP post
//
//            try {
//                try {
//                    final String[] KEYS = listKey.toArray(new String[listKey.size()]);
//
//                    int p;
//
//                   for(p=0;p<=listKey.size();p++)
//                   {
//                       String qnty_to_buy= productQnty.get(KEYS[p]);
//                      // String im_url = productImage.get(KEYS[p]);
//                       String product_to_buy= KEYS[p];
//                       String price_to_buy= listPrice.get(p);
//
//                       int pq =Integer.parseInt(price_to_buy)*Integer.parseInt(qnty_to_buy);
//                       String aq = pq+"";
//
//
//                    nameValuePairs = new HashMap<String, String>();
//                    nameValuePairs.put("user_mail", pMa);
//                    nameValuePairs.put("prod_name", product_to_buy);
//                    nameValuePairs.put("qnty", qnty_to_buy);
//                    nameValuePairs.put("price", aq);
//                    nameValuePairs.put("status", "wait");
//                     //nameValuePairs.put("image",im_url);
//
//                    SendRequestServer req = new SendRequestServer();
//                    String url1 = "android_insert_shopping_bag2.php";
//                    responsefromserver = req.requestSender(url1, nameValuePairs, ListviewActivity.this);
//                }
//
//
//                    try {
//                        jArray = new JSONArray(responsefromserver);
//                        JSONObject json_data = null;
//                        for (int i = 0; i < jArray.length(); i++) {
//                            json_data = jArray.getJSONObject(i);
//
////
//
//
//
//
//                        }
//                    } catch (JSONException e) {
//                        Toast.makeText(getApplicationContext(), e.toString(),
//                                Toast.LENGTH_LONG).show();
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//    }
//}

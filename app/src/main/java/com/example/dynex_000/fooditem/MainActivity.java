package com.example.dynex_000.fooditem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

   // ProgressDialog pd ;


    ImageView ic_arrows,ic_arrowsa;
    Typeface fonts1;
    private SpecialAdapter sp;
    private ListView listview;
    private ArrayList<BeanClassForListView> beanClassArrayList;
    JSONArray jArray;
    DrawerLayout mDrawerLayout;


    static ArrayList<String> list = new ArrayList<String>();
    static ArrayList<String> listImage = new ArrayList<String>();
    static ArrayList<String> listTitle = new ArrayList<String>();
    static ArrayList<String> listMobile = new ArrayList<String>();
    static ArrayList<String> listVehicleType = new ArrayList<String>();
    static ArrayList<String> listBrandName = new ArrayList<String>();
    static ArrayList<String> listEmail = new ArrayList<String>();
    static ArrayList<String> listServiceType = new ArrayList<String>();

    boolean doubleBackToExitPressedOnce = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //pd = ProgressDialog.show(MainActivity.this, "", "Please wait...", true);
       // pd.setCancelable(true);

        ic_arrowsa = (ImageView)findViewById(R.id.ic_arrowsa);
        ic_arrowsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Procnfm.class);
                startActivity(i);
            }
        });

        ic_arrows =(ImageView)findViewById(R.id.ic_arrows);
        ic_arrows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Registration.class);
                startActivity(i);
//                mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
//                mDrawerLayout.openDrawer(Gravity.LEFT);

            }
        });

        fab.setVisibility(View.GONE);
        listview = (ListView) findViewById(R.id.listview);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View header = navigationView.inflateHeaderView(R.layout.nav_header_main);

        navigationView.removeHeaderView(navigationView.getHeaderView(0));
        String status = NetCheck.getConnectivityStatusString(getBaseContext());
        if(status.compareTo("Not connected to Internet")==0)
        {
            Toast.makeText(this, "Your Internet connection is unstable, Please try again later", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(MainActivity.this, NoCunnectionPage.class);
            startActivity(i);

        }


        new LoadDataS().execute();


           }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
      if (id == R.id.nav_login) {

          String status = NetCheck.getConnectivityStatusString(getBaseContext());
          if(status.compareTo("Not connected to Internet")==0)
          {
              Toast.makeText(this, "Your Internet connection is unstable, Please try again later", Toast.LENGTH_SHORT).show();

          }

          else {
              Intent i = new Intent(MainActivity.this, Procnfm.class);
              startActivity(i);
          }
        } else if  (id == R.id.nav_registration) {

          AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
          adb.setTitle("Exit").setMessage("Do you want to really exit ?");
          adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                  Intent a = new Intent(Intent.ACTION_MAIN);
                  a.addCategory(Intent.CATEGORY_HOME);
                  a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  startActivity(a);
                  finish();
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


        }  else if (id == R.id.nav_help) {


          DynexoPrefManager D = new DynexoPrefManager();

          D.saveMailId(null,MainActivity.this);
          Intent i = new Intent(MainActivity.this, login2.class);
          startActivity(i);

          finish();



        }   else if (id == R.id.nav_about) {
//          Intent i=new Intent(MainActivity.this,About.class);
//          startActivity(i);
        }

      else if (id == R.id.nav_profile) {

          String status = NetCheck.getConnectivityStatusString(getBaseContext());
          if(status.compareTo("Not connected to Internet")==0)
          {
              Toast.makeText(this, "Your Internet connection is unstable, Please try again later", Toast.LENGTH_SHORT).show();

          }

          else {
              Intent i = new Intent(MainActivity.this, History.class);
              startActivity(i);
          }




      }
      else if (id == R.id.nav_share) {

          Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
          sharingIntent.setType("text/plain");


          sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Call Driver");
          String sAux = "\nLet me recommend you this application\n\n";
          sAux = sAux + "https://play.google.com/store/apps/details?id=com.dynexo.bloodBank";
          sharingIntent.putExtra(Intent.EXTRA_TEXT, sAux);
          //  startActivity(Intent.createChooser(sharingIntent, "choose one"));
          startActivity(Intent.createChooser(sharingIntent, "Share via"));


          return true;

        //  Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
      }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//.............................LOAD LIST DATA FOR COMMON SEARCH RESULT..................................................................

    private class LoadDataS extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        String responsefromserver = null;
        HashMap<String, String> nameValuePairs;

        @Override
        // can use UI thread here
        protected void onPreExecute() {
            listTitle.clear();
            list.clear();
            listImage.clear();
            listMobile.clear();
            listVehicleType.clear();
            listBrandName.clear();
            listEmail.clear();
            listServiceType.clear();

            this.progressDialog = ProgressDialog.show(
                    MainActivity.this, "", " Loading .....");



        }

        @Override
        protected void onPostExecute(Void unused) {
            this.progressDialog.dismiss();


            if (responsefromserver == null) {
                //serverDown();
            } else if (responsefromserver.contains("<html>")) {
                Log.e("ResponseLocation", responsefromserver);
                // serverDown();
            } else if (responsefromserver.equals("no_data")) {
                Toast.makeText(getApplicationContext(), "There is no Products....", Toast.LENGTH_LONG).show();

            } else {

                //  Toast.makeText(getApplicationContext(), "Success....", Toast.LENGTH_LONG).show();

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
//                    nameValuePairs.put("state", "Kerala");
//                    nameValuePairs.put("district", "Calicut");
//                    nameValuePairs.put("area", "Feroke");

                    SendRequestServer req = new SendRequestServer();
                    String url1 = "android_list_category.php";
                    responsefromserver = req.requestSender(url1, nameValuePairs, MainActivity.this);

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




                            listTitle.add(json_data.getString("name"));
                            listImage.add(json_data.getString("image_url"));
                            list.add(json_data.getString("id"));
                            listVehicleType.add(json_data.getString("discription"));
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


    public void setListview()
    {
        final String[] IMAGE = listImage.toArray(new String[listImage.size()]);
        final String[] DESCRIPTION = listVehicleType.toArray(new String[list.size()]);
        final String[] TITLE = listTitle.toArray(new String[listTitle.size()]);


        beanClassArrayList = new ArrayList<BeanClassForListView>();


        for (int i = 0; i < TITLE.length; i++) {

            BeanClassForListView beanClass = new BeanClassForListView(IMAGE[i], TITLE[i], DESCRIPTION[i]);
            beanClassArrayList.add(beanClass);

        }

        sp = new SpecialAdapter(MainActivity.this, beanClassArrayList);

        listview.setAdapter(sp);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {



               // Toast.makeText(MainActivity.this, "Entered " +TITLE[position]  + " and password entered is", Toast.LENGTH_SHORT).show();
                //w(id);


                Intent i=new Intent(MainActivity.this,ListviewActivity.class);
                i.putExtra("name", listTitle.get(position));
//                i.putExtra("mobile", listMobile.get(position));
//                i.putExtra("area",sArea );
//
//                i.putExtra("vType",listVehicleType.get(position));
//                i.putExtra("state", sState);
//                i.putExtra("district", sDistrict);
//                i.putExtra("serviceType",list.get(position) );
//
//                i.putExtra("brandName", listBrandName.get(position));
//                i.putExtra("email", listEmail.get(position));
//                i.putExtra("image",listImage.get(position));
                startActivity(i);





            }


        });

//        Thread timer=new Thread()
//        {
//
//            public void run()
//            {
//                try {
//
//                    pd.show();
//
//                    sleep(5000);
//
//
//                } catch (Exception e) {
//                    // TODO: handle exception
//                    e.printStackTrace();
//                }
//
//                finally
//                {
//
//
//
//                    pd.dismiss();
//
//
//
//
//                }
//            }
//
//        };
//
//        timer.start();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // super.onBackPressed();

            if (doubleBackToExitPressedOnce) {

                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
                finish();

            }

            this.doubleBackToExitPressedOnce = true;

            Toast.makeText(getApplicationContext(), " Press Back again to Exit ", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);

        }
    }

}

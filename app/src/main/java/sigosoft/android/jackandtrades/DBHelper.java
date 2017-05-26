package sigosoft.android.jackandtrades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by pooja on 4/24/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "jackandtrades";
    public static final String TABLE_NAME = "tbl_cart";
    public static final String FIELD1 = "cid";
    public static final String FIELD2 = "id";
    public static final String FIELD3 = "title";
    public static final String FIELD4 = "image";
    public static final String FIELD5 = "price";
    public static final String FIELD6 = "qty";
    public static final String FIELD7 = "unit_price";
    public static final String FIELD8 = "outle_id";

    Context context;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry = " CREATE TABLE " + TABLE_NAME + "(" + FIELD1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FIELD2 + " TEXT, " + FIELD3 + " TEXT ," + FIELD4 + " TEXT," + FIELD5 + " TEXT," + FIELD6 + " TEXT," + FIELD7 + " TEXT," + FIELD8 + " TEXT) ";
        db.execSQL(qry);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String qr = " DROP TABLE IF  EXISTS " + TABLE_NAME;
        db.execSQL(qr);
    }

    public void insert(BeanClassForListView4 model) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FIELD1, model.getCart_id());
        cv.put(FIELD2, model.getId());
        cv.put(FIELD3, model.getTitle());
        cv.put(FIELD4, model.getImage());
        cv.put(FIELD5, model.getDescription());
        cv.put(FIELD6, model.getQty());
        cv.put(FIELD7, model.getUnitPrice());
        cv.put(FIELD8, model.getOutletID());

        db.insert(TABLE_NAME, null, cv);

    }
    public void update(BeanClassForListView4 model) {
        int aa= Integer.parseInt(model.getCart_id());
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FIELD1, model.getCart_id());
        cv.put(FIELD2, model.getId());
        cv.put(FIELD3, model.getTitle());
        cv.put(FIELD4, model.getImage());
        cv.put(FIELD5, model.getDescription());
        cv.put(FIELD6, model.getQty());
        cv.put(FIELD7, model.getUnitPrice());
        cv.put(FIELD8, model.getOutletID());
        db.update(TABLE_NAME, cv,"id="+aa,null);

    }

    public String getData() {
        String data = "";
        StringBuffer sb = new StringBuffer();
        SQLiteDatabase db = getReadableDatabase();
        String qry = " SELECT * FROM  " + TABLE_NAME;
        Cursor c = db.rawQuery(qry, null);

        while (c.moveToNext()) {
            sb.append("cid:" + c.getString(0) + "\n");
            sb.append("id" + c.getString(1) + "\n");
            sb.append("title:" + c.getString(2) + "\n");
            sb.append("image" + c.getString(3) + "\n");
            sb.append("price:" + c.getString(4) + "\n");
            sb.append("qty:" + c.getString(5) + "\n");
            sb.append("unitprice:" + c.getString(6) + "\n");
            sb.append("outletid:" + c.getString(7) + "\n");
        }
        data = sb.toString();

        return data;

    }

    public ArrayList<BeanClassForListView4> getOrders() {
        ArrayList<BeanClassForListView4> orderList = new ArrayList<>();
        try {
            DynexoPrefManager de = new DynexoPrefManager();
            String outletID = de.showSavedResponse("outletID", context);
            SQLiteDatabase db = getReadableDatabase();
           // String select_qry = " SELECT * FROM " + TABLE_NAME +" WHERE outle_id ="+outletID;
            String select_qry = " SELECT * FROM " + TABLE_NAME +" WHERE outle_id ="+outletID;

            Cursor cursor = db.rawQuery(select_qry, null);
            if (cursor != null) {

                while (cursor.moveToNext()) {

                    BeanClassForListView4 model = new BeanClassForListView4();
                    model.setCart_id(String.valueOf(cursor.getInt(0)));
                    model.setId(cursor.getString(1));
                    model.setTitle(cursor.getString(2));
                    model.setImage(cursor.getString(3));
                    model.setDescription(cursor.getString(4));
                    model.setQty(cursor.getString(5));
                    model.setUnitprice(cursor.getString(6));
                    model.setOutletID(cursor.getString(7));

                    Log.d("all", orderList.toString());

                    orderList.add(model);


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderList;
    }

    public int updateqty(BeanClassForListView4 m) {

        // TODO Auto-generated method stub
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value1 = new ContentValues();
        value1.put("qty", m.getQty());
        value1.put("price", m.getDescription());

        return db.update(TABLE_NAME, value1, "id" + "=?", new String[]{String.valueOf(m.getId())});

    }

    public boolean checkidpresent(String pid) {

        boolean condition = false;

        SQLiteDatabase db = this.getReadableDatabase();
        String select_qry = " SELECT * FROM " + TABLE_NAME + " WHERE " + FIELD2 + " ='" + pid + "'";
        Log.e(select_qry, select_qry);
        Cursor cursor = db.rawQuery(select_qry, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                condition = true;
            }
        } else {
            condition = false;
        }
        return condition;
    }

    public void clearOrders() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);

    }

    public void clearSingeOrders(String title) {
        SQLiteDatabase db = getWritableDatabase();
        String KEY_NAME = "title";
        //  String value="Juice";

        //     db.delete(TABLE_NAME, null, null);
        //   db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + "id" + "= '" + order + "'");
        //   db.delete(TABLE_NAME, KEY_NAME+"="+"juice", null);
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + KEY_NAME + "='" + title + "'");
        db.close();


    }

    public ArrayList<HistoryModel> getOrders1() {
        ArrayList<HistoryModel> orderList = new ArrayList<>();
        try {

            SQLiteDatabase db = getReadableDatabase();
            String select_qry = " SELECT * FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(select_qry, null);
            if (cursor != null) {

                while (cursor.moveToNext()) {


                    HistoryModel historyModel = new HistoryModel();
                    historyModel.setCart_id(String.valueOf(cursor.getInt(0)));
                    historyModel.setId(cursor.getString(1));
                    historyModel.setTitle(cursor.getString(2));
                    historyModel.setImage(cursor.getString(3));
                    historyModel.setDescription(cursor.getString(4));
                    historyModel.setQty(cursor.getString(5));

                    Log.d("all", orderList.toString());

                    orderList.add(historyModel);


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return orderList;
    }

}


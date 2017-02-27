package com.example.dynex_000.fooditem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import customfonts.MyTextView1;

/**
 * Created by admin on 3/22/2016.
 */
public class listViewAdapter2 extends BaseAdapter {
    private Context context;
    private ArrayList<BeanClassForListView2> beanClassArrayList;


    public listViewAdapter2(Context context, ArrayList<BeanClassForListView2> beanClassArrayList) {
        this.context = context;
        this.beanClassArrayList = beanClassArrayList;

    }

    @Override
    public int getCount() {
        return beanClassArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return beanClassArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        final ViewHoder viewHoder;
        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listview2, parent, false);

            viewHoder = new ViewHoder();
            viewHoder.banar1 = (ImageView) convertView.findViewById(R.id.banar1);
            viewHoder.txt = (EditText) convertView.findViewById(R.id.txt);
            viewHoder.minus = (ImageButton) convertView.findViewById(R.id.minus);
            viewHoder.plus = (ImageButton) convertView.findViewById(R.id.plus);
            viewHoder.title = (MyTextView1) convertView.findViewById(R.id.title);
            viewHoder.description = (MyTextView1) convertView.findViewById(R.id.description);

            viewHoder.dlt = (ImageButton) convertView.findViewById(R.id.dlt);
            convertView.setTag(viewHoder);

        }

        else

        {

            viewHoder = (ViewHoder) convertView.getTag();
        }


        final BeanClassForListView2 beanClass = (BeanClassForListView2) getItem(position);

        String base= beanClass.getImage();
            viewHoder.title.setText(beanClass.getTitle());
            viewHoder.description.setText(base);

        viewHoder.txt.setText(beanClass.getDescription());

        String base2= beanClass.getprc();
        // byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
        // viewHoder.banar1.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length) );

        Picasso.with(context).load(base2).into(viewHoder.banar1);

        viewHoder.dlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder adb = new AlertDialog.Builder(context);
                adb.setTitle("Remove !").setMessage("Do you want to remove this item ?");
                adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int index =Integer.parseInt(position+"");
                        beanClassArrayList.remove(index);
                        notifyDataSetChanged();

                        DynexoPrefManager q = new DynexoPrefManager();
                        String ok = q.getDeleteProduct(context);
                        String o = beanClass.getTitle()+","+ok;
                        q.saveDeleteProduct(o,context);
                        String base = beanClass.getImage();
                        int t  =Integer.parseInt(Procnfm.ttl.getText().toString())-Integer.parseInt(base);
                        String tty =t+"";
                        Procnfm.ttl.setText(tty);


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




            }
        });

        viewHoder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                BeanClassForListView2 beanClass = (BeanClassForListView2) getItem(position);
                int q = Integer.parseInt(beanClass.getDescription()) + 1;
                String base = beanClass.getImage();
                int t  =Integer.parseInt(Procnfm.ttl.getText().toString())-Integer.parseInt(base);

                int old_qnty = Integer.parseInt(beanClass.getDescription());
                int s_amnt = Integer.parseInt(beanClass.getImage()) / old_qnty;
                int totl_p = q * s_amnt;
                String total_price = totl_p + "";
//
                String pt = q + "";
                beanClass.setDescription(pt);
                beanClass.setImage(total_price);
                viewHoder.txt.setText(beanClass.getDescription());
                viewHoder.description.setText(total_price);
                int ttp = t+totl_p;
                String tty =ttp+"";
                Procnfm.ttl.setText(tty);
                Procnfm.listp_name.add(beanClass.getTitle());
                Procnfm.listp_qnty.add(beanClass.getDescription());
                Procnfm.listp_amnt.add(beanClass.getImage());
//
            }
        });


        viewHoder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String b =viewHoder.txt.getText().toString();
                int a = Integer.parseInt(b);
                if( a>1) {


                    BeanClassForListView2 beanClass = (BeanClassForListView2) getItem(position);
                    int q = Integer.parseInt(beanClass.getDescription()) - 1;
                    String base = beanClass.getImage();
                    int t  =Integer.parseInt(Procnfm.ttl.getText().toString())-Integer.parseInt(base);

                    int old_qnty = Integer.parseInt(beanClass.getDescription());
                    int s_amnt = Integer.parseInt(beanClass.getImage()) / old_qnty;
                    int totl_p = q * s_amnt;
                    String total_price = totl_p + "";
//
                    String pt = q + "";
                    beanClass.setDescription(pt);
                    beanClass.setImage(total_price);
                    viewHoder.txt.setText(beanClass.getDescription());
                    viewHoder.description.setText(total_price);
                    int ttp = t+totl_p;
                    String tty =ttp+"";
                     Procnfm.ttl.setText(tty);

                    Procnfm.listp_name.add(beanClass.getTitle());
                    Procnfm.listp_qnty.add(beanClass.getDescription());
                    Procnfm.listp_amnt.add(beanClass.getImage());
//
                }
                // Toast.makeText(context,"Clicked", Toast.LENGTH_SHORT).show();

            }
        });
//        byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
//        viewHoder.banar1.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length) );

        return convertView;

    }





//    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;


    private class ViewHoder{

        ImageView banar1;
        MyTextView1 title;
        MyTextView1 description;
        ImageButton minus,plus,dlt;
        EditText txt;



    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


    public void dlete()
    {

    }



}

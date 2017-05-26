package sigosoft.android.jackandtrades;//package com.example.dynex_000.fooditem;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.PorterDuff;
//import android.graphics.PorterDuffXfermode;
//import android.graphics.Rect;
//import android.graphics.RectF;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//
///**
// * Created by admin on 3/22/2016.
// */
//public class listViewAdapter4 extends BaseAdapter {
//    private Context context;
//    private ArrayList<BeanClassForListView4> beanClassArrayList;
//    Intent i;
//    String prc,quant;
//
//    public ArrayList quantity = new ArrayList();
//    CustomButtonListener customButtonListener;
//
//
//    public listViewAdapter4(Context context, ArrayList<BeanClassForListView4> beanClassArrayList,String price,String qt) {
//        this.context = context;
//        this.beanClassArrayList = beanClassArrayList;
//        this.prc=price;
//        this.quant=qt;
//
//        for(int i =0; i< beanClassArrayList.size();i++)
//        {
//            quantity.add(0);
//            //quantity[i]=0;
//        }
//    }
//
//    public void setCustomButtonListener(CustomButtonListener customButtonListner)
//    {
//        this.customButtonListener = customButtonListner;
//    }
//
//    @Override
//    public int getCount() {
//        return beanClassArrayList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return beanClassArrayList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//
//
//        final ViewHoder viewHoder;
//        if (convertView == null) {
//
//            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
//            convertView = layoutInflater.inflate(R.layout.listview4, parent, false);
//
//            viewHoder = new ViewHoder();
//
//            viewHoder.banar1 = (ImageView) convertView.findViewById(R.id.banar1);
//            viewHoder.minus  = (ImageButton) convertView.findViewById(R.id.minus);
//            viewHoder.plus  = (ImageButton) convertView.findViewById(R.id.plus);
//            viewHoder.title = (TextView) convertView.findViewById(R.id.txt_title);
//            viewHoder.txt  = (EditText) convertView.findViewById(R.id.txt);
//            viewHoder.id=(TextView)convertView.findViewById(R.id.id);
//            viewHoder.description = (TextView) convertView.findViewById(R.id.description);
//
//
//
//            convertView.setTag(viewHoder);
//
//        }
//
//        else
//
//        {
//
//            viewHoder = (ViewHoder) convertView.getTag();
//        }
//
//
//        BeanClassForListView4 beanClass = (BeanClassForListView4) getItem(position);
//            viewHoder.id.setText(beanClass.getId());
//            viewHoder.title.setText(beanClass.getTitle());
//            viewHoder.description.setText(beanClass.getDescription());
//          viewHoder.txt.setText("0");
//
//
//
//        String base= beanClass.getImage();
//
////        byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
////        viewHoder.banar1.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length) );
//        Picasso.with(context).load(base).into(viewHoder.banar1);
//
//
//        viewHoder.plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int quantity = Integer.parseInt( viewHoder.txt.getText().toString());
//                int q =quantity+1;
//                String qty =q + "";
//                quant=String.valueOf(q);
//                Log.d("qty>>>>>>>>>>>",quant);
//                viewHoder.txt.setText(quant);
//                BeanClassForListView4 beanClass = (BeanClassForListView4) getItem(position);
//                String price = beanClass.getDescription();
//
//                String key =beanClass.getTitle();
//
//               // String base= beanClass.getImage();
//               // String im_url = ListviewActivity.listImage.get(position);
//
//                int p =Integer.parseInt(price);
//                int t = p*q;
//                String pricee  = t+"";
//                prc=String.valueOf(t);
//                Log.d("price>>>>>>",prc);
//                viewHoder.description.setText(prc);
//
//
//
//
////                ListviewActivity.listPrice.add(pr);
////                ListviewActivity.productQnty.put(key,qt);
////               // ListviewActivity.productImage.put(key,base);
////                ListviewActivity.listKey.add(key);
//               // Toast.makeText(context,"Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        viewHoder.minus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                int quantity = Integer.parseInt( viewHoder.txt.getText().toString());
//                if(quantity>0) {
//                    int q = quantity - 1;
//                   String qty = q + "";
//                    quant=String.valueOf(q);
//                    Log.d("qty>>>>>>>>>>>",quant);
//                    viewHoder.txt.setText(quant);
//
//
//                    BeanClassForListView4 beanClass = (BeanClassForListView4) getItem(position);
//                    String price = beanClass.getDescription();
//                   // ListviewActivity.listPrice.add(price);
//                   // String im_url =beanClass.getImage();
//
//                    int p =Integer.parseInt(price);
//                    int t = p*q;
//                  String  pricee  = t+"";
//                    prc=String.valueOf(t);
//                    Log.d("price>>>>>>",prc);
//                    viewHoder.description.setText(prc);
////                    List.listprice.add(pr);
////                    List.listqty.add(qt);
////                    ListviewActivity.listPrice.add(pr);
////                    String key =beanClass.getTitle();
////                    ListviewActivity.listKey.add(key);
////                   // Toast.makeText(context,price, Toast.LENGTH_SHORT).show();
////                  //  ListviewActivity.productImage.put(key,im_url);
////
////                    ListviewActivity.productQnty.put(key,qt);
//                }
////                String y = quantity+"";
////                viewHoder.txt.setText(y);
//
//            }
//        });
//
//
//        return convertView;
//
//    }
//
//
////    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
////        this.onItemClickListener = onItemClickListener;
//
//
//    private class ViewHoder{
//
//        ImageView banar1;
//        TextView title,id;
//        TextView description;
//        ImageButton minus,plus;
//        EditText txt;
//
//
//    }
//
//    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
//        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
//                .getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(output);
//
//        final int color = 0xff424242;
//        final Paint paint = new Paint();
//        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//        final RectF rectF = new RectF(rect);
//        final float roundPx = pixels;
//
//        paint.setAntiAlias(true);
//        canvas.drawARGB(0, 0, 0, 0);
//        paint.setColor(color);
//        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
//
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(bitmap, rect, rect, paint);
//
//        return output;
//    }
//
//
//
//}

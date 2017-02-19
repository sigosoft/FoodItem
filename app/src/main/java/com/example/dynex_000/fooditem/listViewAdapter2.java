package com.example.dynex_000.fooditem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

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


        ViewHoder viewHoder;
        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listview2, parent, false);

            viewHoder = new ViewHoder();

            viewHoder.banar1 = (TextView) convertView.findViewById(R.id.title2);
            viewHoder.title = (TextView) convertView.findViewById(R.id.title);
            viewHoder.description = (TextView) convertView.findViewById(R.id.description);

            convertView.setTag(viewHoder);

        }

        else

        {

            viewHoder = (ViewHoder) convertView.getTag();
        }


        BeanClassForListView2 beanClass = (BeanClassForListView2) getItem(position);

        String base= beanClass.getImage();
            viewHoder.title.setText(beanClass.getTitle());
        viewHoder.banar1.setText(beanClass.getDescription());

                viewHoder.description.setText(base);

//        byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
//        viewHoder.banar1.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length) );

        return convertView;

    }





//    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;


    private class ViewHoder{

        TextView banar1;
        TextView title;
        TextView description;


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



}

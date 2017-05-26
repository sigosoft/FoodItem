package sigosoft.android.jackandtrades;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by admin on 3/22/2016.
 */
public class listViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<BeanClassForListView> beanClassArrayList;


    public listViewAdapter(Context context, ArrayList<BeanClassForListView> beanClassArrayList) {
        this.context = context;
        this.beanClassArrayList = beanClassArrayList;


    }
    ProgressDialog progressDialog;
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
            convertView = layoutInflater.inflate(R.layout.listview, parent, false);

            viewHoder = new ViewHoder();

            viewHoder.banar1 = (ImageView) convertView.findViewById(R.id.banar1);
            viewHoder.title = (TextView) convertView.findViewById(R.id.title);
            viewHoder.description = (TextView) convertView.findViewById(R.id.description);
            viewHoder.lin = (LinearLayout) convertView.findViewById(R.id.contact);

            convertView.setTag(viewHoder);

        }

        else

        {

            viewHoder = (ViewHoder) convertView.getTag();
        }

//modelclass for listview
        BeanClassForListView beanClass = (BeanClassForListView) getItem(position);

            viewHoder.title.setText(beanClass.getTitle());
            viewHoder.description.setText(beanClass.getDescription());

        String base= beanClass.getImage();
       // byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
       // viewHoder.banar1.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length) );

        Picasso.with(context).load(base).into(viewHoder.banar1);

/*
        viewHoder.lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int id = beanClassArrayList.get(position).getId();
                Intent i=new Intent(context,Listnew.class);
                i.putExtra("name", position);
                context.startActivity(i);
              //  Toast.makeText(context, "AAaaaaaaaaaaaaaa", Toast.LENGTH_SHORT).show();
            }
        });
*/



        return convertView;

    }





//    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;


    private class ViewHoder{

        ImageView banar1;
        TextView title;
        TextView description;
LinearLayout lin;

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

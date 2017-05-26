package sigosoft.android.jackandtrades;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alexzh.circleimageview.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pooja on 4/26/2017.
 */

public class CartAdapter extends BaseAdapter {

    Context context;
    List<BeanClassForListView4> finalList = new ArrayList<>();
    BeanClassForListView4 foodItem = new BeanClassForListView4();
    LayoutInflater inflater;
    String id;
    CartAdapter adapter;
    Integer total_price = 0;


    public CartAdapter(Context context, List<BeanClassForListView4> items) {
        this.context = context;
        finalList = items;
        inflater = LayoutInflater.from(context);


    }


    @Override
    public int getCount() {
        return finalList.size();
    }

    @Override
    public Object getItem(int position) {
        return finalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView id, title, description, txt;
        CircleImageView image;
        ImageButton plus, minus, delete;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder viewHolder;
        foodItem = finalList.get(position);
        if (view == null) {
            view = inflater.inflate(R.layout.listview2, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.id = (TextView) view.findViewById(R.id.cart_id);
            viewHolder.title = (TextView) view.findViewById(R.id.txt_title);
            viewHolder.description = (TextView) view.findViewById(R.id.description);
            viewHolder.plus = (ImageButton) view.findViewById(R.id.plus);
            viewHolder.minus = (ImageButton) view.findViewById(R.id.minus);
            viewHolder.delete = (ImageButton) view.findViewById(R.id.dlt);
            viewHolder.image = (CircleImageView) view.findViewById(R.id.banar1);
            viewHolder.txt = (EditText) view.findViewById(R.id.txtqty);
       //     viewHolder.image.setBackgroundResource(R.mipmap.ic_launcher);
            //edit by praveen
            viewHolder.id.setText(String.valueOf(foodItem.getId()));
            viewHolder.title.setText(foodItem.getTitle());
            viewHolder.description.setText(String.valueOf(foodItem.getDescription()));
            viewHolder.txt.setText(String.valueOf(foodItem.getQty()));
            String base = foodItem.getImage();
          //  String base2 = "http://vignette3.wikia.nocookie.net/fantendo/images/c/c4/Project_Universal_Mario.png/revision/latest?cb=20130726165045";
            Picasso.with(context).load(base).into(viewHolder.image);
            view.setTag(viewHolder);

        } else
        {
            viewHolder = (ViewHolder) view.getTag();
        }


        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(viewHolder.txt.getText().toString());
                int q = quantity + 1;
                viewHolder.txt.setText(String.valueOf(q));
            //    Toast.makeText(context, String.valueOf(q), Toast.LENGTH_LONG).show();
                BeanClassForListView4 beanClass = (BeanClassForListView4) getItem(position);
            //    String price = beanClass.getDescription();
                String unitprice = beanClass.getUnitPrice();
                int p = Integer.parseInt(unitprice);
                int t = p * q;
                String pr = t + "";
                id = finalList.get(position).getId();
                DBHelper bn = new DBHelper(context);
                bn.updateqty(new BeanClassForListView4(String.valueOf(q), pr, id));
         //     Toast.makeText(context, "updated", Toast.LENGTH_LONG).show();
                total_price = total_price + t;
                viewHolder.description.setText(String.valueOf(t));
                Intent i=new Intent(context,CartActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(i);
                CartAdapter.this.notifyDataSetChanged();

            }
        });
        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(viewHolder.txt.getText().toString());
                if (quantity==1){
                    int q = quantity - 0;                }
                else {

                int q = quantity - 1;
                viewHolder.txt.setText(String.valueOf(q));
                BeanClassForListView4 beanclass = (BeanClassForListView4) getItem(position);
                String unitprice = beanclass.getUnitPrice();
                int p = Integer.parseInt(unitprice);
                int t = p * q;
                String pr = t + "";
                id = finalList.get(position).getId();
                DBHelper bn = new DBHelper(context);
                bn.updateqty(new BeanClassForListView4(String.valueOf(q), pr, id));
                viewHolder.description.setText(String.valueOf(t));
                Intent i=new Intent(context,CartActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(i);
                CartAdapter.this.notifyDataSetChanged();
            }}
        });
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure,You wanted to make delete");
                        alertDialogBuilder.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {

                                        String itemName = viewHolder.title.getText().toString();
                                        DBHelper bn = new DBHelper(context);
                                        String id = finalList.get(position).getId();
                                        String id2 = foodItem.getId();
                                        int orderID = Integer.parseInt(id);
                                        bn.clearSingeOrders(itemName);
                                        Intent i=new Intent(context,CartActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        context.startActivity(i);
                                    }
                                });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                CartAdapter.this.notifyDataSetChanged();

            }
        });

        return view;
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














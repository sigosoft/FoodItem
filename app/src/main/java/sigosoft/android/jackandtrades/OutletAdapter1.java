package sigosoft.android.jackandtrades;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by pooja on 4/7/2017.
 */

public class OutletAdapter1 extends BaseAdapter {
    private Context context;
    private ArrayList<OutLetModal> outletArrayList;
    ProgressDialog progressDialog;

    public OutletAdapter1(Context ctx, ArrayList<OutLetModal> outLetModalArrayList1) {
        context = ctx;
        outletArrayList = outLetModalArrayList1;

    }


    @Override
    public int getCount() {
        return outletArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return outletArrayList.get(position);
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
            convertView = layoutInflater.inflate(R.layout.outlet_list_2, parent, false);
            viewHoder = new ViewHoder();
            viewHoder.image = (ImageView) convertView.findViewById(R.id.banar1);
            viewHoder.lin = (LinearLayout) convertView.findViewById(R.id.lin);
            viewHoder.id = (TextView) convertView.findViewById(R.id.id);
            viewHoder.description = (TextView) convertView.findViewById(R.id.description);
            viewHoder.title = (TextView) convertView.findViewById(R.id.ttt);

            convertView.setTag(viewHoder);
        } else

        {

            viewHoder = (ViewHoder) convertView.getTag();
        }
        OutLetModal outLetModal = (OutLetModal) getItem(position);
        viewHoder.title.setText(outLetModal.getOutLet_name());
        viewHoder.description.setText(outLetModal.getLocation());
        viewHoder.id.setText(Integer.toString(outLetModal.getOutLet_id()));
        String base = outLetModal.getImage();
        String base2 = "http://vignette3.wikia.nocookie.net/fantendo/images/c/c4/Project_Universal_Mario.png/revision/latest?cb=20130726165045";
        String base3 = "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRHPjyunsGOtaFcATbniJXmoIW3zvHZXOZYUvMUyuAX1uRKFNWp";
        Picasso.with(context).load(base).into(viewHoder.image);

        viewHoder.lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String names = outletArrayList.get(position).getOutLet_name();
                String location = outletArrayList.get(position).getLocation();
                DynexoPrefManager dy = new DynexoPrefManager();
                dy.setResponse("outname", String.valueOf(names), context);
                dy.setResponse("location", String.valueOf(location), context);
                Intent i = new Intent(context, MainActivity.class);
                int o = outletArrayList.get(position).getOutLet_id();
                dy.setResponse("outletID", String.valueOf(o), context);
                i.putExtra("id", String.valueOf(o));
                context.startActivity(i);
            }
        });
        return convertView;
    }

    private class ViewHoder {

        ImageView image;
        TextView title;
        TextView description;
        TextView id;
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





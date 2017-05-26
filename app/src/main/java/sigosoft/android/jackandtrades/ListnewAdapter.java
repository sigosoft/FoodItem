package sigosoft.android.jackandtrades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by pooja on 4/26/2017.
 */

public class ListnewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<BeanClassForListView4> beanClassArrayList;
    String id, title, image, description, qty,price;
    DBHelper bn;

    private ArrayList<BeanClassForListView4> arraylist;

    public ListnewAdapter(Context context, ArrayList<BeanClassForListView4> dataArray) {
        this.context = context;
        this.beanClassArrayList = dataArray;
        this.arraylist = new ArrayList<BeanClassForListView4>();
        this.arraylist.addAll(dataArray);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHoder viewHoder;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listview4, parent, false);

            viewHoder = new ViewHoder();

            viewHoder.banar1 = (ImageView) convertView.findViewById(R.id.banar1);
            viewHoder.title = (TextView) convertView.findViewById(R.id.txt_title);
            viewHoder.description = (TextView) convertView.findViewById(R.id.description);
            viewHoder.pluscart = (ImageButton) convertView.findViewById(R.id.pluscart);
            viewHoder.minuscart = (ImageButton) convertView.findViewById(R.id.minuscart);
            viewHoder.textqty = (EditText) convertView.findViewById(R.id.txtqtycart);

            viewHoder.id = (TextView) convertView.findViewById(R.id.id);
            convertView.setTag(viewHoder);


        } else {
            viewHoder = (ViewHoder) convertView.getTag();
        }

        final BeanClassForListView4 beanClass = (BeanClassForListView4) getItem(position);
        viewHoder.title.setText(beanClass.getTitle());
        viewHoder.description.setText(beanClass.getDescription());
        viewHoder.id.setText(beanClass.getId());
        String base = beanClass.getImage();
        Picasso.with(context).load(base).into(viewHoder.banar1);

        viewHoder.pluscart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = beanClass.getId();
                title = beanClass.getTitle();
                image = beanClass.getImage();
                price = beanClass.getDescription();
                qty = "1";

                int quantity = Integer.parseInt(viewHoder.textqty.getText().toString());
                int q = quantity + 1;
                viewHoder.textqty.setText(String.valueOf(q));
                int p = Integer.parseInt(price);
                int t = p * q;
                String pr = t + "";
             //   id = arraylist.get(position).getId();

                bn = new DBHelper(context);
                if (bn.checkidpresent(id.toString())) {
                    DynexoPrefManager de = new DynexoPrefManager();
                    String outletID = de.showSavedResponse("outletID", context);
                    DBHelper bn = new DBHelper(context);
                    bn.updateqty(new BeanClassForListView4(String.valueOf(q), pr, id));
                    //   bn.update(new BeanClassForListView4(description, title, id, pr, qty, pr, outletID));

//                    Intent i = new Intent(context, CartActivity.class);
//                    context.startActivity(i);
                } else {
                    DynexoPrefManager de = new DynexoPrefManager();
                    String outletID = de.showSavedResponse("outletID", context);
                    bn = new DBHelper(context);
                    bn.insert(new BeanClassForListView4(id, title, image, pr, qty, pr, outletID));
                }
            }
        });

        viewHoder.minuscart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(viewHoder.textqty.getText().toString());
                if (quantity==0){
                    int q = quantity - 0;                }
                else {
                    description = beanClass.getId();
                    title = beanClass.getTitle();
                    id = beanClass.getImage();
                    image = beanClass.getDescription();
                    qty = "1";

                    int q = quantity - 1;
                    viewHoder.textqty.setText(String.valueOf(q));
                    int p = Integer.parseInt(image);
                    int t = p * q;
                    String pr = t + "";
                    bn = new DBHelper(context);
                    if (bn.checkidpresent(description.toString())) {
                        DBHelper bn = new DBHelper(context);
                        bn.updateqty(new BeanClassForListView4(String.valueOf(q), pr, description));
                    } else {
                        DynexoPrefManager de = new DynexoPrefManager();
                        String outletID = de.showSavedResponse("outletID", context);
                        bn = new DBHelper(context);
                        bn.insert(new BeanClassForListView4(description, title, id, image, qty, image, outletID));
                    }
                }
            }
        });

        return convertView;
    }


    private class ViewHoder {

        ImageView banar1;
        TextView title;
        TextView description;
        TextView add_to_cart;
        TextView id;
        ImageButton pluscart, minuscart;
        EditText textqty;


    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        beanClassArrayList.clear();
        if (charText.length() == 0) {
            beanClassArrayList.addAll(arraylist);
        } else {
            for (BeanClassForListView4 wp : arraylist) {
                if (wp.getTitle().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    beanClassArrayList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}

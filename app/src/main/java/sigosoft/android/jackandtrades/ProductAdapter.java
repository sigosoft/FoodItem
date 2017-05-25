package sigosoft.android.jackandtrades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pooja on 5/22/2017.
 */

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ProductModel> productModelArrayList;
 //  String id, title, image, description, qty;
    DBHelper bn;

  //  private ArrayList<BeanClassForListView4> arraylist;

    public ProductAdapter(Context context, ArrayList<ProductModel> dataArray) {
        this.context = context;
        this.productModelArrayList = dataArray;

    }
    @Override
    public int getCount() {
        return productModelArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return productModelArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHoder viewHoder;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listview8, parent, false);
            viewHoder = new ViewHoder();
            viewHoder.title = (TextView) convertView.findViewById(R.id.txt_title);
            convertView.setTag(viewHoder);
        } else{
            viewHoder = (ViewHoder) convertView.getTag();
        }
        ProductModel productModel = (ProductModel) getItem(i);
        viewHoder.title.setText(productModel.getTitle());

        return convertView;
    }

    private class ViewHoder {

        TextView title;


    }

}

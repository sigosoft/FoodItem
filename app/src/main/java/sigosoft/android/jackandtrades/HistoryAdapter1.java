package sigosoft.android.jackandtrades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pooja on 5/2/2017.
 */

public class HistoryAdapter1 extends BaseAdapter {
    private Context context;
    private ArrayList<HistoryModel> beanClassArrayList;


    public HistoryAdapter1(Context context, ArrayList<HistoryModel> dataarray) {
        this.context=context;
        this.beanClassArrayList=dataarray;
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
            convertView = layoutInflater.inflate(R.layout.listview3, parent, false);
            viewHoder = new ViewHoder();
            viewHoder.title = (TextView) convertView.findViewById(R.id.title2);
            viewHoder.cartid = (TextView) convertView.findViewById(R.id.title11);
            viewHoder.price=(TextView)convertView.findViewById(R.id.description);
            convertView.setTag(viewHoder);


        } else {
          viewHoder=(ViewHoder)convertView.getTag();
        }
        final HistoryModel model = (HistoryModel) getItem(position);
        viewHoder.title.setText(model.getTitle());
        viewHoder.cartid.setText(model.getCart_id());
        viewHoder.price.setText(model.getDescription());

        viewHoder.cartid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, "dsfsdf", Toast.LENGTH_SHORT).show();
               String SlNo= viewHoder.cartid.getText().toString();
                //new History2.LoadHistory().execute();

            }
        });
        return convertView;
    }
        private class ViewHoder {

            TextView title, cartid, price;


        }


}


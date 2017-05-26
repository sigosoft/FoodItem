package sigosoft.android.jackandtrades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pooja on 5/10/2017.
 */
public class HistoryDetailsAdapter extends BaseAdapter {
    Context context;
    List<HistoryDetailsModel> finalList = new ArrayList<HistoryDetailsModel>();
    LayoutInflater inflater;

    public HistoryDetailsAdapter(Context context, List<HistoryDetailsModel> items) {
        this.context = context;
        finalList = items;
        inflater = LayoutInflater.from(context);


    }

    @Override
    public int getCount() {
        return finalList.size();
    }

    @Override
    public Object getItem(int i) {
        return finalList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHoder;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listview7, parent, false);
            viewHoder = new ViewHolder();
            viewHoder.bill_id = (TextView) convertView.findViewById(R.id.B);
            viewHoder.product_name = (TextView) convertView.findViewById(R.id.C);
            viewHoder.qty = (TextView) convertView.findViewById(R.id.D);
            viewHoder.total = (TextView) convertView.findViewById(R.id.E);
            convertView.setTag(viewHoder);


        } else {
            viewHoder = (ViewHolder) convertView.getTag();
        }
        final HistoryDetailsModel model = (HistoryDetailsModel) getItem(position);
        viewHoder.bill_id.setText(model.getBillId());
        viewHoder.product_name.setText(model.getProduct_name());
        viewHoder.qty.setText(model.getQty());
        viewHoder.total.setText(model.getTotal());
        HistoryDetailsAdapter.this.notifyDataSetChanged();

        return convertView;
    }

    static class ViewHolder {
        TextView id, bill_id, product_name, qty, total;
    }

}

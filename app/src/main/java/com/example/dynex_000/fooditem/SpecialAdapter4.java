package com.example.dynex_000.fooditem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Ashik on 1/3/2017.
 */

public class SpecialAdapter4 extends listViewAdapter4 {


    private int[] colors = new int[] { 0x30FF0000, 0x300000FF };

    public SpecialAdapter4(Context context, ArrayList<BeanClassForListView4> beanClassArrayList) {
        super(context, beanClassArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
       // int colorPos = position % colors.length;
       // view.setBackgroundColor(colors[colorPos]);


       // view.setBackgroundColor(Color.parseColor("#FFB6B546"));

        return view;
    }

}

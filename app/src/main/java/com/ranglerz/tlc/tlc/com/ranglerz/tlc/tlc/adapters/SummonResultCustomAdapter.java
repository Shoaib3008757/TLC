package com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ranglerz.tlc.tlc.R;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance.ListofVehicleText;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance.ListofVehicleandVinNumbers;

import java.util.ArrayList;

/**
 * Created by Hafiz Adeel on 3/7/2017.
 */

public class SummonResultCustomAdapter extends BaseAdapter{

    private ArrayList<ListofVehicleText> mListItems;
    private LayoutInflater mLayoutInflater;
    Context context;

    public SummonResultCustomAdapter(Context context, ArrayList<ListofVehicleText> arrayList){
        this.context = context;
        mListItems = arrayList;

        //get the layout inflater
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }





    @Override
    public int getCount() {
        //getCount() represents how many items are in the list
        return mListItems.size();
    }

    @Override
    //get the data of an item from a specific position
    //i represents the position of the item in the list
    public Object getItem(int i) {
        return null;
    }

    @Override
    //get the position id of the item from the list
    public long getItemId(int i) {
        return 0;
    }

    @Override

    public View getView(final int position, View view, ViewGroup viewGroup) {


        // create a ViewHolder reference
        final ViewHolder holder;

        //check to see if the reused view is null or not, if is not null then reuse it
        if (view == null) {
            holder = new ViewHolder();

            view = mLayoutInflater.inflate(R.layout.listofvehicle, viewGroup, false);

            // get all views you need to handle from the cell and save them in the view holder
            holder.itemID = (TextView) view.findViewById(R.id.listofvehicletableidlist);
            holder.itemPlate = (TextView) view.findViewById(R.id.platelist);
            holder.itemVIN = (TextView) view.findViewById(R.id.vinlist);
            holder.itemPolicy = (TextView) view.findViewById(R.id.policylist);



            // save the view holder on the cell view to get it back latter
            view.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)view.getTag();
        }

        //get the string item from the position "position" from array list to put it on the TextView
        ListofVehicleText stringItem = mListItems.get(position);
        if (stringItem != null) {
            //set the item name on the TextView
            holder.itemID.setText(stringItem.getListofVehicleId());
            holder.itemPlate.setText(stringItem.getPlateNumber());
            holder.itemVIN.setText(stringItem.getVinNumber());
            holder.itemPolicy.setText(stringItem.getPolicyNumber());
        } else {
            // make sure that when you have an if statement that alters the UI, you always have an else that sets a default value back, otherwise you will find that the recycled items will have the same UI changes
            holder.itemID.setText("Unknown");
            holder.itemPolicy.setText("Unknown");
            holder.itemVIN.setText("Unknown");
            holder.itemPolicy.setText("Unknown");
        }

        //this method must return the view corresponding to the data at the specified position.
        return view;

    }

    /**
     * Used to avoid calling "findViewById" every time the getView() method is called,
     * because this can impact to your application performance when your list is large
     */
    private class ViewHolder {

        protected TextView itemID;
        protected TextView itemPlate;
        protected TextView itemVIN;
        protected TextView itemPolicy;

    }


}

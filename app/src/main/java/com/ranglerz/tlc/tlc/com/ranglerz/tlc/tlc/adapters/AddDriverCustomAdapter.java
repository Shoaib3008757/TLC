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
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance.CurrentDriverList;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Insurance.addDriverText;

import java.util.ArrayList;

/**
 * Created by Hafiz Adeel on 2/8/2017.
 */


public class AddDriverCustomAdapter extends BaseAdapter {
    private ArrayList<addDriverText> mListItems;
    private LayoutInflater mLayoutInflater;
    Context context;

    public AddDriverCustomAdapter(Context context, ArrayList<addDriverText> arrayList){
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
        final AddDriverCustomAdapter.ViewHolder holder;

        //check to see if the reused view is null or not, if is not null then reuse it
        if (view == null) {
            holder = new ViewHolder();

            view = mLayoutInflater.inflate(R.layout.listofadddriver, viewGroup, false);

            // get all views you need to handle from the cell and save them in the view holder
            holder.itemID = (TextView) view.findViewById(R.id.listofadddrivertableidlist);
            holder.itemDriverName = (TextView) view.findViewById(R.id.driverlist);
            holder.itemDriverLicense = (TextView) view.findViewById(R.id.driverlicenselist);
            holder.itemTlcLicense = (TextView) view.findViewById(R.id.tlclicencelist);
            holder.itemPlateNumber = (TextView) view.findViewById(R.id.platenumberlist);
            holder.itemPolicyNumber = (TextView) view.findViewById(R.id.policyumberlist);
            holder.itemBaseName = (TextView) view.findViewById(R.id.basenamecorporationlist);
            holder.itemDelete = (ImageView) view.findViewById(R.id.adddriverdelete);
            holder.itemID.setVisibility(View.GONE);

            holder.itemDelete.setTag(position);

            holder.itemDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {


                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setCancelable(false);
                    dialog.setTitle("Delete Confirmation");
                    dialog.setMessage("Are you sure you want to delete this entry?" );
                    dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //Action for "Delete".
//                            mListItems.remove(position);
//                            notifyDataSetChanged();
                            String CurrentDriverID= holder.itemID.getText().toString();

//                            CurrentDriverList currentDriverList = new CurrentDriverList();
//                            currentDriverList.new deleteCurrentDriver().execute(CurrentDriverID);
                            Toast.makeText(v.getContext(), "ID"+CurrentDriverID, Toast.LENGTH_SHORT).show();



                        }
                    })
                            .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Action for "Cancel".
                                }
                            });

                    final AlertDialog alert = dialog.create();
                    alert.show();


                }
            });



            // save the view holder on the cell view to get it back latter
            view.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (AddDriverCustomAdapter.ViewHolder)view.getTag();
        }

        //get the string item from the position "position" from array list to put it on the TextView
        addDriverText stringItem = mListItems.get(position);
        if (stringItem != null) {
            //set the item name on the TextView
            holder.itemID.setText(stringItem.getAddDriverID());
            holder.itemDriverName.setText(stringItem.getDriverName());
            holder.itemDriverLicense.setText(stringItem.getDriverLicense());
            holder.itemTlcLicense.setText(stringItem.getTLCLicense());
            holder.itemPlateNumber.setText(stringItem.getPlateNumber());
            holder.itemPolicyNumber.setText(stringItem.getPolicyNumber());
            holder.itemBaseName.setText(stringItem.getBaseName());
        } else {
            // make sure that when you have an if statement that alters the UI, you always have an else that sets a default value back, otherwise you will find that the recycled items will have the same UI changes
            holder.itemID.setText("Unknown");
            holder.itemDriverName.setText("Unknown");
            holder.itemDriverLicense.setText("Unknown");
            holder.itemTlcLicense.setText("Unknown");
            holder.itemPlateNumber.setText("Unknown");
            holder.itemPolicyNumber.setText("Unknown");
            holder.itemBaseName.setText("Unknown");
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
        protected TextView itemDriverName;
        protected TextView itemDriverLicense;
        protected TextView itemTlcLicense;
        protected TextView itemPlateNumber;
        protected TextView itemPolicyNumber;
        protected TextView itemBaseName;
        protected ImageView itemDelete;

    }






}


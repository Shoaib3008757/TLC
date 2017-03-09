package com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ranglerz.tlc.tlc.R;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Summons.ScheduleResultText;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Summons.ScheduleSummon;

import java.util.ArrayList;

/**
 * Created by Hafiz Adeel on 2/21/2017.
 */


public class ScheduleResultCustomAdapter extends BaseAdapter {

    private ArrayList<ScheduleResultText> mListItems;
    private LayoutInflater mLayoutInflater;
    Context context;

    public ScheduleResultCustomAdapter(Context context, ArrayList<ScheduleResultText> arrayList){
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
       ViewHolder holder;

        //check to see if the reused view is null or not, if is not null then reuse it
        if (view == null) {
            holder = new ViewHolder();

            view = mLayoutInflater.inflate(R.layout.listofsummons, viewGroup, false);

            // get all views you need to handle from the cell and save them in the view holder
            holder.Summonno = (TextView) view.findViewById(R.id.summonlistlist);
            holder.SummonData = (TextView) view.findViewById(R.id.summondataatelist);
            holder.HearingLocation = (TextView) view.findViewById(R.id.hearinglocationsummonlist);
            holder.TlcLicense = (TextView) view.findViewById(R.id.tlclicencesummonlist);
            holder.PlataMedallion = (TextView) view.findViewById(R.id.platemedallionlist);
            holder.SummonType = (TextView) view.findViewById(R.id.summontypelist);
            holder.SummonTableId = (TextView) view.findViewById(R.id.summontableidlist);
            holder.SummonTableId.setVisibility(View.GONE);
            holder.ScheduleResultButtton = (Button) view.findViewById(R.id.schedulesummonresultbutton);
            holder.ScheduleResultButtton.setTag(position);

            holder.ScheduleResultButtton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ScheduleSummon.class);
                    v.getContext().startActivity(intent);
                }
            });
            // save the view holder on the cell view to get it back latter
            view.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)view.getTag();
        }

        //get the string item from the position "position" from array list to put it on the TextView
        ScheduleResultText stringItem = mListItems.get(position);
        if (stringItem != null) {
            //set the item name on the TextView
            holder.Summonno.setText(stringItem.getSummonNumber());
            holder.SummonData.setText(stringItem.getSummonData());
            holder.HearingLocation.setText(stringItem.getHearingLocation());
            holder.TlcLicense.setText(stringItem.getTlcLicense());
            holder.PlataMedallion.setText(stringItem.getPlateMedallion());
            holder.SummonType.setText(stringItem.getSummonType());
            holder.SummonTableId.setText(stringItem.getSummonTableId());
        } else {
            // make sure that when you have an if statement that alters the UI, you always have an else that sets a default value back, otherwise you will find that the recycled items will have the same UI changes
            holder.Summonno.setText("Unknown");
            holder.SummonData.setText("Unknown");
            holder.HearingLocation.setText("Unknown");
            holder.TlcLicense.setText("Unknown");
            holder.PlataMedallion.setText("Unknown");
            holder.SummonType.setText("Unknown");
            holder.SummonTableId.setText("Unknown");
        }



        //this method must return the view corresponding to the data at the specified position.
        return view;

    }

    /**
     * Used to avoid calling "findViewById" every time the getView() method is called,
     * because this can impact to your application performance when your list is large
     */
    private class ViewHolder {

        protected TextView Summonno;
        protected TextView SummonData;
        protected TextView HearingLocation;
        protected TextView TlcLicense;
        protected TextView PlataMedallion;
        protected TextView SummonType;
        protected TextView SummonTableId;
        protected Button ScheduleResultButtton;

    }

}

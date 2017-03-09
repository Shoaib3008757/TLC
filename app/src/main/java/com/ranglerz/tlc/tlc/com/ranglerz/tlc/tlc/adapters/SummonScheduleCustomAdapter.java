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
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Summons.Result;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Summons.ScheduleResultText;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Summons.ScheduleSummon;
import com.ranglerz.tlc.tlc.com.ranglerz.tlc.tlc.Summons.ScheduleText;

import java.util.ArrayList;

/**
 * Created by Hafiz Adeel on 2/23/2017.
 */

public class SummonScheduleCustomAdapter extends BaseAdapter {

    private ArrayList<ScheduleText> mListItems;
    private LayoutInflater mLayoutInflater;
    Context context;
    String Scheduleid;

    public SummonScheduleCustomAdapter(Context context, ArrayList<ScheduleText> arrayList){
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

            view = mLayoutInflater.inflate(R.layout.listofsummmonschedule, viewGroup, false);

            // get all views you need to handle from the cell and save them in the view holder
            holder.Scheduleid = (TextView) view.findViewById(R.id.summonscheduleid);
            holder.HearingLocation = (TextView) view.findViewById(R.id.hearinflocationlist);
            holder.HearingDate = (TextView) view.findViewById(R.id.hearingdatalist);
            holder.Contectno = (TextView) view.findViewById(R.id.contectnolist);
            holder.Attorneyrep = (TextView) view.findViewById(R.id.attorneyreplist);
            holder.Remarks = (TextView) view.findViewById(R.id.remarkslist);
            holder.Compliance = (TextView) view.findViewById(R.id.compliancelist);
            holder.Scheduleid.setVisibility(View.GONE);
            holder.ScheduletButtton = (Button) view.findViewById(R.id.schedulesummonresultbutton);
            holder.ScheduletButtton.setTag(position);


            holder.ScheduletButtton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), Result.class);

                    Scheduleid = holder.Scheduleid.getText().toString();
                    intent.putExtra("scheduleid" , Scheduleid);
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
        ScheduleText stringItem = mListItems.get(position);
        if (stringItem != null) {
            //set the item name on the TextView
            holder.Scheduleid.setText(stringItem.getScheduleid());
            holder.HearingLocation.setText(stringItem.getHearingLocation());
            holder.HearingDate.setText(stringItem.getHearingDate());
            holder.Contectno.setText(stringItem.getContectno());
            holder.Attorneyrep.setText(stringItem.getAttorneyrep());
            holder.Remarks.setText(stringItem.getRemarks());
            holder.Compliance.setText(stringItem.getCompliance());
        } else {
            // make sure that when you have an if statement that alters the UI, you always have an else that sets a default value back, otherwise you will find that the recycled items will have the same UI changes
            holder.Scheduleid.setText("Unknown");
            holder.HearingLocation.setText("Unknown");
            holder.HearingDate.setText("Unknown");
            holder.Contectno.setText("Unknown");
            holder.Attorneyrep.setText("Unknown");
            holder.Remarks.setText("Unknown");
            holder.Compliance.setText("Unknown");
        }



        //this method must return the view corresponding to the data at the specified position.
        return view;

    }

    /**
     * Used to avoid calling "findViewById" every time the getView() method is called,
     * because this can impact to your application performance when your list is large
     */
    private class ViewHolder {

        protected TextView Scheduleid;
        protected TextView HearingLocation;
        protected TextView HearingDate;
        protected TextView Contectno;
        protected TextView Attorneyrep;
        protected TextView Remarks;
        protected TextView Compliance;
        protected TextView ScheduleID;
        protected Button ScheduletButtton;

    }

}

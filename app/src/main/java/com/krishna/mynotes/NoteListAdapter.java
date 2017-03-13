package com.krishna.mynotes;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by Krishna on 23/02/17.
 */

public class NoteListAdapter extends BaseAdapter {
    private ArrayList<NoteListModel> portListing;
    private Context context;
    NoteListModel listing;
    private LayoutInflater inflater;
    Boolean check_visible=false;
    MainActivity mainActivity;



    public NoteListAdapter(Context context, ArrayList<NoteListModel> portListing) {
        this.context = context;
        this.portListing = portListing;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return portListing.size();
    }
    @Override
    public Object getItem(int position) {
        return portListing.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        NoteListAdapter.Holder holder;
        listing=portListing.get(position);
        if (v == null) {
            v = inflater.inflate(R.layout.row, null);
            holder = new NoteListAdapter.Holder(v);
            v.setTag(holder);
        } else {
            holder = (NoteListAdapter.Holder) v.getTag();
        }

        holder.Note_title.setText(listing.getTitle());


        if(check_visible){
            holder.checkBox.setVisibility(View.VISIBLE);
        }
        else {
            holder.checkBox.setVisibility(View.GONE);
        }

        if(listing.isCheckbox()){
            holder.checkBox.setChecked(true);
        }
        else {
            holder.checkBox.setChecked(false);
        }

        /*holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("status check","=============== "+position);
                frag.checkboxStatus(position);
            }
        });*/

        return v;
    }



    class Holder {
        public final TextView Note_title;
        public final CheckBox checkBox;

        public Holder(View itemView) {
            this.Note_title = (TextView) itemView.findViewById(R.id.Note_title);
            this.checkBox = (CheckBox) itemView.findViewById(R.id.list_checkBox);


        }
    }


    public ArrayList<NoteListModel> getAllData(){
        return portListing;
    }
    public void setCheckBox(int position){
        //Update status of checkbox
        NoteListModel items = portListing.get(position);
        items.setCheckbox(!items.isCheckbox());
        notifyDataSetChanged();
    }

    public void Show_check1(){
        check_visible=true;
        notifyDataSetChanged();
    }

    public void Hide_check1(){

        check_visible=false;

        notifyDataSetChanged();
    }

    public boolean check_visibility(){

        return check_visible;
    }

}

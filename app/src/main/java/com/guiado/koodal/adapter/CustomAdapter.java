package com.guiado.koodal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.guiado.koodal.R;
import com.guiado.koodal.model.CoachItem;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<CoachItem> implements View.OnClickListener{

    private ArrayList<CoachItem> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView tv;
    }

    public CustomAdapter(ArrayList<CoachItem> data, Context context) {
        super(context, R.layout.dialog_list_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        CoachItem dataModel=(CoachItem)object;

        switch (v.getId())
        {
            case R.id.tv:
                Snackbar.make(v, "Release date " +dataModel.getCategoryname(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        CoachItem dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag


        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.dialog_list_item, parent, false);
            viewHolder.tv =  convertView.findViewById(R.id.tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        lastPosition = position;

        viewHolder.tv.setText(dataModel.getCategoryname());
        // Return the completed view to render on screen
        return convertView;
    }
}

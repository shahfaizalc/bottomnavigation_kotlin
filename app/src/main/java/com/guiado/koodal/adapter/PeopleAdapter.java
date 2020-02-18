package com.guiado.koodal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.guiado.koodal.R;
import com.guiado.koodal.model.IndiaItem;
import java.util.ArrayList;
import java.util.List;

public class PeopleAdapter extends ArrayAdapter<IndiaItem> {

    private Context context;
    private List<IndiaItem> items, tempItems;
    public List<IndiaItem> suggestions;

    public PeopleAdapter(Context context, int resource, int textViewResourceId, List<IndiaItem> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.items = items;
        tempItems = new ArrayList<IndiaItem>(items); // this makes the difference.
        suggestions = new ArrayList<IndiaItem>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.autofilitem, parent, false);
        }
        IndiaItem people = items.get(position);
        if (people != null) {
            TextView lblName = view.findViewById(R.id.text_title);
            if (lblName != null)
                lblName.setText(people.getCityname());

        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    private Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((IndiaItem) resultValue).getCityname();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (IndiaItem people : tempItems) {
                    if (people.getCityname().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<IndiaItem> filterList = (ArrayList<IndiaItem>) results.values;
            if (results.count > 0) {
                clear();
                for (IndiaItem people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
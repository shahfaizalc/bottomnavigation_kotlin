package com.guiado.linkify.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.guiado.linkify.R;
import com.guiado.linkify.model.CoachItem;
import com.guiado.linkify.model.IndiaItem;
import com.guiado.linkify.viewmodel.TheEventsModel;

import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder>{
    TheEventsModel listdata;
    // RecyclerView recyclerView;
    public CountryAdapter(TheEventsModel listdata) {


        this.listdata = listdata;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.dialog_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        final IndiaItem myListData = listdata.getObservableArrayListFilter().get(position);
        holder.textView.setText(listdata.getObservableArrayListFilter().get(position).getCityname());
        holder.textView.setOnClickListener(view -> {
            listdata.filterByCategory(position);
         //   Toast.makeText(view.getContext(),"click on item: "+myListData.getCityname(),Toast.LENGTH_LONG).show();
        });
    }


    @Override
    public int getItemCount() {
        return listdata.getObservableArrayListFilter().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
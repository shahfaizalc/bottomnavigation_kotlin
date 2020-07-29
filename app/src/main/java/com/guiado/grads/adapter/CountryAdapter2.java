package com.guiado.grads.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.guiado.grads.R;
import com.guiado.grads.model.IndiaItem;
import com.guiado.grads.viewmodel.ProfileEditViewModel;

public class CountryAdapter2 extends RecyclerView.Adapter<CountryAdapter2.ViewHolder>{
    ProfileEditViewModel listdata;
    // RecyclerView recyclerView;
    public CountryAdapter2(ProfileEditViewModel listdata) {


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
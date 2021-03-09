package com.guiado.grads.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.guiado.grads.R;
import com.guiado.grads.adapter.RelationshipRecyclerViewAdapter;
import com.guiado.grads.databinding.RelationshipLayoutBinding;
import com.guiado.grads.model.Flight;

import java.util.ArrayList;
import java.util.List;

public class RelegionActivity extends AppCompatActivity {
    private RelationshipRecyclerViewAdapter adapter;
    private RelationshipLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.relationship_layout);
        binding.flightsRv.setLayoutManager(new LinearLayoutManager(this));
        binding.flightsRv.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        RelationshipRecyclerViewAdapter adapter =
                new RelationshipRecyclerViewAdapter(prepareData(), this);
        binding.flightsRv.setAdapter(adapter);
        binding.nextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RelegionActivity.this, ChildrenActivity.class));
            }
        });



    }

    public List<Flight> prepareData(){
        List<Flight> flights = new ArrayList<>();

        Flight flight = new Flight("Atheist");
        flights.add(flight);
        flight = new Flight("Agnostic");
        flights.add(flight);
        flight = new Flight("Buddist");
        flights.add(flight);
        flight = new Flight("Christian");
        flights.add(flight);
        flight = new Flight("Catholic");
        flights.add(flight);
        flight = new Flight("Hindu");
        flights.add(flight);
        flight = new Flight("Islamic");
        flights.add(flight);
        flight = new Flight("Jewish");
        flights.add(flight);
        flight = new Flight("Other");
        flights.add(flight);

        return flights;
    }
}

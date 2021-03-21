package com.reelme.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.reelme.app.R;
import com.reelme.app.adapter.ChildrenRecyclerViewAdapter;
import com.reelme.app.adapter.FlightsRecyclerViewAdapter;
import com.reelme.app.databinding.ChildrenLayoutBinding;
import com.reelme.app.databinding.FlightLayoutBinding;
import com.reelme.app.model.Flight;

import java.util.ArrayList;
import java.util.List;

public class ChildrenActivity extends AppCompatActivity {
    private ChildrenRecyclerViewAdapter adapter;
    private ChildrenLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.children_layout);
        binding.flightsRv.setLayoutManager(new LinearLayoutManager(this));
        binding.flightsRv.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        FlightsRecyclerViewAdapter adapter =
                new FlightsRecyclerViewAdapter(prepareData(), this);
        binding.flightsRv.setAdapter(adapter);
        binding.nextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChildrenActivity.this, FragmentOccupation.class));
            }
        });

    }

    public List<Flight> prepareData(){
        List<Flight> flights = new ArrayList<>();

        Flight flight = new Flight("Yes");
        flights.add(flight);
        flight = new Flight("No");
        flights.add(flight);
        flight = new Flight("Someday");
        flights.add(flight);
        flight = new Flight("Never");
        flights.add(flight);

        return flights;
    }
}

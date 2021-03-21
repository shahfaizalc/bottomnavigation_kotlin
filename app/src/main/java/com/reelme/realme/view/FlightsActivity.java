package com.reelme.realme.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.reelme.realme.R;
import com.reelme.realme.adapter.FlightsRecyclerViewAdapter;
import com.reelme.realme.databinding.FlightLayoutBinding;
import com.reelme.realme.model.Flight;

import java.util.ArrayList;
import java.util.List;

public class FlightsActivity extends AppCompatActivity {
    private FlightsRecyclerViewAdapter adapter;
    private FlightLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.flight_layout);
        binding.flightsRv.setLayoutManager(new LinearLayoutManager(this));
        binding.flightsRv.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        FlightsRecyclerViewAdapter adapter =
                new FlightsRecyclerViewAdapter(prepareData(), this);
        binding.flightsRv.setAdapter(adapter);
        binding.nextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FlightsActivity.this,RelationshipActivity.class));
            }
        });

    }

    public List<Flight> prepareData(){
        List<Flight> flights = new ArrayList<>();

        Flight flight = new Flight("Male");
        flights.add(flight);
        flight = new Flight("Female");
        flights.add(flight);
        flight = new Flight("Gender Neutral");
        flights.add(flight);
        flight = new Flight("Transgender");
        flights.add(flight);
        flight = new Flight("Other");
        flights.add(flight);

        return flights;
    }
}

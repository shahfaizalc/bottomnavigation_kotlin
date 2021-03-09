package com.guiado.grads.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.guiado.grads.R;
import com.guiado.grads.adapter.FlightsRecyclerViewAdapter;
import com.guiado.grads.databinding.FlightLayoutBinding;
import com.guiado.grads.model.Flight;

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

package com.guiado.grads.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.guiado.grads.R;
import com.guiado.grads.adapter.ChildrenRecyclerViewAdapter;
import com.guiado.grads.adapter.FlightsRecyclerViewAdapter;
import com.guiado.grads.databinding.ChildrenLayoutBinding;
import com.guiado.grads.databinding.FlightLayoutBinding;
import com.guiado.grads.model.Flight;
import com.guiado.grads.viewmodel.OccupationViewModel;

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

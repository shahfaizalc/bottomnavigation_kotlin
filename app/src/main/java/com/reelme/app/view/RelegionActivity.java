package com.reelme.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.reelme.app.R;
import com.reelme.app.adapter.RelegiousRecyclerViewAdapter;
import com.reelme.app.databinding.RelegiousLayoutBinding;
import com.reelme.app.model.Flight;

import java.util.ArrayList;
import java.util.List;

public class RelegionActivity extends AppCompatActivity {
    private RelegiousRecyclerViewAdapter adapter;
    private RelegiousLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.relegious_layout);
        binding.flightsRv.setLayoutManager(new LinearLayoutManager(this));
        binding.flightsRv.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        RelegiousRecyclerViewAdapter adapter =
                new RelegiousRecyclerViewAdapter(prepareData(), this);
        binding.flightsRv.setAdapter(adapter);
        binding.nextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RelegionActivity.this, FragmentHobbies.class));
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

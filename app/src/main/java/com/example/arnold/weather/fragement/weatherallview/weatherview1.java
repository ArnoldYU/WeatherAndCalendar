package com.example.arnold.weather.fragement.weatherallview;

/**
 * Created by Arnold on 2017/7/16.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.arnold.weather.R;

public class weatherview1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view=inflater.inflate(R.layout.weatherview1, container, false);
        return view;
    }

}
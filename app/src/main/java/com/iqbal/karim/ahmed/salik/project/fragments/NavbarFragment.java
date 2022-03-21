package com.iqbal.karim.ahmed.salik.project.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.iqbal.karim.ahmed.salik.project.R;


public class NavbarFragment extends Fragment {

    NavigationInterface navigationInterface;
    public interface NavigationInterface {
        public void changeScreen(int subject);
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof NavigationInterface)
            navigationInterface = (NavigationInterface) context;
    }

    public NavbarFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navbar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        BottomNavigationView.OnNavigationItemSelectedListener(new )
        BottomNavigationView nav = view.findViewById(R.id.bottom_navigation);
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                navigationInterface.changeScreen(item.getItemId());
                return true;
            }
        });
    }

    private void prepareScreen(View view) {
    }
}
package com.iqbal.karim.ahmed.salik.project.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iqbal.karim.ahmed.salik.project.Commons;
import com.iqbal.karim.ahmed.salik.project.R;

public class HeaderFragment extends Fragment {


    HeaderFragmentInterface logOutListener;
    public interface HeaderFragmentInterface {
        public void logOut();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof HeaderFragmentInterface )
            logOutListener = (HeaderFragmentInterface) context;
    }

    public HeaderFragment() {
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
        return inflater.inflate(R.layout.fragment_header, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prepareScreen(view);
    }

    private void prepareScreen(View view) {
        TextView tvName, tvEmail, tvLogout;
        tvName = view.findViewById(R.id.headerName);
        tvLogout = view.findViewById(R.id.headerLogout);
        tvEmail = view.findViewById(R.id.headerEmail);
        ImageView dp = view.findViewById(R.id.headerImage);

        tvName.setText(Commons.getCurrentUser().getName());
        tvEmail.setText(Commons.getCurrentUser().getEmail());
        dp.setImageResource(Commons.getCurrentUser().getImage());
        dp.setTag(Commons.getCurrentUser().getImage());

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOutListener.logOut();
            }
        });
    }

}
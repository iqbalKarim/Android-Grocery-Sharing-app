package com.iqbal.karim.ahmed.salik.project.fragments;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iqbal.karim.ahmed.salik.project.Commons;
import com.iqbal.karim.ahmed.salik.project.HomeActivity;
import com.iqbal.karim.ahmed.salik.project.R;
import com.iqbal.karim.ahmed.salik.project.databaseClasses.DatabaseHelperItems;
import com.iqbal.karim.ahmed.salik.project.databaseClasses.Item;
import com.iqbal.karim.ahmed.salik.project.databaseClasses.ItemsTable;
import com.iqbal.karim.ahmed.salik.project.databaseClasses.UsersTable;


public class BodyFragment extends Fragment {

    FrameLayout frameLayout;
    boolean defaultSelected, newImgSelected = false;
    int imgId = Commons.getCurrentUser().getImage();
    int[] imgIds = new int[]{R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5};

    public BodyFragmentInterface bodyListener;
    public interface BodyFragmentInterface{
        public void loadSpinner(Spinner spn);
        public void loadRecycler(RecyclerView recyclerView);
        public void toast(String t);
        public void updateUser(String name, String email, int imgId);
        public void addItem(View view);
        public void changePassword(String newPass);
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof BodyFragmentInterface)
            bodyListener =  (BodyFragmentInterface) context;
    }

    public BodyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        frameLayout = new FrameLayout(getActivity());
        LayoutInflater inflate = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.fragment_share, null);

        initShareScreen(view);

        frameLayout .addView(view);
        return frameLayout;
    }

    public void changeScreen(int subject) {
        frameLayout.removeAllViews();
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view;
        if (subject == R.id.page_1) {
            view = inflater.inflate(R.layout.fragment_share, null);

            initShareScreen(view);
        }
        else if (subject == R.id.page_2) {
            view = inflater.inflate(R.layout.fragment_request, null);
            
            initRequestScreen(view);
        }
        else {
            view = inflater.inflate(R.layout.fragment_profile, null);

            initProfileScreen(view);
        }

        frameLayout.addView(view);
    }

    private void initShareScreen(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerShare);

        bodyListener.loadRecycler(recyclerView);
    }

    private void initRequestScreen(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerRequests);
        bodyListener.loadRecycler(recyclerView);

        FloatingActionButton fab = view.findViewById(R.id.extended_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bodyListener.addItem(view);
            }
        });


    }

    private void initProfileScreen(View view) {
        defaultSelected = true;

        EditText etName, etEmail, etPassword, etNewPassword, etConfirmPassword;
        Spinner spn = view.findViewById(R.id.spAvatars);
        bodyListener.loadSpinner(spn);

        ImageView dp = view.findViewById(R.id.avatar);
        dp.setImageResource(Commons.getCurrentUser().getImage());

        etName = view.findViewById(R.id.etProfileName);
        etEmail = view.findViewById(R.id.etProfileEmail);
        etPassword = view.findViewById(R.id.etProfilePassword);
        etNewPassword = view.findViewById(R.id.etProfileNewPassword);
        etConfirmPassword = view.findViewById(R.id.etProfileConfirmPassword);

        etName.setText(Commons.getCurrentUser().getName());
        etEmail.setText(Commons.getCurrentUser().getEmail());

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!defaultSelected) {
                    imgId = imgIds[i];
                    newImgSelected = true;
                }else{
                    defaultSelected = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button changeInformation, changePassword;
        changeInformation = view.findViewById(R.id.btnChangeInfo);
        changePassword = view.findViewById(R.id.btnChangePassword);

        changeInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, email;
                name = etName.getText().toString();
                email = etEmail.getText().toString();

                if (name.equals(Commons.getCurrentUser().getName()) && email.equals(Commons.getCurrentUser().getEmail()) && newImgSelected != true){
                    bodyListener.toast("Information hasn't been changed");
                }else{
                    bodyListener.updateUser(name,email,imgId);
                }
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = etPassword.getText().toString();
                String newPass = etNewPassword.getText().toString();
                String confirmPass = etConfirmPassword.getText().toString();

                if (pass.equals("")) bodyListener.toast("Please enter your old password");
                else if (pass.equals(Commons.getCurrentUser().getPassword())) {
                    if (newPass.equals(pass)) bodyListener.toast("Old and New passwords cannot be same");
                    else {
                        if (newPass.equals("") || confirmPass.equals(""))
                            bodyListener.toast("Please fill all fields");
                        else if (newPass.equals(confirmPass)) {
                            bodyListener.changePassword(newPass);
                        }else {
                            bodyListener.toast("New and Confirm passwords do not match.");
                        }
                    }
                }
                else bodyListener.toast("old password is wrong");
            }
        });
    }
}
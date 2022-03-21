package com.iqbal.karim.ahmed.salik.project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.iqbal.karim.ahmed.salik.project.R;

import java.util.ArrayList;


public class AvatarSpinnerAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> avatars;
    private int[] imgIds;

    public AvatarSpinnerAdapter(Context baseContext, ArrayList<String> avatars, int[] imgIds) {
        super(baseContext, R.layout.avatar_spinner_layout, avatars);
        this.avatars = avatars;
        this.context = baseContext;
        this.imgIds = imgIds;
    }

    @Override
    public View getDropDownView(int position,  View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.avatar_spinner_layout, parent, false);

        ConstraintLayout layout = view.findViewById(R.id.itemLayout);
        if (position % 2 == 0) layout.setBackgroundColor(000000) ;

        ImageView avatarImage = view.findViewById(R.id.spImageAvatar);
        avatarImage.setImageResource(imgIds[position]);

        return view;
    }

}

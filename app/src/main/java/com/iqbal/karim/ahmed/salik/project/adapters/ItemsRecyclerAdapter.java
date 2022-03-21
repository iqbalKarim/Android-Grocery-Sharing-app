package com.iqbal.karim.ahmed.salik.project.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.iqbal.karim.ahmed.salik.project.Commons;
import com.iqbal.karim.ahmed.salik.project.HomeActivity;
import com.iqbal.karim.ahmed.salik.project.R;
import com.iqbal.karim.ahmed.salik.project.databaseClasses.DatabaseHelperItems;
import com.iqbal.karim.ahmed.salik.project.databaseClasses.Item;
import com.iqbal.karim.ahmed.salik.project.databaseClasses.ItemsTable;

import java.util.ArrayList;


public class ItemsRecyclerAdapter extends RecyclerView.Adapter<ItemsRecyclerAdapter.MyViewHolder>{

    Context context;
    DatabaseHelperItems dbHelper;
    int layout;

    public ItemsRecyclerAdapter(Context context, DatabaseHelperItems dbHelper, int layout) {
        this.context = context;
        this.dbHelper = dbHelper;
        this.layout = layout;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item i = Commons.items.get(position);

        holder.itemName.setText(i.getName());
        holder.itemName.setTag(i);

        if (layout == R.layout.request_item_layout){
            if (i.getShared() == 1) {
                holder.requestedBy.setText(i.getGivenBy());

                holder.itemLayout.setBackgroundColor(context.getResources().getColor(R.color.tertiaryColor));

            }else{
                holder.requestedBy.setText("none");
            }
        }
        else {
            holder.requestedBy.setText(i.getRequesterName());
        }

        String c = i.getCategory();

        switch (c){
            case "Fruits":
                holder.itemImage.setImageResource(R.drawable.fruits);
                break;
            case "Vegetables":
                holder.itemImage.setImageResource(R.drawable.vegetables);
                break;
            case "Electronics":
                holder.itemImage.setImageResource(R.drawable.electronics);
                break;
            case "Clothes":
                holder.itemImage.setImageResource(R.drawable.clothes);
                break;
        }

        holder.clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layout == R.layout.request_item_layout){

                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Warning: Delete Item");
                    alert.setIcon(R.drawable.ic_baseline_delete_24);
                    alert.setMessage("Are you sure you want to delete the item?");
                    alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Item t = (Item) holder.itemName.getTag();
                            ItemsTable.deleteItem(dbHelper, t);

                            Commons.setItems((ArrayList<Item>) ItemsTable.getRequestItems(dbHelper));

                            notifyDataSetChanged();
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Log.d("dialog", "cancel");
                        }
                    });
                    alert.create().show();
                }
                else{
                    Log.d("share", "recycler");

                    Item t = (Item) holder.itemName.getTag();
                    Log.d("recycler",t.getName() + " " + t.getId());

                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Warning: Give Item");
                    alert.setIcon(R.drawable.ic_baseline_done_24);
                    alert.setMessage("Are you sure you want to give the item?");
                    alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Item t = (Item) holder.itemName.getTag();
                            ItemsTable.markShared(dbHelper, t);

                            Commons.setItems((ArrayList<Item>) ItemsTable.getShareItems(dbHelper));

                            notifyDataSetChanged();
                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Log.d("dialog", "cancel");
                        }
                    });
                    alert.create().show();

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Commons.getItems().size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView itemName, requestedBy;
        ImageView itemImage, clickView;
        ConstraintLayout itemLayout;

        MyViewHolder(View viewItem){
            super(viewItem);
            clickView = viewItem.findViewById(R.id.imageView2);
            itemLayout = viewItem.findViewById(R.id.itemLayout);
            itemName = viewItem.findViewById(R.id.tvItemName);
            requestedBy = viewItem.findViewById(R.id.tvRequestedBy);
            itemImage = viewItem.findViewById(R.id.itemImage);
        }
    }
}

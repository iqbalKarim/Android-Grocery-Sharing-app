package com.iqbal.karim.ahmed.salik.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.iqbal.karim.ahmed.salik.project.adapters.AvatarSpinnerAdapter;
import com.iqbal.karim.ahmed.salik.project.adapters.ItemsRecyclerAdapter;
import com.iqbal.karim.ahmed.salik.project.databaseClasses.DatabaseHelper;
import com.iqbal.karim.ahmed.salik.project.databaseClasses.DatabaseHelperItems;
import com.iqbal.karim.ahmed.salik.project.databaseClasses.Item;
import com.iqbal.karim.ahmed.salik.project.databaseClasses.ItemsTable;
import com.iqbal.karim.ahmed.salik.project.databaseClasses.UsersTable;
import com.iqbal.karim.ahmed.salik.project.fragments.BodyFragment;
import com.iqbal.karim.ahmed.salik.project.fragments.HeaderFragment;
import com.iqbal.karim.ahmed.salik.project.fragments.NavbarFragment;
import com.iqbal.karim.ahmed.salik.project.services.NotifService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import maes.tech.intentanim.CustomIntent;

public class HomeActivity extends AppCompatActivity implements HeaderFragment.HeaderFragmentInterface, NavbarFragment.NavigationInterface, BodyFragment.BodyFragmentInterface {

    BodyFragment bodyFragment;
    DatabaseHelperItems dbHelperItems;
    DatabaseHelper dbHelperUsers;

    ItemsRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitleBar();
        setContentView(R.layout.activity_home);

        try {
            String fileToDatabase = "/data/data/" + getPackageName() + "/databases/"+ DatabaseHelperItems.DATABASE_NAME;
            File file = new File(fileToDatabase);
            File pathToDatabasesFolder = new File("/data/data/" + getPackageName() + "/databases/");
            if (!file.exists()) {
                pathToDatabasesFolder.mkdirs();
                Log.d("BURDA", "BURDA");
                CopyDB( getResources().getAssets().open(DatabaseHelperItems.DATABASE_NAME),
                        new FileOutputStream(fileToDatabase));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dbHelperItems = new DatabaseHelperItems(this);
        dbHelperUsers = new DatabaseHelper(this);

        bodyFragment = new BodyFragment();
        Bundle b = new Bundle();
        b.putInt("screen", 1);
        bodyFragment.setArguments(b);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.bodyFragment, bodyFragment);
        fragmentTransaction.commit();
    }

    private void hideTitleBar() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void logOut() {
        Commons.setCurrentUser(null);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
        finish();
    }

    @Override
    public void changeScreen(int subject) {
        Intent intent = new Intent(this, NotifService.class);
        startService(intent);
        bodyFragment.changeScreen(subject);
    }

    @Override
    public void loadSpinner(Spinner spn) {
        ArrayList<String> avatars = new ArrayList<String>();
        Collections.addAll(avatars, "Avatar 1", "Avatar 2", "Avatar 3", "Avatar 4", "Avatar 5");

        int[] imgIds = new int[]{R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5};

        AvatarSpinnerAdapter adp = new AvatarSpinnerAdapter(this, avatars, imgIds);
        spn.setAdapter(adp);
    }

    @Override
    public void loadRecycler(RecyclerView recyclerView) {
        if (recyclerView.getId() == R.id.recyclerShare) {
            ArrayList<Item> items = (ArrayList<Item>) ItemsTable.getShareItems(dbHelperItems);
            Commons.setItems(items);

            adapter = new ItemsRecyclerAdapter(HomeActivity.this, dbHelperItems, R.layout.share_item_layout);
            recyclerView.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
        }
        else if (recyclerView.getId() == R.id.recyclerRequests){
            ArrayList<Item> items = (ArrayList<Item>) ItemsTable.getRequestItems(dbHelperItems);
            Commons.setItems(items);
            adapter = new ItemsRecyclerAdapter(HomeActivity.this, dbHelperItems, R.layout.request_item_layout);
            recyclerView.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
        }
    }

    @Override
    public void toast(String t) {
        Toast.makeText(this, t, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateUser(String name, String email, int imgId) {

        boolean res = UsersTable.updateUser(dbHelperUsers, name, email, imgId);
        if (res) {
            Commons.setCurrentUser(null);
            finish();
        }
        else{
            Toast.makeText(this, "Unable to update data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addItem(View view) {
        Dialog customDialog = new Dialog(this);
        customDialog.setContentView(R.layout.request_dialog);
        customDialog.show();

        Button back, addItem;
        back = customDialog.findViewById(R.id.requestDialogBack);
        addItem = customDialog.findViewById(R.id.requestDialogAddItem);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText et = customDialog.findViewById(R.id.requestDialogItemName);
                Spinner spn = customDialog.findViewById(R.id.categoriesSpinner);

                String cat = spn.getSelectedItem().toString();
                String name = et.getText().toString();
                String requesterName = Commons.getCurrentUser().getName();
                int requesterId = Commons.getCurrentUser().getId();

                ItemsTable.insertItem(dbHelperItems, name, requesterId, requesterName, cat);
                customDialog.dismiss();

                Commons.setItems((ArrayList<Item>) ItemsTable.getRequestItems(dbHelperItems));
                adapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    public void changePassword(String newPass) {
        Toast.makeText(this, "changing passwords", Toast.LENGTH_SHORT).show();
        boolean res = UsersTable.updateUserPass(dbHelperUsers, newPass);
        if (res) {
            Commons.setCurrentUser(null);
            finish();
        }
        else{
            Toast.makeText(this, "Unable to update password", Toast.LENGTH_SHORT).show();
        }
    }


    private void CopyDB(InputStream inputStream, FileOutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        Log.d("BURDA", "BURDA2");

        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
            Log.d("BURDA", "BURDA3");
        }
        inputStream.close();
        outputStream.close();
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "fadein-to-fadeout");
    }
}
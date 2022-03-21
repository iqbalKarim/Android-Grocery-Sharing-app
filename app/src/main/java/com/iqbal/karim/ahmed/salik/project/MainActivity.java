package com.iqbal.karim.ahmed.salik.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.iqbal.karim.ahmed.salik.project.adapters.AvatarSpinnerAdapter;
import com.iqbal.karim.ahmed.salik.project.databaseClasses.DatabaseHelper;
import com.iqbal.karim.ahmed.salik.project.databaseClasses.User;
import com.iqbal.karim.ahmed.salik.project.databaseClasses.UsersTable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitleBar();
        setContentView(R.layout.activity_main);

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());

        try {
            String fileToDatabase = "/data/data/" + getPackageName() + "/databases/"+DatabaseHelper.DATABASE_NAME;
            File file = new File(fileToDatabase);
            File pathToDatabasesFolder = new File("/data/data/" + getPackageName() + "/databases/");
            if (!file.exists()) {
                pathToDatabasesFolder.mkdirs();
                Log.d("BURDA", "BURDA");
                CopyDB( getResources().getAssets().open(DatabaseHelper.DATABASE_NAME),
                        new FileOutputStream(fileToDatabase));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dbHelper = new DatabaseHelper(this);

        if (Commons.getCurrentUser() != null){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
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

    private void hideTitleBar() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void signUp(View view) {
        Dialog customDialog = new Dialog(this);
        customDialog.setContentView(R.layout.signup_dialog);

        Button back, signUp;
        back = customDialog.findViewById(R.id.signUpBack);
        signUp = customDialog.findViewById(R.id.signUp);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etFirstName, etSecondName, etEmail, etPassword;
                String name, email, password;

                etSecondName = customDialog.findViewById(R.id.signUpSecondName);
                etFirstName = customDialog.findViewById(R.id.signUpFirstName);
                etEmail = customDialog.findViewById(R.id.signUpEmail);
                etPassword = customDialog.findViewById(R.id.signUpPassword);

                name = etFirstName.getText().toString() + " " + etSecondName.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                int imageId = R.drawable.p1;

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please fill all necessary information", Toast.LENGTH_SHORT).show();
                }else{
                    boolean res = UsersTable.insertUser(dbHelper, name, password, email, imageId);
                    if (res) {
                        Toast.makeText(MainActivity.this, "User added", Toast.LENGTH_SHORT).show();
                        customDialog.dismiss();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "This user already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        customDialog.show();
    }

    public void signIn(View view) {
        String email, password;
        EditText etEmail, etPassword;
        
        etEmail = findViewById(R.id.loginEmail);
        etPassword = findViewById(R.id.loginPassword);
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

        User res = UsersTable.authUser(dbHelper, email, password);
        if (res != null){
            Commons.setCurrentUser(res);
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
        }
        else {
            Toast.makeText(this, "Incorrect Email or Password", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        public boolean onDoubleTap(MotionEvent event) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
            return true;
        }
        public void onLongPress(MotionEvent motionEvent) {
            Intent intent = new Intent(MainActivity.this, TermsAndConditionsActivity.class);
            startActivity(intent);
            CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
        }
    }
}
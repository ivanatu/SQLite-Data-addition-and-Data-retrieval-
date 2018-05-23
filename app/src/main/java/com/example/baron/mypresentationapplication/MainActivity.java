package com.example.baron.mypresentationapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * main activity
 */
public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    private EditText Name;
    private EditText Mobile;
    private EditText Email;
    private Button Button;
    private Button Button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name = findViewById(R.id.name);
        Mobile = findViewById(R.id.mobile);
        Email = findViewById(R.id.email);
        Button = findViewById(R.id.button);
        Button1 = findViewById(R.id.button2);
        databaseHelper = new DatabaseHelper(this);
        viewAll();

        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                String mobile = Mobile.getText().toString();
                String email = Email.getText().toString();
                String name = Name.getText().toString();

                if (Name.length()!=0 || Email.length()!=0 || Mobile.length()!=0){

                   AddData(name, mobile, email);

                   Name.setText("");
                    Email.setText("");
                    Mobile.setText("");
                }else{
                    Toast.makeText(context, "put something", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Retrieve all data stored in the database
     */
    public void viewAll() {
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = databaseHelper.getData();
                if(res.getCount() == 0) {
                    // show message
                    showMessage("Error","Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id :"+ res.getString(0)+"\n");
                    buffer.append("Name :"+ res.getString(1)+"\n");
                    buffer.append("Mobile :"+ res.getString(2)+"\n");
                    buffer.append("Email :"+ res.getString(3)+"\n\n");
                }

                // Show all data
                showMessage("Data",buffer.toString());
            }
        });
    }

    public void AddData(String newEntry1, String newEntry2, String newEntry3){
        Context context = getApplicationContext();
        boolean insertData = databaseHelper.addData(newEntry1, newEntry2, newEntry3);
        if(insertData){
            Toast.makeText(context, "Data stored successfully", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Error entering data", Toast.LENGTH_SHORT).show();
        }
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}

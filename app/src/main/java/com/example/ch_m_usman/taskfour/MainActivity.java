package com.example.ch_m_usman.taskfour;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button addStudent,deleteStudent;
    Spinner spinner;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        addStudent = (Button) findViewById(R.id.addnew);
        deleteStudent = (Button) findViewById(R.id.delete);
        spinner = (Spinner) findViewById(R.id.option);
        spinner.setPrompt("Select Option");
        spinner.setOnItemSelectedListener(this);

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,AddStudent.class));
            }
        });

        deleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,DeleteStudent.class));
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();

        final Intent intent = new Intent(MainActivity.this,AllStudent.class) ;
        if (i==1){
            intent.putExtra("type","all");
            startActivity(intent);
        }else if (i==2){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            final EditText groupName = new EditText(context);
            groupName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            alertDialogBuilder.setTitle("Enter city");
            final String[] inputs = new String[1];
            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(groupName);
            alertDialogBuilder.setCancelable(false).setPositiveButton("Find", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    inputs[0] = groupName.getText().toString();
                    if (inputs[0].trim().isEmpty()){
                        (Toast.makeText(MainActivity.this,"Required field missing",Toast.LENGTH_LONG)).show();
                        return;
                    }
                    intent.putExtra("type","city");
                    intent.putExtra("input",inputs[0]);
                    startActivity(intent);

                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }else if (i==3){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            final EditText groupName = new EditText(context);
            groupName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            alertDialogBuilder.setTitle("Enter class");
            final String[] inputs = new String[1];
            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(groupName);
            alertDialogBuilder.setCancelable(false).setPositiveButton("Find", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    inputs[0] = groupName.getText().toString();
                    if (inputs[0].trim().isEmpty()){
                        (Toast.makeText(MainActivity.this,"Required field missing",Toast.LENGTH_LONG)).show();
                        return;
                    }
                    intent.putExtra("input",inputs[0]);
                    intent.putExtra("type","class");
                    startActivity(intent);

                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();



        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

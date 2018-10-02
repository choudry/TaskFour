package com.example.ch_m_usman.taskfour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllStudent extends AppCompatActivity{

    ListView allStudents;
    ArrayList<Student> studentList;
    StringRequest stringRequest;

    RequestQueue requestQueue;

    AllStudentAdapter adapter;

    public static final String data = "object";
    public String input = null;
    public static final String ALLSTUDENT_URL = "http://192.168.0.106:8080/allstudents.php";
    public static final String CLASSSTUDENT_URL="http://192.168.0.106:8080/classstudents.php";
    public static final String CITYSTUDENT_URL ="http://192.168.0.106:8080/citystudents.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_student);

        requestQueue = Volley.newRequestQueue(AllStudent.this);

        allStudents = (ListView) findViewById(R.id.studenlv);

        studentList = new ArrayList<>();

        String type = getIntent().getStringExtra("type");
        Log.e("Type",type+"");
        if (type.equals("all")){
            //studentList = dataSource.findAll();
                   stringRequest = new StringRequest(Request.Method.POST,
                    ALLSTUDENT_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jObj = new JSONObject(response);
                        JSONArray array = jObj.getJSONArray("students");
                        if (jObj.getInt("success")==1) {
                            if (array.length()>0){
                                Student student;
                                for (int i = 0; i < array.length() ; i++){
                                    JSONObject object = array.getJSONObject(i);
                                    student = new Student();
                                    student.setName(object.getString("name"));
                                    student.setPhone(Long.parseLong(object.getString("phone")));
                                    student.setCity(object.getString("city"));
                                    student.setId(Long.parseLong(object.getString("id")));
                                    student.setAge(Integer.parseInt(object.getString("age")));
                                    student.setStudentclass(object.getString("class"));
                                    student.setPicture(object.getString("picture"));
                                    studentList.add(student);
                                }

                                adapter = new AllStudentAdapter(AllStudent.this,R.layout.listrow_layout,studentList);
                                allStudents.setAdapter(adapter);
                            }else {
                                Toast.makeText(getApplicationContext(),
                                        "No record found..!", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Ërror occured while creating account..!", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error:", " " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            "Volley Error:"+error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return super.getParams();
                }
            };

            // Adding request to request queue

            requestQueue.add(stringRequest);


        }else  if (type.equals("class")){
            input = getIntent().getStringExtra("input");
            stringRequest = new StringRequest(Request.Method.POST, CLASSSTUDENT_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jObj = new JSONObject(response);

                                if (jObj.getInt("success")==1) {
                                    JSONArray array = jObj.getJSONArray("students");
                                    if (array.length()>0){
                                        Student student;
                                        for (int i = 0; i < array.length() ; i++){
                                            JSONObject object = array.getJSONObject(i);
                                            student = new Student();
                                            student.setName(object.getString("name"));
                                            student.setPhone(Long.parseLong(object.getString("phone")));
                                            student.setId(Long.parseLong(object.getString("id")));
                                            student.setAge(Integer.parseInt(object.getString("age")));
                                            student.setStudentclass(object.getString("class"));
                                            student.setPicture(object.getString("picture"));
                                            studentList.add(student);
                                        }

                                        adapter = new AllStudentAdapter(AllStudent.this,R.layout.listrow_layout,studentList);
                                        allStudents.setAdapter(adapter);
                                    }else {
                                        Toast.makeText(getApplicationContext(),
                                                "No record found..!", Toast.LENGTH_LONG).show();
                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            "No record found..!", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("class",input);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
        else  if (type.equals("city")){
            Log.e("Input",getIntent().getStringExtra("input")+"");
            input = getIntent().getStringExtra("input");
            stringRequest = new StringRequest(Request.Method.POST, CITYSTUDENT_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jObj = new JSONObject(response);
                                JSONArray array = jObj.getJSONArray("students");
                                if (jObj.getInt("success")==1) {
                                    if (array.length()>0){
                                        Student student;
                                        for (int i = 0; i < array.length() ; i++){
                                            JSONObject object = array.getJSONObject(i);
                                            student = new Student();
                                            student.setName(object.getString("name"));
                                            student.setPhone(Long.parseLong(object.getString("phone")));
                                            student.setId(Long.parseLong(object.getString("id")));
                                            student.setAge(Integer.parseInt(object.getString("age")));
                                            student.setStudentclass(object.getString("class"));
                                            student.setPicture(object.getString("picture"));
                                            student.setCity(object.getString("city"));
                                            studentList.add(student);
                                        }
                                        adapter = new AllStudentAdapter(AllStudent.this,R.layout.listrow_layout,studentList);
                                        allStudents.setAdapter(adapter);
                                    }else {
                                        Toast.makeText(getApplicationContext(),
                                                "No record found..!", Toast.LENGTH_LONG).show();
                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            "No record found..!", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("city",input);
                    return params;
                }
            };

            requestQueue.add(stringRequest);

        }
        Log.e("studentlist",studentList.toString()+"");


        allStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Student st = studentList.get(i);
                Log.e("object::",st.toString());
                Intent intent = new Intent(AllStudent.this,StudentProfile.class);

                intent.putExtra("object",st);


                startActivity(intent);
            }
        });
    }
}

package com.example.ch_m_usman.taskfour;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class DeleteStudent extends AppCompatActivity {

    Button searchstudent;
    EditText etstudent;
    ListView searchlist;

    //StudentDataSource dataSource;
    ArrayList<Student> studentArrayList;
    DeleteListAdapter adapter;

    ArrayList<Student> studentList;
    StringRequest stringRequest;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student);

        requestQueue = Volley.newRequestQueue(DeleteStudent.this);
        //dataSource = new StudentDataSource(this);
        searchstudent = (Button) findViewById(R.id.searchstudent);
        etstudent = (EditText) findViewById(R.id.searchname);
        searchlist = (ListView) findViewById(R.id.searchlist);

        studentList = new ArrayList<>();

        searchstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = etstudent.getText().toString();
                studentList.clear();
                stringRequest = new StringRequest(Request.Method.POST,
                        "http://192.168.0.106:8080/findbyName.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("reso",response+"");
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
                                        student.setCity(object.getString("city"));
                                        student.setId(Long.parseLong(object.getString("id")));
                                        student.setAge(Integer.parseInt(object.getString("age")));
                                        student.setStudentclass(object.getString("class"));
                                        student.setPicture(object.getString("picture"));
                                        studentList.add(student);
                                    }

                                    adapter = new DeleteListAdapter(DeleteStudent.this,R.layout.deleterow_layout,studentList);
                                    searchlist.setAdapter(adapter);
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
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("name",name);
                        return params;
                    }
                };

                // Adding request to request queue

                requestQueue.add(stringRequest);



//
//                adapter = new DeleteListAdapter(DeleteStudent.this,R.layout.deleterow_layout,studentArrayList);
//                searchlist.setAdapter(adapter);

            }
        });
    }
}

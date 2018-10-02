package com.example.ch_m_usman.taskfour;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AddStudent extends AppCompatActivity {

    private static final int IMAGE_GALLERY_REQUEST = 1;
    EditText etName,etAge,etPhone,etClass,etCity;
    ImageButton ib;
    Button save,cancel;

    String f_pic;

    String REGISTER_URL = "http://192.168.0.106:8080/register.php";

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Submitting your data..");

        bindViews();
      //  dataSource = new StudentDataSource(this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pDialog.show();
                if (etName.getText().toString().equals("") || Integer.parseInt(etAge.getText().toString())== 0  || etCity.getText().toString().equals("") || Long.parseLong(etPhone.getText().toString())== 0 || f_pic==""){
                    pDialog.dismiss();
                    (Toast.makeText(AddStudent.this,"Required field missing..!",Toast.LENGTH_LONG)).show();
                }else {
                    final Student st = new Student();
                    st.setName(etName.getText().toString());
                    st.setStudentclass(etClass.getText().toString());
                    st.setAge(Integer.parseInt(etAge.getText().toString()));
                    st.setPhone(Long.parseLong(etPhone.getText().toString()));
                    st.setCity(etCity.getText().toString());
                    st.setPicture(f_pic);

                    StringRequest strReq = new StringRequest(Request.Method.POST,
                            REGISTER_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Response",response);
                            try {
                                JSONObject jObj = new JSONObject(response);
                                if (jObj.getInt("success")==1) {
                                    pDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Hi , You are successfully Added!", Toast.LENGTH_SHORT).show();
                                } else {
                                    pDialog.dismiss();
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
                            pDialog.dismiss();
                            Log.e("Error:", "Registration Error: " + error.getMessage());
                            Toast.makeText(getApplicationContext(),
                                    "Volley Error:"+error.getMessage(), Toast.LENGTH_LONG).show();
                            //           hideDialog();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            // Posting params to register url
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("name", st.getName());
                            params.put("class",st.getStudentclass());
                            params.put("city",st.getCity());
                            params.put("picture",st.getPicture());
                            params.put("age",st.getAge()+"");
                            params.put("phone",st.getPhone()+"");
                            return params;
                        }
                    };



                    // Adding request to request queue
                    RequestQueue requestQueue = Volley.newRequestQueue(AddStudent.this);
                    requestQueue.add(strReq);



//                    dataSource.open();

  //                  Student student = dataSource.addStudent(st);

//                    if (student.getId()== -1){
//                        (Toast.makeText(AddStudent.this,"Error occured while inserting data",Toast.LENGTH_LONG)).show();
//                    }else{
//                        (Toast.makeText(AddStudent.this,"Data inserted successfully.",Toast.LENGTH_LONG)).show();
//                    }
//                    Log.e("Data created with id ",student.getId()+"");


                }


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearData();
            }
        });

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoGalleryIntent();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_GALLERY_REQUEST && resultCode == RESULT_OK && data.getData() != null){
            Uri filePath = data.getData();
            Uri imageUri=data.getData();

            InputStream inputStream;
            try {
                inputStream=getContentResolver().openInputStream(imageUri);
                Bitmap image= BitmapFactory.decodeStream(inputStream);
                ib.setImageBitmap(image);
                Log.i("test",""+imageUri);
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG,30,stream);
                byte[] byteFormat=stream.toByteArray();
                f_pic= Base64.encodeToString(byteFormat, Base64.DEFAULT);
                //toast=Toast.makeText(getApplicationContext(),"Khan is back : "+f_pic ,Toast.LENGTH_LONG);
                //toast.show(); // IT DOES NOT SHOW IN TOAST BECAUSE IT HAS BASE 64 WHICH IS A VERY BIG TEXT



            } catch (FileNotFoundException e) {
                e.printStackTrace();
                //toast= Toast.makeText(getApplicationContext(),"Unable to open Image", Toast.LENGTH_LONG);
                //toast.show();
            }
        }
    }

    void clearData(){
        etName.setText("");
        etAge.setText("");
        etPhone.setText("");
        etClass.setText("");
        etCity.setText("");
        ib.setImageResource(R.drawable.uploadimage);
    }



    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }

    void bindViews(){
        etName = (EditText) findViewById(R.id.name);
        etAge = (EditText) findViewById(R.id.age);
        etPhone = (EditText) findViewById(R.id.phoneNumber);
        etClass = (EditText) findViewById(R.id.studentClass);
        ib = (ImageButton) findViewById(R.id.iv);
        etCity = (EditText) findViewById(R.id.city);
        save = (Button) findViewById(R.id.btn_save);
        cancel = (Button) findViewById(R.id.btn_cancel);
    }

    /**
     * Intent for photo gallery
     */
    private void photoGalleryIntent(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_GALLERY_REQUEST);
    }
}

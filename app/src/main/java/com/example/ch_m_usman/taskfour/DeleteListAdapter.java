package com.example.ch_m_usman.taskfour;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CH_M_USMAN on 27-Apr-17.
 */
public class DeleteListAdapter extends ArrayAdapter<Student> {
    ArrayList<Student> students;
    int layoutid;
    Context context;
    //StudentDataSource dataSource;
    public DeleteListAdapter(Context context, int resource, ArrayList<Student> students) {
        super(context, resource,students);
        this.context = context;
        this.layoutid = resource;
        this.students = students;
    //    dataSource = new StudentDataSource(context);

    }

    @Override
    public void remove(final Student object) {
        super.remove(object);
        DeleteListAdapter.this.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutid,parent,false);
            holder.tv = (TextView) convertView.findViewById(R.id.username);
            holder.iv = (ImageView) convertView.findViewById(R.id.userimg);
            holder.delete = (ImageView) convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setData(students.get(position));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // students.remove(position);
                final Student s = students.get(position);
                StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.0.106:8080/deletestudent.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("Ressp",response);
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    if (obj.getInt("success")==1){
                                        (Toast.makeText(context,"Student deleted succesfully..",Toast.LENGTH_LONG)).show();
                                    }else {
                                        (Toast.makeText(context,"Error occured while deleting student..",Toast.LENGTH_LONG)).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map = new HashMap<>();
                        map.put("id",s.getId()+"");
                        return map;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(request);
                DeleteListAdapter.this.remove(s);


            }
        });
        return convertView;
    }

    public class ViewHolder{
        ImageView iv,delete;
        TextView tv;

        void setData(Student student){
            tv.setText(student.getName());

            String picture=student.getPicture();
            if (picture!=null){
                byte[] imageAsBytes = Base64.decode(picture.getBytes(), 0);
//            imageView.setImageBitmap(BitmapFactory.decodeByteArray(
//                    imageAsBytes, 0, imageAsBytes.length));

                iv.setImageBitmap(BitmapFactory.decodeByteArray(
                        imageAsBytes, 0, imageAsBytes.length));

            }

        }
    }
}

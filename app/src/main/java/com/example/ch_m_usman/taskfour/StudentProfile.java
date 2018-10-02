package com.example.ch_m_usman.taskfour;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class StudentProfile extends AppCompatActivity {

    ImageView profilePic;
    TextView name, age,city,sclass,phone;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        profilePic = (ImageView) findViewById(R.id.profilePic);
        name = (TextView) findViewById(R.id.tvname);
        city = (TextView) findViewById(R.id.tvcity);
        age = (TextView) findViewById(R.id.tvage);
        sclass = (TextView) findViewById(R.id.tvclass);
        phone = (TextView) findViewById(R.id.tvphone);
//        String a = getIntent().getStringExtra(AllStudent.data);
//        Log.e("a:",a+"");

        Bundle bundle = getIntent().getExtras();

        student = getIntent().getParcelableExtra("object");
        if (student != null){
            Log.e("Bundel",bundle.getParcelable("object")+"");
            name.setText(student.getName());
            city.setText(student.getCity());
//                age.setText(student.getAge());
            sclass.setText(student.getStudentclass());
            phone.setText(student.getPhone().toString());

            String picture=student.getPicture();
            byte[] imageAsBytes = Base64.decode(picture.getBytes(), 0);
            profilePic.setImageBitmap(BitmapFactory.decodeByteArray(
                    imageAsBytes, 0, imageAsBytes.length));
            Glide.with(this).load(imageAsBytes)
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(profilePic);
        }




    }
}

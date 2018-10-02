package com.example.ch_m_usman.taskfour;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by CH_M_USMAN on 27-Apr-17.
 */
public class AllStudentAdapter extends ArrayAdapter<Student> {
    Context context;
    ArrayList<Student> studentList;
    int layoutId;
    public AllStudentAdapter(Context context, int layoutId, ArrayList<Student> studentList) {
        super(context, layoutId,studentList);
        this.context = context;
        this.layoutId = layoutId;
        this.studentList = studentList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutId,parent,false);
            holder.tv = (TextView) convertView.findViewById(R.id.displayname);
            holder.iv = (ImageView) convertView.findViewById(R.id.displaypic);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setData(studentList.get(position));
        return convertView;

    }

   public class ViewHolder{
        ImageView iv;
        TextView tv;

        void setData(Student student){
            tv.setText(student.getName());

            String picture=student.getPicture();
            if (picture!=null){
                byte[] imageAsBytes = Base64.decode(picture.getBytes(), 0);


                iv.setImageBitmap(BitmapFactory.decodeByteArray(
                        imageAsBytes, 0, imageAsBytes.length));
            Glide.with(context).load(imageAsBytes)
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(context))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iv);
            }

        }
    }
}

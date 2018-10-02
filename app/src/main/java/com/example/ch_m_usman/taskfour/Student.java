package com.example.ch_m_usman.taskfour;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CH_M_USMAN on 26-Apr-17.
 */
public class Student implements Parcelable {
   private String name;
    private String studentclass;
    private  String city;
    private  String picture;
    Long phone,id;
    int age;

    Student(Bundle bundle){
        this.name = bundle.getString("name");
        this.city = bundle.getString("city");
        this.studentclass = bundle.getString("class");
        this.age = bundle.getInt("age");
        this.phone = bundle.getLong("phone");
        this.picture = bundle.getString("picture");
    }

    public Student toStudent(Bundle bundle){
        Student student = new Student();
        student.setName(bundle.getString("name"));
        student.setCity(bundle.getString("city"));
        student.setStudentclass(bundle.getString("class"));
        student.setPicture(bundle.getString("picture"));
        student.setAge(bundle.getInt("age"));
        student.setPhone(bundle.getLong("phone"));
        return student;
    }


    public Bundle toBundle(){
        Bundle bundle = new Bundle();
        bundle.putString("name",this.name);
        bundle.putString("city",this.city);
        bundle.putString("class",this.studentclass);
        bundle.putLong("phone",this.phone);
        bundle.putInt("age",this.age);
        bundle.putString("picture",this.picture);
        return bundle;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getStudentclass() {
        return studentclass;
    }

    public void setStudentclass(String studentclass) {
        this.studentclass = studentclass;
    }


    public Student(){

    }

    public Student(Parcel in){
        id = in.readLong();
        name = in.readString();
        city = in.readString();
        studentclass  = in.readString();
        phone = in.readLong();
        age = in.readInt();
        picture = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(city);
        parcel.writeString(studentclass);
        parcel.writeLong(phone);
        parcel.writeInt(age);
        parcel.writeString(picture);
    }

    public static final Creator<Student> CREATOR = new Creator<Student>(){

        @Override
        public Student createFromParcel(Parcel parcel) {
            Student student = new Student();
            student.setId(parcel.readLong());
            student.setName(parcel.readString());
            student.setCity(parcel.readString());
            student.setStudentclass(parcel.readString());
            student.setPhone(parcel.readLong());
            student.setAge(parcel.readInt());
            student.setPicture(parcel.readString());
            return student;
        }

        @Override
        public Student[] newArray(int i) {
            return new Student[0];
        }
    };
}

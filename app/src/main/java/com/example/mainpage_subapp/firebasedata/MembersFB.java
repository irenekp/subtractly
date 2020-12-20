package com.example.mainpage_subapp.firebasedata;

import com.example.mainpage_subapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class MembersFB {
    public String member_name;

    public MembersFB(){
        //empty constructor needed for firebase
    }

    public MembersFB(String member_name){
        this.member_name = member_name;
    }

    public void insertMember(String name) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("members");
        MembersFB memberToInsert = new MembersFB(name);
        database.setValue(memberToInsert);
    }

    public void populateMembersDB(){
        MembersFB obj = new MembersFB();
        obj.insertMember( "Bobby");
        obj.insertMember( "John");
        obj.insertMember("Simba");
        obj.insertMember("Leo");
        obj.insertMember( "Ryan");
        obj.insertMember( "Dylan");
        obj.insertMember( "Chandrashekar");
        obj.insertMember( "Helios");
        obj.insertMember( "Stephen");
        obj.insertMember( "Elisabeth");
        obj.insertMember( "Michael");
        obj.insertMember( "Mark");
        obj.insertMember( "John");
        obj.insertMember( "Lennon");
        obj.insertMember( "Bryan");
    }

}

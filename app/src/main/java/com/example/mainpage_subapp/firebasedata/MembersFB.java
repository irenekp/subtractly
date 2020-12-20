package com.example.mainpage_subapp.firebasedata;

import com.example.mainpage_subapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class MembersFB {
    public int member_id;
    public String member_name;
    public String subscription_id;

    public MembersFB(){
        //empty constructor needed for firebase
    }

    public MembersFB(int member_id, String member_name, String subscription_id){
        this.member_id = member_id;
        this.member_name = member_name;
        this.subscription_id = subscription_id;
    }

    public void insertMember(int id, String name, String subid) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("members");
        MembersFB memberToInsert = new MembersFB(id, name, subid);
        database.child(subid).setValue(memberToInsert);
    }

    public void populateMembersDB(){
        MembersFB obj = new MembersFB();
        obj.insertMember(1, "Bobby", "NetFam30");
        obj.insertMember(2, "John", "SpotDuo90");
        obj.insertMember(3, "Simba", "PrimeFam365");
        obj.insertMember(4, "Leo", "NetInd30");
        obj.insertMember(5, "Ryan", "SpotFam30");
        obj.insertMember(6, "Dylan", "NetFam30");
        obj.insertMember(7, "Chandrashekar", "SpotDuo90");
        obj.insertMember(8, "Helios", "PrimeFam365");
        obj.insertMember(9, "Stephen", "NetInd30");
        obj.insertMember(10, "Elisabeth", "SpotFam30");
        obj.insertMember(11, "Michael", "SpotDuo90");
        obj.insertMember(12, "Mark", "PrimeFam365");
        obj.insertMember(13, "John", "NetInd30");
        obj.insertMember(14, "Lennon", "SpotFam30");
        obj.insertMember(15, "Bryan", "SpotDuo90");
    }

}

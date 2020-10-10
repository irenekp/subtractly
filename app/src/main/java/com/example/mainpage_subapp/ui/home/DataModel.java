package com.example.mainpage_subapp.ui.home;


public class DataModel {
    String name;
    String version;
    int id_;
    int image;
    String price;
    String timeLeft;
    String billing;
    String shared;

    public DataModel(String name, String version, int id_, int image, String price, String timeLeft, String billing, String shared) {
        this.name = name;
        this.version = version;
        this.id_ = id_;
        this.image = image;
        this.price = price;
        this.timeLeft = timeLeft;
        this.billing = billing;
        this.shared = shared;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public int getImage() {
        return image;
    }

    public int getId() {
        return id_;
    }

    public String getPrice(){ return price; }

    public String getTimeLeft(){ return timeLeft; }

    public String getBilling(){ return billing; }

    public String getShared(){ return shared; }


}

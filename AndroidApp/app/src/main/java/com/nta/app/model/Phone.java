package com.nta.app.model;

import android.text.Editable;

public class Phone {
    private String _id;
    private String manufacturer;
    private String name;
    private String price;
    private String image;
    private String display;
    private String system;
    private String camera;
    private String cpu;
    private String disk1;
    private String disk2;
    private String sim;
    private String pin;

    public Phone() {
    }

    public Phone(String _id, String manufacturer, String name, String price, String image, String display, String system, String camera, String cpu, String disk1, String disk2, String sim, String pin) {
        this._id = _id;
        this.manufacturer = manufacturer;
        this.name = name;
        this.price = price;
        this.image = image;
        this.display = display;
        this.system = system;
        this.camera = camera;
        this.cpu = cpu;
        this.disk1 = disk1;
        this.disk2 = disk2;
        this.sim = sim;
        this.pin = pin;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

//    public void setPrice(Editable price) {
//        this.price = price;
//    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getDisk1() {
        return disk1;
    }

    public void setDisk1(String disk1) {
        this.disk1 = disk1;
    }

    public String getDisk2() {
        return disk2;
    }

    public void setDisk2(String disk2) {
        this.disk2 = disk2;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setPrice(Editable text) {
    }
}

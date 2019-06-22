package com.example.iot.model;

public class Switch {
    private String pin_number;
    private int pin_value;

    public Switch() {
    }

    public Switch(String pin_number) {
        this.pin_number = pin_number;
        this.pin_value=0;
    }

    public Switch(String pin_number, int pin_value) {
        this.pin_number = pin_number;
        this.pin_value = pin_value;
    }

    public int getPin_value() { return pin_value; }

    public void setPin_value(int pin_value) { this.pin_value = pin_value; }

    public String getPin_number() { return pin_number; }

    public void setPin_number(String pin_number) { this.pin_number = pin_number; }
}

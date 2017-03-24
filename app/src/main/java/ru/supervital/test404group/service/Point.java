package ru.supervital.test404group.service;

import android.text.method.DateTimeKeyListener;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Vitaly Oantsa on 24.03.2017.
 */

public class Point implements Serializable {
    public Date date;
    public Double rate;

    public Point(Date date, Double rate) {
        this.date = date;
        this.rate = rate;
    }

    @Override
    public String toString(){
        return "created Point: " + date.toString() +":" +rate.toString();
    }
}

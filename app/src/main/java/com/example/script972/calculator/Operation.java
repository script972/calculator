package com.example.script972.calculator;

/**
 * Created by script972 on 08.05.2017.
 */

public class Operation {
    public double add(double a, double b) {
        return a+b;
    }

    public double minus(double a, double b) {
        return a-b;
    }

    public double dived(double a, double b) {
        if(b==0){
            throw new DivZero();
        }
        return a/b;
    }

    public double multiply(double a, double b) {
        return a*b;
    }
}

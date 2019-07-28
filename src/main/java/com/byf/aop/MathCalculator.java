package com.byf.aop;

public class MathCalculator {
    public int div(int i,int j){
        System.out.println("Calculator ... " + i + "/" + j + " = ?");
        return i / j;
    }
}

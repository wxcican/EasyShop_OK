package com.feicuiedu.com.easyshop.commons;

public class Preconditions {

    public static void checkNonNull(Object object, String info){
        if (object == null){
            throw new RuntimeException("CheckNonNull fail: "+ info);
        }
    }

    public static void checkCondition(boolean isPass, String info){
        if (!isPass){
            throw new RuntimeException("checkCondition fail: "+ info);
        }
    }
}

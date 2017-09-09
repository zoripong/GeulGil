package com.five.high.emirim.geulgil;

/**
 * Created by 유리 on 2017-06-19.
 */

public class M {
    private static int sWidth;
    private static int sHeight;

    public static void setSize(int width, int heigth){
        sWidth = width;
         sHeight = heigth;
    }
    public static int getWidth(){
        return  sWidth;
    }
    public static int getHeight(){
        return  sWidth;
    }

    public static final boolean [] sCatCheck = new boolean[5];
    public static final boolean [] sPartCheck = new boolean[5];

    public static boolean isNull = false;

}

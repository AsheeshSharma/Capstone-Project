package com.donteatalone.asheeshsharma.capstone_porject2.Utilities;

/**
 * Created by Asheesh.Sharma on 05-10-2016.
 */
public class TextUtils {
    public static boolean isEmpty(String text){
        if(text==null || text.contentEquals("")){
            return false;
        }
        return true;
    }
}

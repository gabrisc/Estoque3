package com.example.estoque3.util;

import android.util.Base64;

public class Base64Custom {
    public static String Code64(String uncodetext){
        return Base64.encodeToString(uncodetext.getBytes(),Base64.DEFAULT ).replaceAll("(\\n|\\r)","");
    }

    public static String Decode64(String codeText){
        return new String(Base64.encodeToString(codeText.getBytes(),Base64.DEFAULT));
    }
}

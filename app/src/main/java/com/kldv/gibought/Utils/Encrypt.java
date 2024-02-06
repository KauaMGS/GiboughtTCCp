package com.kldv.gibought.Utils;

import android.os.Message;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class Encrypt {

    public static String encryptPass (String password){

        String pass = "";
        try{
            pass = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        }catch(Exception ignored){}
        return pass;
    }

}

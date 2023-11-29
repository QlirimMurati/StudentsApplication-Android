package com.example.studentsapplication;
import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Hashing {
    public String hashString(String message, String algorithm){
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));
            return convertByteArrayToHexString(hashedBytes);
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            return "Gabim";
        }
    }

    public String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuffer.toString();
    }

    public String saltGenerator() {
        Random randNumber=new Random();
        Random randLength=new Random();
        StringBuilder sb=new StringBuilder();
        int nrCharacters=randLength.nextInt(25);
        while(nrCharacters<5) {
            nrCharacters=randLength.nextInt(25);
        }
        for (int i = 0; i <= nrCharacters; i++) {
            int nrRandom=randNumber.nextInt(26)+65;
            sb.append((char)nrRandom);
        }
        return sb.toString();
    }
}

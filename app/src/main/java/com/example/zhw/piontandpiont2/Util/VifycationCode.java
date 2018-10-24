package com.example.zhw.piontandpiont2.Util;

import java.util.Random;

//生成随机验证码的类
public class VifycationCode {
    private String Code="";
    public VifycationCode(){
        String[] a = {"a","b","c","d","e","f","g","h","i","j","k","l","q",
                "w","r","t","y","u","p","s","z","x","v","n","m","0","1","2","3","4","5","6","7","8","9"};
        for(int i=0;i<4;i++){
                Random random=new Random();
                Code += a[random.nextInt(35)];
        }
    }
    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}

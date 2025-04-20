//package com.example.demo1234;
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//public class PasswordTest {
//    public static void main(String[] args){
//        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
//
//        String rawPassword="123456";
//        String encodedPassword=passwordEncoder.encode(rawPassword);
//
//        System.out.println(rawPassword);
//        System.out.println(encodedPassword);
//
//        boolean isTrue= passwordEncoder.matches(rawPassword,encodedPassword);
//        System.out.println(isTrue);
//    }
//}

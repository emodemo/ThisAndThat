package com.pocs;

import java.util.ArrayList;
import java.util.List;

public class Autocasting {

//    public static void main(String[] args) {
//        Autocasting cast = new Autocasting();
//        Object o1 = cast.longlist().get(0);
//        Object o2 = cast.stringlist().get(0);
//        cast.toString();
//    }

    // WITHOUT GENERICS
    // requires "-Xlint:unchecked"

//    public List<Long> longlist() {
//        List list = new ArrayList<>();
//        list.add("string");
//        return list;
//    }
//
//    public List<String> stringlist() {
//        List list = new ArrayList<>();
//        list.add(1L);
//        return list;
//    }

    // WITH GENERICS

//    public List<Long> longlist() {
//        List<Long> list = new ArrayList<>();
//        list.add(1L);
//        return list;
//    }
//
//    public List<String> stringlist() {
//        List<String> list = new ArrayList<>();
//        list.add("string");
//        return list;
//    }
}

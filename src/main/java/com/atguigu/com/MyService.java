package com.atguigu.com;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class MyService {
    public static void main(String[] args) throws MalformedURLException {
        Collection<?>[] collections =
                {new HashSet<String>(), new ArrayList<String>(), new HashMap<String, String>().values()};
        Super subToSuper = new Sub();
        for(Collection<?> collection: collections) {
            System.out.println(subToSuper.getType(collection));
        }
        System.out.println(subToSuper.getType(new ArrayList<>()));
    }
}

abstract class Super {
    public static String getType(Collection<?> collection) {
        return "Super:collection";
    }
    public static String getType(List<?> list) {
        return "Super:list";
    }
    public String getType(ArrayList<?> list) {
        return "Super:arrayList";
    }
    public static String getType(Set<?> set) {
        return "Super:set";
    }
    public String getType(HashSet<?> set) {
        return "Super:hashSet";
    }
}
 class Sub extends Super {
    public static String getType(Collection<?> collection) {
        return "Sub"; }
}
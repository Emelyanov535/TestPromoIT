package MyMap_CourseWork;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        var map = new HashMap<Integer, String>();
        System.out.println(map.put(5,"q"));
        System.out.println(map.put(5,"w"));
        map.put(6,"q");
        System.out.println(map.entrySet());
        System.out.println(map);

        var myMap = new MyMap<Integer, String>();
        System.out.println(myMap.put(5,"q"));
        System.out.println(myMap.put(5,"w"));
        System.out.println(myMap.put(25,"c"));
        myMap.put(6,"q");
        System.out.println(myMap.get(5));
    }
}


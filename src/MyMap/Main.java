package MyMap;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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
        myMap.put(6,"q");
    }
}


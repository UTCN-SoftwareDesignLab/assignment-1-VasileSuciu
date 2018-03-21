package repository;

import java.util.ArrayList;
import java.util.List;

public class HelloWorld {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        List<String> list2 = new ArrayList<String>(list);
        list2.add("1");
        list2.add("2");
        list2.add("4");
        //list.removeAll(list2);
        System.out.println(list2);
        System.out.println("Hello World!");
    }
}

package com.xuxiang.study.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * Point
 *
 * @author xuxiang
 * @since 16/5/22
 */
public class Point<T> {
    private T x;
    private T y;

    public T getX() {
        return x;
    }

    public void setX(T x) {
        this.x = x;
    }

    public static <T extends Comparable> T min(T... a){
        T samllest = a[0];
        for(T item : a){
            if(samllest.compareTo(item)){
                samllest = item;
            }
        }
        return samllest;
    }

    public static void main(String[] args) {
        Point<?> point;
        point = new Point<Integer>();
        point = new Point<Long>();

        Point<? extends Comparable> cp;
        cp = new Point<StringCompare>();
        cp.getX();
//        cp.setX(new StringCompare());


        List<? super Manager> list;
        list = new ArrayList<Employee>();

//        list.add(new Employee());
        list.add(new CEO());
        list.add(new Manager());

        Object manager = list.get(0);
//        Employee employee = list.get(1);

    }
}

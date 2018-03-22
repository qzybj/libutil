package com.brady.sample.model;

/**
 * Created by zyb
 *
 * @date 2018/3/22
 * @description
 */
public class Person {
    private String id;
    private String name;
    private int boxNum;

    public Person(String id, String name, int boxNum) {
        this.id = id;
        this.name = name;
        this.boxNum = boxNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(int boxNum) {
        this.boxNum = boxNum;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", boxNum=" + boxNum +
                '}';
    }
}

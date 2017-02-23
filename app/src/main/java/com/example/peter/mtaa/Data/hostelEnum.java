package com.example.peter.mtaa.Data;

import java.util.ArrayList;

/**
 * Created by Peter on 23/Feb/17.
 */

public enum hostelEnum {
    Mladost(0, "Mladost"),
    Sturak(1, "Sturak"),
    Atriaky(2, "Atriaky"),
    Manzelaky(3, "Manzelaky"),
    Bernolak(4, "Bernolak");

    private final int value;
    private final String name;


    private hostelEnum(int value, String name) {

        this.value = value;
        this.name = name;
    }

    public static hostelEnum getByValue(int test) {
        try {
            return hostelEnum.values()[test];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Unknown enum value :"+ test);
        }
    }

    public static hostelEnum getByName(String string)
    {
        return hostelEnum.valueOf(string);
    }


    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public static ArrayList<String> getAllNames() {
        hostelEnum[] array = hostelEnum.values();
        ArrayList<String> zoznam = new ArrayList<String>();
       // String[] out = new String[array.length];
        for (int i=0;i<array.length;i++){
           zoznam.add(array[i].getName());
            // out[i] = array[i].getName();
        }
        return zoznam;
    }




}

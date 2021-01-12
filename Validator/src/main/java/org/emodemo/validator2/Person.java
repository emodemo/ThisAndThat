package org.emodemo.validator2;

public class Person {

    private String country;
    private String city;
    private int age;

    public Person(String country, String city, int age) {
        this.country = country;
        this.city = city;
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public int getAge() {
        return age;
    }
}

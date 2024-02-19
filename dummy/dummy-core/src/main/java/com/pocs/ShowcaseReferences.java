package com.pocs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ShowcaseReferences {

    public static void main(String[] args) {

        ShowcaseReferences ShowcaseReferences = new ShowcaseReferences();

        Property property = new Property(1);
        MutateMe mutateme = new MutateMe(1, "to6o", property);
        ShowcaseReferences.mutate(mutateme);
        System.out.println("shouldn't be 1: " + mutateme.getAge());
        System.out.println("shouldn't be to6o: " + mutateme.getName());
        System.out.println("shouldn't be 1: " + mutateme.getProperty().getValue());

        String s = "bbb";
        ShowcaseReferences.willNotMutate(s);
        System.out.println(s);

        MutateMe mutateme2 = mutateme;
        mutateme2.setAge(4);
        System.out.println("shouldn't be 2: " + mutateme.getAge());
    }


    public void mutate(MutateMe mutateme){
        mutateme.setAge(2);
        mutateme.setName("Go6o");
        mutateme.setProperty(new Property(2));
    }

    public void willNotMutate(String string){
        string = "aaa";
    }
}

@AllArgsConstructor
@Getter
@Setter
class MutateMe {
    private int age;
    private String name;
    private Property property;
}

@AllArgsConstructor
@Getter
@Setter
class Property {
    private int value;
}


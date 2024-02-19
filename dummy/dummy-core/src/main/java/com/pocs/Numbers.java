package com.pocs;

import java.util.Date;

public class Numbers {

    // Math operands should be cast before assignment

    public static void main(String[] args) {
        new Numbers().nonCompliant();
        new Numbers().compliant();
    }

    ///////////////////////////////////////////
    // NON-COMPLIANT CODE
    ///////////////////////////////////////////

    private void nonCompliant() {
        float twoThirds = 2/3; // Noncompliant; int division. Yields 0.0
        long millisInYear = 1_000*3_600*24*365; // Noncompliant; int multiplication. Yields 1471228928
        long bigNum = Integer.MAX_VALUE + 2; // Noncompliant. Yields -2147483647
        long bigNegNum =  Integer.MIN_VALUE-1; //Noncompliant, gives a positive result instead of a negative one.
        int seconds = 2_147_484;
        Date myDate = new Date(seconds * 1_000); //Noncompliant, won't produce the expected result if seconds > 2_147_483
        computeNonCompliant1(214749);
        computeNonCompliant2(214749);
    }

    private long computeNonCompliant1(int factor){
        return factor * 10_000;  //Noncompliant, won't produce the expected result if factor > 214_748
    }

    private float computeNonCompliant2(long factor){
        return factor / 123;  //Noncompliant, will be rounded to closest long integer
    }

    ///////////////////////////////////////////
    // COMPLIANT CODE
    ///////////////////////////////////////////

    private void compliant() {

        float twoThirds = 2f/3; // 2 promoted to float. Yields 0.6666667
        long millisInYear = 1_000L*3_600*24*365; // 1000 promoted to long. Yields 31_536_000_000
        long bigNum = Integer.MAX_VALUE + 2L; // 2 promoted to long. Yields 2_147_483_649
        long bigNegNum =  Integer.MIN_VALUE-1L; // Yields -2_147_483_649
        int seconds = 2_147_484;
        Date myDate = new Date(seconds * 1_000L);
        compute(214749);
        compute2(214749);
    }

    public long compute(int factor){
        return factor * 10_000L;
    }

    public float compute2(long factor){
        return factor / 123f;
    }
}

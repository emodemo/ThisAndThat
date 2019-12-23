package statistics;

import java.util.Scanner;

public class Statistics {

	public static void main(String[] args) {
		System.out.println(String.format("%.3f", day4GeometricDistribution2()));	
	}
	
	public static double day4GeometricDistribution1() {
		int n = 5;
		double p = 1.0/3.0;
		double q = 1.0 - p;
		double distro = Math.pow(q, (n-1)) * p;
		return distro;
	}

	public static double day4GeometricDistribution2() {
		Scanner scan = new Scanner(System.in);
        int numerator   = scan.nextInt();
        int denominator = scan.nextInt();
        int n           = scan.nextInt();
        scan.close();

        double p = (double) numerator / denominator;
        double distro = 0.0;
        for(int i = 1; i < n+1; i++) {
            distro += Math.pow(1.0-p, (i-1)) * p;
        }

        //double distro = Math.pow(1-p, (n-1)) * p;
        System.out.println(String.format("%.3f", distro));
        return distro;
	}	
	
	
	
}

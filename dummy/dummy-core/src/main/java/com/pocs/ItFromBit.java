package com.pocs;


// OR (forcing a bit to 1), AND mask (forcing a bit to 0), or XOR (forcing a bit to toggle)
// char: 2 byte, int: 4 bytes, long 8 bytes
// storage is 2^(n-1) for signed, or 2^n for unsigned
// int: 4 bytes = 32 bits = 2^(32-1) = -2,147,483,648 to 2,147,483,647
// int: 4 bytes = 32 bits = 2^32 = 4,294,967,296
public class ItFromBit {

	public static void main(String[] args) {
		// printBinaryString();
//		leftShifting();
//		rightShifting();
//		rightShiftingLogical();
//		for(int i = 0; i <= 7; i++){
//			// i=7 and i=5 must be true 128+32-160, others must be zero
//			System.out.println(getTheBit(160, i));
//		}
		// setting the 5th bit to 1 must do 160 (128+32)
		System.out.println(setTheBitTo1(128, 5));
		// setting the 5th bit to 0 must do 128 (160-32)
		System.out.println(setTheBitTo0(160, 5));
		System.out.println(toggle(128, 5));
		System.out.println(toggle(160, 5));
	}

	// NOTES:
	// 101_{10} can be written as:
	// 1x100  + 0x10   + 1x1 =
	// 1x10^2 + 0x10^1 + 1X10^0 = 101_{10}
	// convert to base 8 => look at powers of 8 less than 101 (1,8,64), where each digit can be from 0 to 7
	// 1x64   + 4x8    + 5x1  =
	// 1x8^2  + 4x8^1  + 5x8^0 = 145_{8}

	// -- the insight part
	// power:     7,  6,  5,  4, 3, 2, 1, 0
	// 2^power: 128, 64, 32, 16, 8, 4, 2, 1
	// binary     0   1   1   0  0  1  0  1 => 64+32+4+1=101_{10}

	// negative numbers: leftmost is -2^{n-1}, the other n-1 bits are interpreted as n-1 bit number


	// Example is with 8-bit integer.
	// Left shifting an integer by n is the same as multiplying by 2^n.
	// Takes the variable x and shifts it to the left n bits, filling new spaces on the right with zeros
	// 45_{10} = 101101, so 45 << 2 = 10110100 = 180_{10}
	public static void leftShifting(){
		int myNumber = 45;
		int myPower = 2;
		int shifted = myNumber << myPower; // 45 x (2^2) = 45 x 4 = 180
		System.out.println("45 << 2 = 45 x (2^2) = 45 x 4 = 180. Computed result is: " + shifted);
	}

	// Example is with 8-bit integer.
	// Takes the variable x and shifts it to the left n bits, filling new spaces left with whatever is contained in the leftmost bit
	// 45_{10} = 00101101, so 45 >> 2 = 00001011 = 11_{10}
	public static void rightShifting(){
		int myNumber = 45;
		int myPower = 2;
		int shifted = myNumber >> myPower; // 45 x (2^2) = 45 x 4 = 11
		System.out.println("45 >> 2 = 45 x (2^2) = 45 x 4 = 11. Computed result is: " + shifted);
		// for positive numbers arithmetic and logical right-shifts are the same.
		// for negative numbers it is different
		myNumber = -45; // 11010011
		shifted = myNumber >> myPower;
		System.out.println("-45 >> 2 = -12. Computed result is: " + shifted);
	}

	// Example is with 8-bit integer.
	// Takes the variable x and shifts it to the left n bits, filling new spaces left with whatever is contained in the leftmost bit
	// 45_{10} = 00101101, so 45 >>> 2 = 00001011 = 11_{10}
	public static void rightShiftingLogical(){
		int myNumber = 45;
		int myPower = 2;
		int shifted = myNumber >>> myPower; // 45 / (2^2) = 45 / 4 = 11
		System.out.println("45 >>> 2 = 45 / (2^2) = 45 / 4 = 11. Computed result is: " + shifted);
		// for positive numbers arithmetic and logical right-shifts are the same.
		// for negative numbers it is different
		myNumber = -45; // 11010011
		shifted = myNumber >>> myPower;
		// will not print 52, as in java integers are 32 bits // byte is 8 bits, and short is 16 bits
		System.out.println("-45 >>> 2 = 52. Computed result is: " + shifted);
		// When making a logical right-shift of a negative number,
		// the answer will depend on the number of bits that are used to store the integer.
		// Some languages that have variable-sized integers (notably Python) don't have a logical right-shift.
	}

	public static void printBinaryString(){
		for(int i = 0; i < 1025; i++) {
			System.out.println(i + " " + Integer.toBinaryString(i));
		}
	}

	// GET the i-th bit // number & mask
	// We can find the value of the i-th bit of n by using (n & (1 << i)) != 0
	// AND(0,x) = 0, i.e. AND-ing with zero ignores that bit
	// AND(1,x) = x, i.e. AND-ing with one returns the value of the bit
	// The number 1 << i is the number with 0 everywhere, except for a one in the ith position.
	// Therefore n & (1 << i) ensures all bits except the ith bit is zero.
	public static boolean getTheBit(int number, int bit){
		return (number & (1 << bit)) != 0;
	}

	public static int getLastXbits(int number, int x){
		return number & ((1 << x) -1);
	}

	// from a to b inclusive, hence +1
	public static int getBitsFromAtoB(int number, int a, int b){
		return number & ((1 << (b-a+1)) -1);
	}


	// SET the i-th bit to 1
	// We can get a number that is n with the ith position changed to 1 with the code n | ( 1 << i )
	// OR(0,x) = x, i.e. OR-ing with zero returns the value of the bit.
	// OR(1,x) = 1, i.e. OR-ing with one always gives one.
	public static int setTheBitTo1(int number, int bit){
		return (number | (1 << bit));
	}

	// SET the i-th bit to 0
	// We can force a bit to zero using AND(0,x) = 0.
	// We can preserve the other bits by using AND(1,x) = x.
	// So if we can make a number mask that has all of its bits as 1 except the ith bit, we want to return n & mask.
	// To generate mask. note that we know how to set only the ith bit with (1 << i).
	// Hence, we want the bitwise NOT of this: mask = ~(1 << i).
	public static int setTheBitTo0(int number, int bit){
		return (number & (~(1 << bit)));
	}

	public static int toggle(int number, int bit){
		return (number ^ (1 << bit));
	}

}

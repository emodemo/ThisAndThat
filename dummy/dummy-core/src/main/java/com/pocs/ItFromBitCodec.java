package com.pocs;

public class ItFromBitCodec {

	private static final int ACCOUNT_ACTIVE = 1;
	private static final int ACCOUNT_PREMIUM = 2;
	private static final int ACCOUNT_PREPAID = 4;
	private static final int ACCOUNT_WHATEVER = 8;
	private static final int ACCOUNT_SOMETHING = 16;
	private static final int ACCOUNT_NOTHING = 32;
	private static final int ACCOUNT_NOT = 64;
	private static final int ACCOUNT_JUST_ACCOUNT = 128;

	public static void main(String[] args) {
		basicCodec();
	}

	private static void basicCodec(){
		int accountInformation = ACCOUNT_ACTIVE + ACCOUNT_SOMETHING + ACCOUNT_PREMIUM;
		System.out.println(accountInformation);
		System.out.println(hasProperty(accountInformation, ACCOUNT_ACTIVE)); //true
		System.out.println(hasProperty(accountInformation, ACCOUNT_PREMIUM)); //true
		System.out.println(hasProperty(accountInformation, ACCOUNT_PREPAID));
		System.out.println(hasProperty(accountInformation, ACCOUNT_WHATEVER));
		System.out.println(hasProperty(accountInformation, ACCOUNT_SOMETHING)); // true
		System.out.println(hasProperty(accountInformation, ACCOUNT_NOTHING));
		System.out.println(hasProperty(accountInformation, ACCOUNT_NOT));
		System.out.println(hasProperty(accountInformation, ACCOUNT_JUST_ACCOUNT));
	}

	// there is also java.util.BitSet
	private static boolean hasProperty(int settings, int property){
		int bit = 0; // log2(property)
		while ((property >>= 1) > 0) bit ++;
		return ItFromBit.getTheBit(settings, bit);
	}

	private static boolean hasPropertyV2(int settings, int property){
		//int bit = (int)(Math.log(property) / Math.log(2)); // log2
		int bit = -1;
		while (property > 0) {
			property >>= 1;
			bit ++;
		}
		return ItFromBit.getTheBit(settings, bit);
	}

}

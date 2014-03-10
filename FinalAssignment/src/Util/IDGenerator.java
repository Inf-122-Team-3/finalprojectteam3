/* Code taken from SimSe */
package Util;

import java.util.ArrayList;
import java.util.Random;

public class IDGenerator {
	private static ArrayList<Integer> usedIDs = new ArrayList<Integer>();
	private static Random ranNumGen = new Random();

	public static int getNextID() {
		Integer int1 = new Integer(Math.abs(ranNumGen.nextInt()));
		if (usedIDs.contains(int1)) {
			return getNextID();
		} else {
			usedIDs.add(int1);
			return int1.intValue();
		}
	}
}

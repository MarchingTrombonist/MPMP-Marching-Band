import java.io.*;
import java.util.*;

public class Functions {
	
	// Generates prime factors
	public static Long[] primeFactors(long num) {
		String factors = "";

		long modBy = 2;

		// Loops until it reaches 1
		while (num != 1) {
			// Divides until it can't anymore and then increments the divisor
			// Appends divisor onto string each time
			while (num % modBy == 0) {
				factors += modBy + ",";
				num /= modBy;
			}
			modBy++;
		}
		// Removes trailing ','
		factors = factors.substring(0, factors.length() - 1);
		
		// Turns the string of factors into an array
		String[] strArray = factors.split(",");
		Long[] factorArray = new Long[strArray.length];

		// Turns the array of factors from Strings to longs
		int i = 0;
		for (String str : strArray) {
			factorArray[i] = Long.parseLong(strArray[i]);
			i++;
		}
		return factorArray;
	}

	// Generates the counts of prime factors
	public static Long[] factorCounts(long num) {
		Long[] factors = primeFactors(num);

		ArrayList<Long> counts = new ArrayList<Long>();
		int check = 0;
		long count = 0;

		// Counts how many times each prime factor appears and puts it in a list
		for (int i = 0; i < factors.length; i++) {
			if (factors[i] == factors[check]) {
				count++;
			} else {
				counts.add(count);
				check = i;
				count = 1;
			}
		}
		counts.add(count);

		// Turns the list of counts into an array
		Long[] countsArray = new Long[counts.size()];

		for (int i = 0; i < counts.size(); i++) {
			countsArray[i] = counts.get(i);
		}

		return countsArray;
	}

	// Calculates the total factors from the factor counts
	public static long factorTotal(long num) {
		Long[] factorCounts = factorCounts(num);

		long factorTotal = 1;

		for (long i : factorCounts) {
			factorTotal *= (i + 1);
		}

		return factorTotal;
	}

	// Creates a list of all factors
	public static long[] listFactors(long num) {
		int count = 0;

		long[] factorList = new long[(int) factorTotal(num)];

		// Increments from 1 to the square root of num and puts it in the list if it's a factor
		for (long i = 1; i <= Math.sqrt(num); i++) {
			if (num % i == 0) {
				factorList[count] = i;
				count++;
			}
		}

		// Generates the second half of the factors from the first half
		for (int i = 0; i < factorList.length; i++) {
			if (factorList[factorList.length - i - 1] != 0) {
				factorList[i] = num / factorList[factorList.length - i - 1];
			}
		}

		return factorList;
	}
	
	// Finds the smallest integer with n factors
	public static String[] smallest(long num) {
		Long[] primeFactorsShortened = shortenRepeatStarter(primeFactors(num))[0];
		
		long smallest = 1;
		String smallestString[] = {"", "", ""};
		
		Collections.reverse(Arrays.asList(primeFactorsShortened));

		// For each prime factor of num, multiplies the prediction by the nth prime ^ (nth prime factor - 1)
		for (int i = 0; i < primeFactorsShortened.length; i++) {
			smallest *= (long) Math.pow(prime(i + 1), primeFactorsShortened[i] - 1);
			smallestString[0] += (prime(i + 1) + "^" + (primeFactorsShortened[i] - 1) + " * ");
		}

		smallestString[0] = smallestString[0].substring(0, smallestString[0].length() - 3);

		if (smallest > 0) {
			smallestString[1] += (" = " + smallest);
		}

		smallestString[2] += smallest;

		return smallestString;
	}

	// Finds the nth prime number
	public static long prime(long num) {
		ArrayList<Long> primes = new ArrayList<Long>();
		// Starts with 2
		Long two = new Long(2);
		primes.add(two);
		long check = 0;
		boolean prime = true;

		// Divides integers by every prime smaller until the nth prime is reached
		while (primes.size() < num) {
			for (int i = 0; i < primes.size(); i++) {
				if ((check % primes.get(i) == 0) || (check < primes.get(i))) {
					prime = false;
					break;
				}
			}
			if (prime) {
				primes.add(check);
			}
			check++;
			prime = true;
		}

		return primes.get((int) num - 1);
	}

	// Determines if a number is prime
	public static boolean isPrime(long num) {
		ArrayList<Long> primes = new ArrayList<Long>();
		Long two = new Long(2);
		primes.add(two);
		long check = 0;
		boolean prime = true;

		// Generates primes until it either reaches the given number or goes over
		while (primes.get(primes.size() - 1) < num) {
			for (int i = 0; i < primes.size(); i++) {
				if ((check % primes.get(i) == 0) || (check < primes.get(i))) {
					prime = false;
					break;
				}
			}
			if (prime) {
				primes.add(check);
			}
			check++;
			prime = true;
		}

		// If the last prime found matches the given number, then it is prime
		if (num == primes.get(primes.size() - 1)) {
			return true;
		}
		return false;
	}

	// Turns a list of factors into a shorter list
	public static Long[] shorten(Long[] factorList) {

		Collections.reverse(Arrays.asList(factorList));
		
		int pos = -1;
		long first;
		long last;
		long p1Top;
		long p1Bottom;
		long p1;
		long pN;

		do {
			pos++;
			first = factorList[pos];
			last = factorList[factorList.length - 1];
			p1Top = (long) Math.pow(prime(pos + 1), (first * last - 1));
			p1Bottom = (long) Math.pow(prime(pos + 1), (first - 1));
			p1 = p1Top / p1Bottom;
			pN = (long) Math.pow(prime(factorList.length), (last - 1));
			if (p1 < pN) {
				factorList[pos] = first * last;

				Long[] newFactorList = new Long[factorList.length - 1];
				for (int i = 0; i < newFactorList.length; i++) {
					newFactorList[i] = factorList[i];
				}
				
				Collections.reverse(Arrays.asList(factorList));
				Collections.reverse(Arrays.asList(newFactorList));

				return newFactorList;
			}

		} while (pos < factorList.length - 1);

		Collections.reverse(Arrays.asList(factorList));

		return factorList;
	}

	// Starts the recursion to completely shorten the factors
	public static Long[][] shortenRepeatStarter(Long[] factorListA) {
		int step = 0;

		if (factorListA.length == 1) {
			Long[][] factorListC = {factorListA, {}};
			System.out.println("\nStep " + step + ": " + factorListA[0]);
			return factorListC;
		}

		System.out.print("\nStep " + step + ": ");
		for (long i : factorListA) {
			System.out.print(i + " ");
		}
		System.out.println();
		step++;

		Long[] factorListB = factorListA;
		factorListA = shorten(factorListA);

		Long[][] factorListAB = {factorListA, factorListB};
		Long[][] factorListC = {shortenRepeat(factorListAB, step),{}};
		return factorListC;
	}

	// Recurses to completely shorten the factors
	public static Long[] shortenRepeat(Long[][] factorListAB, int step) {
		Long[] factorListA = factorListAB[0];
		Long[] factorListB = factorListAB[1];

		if (Arrays.equals(factorListA, factorListB)) {
			return factorListA;
		} else {
			factorListB = factorListA;

			Collections.reverse(Arrays.asList(factorListA));

			System.out.print("Step " + step + ": ");
			for (long i : factorListA) {
				System.out.print(i + " ");
			}
			System.out.println();
			step++;

			Collections.reverse(Arrays.asList(factorListA));

			factorListA = shorten(factorListA);
			Long[][] factorListAB2 = {factorListA, factorListB};
			factorListA = shortenRepeat(factorListAB2, step);
		}

		return factorListA;
	}
}

import java.io.*;
import java.util.*;

public class Factoring {
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);

		System.out.println("Welcome to Factor Thingy");

		int option;
		int num;
		String str;

		do {
			System.out.print("\nMenu:\n-1: Quit\n0: Find the smallest positive integer with n factors\n1: Find the factors of a positive integer\n\nEnter your choice: ");

			option = inputValidation();

			// Runs desired function
			switch (option) {

				case -2:
					System.out.println("Invalid input. Please try again.");
				break;

				case -1:
				break;

				// Loops printing predicted smallest and actual smallest for given factor count
				case 0:
					do {
						System.out.print("\nEnter your positive integer (-1 to quit to menu): ");
						num = inputValidation();

						switch (num) {

							case -2:
								System.out.println("Invalid input. Please try again.");
							break;

							case -1:
							break;

							case 0:
								System.out.println("There are no positive integers with 0 factors.");
							break;

							case 1:
								System.out.println("The only positive integer with 1 factor is 1.");
							break;

							default:
								String[] smallest = Functions.smallest(num);
								smallestPrint(smallest);

								System.out.print("List all factors? (y/n): ");
								str = input.next();
								if (str.charAt(0) == 'y') {
									factorPrint(Long.parseLong(smallest[2]));
								}
							break;
						}
					} while (num != -1);
				break;

				// Prints all the information about the factors
				case 1:
					do {
						System.out.print("\nEnter your positive integer (-1 to quit to menu): ");
						num = inputValidation();

						switch (num) {

							case -2:
								System.out.println("Invalid input. Please try again.");
							break;

							case -1:
							break;

							case 0:
								System.out.println("Every number is a factor of 0.");
							break;

							case 1:
								System.out.println("1 is the only factor of 1.");
							break;

							default:
								primeFactorPrint(num);
								factorCountsPrint(num);
								factorTotalPrint(num);

								System.out.print("List all factors? (y/n): ");
								str = input.next();
								if (str.charAt(0) == 'y') {
									factorPrint(num);
								}
							break;
						}
					} while (num != -1);
				break;

				// Prints just the path it takes to shorten the array
				case 2:
					do {
						System.out.print("\nEnter your positive integer (-1 to quit to menu): ");
						num = inputValidation();

						switch (num) {

							case -2:
								System.out.println("Invalid input. Please try again.");
							break;

							case -1:
							break;

							case 0:
								System.out.println("This doesn't work for 0.");
							break;

							case 1:
								System.out.println("1 has no prime factors and cannot be shortened.");
							break;

							default:
								shortenPrint(num);
							break;
						}
					} while (num != -1);
				break;

				// Default
				default:
					System.out.println("Invalid input. Please try again.");
				break;
			}
		} while (option != -1);


		System.out.println("Thank you for using Factor Thingy");

		input.close();
	}


	// Makes sure that the inputs work
	// Returns -2 if the input is invalid (less than -1 or not an integer)
	public static int inputValidation() {
		Scanner input = new Scanner(System.in);

		int num;
		
		try {
			num = Integer.parseInt(input.nextLine());
			if (num < -1) {
				throw new InputMismatchException();
			}
		} catch (Exception e) {
			num = -2;
		}

		return num;
	}

	// Prints prime factors
	public static void primeFactorPrint(long num) {
		Long[] factorList = Functions.primeFactors(num);
					
		System.out.print("Prime Factors: ");
		for (long i : factorList) {
			System.out.print(i + " ");
		}
		System.out.println("\n");
	}

	// Prints factor counts
	public static void factorCountsPrint(long num) {
		Long[] factorCounts = Functions.factorCounts(num);

		System.out.print("Factor Counts: ");
		for (long i : factorCounts) {
			System.out.print(i + " ");
		}
		System.out.println("\n");
	}

	// Prints total factors
	public static void factorTotalPrint(long num) {
		long factorTotal = Functions.factorTotal(num);

		System.out.println("Total Factors: " + factorTotal + "\n");
	}

	// Prints smallest
	public static void smallestPrint(String[] str) {
		String[] smallest = str;

		System.out.println("\nSmallest: " + smallest[0] + smallest[1] + "\n");
	}

	// Prints shorten path
	public static void shortenPrint(long num) {
		Functions.shortenRepeatStarter(Functions.primeFactors(num));
	}

	// Prints all factors
	public static void factorPrint(long num) {
		long[] output = Functions.listFactors(num);
		for (long i : output) {
			System.out.println(i + " * " + num / i);
		}
	}

}
//Author: Jeremy McMahan
import java.util.Scanner;

public class Job {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter initial ballance: ");
		int t = input.nextInt();
		Budget myBudget = new Budget(t);
		String s;

		Print();
		int option = input.nextInt();
		while(option <= 7 && option >= 1) {
			if (option == 1) {
				System.out.print("Change: 1. Name\n2. Balance");
				option = input.nextInt();
				if (option == 1) {
					System.out.print("New name: ");
					s = input.next();
					System.out.print("To Category: ");
					myBudget.changeCategoryName(input.next(), s);
				} else if (option == 2) {
					System.out.print("New balance: ");
					option = input.nextInt();
					System.out.print("To Category: ");
					myBudget.changeCategoryBalance(input.next(), option);
				} else {
					System.out.println("Invalid Option");
				}
			} else if (option == 2) {
				System.out.print("Category name: ");
				s = input.next();
				System.out.print("balance: ");
				myBudget.addCategory(s, input.nextInt());
			} else if (option == 3) {
				System.out.print("SubCategory name: ");
				s = input.next();
				System.out.print("balance: ");
				option = input.nextInt();
				System.out.print("To Category: ");
				myBudget.addSubCategory(input.next(), s, option);
			} else if (option == 4) {
				System.out.print("Item name: ");
				s = input.next();
				System.out.print("balance: ");
				option = input.nextInt();
				System.out.print("To Category: ");
				myBudget.addItem(input.next(), s, option);
			} else if (option == 5) {
				System.out.println("Delete: ");
				System.out.println("1. Category\n2. Item");
				option = input.nextInt();
				if (option == 1) {
					System.out.print("Category: ");
					myBudget.deleteCategory(input.next());
				} else if (option == 2) {
					System.out.print("Item name: ");
					s = input.next();
					System.out.print("From Category: ");
					myBudget.deleteItemFrom(input.next(), s);
				} else {
					System.out.println("Invalid option");
				}
			} else if (option == 6) {
				System.out.println("Set current to: ");
				myBudget.goToCategory(input.next());
			} else {
				myBudget.PrintBudget();
			}

			Print();
			option = input.nextInt();
		}

	}

	public static void Print() {
		System.out.println("1. Change Category");
		System.out.println("2. Add Category");
		System.out.println("3. Add SubCategory");
		System.out.println("4. Add Item");
		System.out.println("5. Delete");
		System.out.println("6. Set");
		System.out.println("7. Print");
	}
}

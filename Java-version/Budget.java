//Author: Jeremy McMahan
import java.util.ArrayList;
import java.util.Iterator;

//consider making item an outside class!
//ADD ERROR HANDELING!!!
class BudgetTypes implements Comparable<BudgetTypes> {
	public class item implements Comparable<item> {
		String name;
		int amount;

		public item() {

		}

		public item(String name, int amount) {
			this.name = name;
			this.amount = amount;
		}
		@Override
		public boolean equals(Object other) {
			if (name.equals(((item) other).name)) {
				return true;
			}
			return false;
		}

		public int compareTo(item other) {
			return other.name.compareTo(name);
		}

		@Override
		public String toString() {
			return "Item: " + name + " $" + amount;
		}
	}
	public String CategoryName;
	public int currentBalance;
	public int initialBalance;
	private ArrayList<item> items;

	public BudgetTypes() {
		items = new ArrayList<item>();
	}

	public BudgetTypes(String CategoryName, int balance) {
		this.CategoryName = CategoryName;
		initialBalance = balance;
		currentBalance = balance;
		items = new ArrayList<item>();
	}
	//amount must be positive!
	public void addItem(String name, int amount) {
		items.add(new item(name, amount));
		currentBalance -= amount;
	}
	//consider returning an item type and making item an outside class
	public int getItem(String name) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).name.equals(name)) {
				return items.get(i).amount;
			}
		}
		return -1;
	}

	public Iterator iterator() {
		return items.iterator();
	}

	public boolean isEmpty() {
		if (items.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public int deleteItem(String name) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).name.equals(name)) {
				int value = items.get(i).amount;
				currentBalance += value;
				items.remove(i);
				return value;
			}
		}
		return -1;
	}

	public int deleteAllItems() {
		int value = initialBalance - currentBalance;
		currentBalance = initialBalance;
		items = null;
		return value;
	}
	@Override
	public boolean equals(Object other) {
		if (CategoryName.equals(((BudgetTypes) other).CategoryName)) {
			return true;
		}
		return false;
	}

	public int compareTo(BudgetTypes other) {
		return other.CategoryName.compareTo(CategoryName);
	}
	@Override
	public String toString() {
		return CategoryName + " Initial: $" + initialBalance + " Current: $" + currentBalance;
	}
}

public class Budget {
	private Category<BudgetTypes> checkBook; // the main category will hold the data for the total budget

	public Budget() {
		checkBook = new Category<BudgetTypes>(new BudgetTypes("", 0));
	}

	public Budget(int balance) {
		checkBook = new Category<BudgetTypes>(new BudgetTypes("", balance));
	}

	public void changeInitialBalance(int newBalance) {
		int additional = newBalance - checkBook.getData().initialBalance;
		checkBook.getData().initialBalance = newBalance;
		checkBook.getData().currentBalance += additional;
	}

	public void addCategory(String name, int balance) {
		checkBook.resetCategory();
		checkBook.addSubCategory(new BudgetTypes(name, balance));
	}

	public void addSubCategory(String parent, String name, int balance) {
		goToCategory(parent);
		checkBook.addSubCategory(new BudgetTypes(name, balance));
	}

	private void addCategoryToCurrent(String name, int balance) {
		checkBook.addSubToCurrent(new BudgetTypes(name, balance));
	}
	//FIX ERROR Handeling!!!
	public void goToCategory(String name) {
		try {
			checkBook.setCategory(new BudgetTypes(name, 0));
		} catch (Exception e) {
			System.out.println(name + " not found");
		}
	}

	public void changeCategoryName(String CategoryName, String newName) {
		goToCategory(CategoryName);
		changeCurrentName(newName);
	}

	public void changeCategoryBalance(String CategoryName, int newBalance) {
		goToCategory(CategoryName);
		changeCurrentBalance(newBalance);
	}

	private void changeCurrentName(String newName) {
		checkBook.getData().CategoryName = newName;
	}

	private void changeCurrentBalance(int newBalance) {
		int additional = newBalance - checkBook.getData().initialBalance;
		checkBook.getData().initialBalance = newBalance;
		checkBook.getData().currentBalance += additional;
	}

	private void addCurrentItem(String itemName, int amount) {
		checkBook.getData().addItem(itemName, amount);
		do {
			checkBook.Traverse(Category.PARENT);
			checkBook.getData().currentBalance -= amount;
		}
		while (!checkBook.getData().CategoryName.equals(""));
	}

	public void addItem(String CategoryName, String itemName, int amount) {
		goToCategory(CategoryName);
		checkBook.getData().addItem(itemName, amount);
		do {
			checkBook.Traverse(Category.PARENT);
			checkBook.getData().currentBalance -= amount;
		}
		while (!checkBook.getData().CategoryName.equals(""));
	}
	//change to private
	private void deleteCurrentItem(String itemName) {
		int value = checkBook.getData().deleteItem(itemName);
		do {
			checkBook.Traverse(Category.PARENT);
			checkBook.getData().currentBalance += value;
		}
		while (!checkBook.getData().CategoryName.equals(""));
	}

	public void deleteItemFrom(String CategoryName, String itemName) {
		goToCategory(CategoryName);
		deleteCurrentItem(itemName);
	}

	private void deleteCurrentAllItems() {
		int value = checkBook.getData().deleteAllItems();
		do {
			checkBook.Traverse(Category.PARENT);
			checkBook.getData().currentBalance += value;
		}
		while (!checkBook.getData().CategoryName.equals(""));
	}

	public void deleteAllItemsFrom(String CategoryName) {
		goToCategory(CategoryName);
		deleteCurrentAllItems();
	}

	//deletes the category and returns the amounts from added items
	private void deleteCurrentCategory() {
		//traverse through subtree each time deleting all items then use checkbook.removeCategory
		int value = checkBook.getData().initialBalance - checkBook.getData().currentBalance;
		checkBook.removeSubCategory();
		do {
			checkBook.Traverse(Category.PARENT);
			checkBook.getData().currentBalance += value;
		}
		while (!checkBook.getData().CategoryName.equals(""));
	}

	public void deleteCategory(String CategoryName) {
		goToCategory(CategoryName);
		deleteCurrentCategory();
	}

	//use Traverse to print similarly to the Category.Print() so tabs will be outputed correctly!
	public void PrintBudget() {
		checkBook.Print();
		// Iterator<BudgetTypes> i = checkBook.iterator();
		// BudgetTypes temp = i.next();
		// String numTabs;
		// System.out.println("Initial Balance: " + temp.initialBalance + " Current Balance: " + temp.currentBalance);
		// while(i.hasNext()) {
		// 	temp = i.next();
		// 	if (((Tree.TreeIterator) i).getTreeLevel() == 0) {
		// 		System.out.println("Category: " + temp.CategoryName + " Initial Balance: " + temp.initialBalance + " Current Balance: " + temp.currentBalance);
		// 		System.out.println("-------------------------------------------------------------------------------------------");
		// 	} else {
		// 		numTabs = "";
		// 		for (int j = 0; j < ((Tree.TreeIterator) i).getTreeLevel(); j++) {
		// 			numTabs += "\t";
		// 		}
		// 		Iterator t = temp.iterator();
		// 		while (t.hasNext()) {
		// 			System.out.println(numTabs + t.next());
		// 		}
		// 		System.out.println(numTabs + "Sub Category: " + temp);

		// 	}
		// }

	}

}

class check implements Comparable<check> {
	public double ballance;
	public String name;
	
	public check() {}
	
	public check(String name) {
		this.name = new String(name);
	}
	
	public check(String name, double ballance) {
		this.name = new String(name);
		this.ballance = ballance;
	}
	
	@Override
	public int compareTo(check o) {
		if (this.name.equals(o.name)) {
			return 0;
		} else if (ballance > o.ballance) {
			return -1;
		} else {
			return 1;
		}
	}

	public void setBallance(double newBallance) {
		this.ballance = newBallance;
	}
	
	public String toString() {
		return name + " $" + ballance;
	}
	
}

class Item implements Comparable<Item>{
	double cost;
	String name;
	
	public Item() {
		
	}
	
	public Item(String name, double cost) {
		this.name = new String(name);
		this.cost = cost;
	}
	
	public boolean equals(Item other) {
		return name.equals(other.name);
	}
	
	public boolean equals(String other) {
		return name.equals(other);
	}
	
	public String toString() {
		return "Item: " + name + " $" + cost;
	}

	@Override
	public int compareTo(Item o) {
		return name.compareTo(o.name);
	}
}

public class Budget {
	private Category<Item, check> table;
	private Category<Item, check> current;
	private double initialBallance;
	private double editedBallance;
	
	public Budget() {}
	
	//edit for current
	public void delete(String s) {
		if (s.compareTo(table.getElement().name) == 0) {
				if (table.getPrev() != null) {
					table.getPrev().setNext(table.getNext());
					if (table.getSubCategory() != null) {
						table.getSubCategory().setPrev(table.getPrev());
					}
				} else {
					if (table.getSubCategory() != null && table.getNext() != null) {
						Category<Item, check> i = table.getSubCategory();
						for (; i.getNext() != null; i = i.getNext());
						i.setNext(table.getNext());
						table = table.getSubCategory();
					} else {
						table = table.getNext();
					}
				}
		} else {
			table.delete(new check(s));
		}
	}

	public Budget(double ballance) {
		initialBallance = ballance;
		editedBallance = ballance;
	}
	
	public Category<Item, check> FindCategory(String name) {
		Category<Item, check> temp = table.Find(new check(name));
		if (temp != null) {
			current = temp;
			return temp.Clone();
		} else {
			return null;
		}
	}
	
	public void AddCategory(String name, double ballance) {
		if (table == null) {
			table = new Category<Item, check>(new check(name, ballance));
			current = table;
		} else {
			Category<Item, check> i;
			for (i = table; i.getNext() != null; i = i.getNext());
			i.setNext(new Category<Item, check>(new check(name, ballance)));
			current = i.getNext();
		}
	}
	
	public void AddSubCategory(String name, double ballance, String nameOfCategory) {
		Category<Item, check> temp = table.Find(new check(nameOfCategory));
		if (temp != null) {
			temp.CreateSubCategory(new check(name, ballance));
		} else {
			System.out.println("Invalid Category: " + nameOfCategory);
		}
	}
	
	public void AddSubCategory(String name, double ballance) {
		Category<Item, check> temp = current;
		if (temp != null) {
			temp.CreateSubCategory(new check(name, ballance));
		} else {
			System.out.println("Invalid Category: " + current);
		}
	}
	
	public void changeBallance(double newBallance) {
		if (newBallance >= 0) {
			editedBallance = newBallance - initialBallance;
			initialBallance = newBallance;
			
		} else {
			System.out.println("Invalid Ballance: " + newBallance);
		}
	}
	
	public void changeBallanceOfCategory(double newBallance, String nameOfCategory) {
		Category<Item, check> temp = table.Find(new check(nameOfCategory));
		if (temp != null) {
			if (newBallance >= 0) {
				temp.getElement().setBallance(newBallance);
			} else {
				System.out.println("Invalid Ballance: " + newBallance);
			}
		} else {
			System.out.println("Invalid Category: " + nameOfCategory);
		}
	}
	
	public double getNewBallance() {
		return editedBallance;
	}
	
	public double getInitialBallance() {
		return initialBallance;
	}
	
	public void printBallance() {
		System.out.println("Initial Ballance: " + initialBallance);
		System.out.println("Current Ballance: " + editedBallance);
	}
	
	public Category<Item, check> findCategory(String name) {
		current = table.Find(new check(name));
		return current;
	}
	
	public Item findItem(String name) {
		current = table.findItemCategory(new Item(name, 0));
		return current.getItem(new Item(name, 0));
	}
	
	public Category<Item, check> getCurrent() {
		return current.Clone();
	}
	
	public void addItem(String name, double cost, String nameOfCategory) {
		editedBallance -= cost;
		Category<Item, check> temp = table.Find(new check(nameOfCategory));
		if (temp != null) {
			temp.addItem(new Item(name, cost));
			Category<Item, check> i;
			for (i = temp; i != null; i = i.Traverse("parent", 1)) {
				i.getElement().setBallance(i.getElement().ballance - cost);
			}
		} else {
			System.out.println("Invalid Category: " + nameOfCategory);
		}
		
	}
	
	public void addItem(String name, double cost) {
		editedBallance -= cost;
		Category<Item, check> temp = current;
		if (temp != null) {
			temp.addItem(new Item(name, cost));
			Category<Item, check> i;
			for (i = temp; i != null; i = i.Traverse("parent", 1)) {
				i.getElement().setBallance(i.getElement().ballance - cost);
			}
		} else {
			System.out.println("Invalid Category: " + current);
		}
	}
	
	public void PrintCurrentCategory() {
		current.PrintCategory();
	}
	
	public void removeItem(String name) {
		Item j = table.findItem(new Item(name, 0));
		if (j != null) {
			double cost = j.cost;
			editedBallance += cost;
			Category<Item, check> temp = table.findItemCategory(new Item(name, 0));
			if (temp != null) {
				temp.removeItem(new Item(name, 0));
				Category<Item, check> i;
				for (i = temp; i != null; i = i.Traverse("parent", 1)) {
					i.getElement().setBallance(i.getElement().ballance + cost);
				}
			} else {
				System.out.println("Invalid Category: ");
			}
		} else {
			System.out.println("Invalid Item: " + name);
		}
	}
	
	public void removeItem(String name, String nameOfCategory) {
		Item j = table.findItem(new Item(name, 0));
		if (j != null) {
			double cost = j.cost;
			editedBallance += cost;
			Category<Item, check> temp = table.Find(new check(nameOfCategory));
			if (temp != null) {
				temp.removeItem(new Item(name, 0));
				Category<Item, check> i;
				for (i = temp; i != null; i = i.Traverse("parent", 1)) {
					i.getElement().setBallance(i.getElement().ballance + cost);
				}
			} else {
				System.out.println("Invalid Category: " + nameOfCategory);
			}
		} else {
			System.out.println("Invalid Item: " + name);
		}
	}
	
	public void PrintBudget() {
		System.out.println("Initial Budget: " + initialBallance + " Current Ballance: " + editedBallance);
		if (table != null) {
			table.PrintCategoryTree();
		}
	}
}
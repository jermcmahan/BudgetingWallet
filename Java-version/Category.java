//Author: Jeremy McMahan
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import Tree.*;

//manipulates references to objects not copies!
interface CategoryInterface <E> {
	public static final int PARENT = 0;
	public static final int NEXT = 2;
	public static final int PREV = 3;
	public static final int SUBCATEGORY = 1;

	void Print();
	//Effect: prints the category structure to the standard output

	void addSubCategory(E o);
	//Effect: adds a subcategory to the list of subcategories of the current category
	//Post-Conditions: this object is added to the subcategory list

	public void addSubToCurrent(E o);
	//Effect: adds a subcategory to the list of subcategories and sets current to the new subcategory
	//Post-Conditions: a new sub category is created and current references it

	void removeSubCategory(E o) throws Exception;
	//Effect: removes the subcategory and all following data
	//Pre-Conditions: this subcategory is in the list and is not the main category
	//Post-Conditions: this subcategory and its children will be removed

	void sRemoveSubCategory(E o) throws Exception;
	//Effect: removes the subcategory and replaces it with its subcategory
	//Pre-Conditions: this subcategory is in the list
	//Post-Conditions: this subcategory is replaced with its subcategory

	E findData(E o);
	//Effect: returns the object contained in the subCategory specified
	//Pre-Condition: this subcategory is in the list
	//Post-Condition: returns the Object

	E getData();
	//Effect: returns the object in the current category

	void setCategory(E o) throws Exception;
	//Effect: sets the current category to the subcategory that contains o
	//Pre-Conditons: the subcategory is in the structure
	//Post-Conditoins: current will be set to the specified subcategory

	boolean Traverse(int option);
	//Effect: sets the current category to the parent, next, previous, or subcategory
	//Pre-Conditions: the current category and the desired successor are not null
	//Post-Conditions: the current category is referencing the desired successor

	void resetCategory();
	//Effect: sets the current category to the main category

}

//implement the category interface!
//add a method to change current categories object
//this is a k-ary tree implementation
public class Category<E> implements CategoryInterface<E> {
	Tree<E> category;

	public Category() {

	}

	public Category(E o) {
		category = new Tree<E>(o);
	}

	//only way to effectively delete the main category is change its object to null
	public void change(E o) {
		category.change(o);
	}

	public void Print() {
		category.Print();
	}

	//change
	public void addSubTo(E subCategory, E addTo) {
		category.set(addTo);
		category.add(subCategory);
	}

	public void addSubCategory(E o) { // breadth first
		category.Add(o);
	}

	public void addSubToCurrent(E o) { //depth first
		category.add(o);
	}

	public void removeSubCategory() {
		category.completeRemove();
	}

	public void removeSubCategory(E o) throws Exception {
		category.set(o);
		category.completeRemove();
	}

	public void sRemoveSubCategory() throws Exception {
		category.remove();
	}

	public void sRemoveSubCategory(E o) throws Exception {
		category.set(o);
		category.remove();
	}

	public Iterator<E> iterator() {
		return category.iterator();
	}

	public boolean Traverse (int option) {
		if (option == SUBCATEGORY) {
			option = Tree.CHILD;
		}
		return category.traverse(option);
	}

	public E findData(E o) {
		category.set(o);
		return category.getData();
	}

	public E getData() {
		return (E) category.getData();
	}

	public void setCategory(E o) throws Exception {
		if (category.set(o) != true) {
			throw new Exception("Not found");
		}
	}

	public void resetCategory() {
		category.reset();
	}

}

// interface Readable<E> {
// 	void Write();
// 	//provides a way to write the E class to a file to be later read

// 	E Read();
// 	//reads the writen object from a file and returns the original E object
// }
// //same as category class but with ability to save contents to file and reload them
// class SaveCategory<E extends Readable<E>> extends Category<E> {
// 	//use dollar signs like tabs on the file to show how nested the current sub category is
// 	private int nesticity = 1;

// 	public void printToFile() {
// 		try {
// 			File.createFile("Category.dat");
// 			writer = new PrintWriter("Category.dat", "UTF-8");
// 		} catch (Exception e) {
// 			writer = new PrintWriter("Category.dat", "UTF-8");
// 		}

// 		root.data.Write();

// 		if (root.subCategories != null) {
// 			for (int i = 0; i < root.subCategories.size(); i++) {
// 				printSubToFile(1, (CategoryNode<E>) root.subCategories.get(i));
// 			}
// 		}

// 		writer.close();
// 	}

// 	private void printSubToFile(int n, CategoryNode<E> start) {
// 		writer.println("&" + n)
// 		start.Write();

// 		if (start.subCategories != null) {
// 			for (int i = 0; i < start.subCategories.size(); i++) {
// 				root.subCategories.get(i).printSubToFile(n+1);
// 			}
// 		}
// 	}

// 	private ReadFromFile() {
// 		BufferedReader reader;
// 		String line;
// 		int num;
// 		root.data = data.Read();
// 		while ((line = reader.nextLine()) {
// 			if (line.charAt(0) == '&') {
// 				num = Integer.parseInt(line.substring(1)).valueOf;
// 				if (num == nesticity) {
// 					traverse(NEXT);
// 				} else if (num > nesticity) {
// 					while(num > nesticity) {
// 						traverse(SUBCATEGORY);
// 						nesticity++;
// 					}
// 				} else { // num < nestisicty
// 					while(num < nestisicty) {
// 						traverse(PARENT);
// 						nestisicty--;
// 					}
// 				}
// 				E o = E.write();
// 				category.addSubCategory(o);
// 			}
// 		}
// 		line = reader.nextLine();

// 	}

// }

// if (current line has an ampersand) {
// 	create new sub category
// 	if the next line has an additional ampersand then create another sub category
// 	if same amount of ampersand then add to next
// 	if less then go to previous then go next
// }

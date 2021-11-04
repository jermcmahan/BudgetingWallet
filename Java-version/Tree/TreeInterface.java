package Tree;
//note this tree operates under the assumption that a current node is kept
public interface TreeInterface<E> extends Iterable<E> {
	public static final int PARENT = 0;
	public static final int CHILD = 1;
	public static final int NEXT = 2;
	public static final int PREV = 3;

	public void change(E item); 
	//Effect: changes the data held in the current node
	//Pre-Conditions: the current node is not null
	//Post-Conditions: the data in the current node is set to item

	public void add(E item); 
	//Effect: adds a new node containing item to the tree
	//Pre-Conditions: none
	//Post-Conditions: a new node containing item is added to the tree

	public void remove();
	//Effect: removes the current node from the tree and adjusts the tree appropriately
	//Pre-Conditions: the current node is not null
	//Post-Conditions: the current node will be removed and the tree adjusted appropriately

	public void completeRemove();
	//Effect: removes the current sub tree from the tree
	//Pre-Conditions: the current node is not null
	//Post-Conditions: the current node and its children will be removed from the tree

	public boolean set(E item); 
	//Effect: sets the current node to the specified node
	//Pre-Conditions: the node containing item is in the tree, otherwise current is unchanged and false is returned
	//Post-Conditions: the current node is set to the node containing item and true is returned

	public boolean traverse(int option); 
	//Effect: sets current to the specified related node
	//Pre-Conditions: the node requested is defined, otherwise current will be unchanged and false will be returned
	//Post-Conditions: current is set to the specified node and true is returned

	public E getData(); 
	//Effect: returns the data in the current node
	//Pre-Conditions: current is not null
	//Post-Conditions: the data inside the current node is returned

	public void reset(); 
	//Effect: sets current to the root
	//Pre-Conditions: none
	//Post-Conditions: current references the root node

}


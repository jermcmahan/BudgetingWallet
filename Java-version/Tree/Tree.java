package Tree;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

//uses a current node          MAKE SERIALIZABLE!!!
public class Tree<E> implements TreeInterface<E> {
	//private classes-----------------------------------------------------------------------------------------------------------------
	private class TreeNode<J> {
		public J data;
		public TreeNode<J> parent;
		public ArrayList<TreeNode<J>> children;

		public TreeNode() {
			parent = null;
			children = null;
			data = null;
		}

		public TreeNode(J o) {
			data = o;
		}

		public int currentChild() {
			return parent.children.indexOf(this);
		}
	}

	private class TreeIterator implements Iterator<E> {
		private TreeNode<E> next;
		private int treeLevel;

		public TreeIterator(TreeNode<E> root) {
			next = root;
			treeLevel = 0;
		}

		public boolean hasNext() {
			return (next != null);
		}

		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			E value = next.data;

			if (next.children != null) {
				next = next.children.get(0);
				treeLevel++;
			} else {
				while (next.currentChild() >= next.parent.children.size() - 1) {
					next = next.parent;
					treeLevel--;
					if (next.parent == null) {
						next = null;
						return value;
					}
				}
				next = next.parent.children.get(next.currentChild() + 1);
			}

			return value;
		}

		public E parent() {
			return next.parent.data;
		}

		public int getTreeLevel() {
			return treeLevel;
		}
	}
	//private items--------------------------------------------------------------------------------------------------------------------

	private TreeNode<E> root;
	private TreeNode<E> current;

	private TreeNode<E> newTreeNode(E item) {
		TreeNode<E> newNode = new TreeNode<E>(item);
		newNode.parent = current;
		return newNode;
	}
	
	private void newChild(E item) {
		if (current.children == null) {
			current.children = new ArrayList<TreeNode<E>>();
		} 
		current.children.add(newTreeNode(item));
		current = current.children.get(current.children.size()-1);
	}

	private TreeNode<E> findChild(E item, TreeNode<E> start) {
		TreeNode<E> temp;
		if (start.data.equals(item)) {
			return start;
		} else if (start.children != null) {
			for (int i = 0; i < start.children.size(); i++) {
				temp = findChild(item, start.children.get(i));
				if (temp != null) {
					return temp;
				}
			}
		}
		return null;
	}

	private void printChildren(int n, TreeNode<E> start) {
		System.out.print("|");

		for (int i = 0; i < n; i++) {
			System.out.print("\t");
		}

		System.out.println("SubTree: " + start.data);

		if (start.children != null) {
			for (int i = 0; i < start.children.size(); i++) {
				printChildren(n+1, start.children.get(i));
			}
		}
	}

	//interface methods----------------------------------------------------------------------------------------------------------------------
	
	public Tree() {
		
	}

	public Tree(E item) {
		root = newTreeNode(item);
		current = root;
	}

	public void change(E item) {
		current.data = item;
	}

	public void add(E item) {
		if (root == null) { // initialize the root node
			root = newTreeNode(item);
			current = root;
		} else { //add a child to the current node
			newChild(item);
		}
	}
	//Make sure remove adjusts the currentChild element Correctly!! and edit for simplicity and efficiency
	public void remove() {
		TreeNode<E> temp = current; 
		TreeNode<E> temp1;

		current = current.parent;
		if (current == null && root.children == null) {
			root = null;
		} else if (temp.children == null) {
			current.children.remove(temp.currentChild());
			if (current.children.size() == 0) {
				current.children = null;
			} 
		} else if (temp.children.size() == 1) {
			temp.children.get(0).parent = current;
			if (current == null) {
				root = temp.children.get(0);
			} else {
				current.children.set(temp.currentChild(), temp.children.get(0));
			}
		} else {
			//roll elements down (EDIT!)
			temp1 = temp.children.get(0); //temp1 is the sub of the item to be deleted
			if (temp1.children == null) {
				temp1.children = new ArrayList<TreeNode<E>>();
			} 
			for (int i = 1; i < temp.children.size(); i++) { //adjust 
				temp.children.get(i).parent = temp1;
				temp1.children.add(temp.children.get(i));
			}
			temp1.parent = current;
			if (current == null) {
				root = temp1;
			} else {
				current.children.set(temp.currentChild(), temp1);
			}
			//temp.children = null;
		}
		if (current == null) {
			current = root;
		}
	}

	public void completeRemove() {
		if (current.parent != null) {
			TreeNode<E> temp = current;
			current = current.parent;
			current.children.remove(temp.currentChild());
		} else {
			root = null;
		}
	}

	public boolean set(E item) {
		TreeNode<E> temp = findChild(item, root);
		if (temp != null) {
			current = temp;
			return true;
		} else {
			return false;
		}
	}

	public boolean traverse(int option) {
		if (current == root && option != CHILD) {
			return false;
		} else if (option == PARENT) {
			current = current.parent;
		} else if (option == CHILD) {
			if (current.children != null) {
				current = (TreeNode<E>) current.children.get(0);
			} else {
				return false;
			}
		} else if (option == NEXT) {
			if (current.currentChild() < current.parent.children.size()-1) {
				current = (TreeNode<E>) current.parent.children.get(current.currentChild()+1);
			} else {
				return false;
			}
		} else if (option == PREV) {
			if (current.currentChild() > 0) {
				current = (TreeNode<E>) current.parent.children.get(current.currentChild()-1);
			} else {
				return false;
			}
		} 
		return true;
	}

	public E getData() {
		return current.data;
	}

	public void reset() {
		current = root;
	}

	public Iterator<E> iterator() {
		return new TreeIterator(root);
	}

	//other public methods-------------------------------------------------------------------------------------------------------------

	public E find(E item) {
		TreeNode<E> temp = findChild(item, root);
		if (temp != null) {
			return (E) temp.data;
		} else {
			return null;
		}
	}
	//adds to the current category but does not change current afterwards
	public void Add(E item) {
		add(item);
		current = current.parent;
	}

	public void Print() {
		System.out.println("Root: " + root.data);
		if (root.children != null) {
			System.out.println(" -----------------------------------------------------");

			for (int i = 0; i < root.children.size(); i++) {
				printChildren(1, (TreeNode<E>) root.children.get(i));
			}

			System.out.println(" -----------------------------------------------------\n");
		}
	}

}
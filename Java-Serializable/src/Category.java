import java.io.Serializable;
import java.util.ArrayList;


//Note: this class requires that list and item implement the comparable interface!
//change getters and setters to take into account last. also look at the setNext and others and createSubCategory for errors
public class Category<list extends Comparable<list>, item extends Comparable<item>> 
	implements Comparable<Category<list, item>>, Cloneable, Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<list> head;
	private item element;
	private Category<list, item> next;
	private Category<list, item> prev;
	private Category<list, item> subCategory;
	private Category<list, item> last; 
	
	public Category(){
		head = new ArrayList<list>();
	}
	
	public Category(Category<list, item> input) {
		this.element = input.element;
		last = this;
		head = new ArrayList<list>(input.head);
	}
	
	public Category(item e){
		head = new ArrayList<list>();
		element = e;
		last = this;
	}
	//edit
	public void delete(item e) {
		Category<list, item> t = Find(e);
		if (t == null) {
			return;
		} 
		Category<list, item> parent = t.Traverse("parent", 1);
		Category<list, item> previous = t.prev;
		if (parent == null) {
			if (t.prev != null) {
				t.prev.next = t.subCategory;
				if (t.subCategory != null) {
					t.subCategory.prev = t.prev;
				}
			} else {
				if (t.subCategory != null && t.next != null) {
					Category<list, item> i = t.subCategory;
					for (; i.next != null; i = i.next);
					i.next = t.next;
					t = t.subCategory;
				} else {
					//this = t.next;
					
				}
			}
			return;
		}
		
		if (t.prev == null) {
			if (t.subCategory != null) {
				Category<list, item> i;
				for (i = t.subCategory; i.next != null; i = i.next);
				i.next = t.next;
				if (t.next != null) {
					t.next.prev = i;
				}
				t = t.subCategory;
			} else if (t.next != null) {
				t = t.next;
			} else {
				t = null;
			}
		} else {
			if (t.subCategory != null) {
				if (parent != previous) {
					Category<list, item> i;
					for (i = t.subCategory; i.next != null; i = i.next);
					i.next = t.next;
					if (t.next != null) {
						t.next.prev = i;
					}
					previous.next = t.subCategory;
					t.next.prev = previous;
				} else if (parent == previous) {
					Category<list, item> i;
					for (i = t.subCategory; i.next != null; i = i.next);
					i.next = t.next;
					if (t.next != null) {
						t.next.prev = i;
					}
					parent.subCategory = t.subCategory;
					t.subCategory.prev = parent;
				}
			} else {
				if (parent != previous) {
					previous.next = t.next;
					if (t.next != null) {
						t.next.prev = previous;
					}
				} else {
					parent.subCategory = t.next;
					if (t.next != null) {
						t.next.prev = parent;
					}
				}
			}
			
		}
		
		t.next = null;
		t.prev = null;
		t.head = null;//for now
		t = null;
		
	}
	
	public list getItem(list s) {
		for (int i = 0; i < head.size(); i++) {
			if ((head.get(i)).compareTo(s) == 0) {
				return head.get(i);
			}
		}
		return null;
	}
	
	private int getIndexOfItem(list s) {
		for (int i = 0; i < head.size(); i++) {
			if ((head.get(i)).compareTo(s) == 0) {
				return i;
			}
		}
		return -1;
	}
	
	//Removes item from the current category
	public void removeThisItem(list s) {
		int index = getIndexOfItem(s);
		if (index != -1) {
			head.remove(index);
		}
	}
	
	public Category<list, item> findItemCategory(list s) {
		for (Category<list, item> temp = this; temp != null; temp = temp.next) {
			list find = (list) temp.getItem(s);
			if (find != null) {
				return temp;
			}
			if (temp.subCategory != null) {
				Category<list, item> t = temp.subCategory.findItemCategory(s);
				if (t != null) {
					return t;
				}
			}
		}
		
		return null;
	}
	
	public list findItem(list s) {
		for (Category<list, item> temp = this; temp != null; temp = temp.next) {
			list find = temp.getItem(s);
			if (find != null) {
				return find;
			}
			if (temp.subCategory != null) {
				list t = (list) temp.subCategory.findItem(s);
				if (t != null) {
					return t;
				}
			}
		}
		
		return null;
	}
	
	public void addItem(list s) {
		head.add(s);
	}
	
	public void removeItem(list s) {
		Category<list, item> temp = findItemCategory(s);
		if (temp != null) {
			int index = temp.getIndexOfItem(s);
			temp.head.remove(index);
		}
	}
	
	public void CreateSubCategory(item e) {
		if (subCategory == null) {
			subCategory = new Category<list, item>(e);
			subCategory.prev = this;
		} else {
			Category<list, item> temp = subCategory;
			while(temp.next != null) {
				temp = temp.next;
			}
			Category<list, item> newSub = new Category<list, item>(e);
			temp.next = newSub;
			newSub.prev = temp;
		}
	}
	
	public Category<list, item> Find(item e) {
		for (Category<list, item> temp = this; temp != null; temp = temp.next) {
			if (((Comparable<item>) temp.element).compareTo(e) == 0) {
				return temp;
			} else {
				if (temp.subCategory != null) {
					Category<list, item> t = temp.subCategory.Find(e);
					if (t != null) {
						return t;
					}
				}
			}
		}
		
		return null;
	}
	
	public void setElement(item e) {
		element = e;
	}
	
	public item getElement() {
		return element;
	}
	
	public void setNextOrLast(Category<list, item> next) {
		if (next != null) {
			if (this.next != null) {
				last.next = next;
				next.prev = last;
			} else {
				next.prev = this;
				this.next = next;
			}
		}
	}
	
	public void setNext(Category<list, item> next) {
		if (next != null) {
			next.prev = this;
			this.next = next;
		} else {
			next = null;
		}
	}
	
	public void setPrev(Category<list, item> prev) {
		this.prev = prev;
	}
	
	public Category<list, item> getNext() {
		return next;
	}
	
	public Category<list, item> getPrev() {
		return prev;
	}
	
	public Category<list, item> getSubCategory() {
		return subCategory;
	}
	
	public Category<list, item> Traverse(String option, int n) {
		Category<list, item> temp = this;
		int i = 0;
		
		if (option.equals("subCategory")) {
			for (; i < n && temp.subCategory != null; i++, temp = temp.subCategory);
			
			if (i < n || temp == this) {
				return null;
			} else {
				return temp;
			}
			
		} else if (option.equals("next")) {
			for (; i < n && temp.next != null; i++, temp = temp.next);
			
			if (i < n || temp == this) {
				return null;
			} else {
				return temp;
			}
			
		} else if (option.equals("prev")) {
			for (; i < n && temp.prev != null; i++, temp = temp.prev);
			
			if (i < n || temp == this) {
				return null;
			} else {
				return temp;
			}
			
		} else if (option.equals("parent")) {
			for (; i < n && temp != null && temp.prev != null; i++, temp = temp.prev) {
				for (; temp.prev != null; temp = temp.prev) {
					if (temp.prev.subCategory == temp) {
						break;
					}
				}
			}
			
			if (i < n || temp == this) {
				return null;
			} else {
				return temp;
			}
			
		} else {
			System.out.println("Invalid Operand: " + option);
			return null;
		}
	}
	
	public void PrintCategoryTree() {
		for (Category<list, item> temp = this; temp != null; temp = temp.next) {
			temp.PrintCategory();
		}
	}
	
	public void PrintCategory() {
		System.out.println("Category: " + element);
		if (head.size() > 0 || subCategory != null) {
			System.out.println(" -----------------------------------------------------");
			if (head.size() > 0) {
				PrintItems();
			}
			if (subCategory != null) {
				subCategory.PrintSubCategory();
			}
			System.out.println(" -----------------------------------------------------");
		}
	}
	
	private void PrintSubCategory() {
		PrintSubCategory(1);
	}
	
	private void PrintSubCategory(int n) {
		System.out.print("|");
		for (int i = 0; i < n; i++) {
			System.out.print("\t");
		}
		System.out.println("SubCategory: " + element);
		if (head.size() > 0) {
			for (int i = 0; i < head.size(); i++) {
				System.out.print("|");
				for (int j = 0; j <= n; j++) {
					System.out.print("\t");
				}
				System.out.println(head.get(i));
			}
		}
		if (subCategory != null) {
			subCategory.PrintSubCategory(n+1);
		}
		if (next != null) {
			next.PrintSubCategory(n);
		}
	}
	
	private void PrintItems() {
		for (int i = 0; i < head.size(); i++) {
			System.out.println("|\t" + head.get(i));
		}
	}
	
	public boolean equals(Category<list, item> other) {
		return (element.compareTo(other.element) == 0)? true:false;
	}

	@Override
	public int compareTo(Category<list, item> o) {
		return element.compareTo(o.element);
	}
	
	public Category<list, item> Clone() {
		Category<list, item> s = new Category<list, item>(this);
		return s;
	}
	
	public String toString() {
		return "Category: " + element;
	}
	
}
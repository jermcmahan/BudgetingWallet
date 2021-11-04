package Tree;
import java.util.Iterator;
public class Driver {
	public static void main(String[] args) {
		Tree<String> t = new Tree<String>();
		t.add("Hello");
		t.add("World");
		t.add("Chop");
		//t.Print();
		t.set("Hello");
		t.add("Chi");
		t.set("World");
		t.add("China");
		t.set("Hello");
		t.add("CHo");
		t.add("KI");
		t.set("Hello");
		t.remove();
		//System.out.println(t.getData());
		//t.set("China");
		// t.traverse(Tree.PREV);
		// System.out.println(t.getData());
		// t.traverse(Tree.NEXT);
		// System.out.println(t.getData());
		//t.remove();
		t.Print();

		Iterator<String> s = t.iterator();
		while(s.hasNext()) {
			System.out.println("*" + s.next());
		}
	}
}
//create a calculatecurrentchild index method because using a current child index won't work 
//traverse and findchild react differently and mess up the iterator so needs a different solution
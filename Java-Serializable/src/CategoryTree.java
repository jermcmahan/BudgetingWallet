import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


interface AccordianAlgorithm<o1, o2> {
	o1 AccordianAlgorithm(o2 e);
}

//add implementation for last mid and current pointers and add methods. 
//Also, consider adding a unique recursion to the Accordian Algorithm!
public class CategoryTree<LIST extends Comparable<LIST>, ITEM extends Comparable<ITEM>> implements AccordianAlgorithm<Category<LIST, ITEM>, ITEM> {
	Category<LIST, ITEM> head;
	Category<LIST, ITEM> last;
	Category<LIST, ITEM> mid;
	Category<LIST, ITEM> current;
	
	//Helper for Accordian Algorithm
	class AccordianThread implements Callable<Category<LIST, ITEM>> {
		Category<LIST, ITEM> current;
		ITEM e;
		
		AccordianThread() {}
		AccordianThread(Category<LIST, ITEM> t, ITEM e) {
			current = t;
			this.e = e;
		}
		
		
		@Override
		public Category<LIST, ITEM> call() throws Exception {
			return current.Find(e);
		}
		
	}
	
	@Override
	public Category<LIST, ITEM> AccordianAlgorithm(ITEM e) {
		Category<LIST, ITEM> midl = mid.getPrev();
		Category<LIST, ITEM> midr = mid;
		Category<LIST, ITEM> left = head;
		Category<LIST, ITEM> right = last;
		Category<LIST, ITEM> tempL = null;
		Category<LIST, ITEM> tempR = null;
		Category<LIST, ITEM> tempML = null;
		Category<LIST, ITEM> tempMR = null;
		ExecutorService service = Executors.newFixedThreadPool(4);
		Future<Category<LIST, ITEM>> task1;
		Future<Category<LIST, ITEM>> task2;
		Future<Category<LIST, ITEM>> task3;
		Future<Category<LIST, ITEM>> task4;
		int midlc = 0;
		int midrc = 0;
		
		try {
			for (; left != right && left != null && right != null; left = left.getNext(), right = right.getPrev(), midl = midl.getPrev(), midr = midr.getNext()) {
				if (midlc > 0 && midrc > 0) {
					return null;
				} else if (midlc > 0) {
					if ((right.getElement()).compareTo(e) == 0) {
						return right;
					} else if ((midr.getElement()).compareTo(e) == 0) {
						return midr;
					} else {
						task1 = service.submit(new AccordianThread(right, e));
						task2 = service.submit(new AccordianThread(midr, e));
						tempR = task1.get();
						tempMR = task2.get();
						if (tempR != null) {
							return tempR;
						} else if (tempMR != null) {
							return tempMR;
						}
					}
				} else if (midrc > 0) {
					if ((left.getElement()).compareTo(e) == 0) {
						return left;
					} else if ((midl.getElement()).compareTo(e) == 0) {
						return midl;
					} else {
						task1 = service.submit(new AccordianThread(left, e));
						task2 = service.submit(new AccordianThread(midl, e));
						tempL = task1.get();
						tempML = task2.get();
						if (tempL != null) {
							return tempL;
						} else if (tempML != null) {
							return tempML;
						}
					}
				} else {
					if ((left.getElement()).compareTo(e) == 0) {
						return left;
					} else if ((right.getElement()).compareTo(e) == 0) {
						return right;
					} else if ((midl.getElement()).compareTo(e) == 0) {
						return midl;
					} else if ((midr.getElement()).compareTo(e) == 0) {
						return midr;
					} else {
						task1 = service.submit(new AccordianThread(left, e));
						task2 = service.submit(new AccordianThread(right, e));
						task3 = service.submit(new AccordianThread(midl, e));
						task4 = service.submit(new AccordianThread(midr, e));
						tempL = task1.get();
				        tempR = task2.get();
						tempML = task3.get();
						tempMR = task4.get();
						if (tempL != null) {
							return tempL;
						} else if (tempR != null) {
							return tempR;
						} else if (tempML != null) {
							return tempML;
						} else if (tempMR != null) {
							return tempMR;
						}
					}
				}
				if (left == midl) {
					midlc++;
				} else if (right == midr) {
					midrc++;
				}
			}
		} catch (InterruptedException | ExecutionException e1) {
			System.out.println("THREAD FAILURE: ACCORDIAN ALGORITHM" + e1.getMessage());
		}
		return null;
	}
	
}

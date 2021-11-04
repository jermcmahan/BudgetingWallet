import java.util.Scanner;

//EDIT CategoryTree extensively
//BUDGET need to be able to delete the first category and adjust appropriately
//Single delete vs full(complete) delete
//no smart delete unneccessary; however, should be able to delete a category and adjust for the items
public class Main {

	public static void main(String[] args) {
		System.out.println("Enter initial Ballance: ");
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		double f = input.nextDouble();
		Budget My_Budget = new Budget(f);
	    int option = 0;
	    print();
	    String s = null;
	    while ((option = input.nextInt()) != 0) {
	        switch (option) {
	            case 1: {
	            	System.out.print("Category Name: ");
	                s = input.next();
	                System.out.print("Balance: ");
	                f = input.nextDouble();
	                My_Budget.AddCategory(s, f);
	                System.out.println("1. add item\n2. add sub category\n3. exit\n");
	                option = input.nextInt();
	                while (option < 3) {
	                	System.out.print("Name: ");
	                    s = input.next();
	                    System.out.print("Amount: ");
	                    f = input.nextDouble();
	                    if (option == 1) {
	                        My_Budget.addItem(s, f);
	                    }
	                    else {
	                        My_Budget.AddSubCategory(s, f);
	                    }
	                    System.out.println("1. add item\n2. add sub category\n3. exit\n");
	                    option = input.nextInt();
	                }
	                break;
	            }
	            case 2:{
	            	System.out.println("1. add item\n2. add sub category\n3. delete\n4. exit\n");
	                option = input.nextInt();
	                while (option < 4) {
	                    try {
	                        if (option < 3) {
	                        	System.out.println("To What Category?");
	                            s = input.next();
	                            My_Budget.FindCategory(s);
	                            System.out.print("Name: ");
	                            s = input.next();
	                            System.out.print("Amount: ");
	                            f = input.nextDouble();
	                            if (option == 1) {
	                                My_Budget.addItem(s, f);
	                            }
	                            else {
	                                My_Budget.AddSubCategory(s, f);
	                            }
	                        }
	                        else if (option == 3) {
	                        	System.out.print("Delete: \n1. Item\n2. Category\n");
	                        	option = input.nextInt();
	                        	if (option == 1) {
	                        		System.out.println("Delete Which Item?");
	                        		s = input.next();
	                        		My_Budget.removeItem(s);
	                        	} else {
	                        		System.out.println("Delete Which Category?");
	                        		s = input.next();
	                        		My_Budget.delete(s);
	                        	}
	                        }
	                        else {
	                            break;
	                        }
	                    }
	                    catch (Exception e){
	                    	System.out.println("could not find: " + s + "\n");
	                    }
	                    System.out.println("1. add item\n2. add sub category\n3. delete\n4. exit\n");
	                    option = input.nextInt();
	                }
	                break;
	            }
	            case 3: {
	            	System.out.println("1. print ballance\n2. change ballance\n");
	                option = input.nextInt();
	                My_Budget.printBallance();
	                if (option == 2) {
	                	System.out.println("What would you like to change it to?");
	                    f = input.nextDouble();
	                    My_Budget.changeBallance(f);
	                }
	                break;
	            }
	            case 4: {
	            	System.out.print("Find: ");
	                s = input.next();
	                try {
	                    My_Budget.FindCategory(s);
	                    My_Budget.PrintCurrentCategory();
	                } catch (Exception e) {
	                	System.out.println("could not find: " + s);
	                }
	                break;
	            }
	            case 5: {
	                My_Budget.PrintBudget();
	                break;
	            }
	            case 6: {
	                My_Budget = null;
	                return;
	            }
	            default:
	            	System.out.println("incorrect command!\n");
	                break;
	        }
	        print();
		
	    }
	}
	
	public static void print(){
	    System.out.println("  <<<Budget>>>   ");
	    System.out.println("1. Add Category");
	    System.out.println("2. Edit");
	    System.out.println("3. ballance");
	    System.out.println("4. Find");
	    System.out.println("5. Print budget");
	    System.out.println("6. Exit");
	}
}

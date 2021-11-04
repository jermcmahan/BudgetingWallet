//
//  main.cpp
//  Budget
//
//  Created by jeremy mcmahan on 7/31/13.
//  Copyright (c) 2013 jeremy mcmahan. All rights reserved.
//
//THIS PROGRAM IS INCOMPLETE
//TO DO:
//optimizing!!!!
//ALERTS!!!
//percentages and more advanced category ballances
//fix item delete
//Add item deletion when a category with items is deleted
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

#include "My_Budget.h"
void print(void);

int main(int argc, const char * argv[])
{
    Budget *My_Budget = new Budget();
    int option = 0;
    print();
    string s;
    fixed_pt f;
    while (cin >> option) {
        switch (option) {
            case 1: {
                cout << "Category Name: ";
                cin >> s;
                cout << "Balance: ";
                cin >> f;
                My_Budget->Add_Category(f, s);
                cout << "1. add item\n2. add sub category\n3. exit\n";
                cin >> option;
                while (option < 3) {
                    cout << "Name: ";
                    cin >> s;
                    cout << "Amount: ";
                    cin >> f;
                    if (option == 1) {
                        My_Budget->Add_Item(s, f);
                    }
                    else {
                        My_Budget->Add_Sub_Category(s, f);
                    }
                    cout << "1. add item\n2. add sub category\n3. exit\n";
                    cin >> option;
                }
                break;
            }
            case 2:{
                cout << "1. add item\n2. add sub category\n3. delete\n4. exit\n";
                cin >> option;
                while (option < 4) {
                    try {
                        if (option < 3) {
                            cout << "To What Category?\n";
                            cin >> s;
                            My_Budget->find_Category(s);
                            cout << "Name: ";
                            cin >> s;
                            cout << "Amount: ";
                            cin >> f;
                            if (option == 1) {
                                My_Budget->Add_Item(s, f);
                            }
                            else {
                                My_Budget->Add_Sub_Category(s, f);
                            }
                        }
                        else if (option == 3) {
                            cout << "Delete: ";
                            cin >> s;
                            My_Budget->deletes(s);
                        }
                        else {
                            break;
                        }
                    }
                    catch (Not_Found& e){
                        cout << "could not find: " << e.name << endl << endl;
                    }
                    cout << "1. add item\n2. add sub category\n3. delete\n4. exit\n";
                    cin >> option;
                }
                break;
            }
            case 3: {
                cout << "1. print ballance\n2. change ballance\n";
                cin >> option;
                My_Budget->get_ballance();
                if (option == 2) {
                    cout << "What would you like to change it to?\n";
                    cin >> f;
                    My_Budget->change_ballance(f);
                }
                break;
            }
            case 4: {
                cout << "Find: ";
                cin >> s;
                try {
                    My_Budget->find_Category(s);
                    My_Budget->Print_Category();
                } catch (Not_Found& e) {
                    cout << "could not find: " << e.name << endl;
                }
                break;
            }
            case 5: {
                My_Budget->get_ballance();
                My_Budget->print_budget(cout);
                break;
            }
            case 6: {
                delete My_Budget;
                exit(EXIT_SUCCESS);
            }
            default:
                cout << "incorrect command!\n";
                break;
        }
        //print();
    }
    return EXIT_SUCCESS;
}

void print(void){
    cout << "  <<<Budget>>>   " << endl;
    cout << "1. Add Category" << endl;
    cout << "2. Edit" << endl;
    cout << "3. ballance" << endl;
    cout << "4. Find" << endl;
    cout << "5. Print budget" << endl;
    cout << "6. Exit" << endl;
}

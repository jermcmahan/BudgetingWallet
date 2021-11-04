//
//  My_Budget.h
//  Budget
//
//  Created by jeremy mcmahan on 7/31/13.
//  Copyright (c) 2013 jeremy mcmahan. All rights reserved.
/***********************************************************************************************************
 contains budget class and category class
 Category class:
       a linked list pointing to other linked lists of categories called sub_categorys, and a pointer to a
       linked list of items added to the list for the checkbook featrure that decrements the budget value
 Budget class:
       contains a pointer to a linked list of categories, an initial ballance, and a continually changing
       new ballance that is decremented when a category is added. several methods for printing and returning
       important information such as the categories, items, and percentages of each category over the total
       budget. Also contains file io for saving the budget to file and getting a budget from file.
 ***********************************************************************************************************/

#ifndef __Budget__My_Budget__
#define __Budget__My_Budget__
#include <iostream>
#include <fstream>
#include <vector>
#include "fixed_pt.h" //for the fixed point number class
#include "Budget_Exceptions.h"//for exception handeling
using namespace fixed_pt_space;
using namespace std;
//an item in the budget
struct item {
    fixed_pt amount;
    string item_name;
    item *next;
};

//a linked list of items
class Category {
public:
    
    Category(){
        head = nullptr;
        subCategory = nullptr;
        next = nullptr;
        prev = nullptr;
    }
    
    ~Category();
    
    Category(const Category& other);
    
    void operator = (const Category& other);
    
    bool operator == (const Category& other);
    
    //inserts a new item to the beginning of the list
    void insert(string name, fixed_pt price);
    
    //returns a pointer to an item if found null if not
    Category *find(string name);
    
    //gets the most recent addition to the list a copy of head
    item getRecent();
    
    //prints name ballance of category and a nice formated print for the list
    void printCategory(ostream& output);
    
    void printCategory_to_file(ostream& output);
    
    void write_helper(ofstream& file);
    
    void printSubCategory(int number, ostream& output);
    
    void printSubCategory_to_file(int number, ostream& output);
    
    Category *CreateSubCategory(string name, fixed_pt price);
    
    Category *findSubCategory(string name);
    
    Category *traverse(int n, string option);
    
    bool Delete(string name);
    
    void delete_item(string name, fixed_pt& other);
    
    bool has_subCategory(){
        return (subCategory != nullptr);
    }
    
    //name of the category
    string name;
    
    //the main categorys ballance
    fixed_pt ballance;
    
private:
    
    Category *prev;
    
    Category *next;
    
    //head of linked list of subcategories
    Category *subCategory;
    
    //pointer to a linked list of items for checkbook uses
    item *head;
    
};

//a hash table containing lists with methods to interact with the data
class Budget {
    
private:
    
    //a table of dynamically allocated pointers to categorys "linked lists"
    vector<Category *> table;
    
    Category *currentCategory;
    
    //grows the hash table to double the size! max size is 80!!
    //void grow();
    
    //the initial ballence
    fixed_pt ballance;
    
    //ballanced continually changed due to new categorys and items
    fixed_pt new_ballance;
    
public:
    //default constructor sets pointer to 0
    Budget();
    
    //destructor deallocates memory from table
    ~Budget();
    
    //returns initial and current ballances
    fixed_pt get_ballance(){
        cout << "initial budget: " << ballance << endl << "current budget: " << new_ballance << endl;
        return new_ballance;
    }
    
    //adds a new category to the table and returnbs a pointer to it
    Category *Add_Category(fixed_pt number, string name);
    
    void Add_Sub_Category(string name, fixed_pt f);
    
    void Add_Item(string name, fixed_pt f);
    
    //prints current budget;
    void print_budget(ostream& output);
    
    void Print_Category();
    
    void print_budget_to_file(ostream& output);
    
    //finds a category and returns a copy of it
    void find_Category(string name) throw(Not_Found);
    
    void deletes(string name) throw(Not_Found);
    
    void change_ballance(fixed_pt f);
    
    void create_from_ballance(fixed_pt b, fixed_pt n){
        ballance = b;
        new_ballance = n;
    }
    
    //finds a item and returns a copy of it
    Category *find_item(string name);
    
};

fstream& operator >> (fstream& out_file, Budget& my_budget);
fstream& operator << (fstream& in_file, Budget& my_budget);
#endif /* defined(__Budget__My_Budget__) */

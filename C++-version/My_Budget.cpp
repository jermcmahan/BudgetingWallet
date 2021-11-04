//
//  My_Budget.cpp
//  Budget
//
//  Created by jeremy mcmahan on 7/31/13.
//  Copyright (c) 2013 jeremy mcmahan. All rights reserved.
//

#include "My_Budget.h"
#include <iomanip>

Budget::Budget(){
    fstream file("hello2.txt", ios::in);
    if (file.bad()) {
        file.close();
        cout << "error: cout not open file!\n";
        exit(EXIT_FAILURE);
    }
    if (file.get() != -1) {
        file.seekg(0);
        file >> *this;
    }
    else {
        fixed_pt f;
        cout << "please enter initial budget value\n";
        cin >> f;
        ballance = f;
        new_ballance = f;
    }
    file.close();
}

Budget::~Budget(){
    fstream file("hello2.txt", ios::out | ios::trunc);
    file << *this;
    file.close();
    for (int i = 0; i < table.size(); i++) {
        delete table[i];
    }
}
//CHECK DELETE
//food deletion is not properly released of its memory and freezes the program
//try calloc and nullptr
Category::~Category(){
    if (head != nullptr){
        delete head;
        //this->head = nullptr;
    }
    if (subCategory != nullptr) {
        delete subCategory;
        //this->subCategory = nullptr;
    }
    if (next != nullptr) {
        delete next;
        //this->next = nullptr;
    }
}

Category::Category(const Category& other){
    ballance = other.ballance;
    name = other.name;
    head = other.head;
    subCategory = other.subCategory;
    next = other.next;
    prev = other.prev;
}

void Category::operator = (const Category& other){
    this->ballance = other.ballance;
    this->name = other.name;
    this->head = other.head;
    this->subCategory = other.subCategory;
    this->next = other.next;
    this->prev = other.prev;
}

bool Category::operator == (const Category& other){
    if (this->ballance == other.ballance && this->name == other.name){
        return true;
    }
    return false;
}

void Budget::find_Category(string name) throw(Not_Found){
    Category *temp, *temp1;
    for (int i = 0; i < table.size(); i++) {
        temp = table[i];
        if (!temp->name.compare(name)) {
            break;
        }
        else if ((temp1 = temp->findSubCategory(name)) != nullptr){
            currentCategory = temp1;
            return;
        }
        else if (i + 1 == table.size()){
            throw Not_Found(name);
            return;
        }
    }
    currentCategory = temp;
}

Category *Category::findSubCategory(string name){
    static Category *temp = nullptr;
    if (temp != nullptr && temp->name.compare(name)) {
        temp = nullptr;
    }
    if (temp != nullptr && !temp->name.compare(name)) {
        return temp;
    }
    if (this != nullptr) {
        if (!this->name.compare(name)) {
            temp = this;
        }
        else if (temp == nullptr){
            if (subCategory != nullptr){
                subCategory->findSubCategory(name);
            }
            if (next != nullptr) {
                next->findSubCategory(name);
            }
        }
    }
    return temp;
}

Category *Budget::find_item(string name){
    Category *temp;
    for (int i = 0; i < table.size(); i++) {
        if ((temp = table[i]->find(name)) != nullptr) {
            return temp;
        }
    }
    return nullptr;
}

Category *Category::find(string name){
    static Category *temp1 = nullptr;
    /*if (temp1 != nullptr) {
        return temp1;
    }*/
    for (item *temp = head; temp != nullptr; temp = temp->next) {
        if (!temp->item_name.compare(name)) {
            return this;
        }
    }
    if (subCategory != nullptr) {
        temp1 = subCategory->find(name);
    }
    if (next != nullptr) {
        temp1 = next->find(name);
    }
    return temp1;
}

Category *Budget::Add_Category(fixed_pt number, string name){
    Category *temp = new Category;
    temp->ballance = number;
    temp->name = name;
    table.push_back(temp);
    currentCategory = table.back();
    return table.back();
}

void Budget::Add_Sub_Category(string name, fixed_pt f){
    currentCategory->CreateSubCategory(name, f);
}

void Budget::Add_Item(string name, fixed_pt f){
    new_ballance -= f;
    if (new_ballance < 0) {
        cout << "Budget is negative!" << endl;
    }
    //equivalent to <= 50
    else if (new_ballance < 51) {
        cout << "ballance is low!" << endl;
    }
    currentCategory->insert(name, f);
}

Category *Category::CreateSubCategory(string name, fixed_pt price){
    //this->ballance -= price;
    if (subCategory == nullptr) {
        subCategory = new Category;
        subCategory->prev = this;
        subCategory->name = name;
        subCategory->ballance = price;
        return subCategory;
    }
    Category *temp = subCategory;
    while (temp->next != nullptr) {
        temp = temp->next;
    }
    Category *temp1 = new Category();
    temp1->name = name;
    temp1->ballance = price;
    temp1->prev = temp;
    temp->next = temp1;
    return temp1;
}

void Category::insert(string name, fixed_pt price){   
    Category *temp1 = this;
    if (temp1->prev == nullptr) {
        
    }
    else if (temp1->prev->prev == nullptr) {
        temp1->prev->ballance -= price;
    }
    else {
        while (temp1->prev != nullptr) {
            if (!(temp1->prev->subCategory == temp1)) {
                while (!(temp1->prev->subCategory == temp1)) {
                    temp1 = temp1->prev;
                }
            }
            temp1 = temp1->prev;
            temp1->ballance -= price;
        }
    }
    ballance -= price;
    item *temp = new item;
    temp->item_name = name;
    temp->amount = price;
    temp->next = head;
    head = temp;
}

void Budget::change_ballance(fixed_pt f){
    if (ballance == new_ballance) {
        ballance = f;
        new_ballance = f;
        return;
    }
    else if (f > ballance){
        new_ballance = (f - ballance) + new_ballance;
        ballance = f;
    }
    else if (f < ballance){
        new_ballance -= (ballance - f);
        ballance = f;
    }
}

item Category::getRecent(void){
    return *head;
}

void Budget::print_budget(ostream& output){
    for (int i = 0; i < table.size(); i++) {
        table[i]->printCategory(output);
        //cout << setprecision(3) << ((table[i]->ballance.get() / ballance.get()) * 100) << "%" << endl;
    }
}

void Budget::Print_Category(){
    currentCategory->printCategory(cout);
    cout << setprecision(3) << ((currentCategory->ballance.get() / ballance.get()) * 100) << "%" << endl;
}

void Category::printCategory(ostream& output){
    output << "Category: " << name << " $" << ballance << endl;
    if (head == nullptr && subCategory == nullptr) {
        return;
    }
    output << "------------------------------------------------------------------" << endl;
    for (item *temp = head; temp != nullptr; temp = temp->next) {
        output << "|$" << temp->amount << " is allowed for " << temp->item_name << endl;
    }
    subCategory->printSubCategory(1, output);
    output << "------------------------------------------------------------------" << endl;
}

void Category::printSubCategory(int number, ostream& output){
    if (this != nullptr) {
        output << '|';
        if (number > 0) {
            for (int i = 0; i < number; i++) {
                output << '\t';
            }
        }
        output << "SubCategory: " << name << " $" << ballance << endl;
        if (head != nullptr) {
            for (item *temp = head; temp != nullptr; temp = temp->next) {
                output << '|';
                for (int i = 0; i < number; i++) {
                    output << '\t';
                }
                output << "$" << temp->amount << " is allowed for " << temp->item_name << endl;
            }
        }
        if (subCategory != nullptr) {
            subCategory->printSubCategory(number + 1, output);
        }
        if (next != nullptr){
            next->printSubCategory(number, output);
        }
    }
}

fstream& operator << (fstream& out_file, Budget& my_budget){
    my_budget.print_budget_to_file(out_file);
    return out_file;
}

void Budget::print_budget_to_file(ostream& output){
    output << ballance << endl << new_ballance << endl;
    for (int i = 0; i < table.size(); i++) {
        table[i]->printCategory_to_file(output);
        output << endl;
    }
}

void Category::printCategory_to_file(ostream& output){
    output << "&" << name << " $" << ballance << endl;
    if (head == nullptr && subCategory == nullptr) {
        return;
    }
    subCategory->printSubCategory_to_file(1, output);
    for (item *temp = head; temp != nullptr; temp = temp->next) {
        output << "#" << temp->item_name << " $" << temp->amount << endl;
    }
}

void Category::printSubCategory_to_file(int number, ostream& output){
    if (this != nullptr) {
        output << "%" << number << name << " $" << ballance << endl;
        if (head != nullptr) {
            for (item *temp = head; temp != nullptr; temp = temp->next) {
                output << "#" << temp->item_name << " $" << temp->amount << endl;
            }
        }
        if (subCategory != nullptr) {
            subCategory->printSubCategory_to_file(number + 1, output);
        }
        if (next != nullptr){
            next->printSubCategory_to_file(number, output);
        }
    }
}

fstream& operator >> (fstream& in_file, Budget& my_budget){
    int i, j = 0;
    fixed_pt f, l;
    char c;
    string input, s;
    in_file >> f;
    in_file >> l;
    my_budget.create_from_ballance(f, l);
    Category *curCategory;
    Category *category;
    for (i = 0; !in_file.eof(); i++){
        in_file >> c;
        if (c == '&') {
            in_file >> s;
            in_file >> c;
            if (c == '$') {
                in_file >> f;
            }
            curCategory = my_budget.Add_Category(f, s);
            category = curCategory;
        }
        else if (c == '%'){
            static int number = 1;
            in_file >> j;
            if (j > 0) {
                in_file >> s;
                in_file >> c;
                in_file >> f;
                if (j == 1){
                    curCategory = category->CreateSubCategory(s, f);
                }
                else if (number == j){
                    curCategory = curCategory->traverse(1, "parent");
                    curCategory = curCategory->CreateSubCategory(s, f);
                }
                else if (j < number){
                    curCategory = category->traverse(number - j, "sub");
                    curCategory = curCategory->CreateSubCategory(s, f);
                }
                else {
                    curCategory = curCategory->traverse(j, "next");
                    curCategory = curCategory->CreateSubCategory(s, f);
                }
                number = j;
            }
            else {
                //error
            }
        }
        else if (c == '#'){
            in_file >> s;
            in_file >> c;
            if (c == '$') {
                in_file >> f;
            }
            curCategory->insert(s, f);
        }
        else {
            //error
        }
    }
    return in_file;
}

Category *Category::traverse(int n, string option){
    Category *temp = this;
    if (!option.compare("next")) {
        if (this->subCategory == nullptr) {
            return this;
        }
        if (this->subCategory->next == nullptr) {
            return subCategory;
        }
        temp = this->subCategory;
        while (n-- > 0 && temp->next != nullptr) {
            temp = temp->next;
        }
    }
    else if (!option.compare("prev")) {
        while (n-- > 0 && temp->prev != nullptr) {
            if (temp->prev->subCategory == temp) {
                break;
            }
            temp = temp->prev;
        }
    }
    else if (!option.compare("parent")){
        while (n-- > 0 && temp->prev != nullptr) {
            while (!(temp->prev->subCategory == temp)) {
                temp = temp->prev;
            }
            temp = temp->prev;
        }
    }
    else if (!option.compare("sub")) {
        while (n-- > 0 && temp->subCategory != nullptr) {
            temp = temp->subCategory;
        }
    }
    return temp;
}

void Budget::deletes(string name) throw(Not_Found){
    for (int i = 0; i < table.size(); i++) {
        if (!table[i]->name.compare(name)) {
            if (table[i]->has_subCategory()) {
                //table[i]->prev = nullptr;
                table[i]->Delete(name);
                currentCategory = table[i];
                //catch (currentCategory == nullptr);
                //throw Not_Found(currentCategory->name);
            }
            else {
                vector<Category *>::iterator itr = table.begin();
                itr += i;
                table.erase(itr);
                currentCategory = table[i];
                break;
            }
        }
        Category *temp = table[i]->findSubCategory(name);
        if (temp != nullptr){
            temp->Delete(name);
            break;
        }
        Category *temp1 = table[i]->find(name);
        if (temp1 != nullptr) {
            temp1->delete_item(name, new_ballance);
            break;
        }
        if (i + 1 == table.size()){
            throw Not_Found(name);
        }
    }
}

void Category::delete_item(string name, fixed_pt& other){
    item *prev_item = head;
    for (item *temp = head; temp != nullptr; prev_item = temp, temp = temp->next) {
        if (!temp->item_name.compare(name)) {
            fixed_pt f = temp->amount;
            ballance += f;
            if (!temp->item_name.compare(head->item_name)) {
                head = head->next;
                delete temp;
            }
            else {
                prev_item->next = temp->next;
                delete temp;
            }
            Category *temp1 = this;
            while (temp1->prev != nullptr) {
                if (!(temp1->prev->subCategory == temp1)) {
                    temp1 = temp1->traverse(1, "parent");
                }
                temp1 = temp1->prev;
                temp1->ballance += f;
            }
            other += f;
        }
    }
}

//remodel //recently just edited see below
bool Category::Delete(string name){
    Category *previous = this->traverse(1, "parent");
    if (!this->name.compare(name)) {
        if (this->head != nullptr) {
            while (head != nullptr) {
                
            }
        }
        if (this->subCategory != nullptr && this->next != nullptr) {
            Category *temp1 = &*this->subCategory->subCategory;
            if (temp1 != nullptr) {
                while (temp1 != nullptr) {
                    temp1 = temp1->next;
                }
                temp1 = this->subCategory->next;
            }
            else {
                this->subCategory->subCategory = this->subCategory->next;
            }
            this->subCategory->next = this->next;
            this->subCategory->prev = this->prev;
            Category *temp = this->subCategory;
            this->subCategory = nullptr;
            this->prev = nullptr;
            this->next = nullptr;
            delete this;
            *this = *temp;
        }
        else if (this->subCategory != nullptr) {
            Category *temp = this->subCategory;
            //this portion is new designed for deletion of categories in the main budget vector
            Category *temp2;
            for (temp2 = temp->subCategory; temp2->next != nullptr; temp2 = temp2->next);
            for (Category *temp1 = temp->next; temp1 != nullptr; temp1 = temp1->next, temp2 = temp2->next) {
                temp2->next = temp1;
            }
            //end new
            this->subCategory = nullptr;
            this->prev = nullptr;
            this->next = nullptr;
            delete this;
            *this = *temp;
        }
        else if ((this->next != nullptr || this->prev != nullptr)){
            if (this->prev != nullptr && !(*this->prev == *previous) && this->next == nullptr) {
                this->prev->next = nullptr;
                delete this;
            }
            else if (this->next == nullptr && *this == *previous->subCategory){
                previous->subCategory = nullptr;
                delete this;
            }
            else if (this->next == nullptr && !(*this == *previous->subCategory)){
                Category *temp = previous;
                for (; temp->next->next != nullptr; temp = temp->next);
                temp->next = nullptr;
                delete this;
            }
            else if (this->next != nullptr && *this == *previous->subCategory){
                previous->subCategory = this->next;
                this->next = nullptr;
                delete this;
            }
            else {
                this->prev->next = this->next;
                this->next->prev = this->prev;
                delete this;
            }
        }
        else {
            previous->subCategory = nullptr;
            delete this;
        }
    }
    return true;
}

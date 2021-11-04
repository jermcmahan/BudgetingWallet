//
//  Budget_Exceptions.h
//  Budget
//
//  Created by jeremy mcmahan on 8/6/13.
//  Copyright (c) 2013 jeremy mcmahan. All rights reserved.
//

#ifndef __Budget__Budget_Exceptions__
#define __Budget__Budget_Exceptions__
#include <iostream>
#include "fixed_pt.h"
class Not_Found : public std::exception {
public:
    Not_Found(std::string s) : name(s){}
    std::string name;
};

#endif /* defined(__Budget__Budget_Exceptions__) */

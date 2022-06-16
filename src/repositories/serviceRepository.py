#!/usr/bin/env python
# -*- coding: utf-8 -*-

from Cheese.cheeseRepository import CheeseRepository

#@repository services;
#@dbscheme (id, name, port, icon, color);
#@dbmodel Service;
class ServiceRepository(CheeseRepository):

    #@query "select * from services order by port;";
    #@return array;
    @staticmethod
    def findServices():
        return CheeseRepository.query()

    #@query "select * from services where name=:name;";
    #@return one;
    @staticmethod
    def findByName(name):
        return CheeseRepository.query(name=name)
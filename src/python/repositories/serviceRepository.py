#!/usr/bin/env python
# -*- coding: utf-8 -*-

from cheese.modules.cheeseRepository import CheeseRepository

#@repository services
#@dbscheme (id, name, port, icon, color)
#@dbmodel Service
class ServiceRepository(CheeseRepository):

    #@query "select * from services order by port;"
    #@return array
    @staticmethod
    def findServices():
        return CheeseRepository.findServices([])

    #@query "select count(*) from services;"
    #@return num
    @staticmethod
    def findNewId():
        return CheeseRepository.findNewId([])

    #@query "select * from services where name=:name;"
    #@return one
    @staticmethod
    def findByName(name):
        return CheeseRepository.findByName([name])

    @staticmethod
    def save(obj):
        return CheeseRepository.save([obj])

    @staticmethod
    def update(obj):
        return CheeseRepository.update([obj])
#!/usr/bin/env python
# -*- coding: utf-8 -*-

from Cheese.cheeseRepository import CheeseRepository

#@repository passwords;
#@dbscheme (id, user_id, password, duration);
#@dbmodel Password;
class PasswordRepository(CheeseRepository):

    #@query "select case when exists
    #        (select * from passwords p where p.user_id = :userId)
    #        then cast(1 as bit)
    #        else cast(0 as bit) end;";
    #@return bool;
    @staticmethod
    def findValidPassword(userId):
        return CheeseRepository.query([userId])

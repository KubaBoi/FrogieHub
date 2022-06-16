#!/usr/bin/env python
# -*- coding: utf-8 -*-

from Cheese.cheeseRepository import CheeseRepository

#@repository users;
#@dbscheme (id, user_name, email, picture_id);
#@dbmodel User;
class UserRepository(CheeseRepository):

    #@query "select u.id, u.user_name, u.email, u.picture_id from passwords p
    #        inner join users u
    #        on u.id = p.user_id
    #        where p.password = :password and p.duration > :duration and u.user_name = :userName;";
    #@return one;
    @staticmethod
    def findUserByCredentials(userName, password, duration):
        return CheeseRepository.query(userName=userName, password=password, duration=duration)

    #@query "select * from users u where u.user_name = :userName;";
    #@return one;
    @staticmethod
    def findUserByName(userName):
        return CheeseRepository.query(userName=userName)

    #@query "select * from users u where u.id = :userId;";
    #@return one;
    @staticmethod
    def findUserById(userId):
        return CheeseRepository.query(userId=userId)

    #@query "select case when exists
    #        (select * from users u where u.user_name = :userName)
    #        then cast(0 as bit)
    #        else cast(1 as bit) end;";
    #@return bool;
    @staticmethod
    def validateUserName(userName):
        return CheeseRepository.query(userName=userName)

    #@query "select u.id, u.user_name, u.email, u.picture_id from users u
    #        inner join tokens t
    #        on t.user_id = u.id
    #        where t.token = :token and t.ip = :ip;";
    #@return one;
    @staticmethod
    def findUserByIpAndToken(ip, token):
        return CheeseRepository.query(ip=ip, token=token)

    #@query "select * from users u where LOWER(u.user_name) like :userNameStart";
    #@return array;
    @staticmethod
    def findUsersDynamic(userNameStart):
        return CheeseRepository.query(userNameStart=userNameStart)

    

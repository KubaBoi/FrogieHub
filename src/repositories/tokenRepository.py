#!/usr/bin/env python
# -*- coding: utf-8 -*-

from Cheese.cheeseRepository import CheeseRepository

#@repository tokens;
#@dbscheme (id, token, user_id, ip, end_time);
#@dbmodel Token;
class TokenRepository(CheeseRepository):

    #@query "select * from tokens t where
    #       t.user_id = :userId and
    #       t.ip = :ip and
    #       t.end_time >= :time;";
    #@return one;
    @staticmethod
    def findTokenByIdAndIpAndActive(userId, ip, time):
        return CheeseRepository.query(userId=userId, ip=ip, time=time)

    
    #@query "select * from tokens t where
    #        t.token = :token and
    #        t.ip = :ip and
    #        t.end_time >= :time;";
    #@return one;
    @staticmethod
    def findToken(token, ip, time):
        return CheeseRepository.query(token=token, ip=ip, time=time)


    #@query "select case when exists
    #       (select * from tokens t where t.token = :token)
    #       then cast(0 as bit)
    #       else cast(1 as bit) end;";
    #@return bool;
    @staticmethod
    def validateTokenUnique(token):
        return CheeseRepository.query(token=token)


    #@query "select case when exists
    #       (select * from tokens t where
    #       t.token = :token and
    #       t.ip = :ip and
    #       t.end_time >= :time)
    #       then cast(1 as bit)
    #       else cast(0 as bit) end;";
    #@return bool;
    @staticmethod
    def authorizeYourselfByToken(token, ip, time):
        return CheeseRepository.query(token=token, ip=ip, time=time)

    
    
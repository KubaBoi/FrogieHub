#!/usr/bin/env python
# -*- coding: utf-8 -*-

from cheese.appSettings import Settings
from cheese.Logger import Logger
from cheese.databaseControll.postgreDB import PostgreDB
from cheese.databaseControll.SQLserverDB import SQLServerDB

"""
File generated by Cheese Framework

database connection of Cheese Application
"""

class Database:

    connected = False

    # connect to database
    @staticmethod
    def connect():
        if (Settings.dbDriver == "postgres"):
            Database.db = PostgreDB()
        else:
            Database.db = SQLServerDB()

        Database.db.connect()

    # close connection with database
    @staticmethod
    def close():
        Database.db.close()
    
    # select query
    @staticmethod
    def query(sql):
        while (Database.connected): pass
        Database.connected = True
        Database.connect()
        ret = Database.db.query(sql)
        Database.connected = False
        return ret

    # insert, update ...
    @staticmethod
    def commit(sql):
        while (Database.connected): pass
        Database.connected = True
        Database.connect()
        Database.db.commit(sql)
        Database.connected = False

    # commit when done
    @staticmethod
    def done():
        Database.db.done()

    @staticmethod
    def rollback():
        Database.db.rollback()
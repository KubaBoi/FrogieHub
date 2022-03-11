#!/usr/bin/env python
# -*- coding: utf-8 -*-
#AUTOGENERATED FILE

from cheese.databaseControll.database import Database
from cheese.Logger import Logger
from python.models.Service import Service


class ServiceRepositoryImpl:

    @staticmethod
    def init():
        ServiceRepositoryImpl.table = "services"
        ServiceRepositoryImpl.scheme = "(id,name,port,icon,color)"
        ServiceRepositoryImpl.schemeNoBrackets = "id,name,port,icon,color"

    @staticmethod
    def convert(var):
        try:
            var = int(var)
        except:
            var = var
        return var

    @staticmethod
    def toJson(object):
        scheme = ServiceRepositoryImpl.schemeNoBrackets.split(",")
        ret = {}
        for s, o in zip(scheme, list(object)):
            try:
                ret[s] = int(o)
            except:
                ret[s] = o
        return ret

    @staticmethod
    def toModel(obj):
        model = Service()
        model.id = ServiceRepositoryImpl.convert(obj[0])
        model.name = ServiceRepositoryImpl.convert(obj[1])
        model.port = ServiceRepositoryImpl.convert(obj[2])
        model.icon = ServiceRepositoryImpl.convert(obj[3])
        model.color = ServiceRepositoryImpl.convert(obj[4])
        return model

    @staticmethod
    def fromModel(model):
        tuple = (
            model.id,
            model.name,
            model.port,
            model.icon,
            model.color
        )
        return tuple

    @staticmethod
    def findServices(args):

        response = None
        try:
            response = Database.query(f"select {ServiceRepositoryImpl.schemeNoBrackets} from services order by port;")
            Database.done()
        except Exception as e:
            Logger.fail(str(e))

        if (response == None): return response
        resp = []
        for a in response:
            resp.append(ServiceRepositoryImpl.toModel(a))
        return resp

    @staticmethod
    def findNewId(args):

        response = None
        try:
            response = Database.query(f"select count(*) from services;")
            Database.done()
        except Exception as e:
            Logger.fail(str(e))

        if (response == None): return response
        return int(response[0][0])

    @staticmethod
    def findByName(args):
        name = args[0]

        response = None
        try:
            response = Database.query(f"select {ServiceRepositoryImpl.schemeNoBrackets} from services where name={name};")
            Database.done()
        except Exception as e:
            Logger.fail(str(e))

        if (response == None): return response
        if (len(response) > 0):
            return ServiceRepositoryImpl.toModel(response[0])
        else: return None

    @staticmethod
    def save(args):
        obj = ServiceRepositoryImpl.fromModel(args[0])

        try:
            Database.commit(f"insert into {ServiceRepositoryImpl.table} {ServiceRepositoryImpl.scheme} values {obj};")
            Database.done()
            return True
        except Exception as e:
            Logger.fail(str(e))
            return False

    @staticmethod
    def update(args):
        obj = ServiceRepositoryImpl.fromModel(args[0])

        try:
            Database.commit(f"update {ServiceRepositoryImpl.table} set {ServiceRepositoryImpl.scheme} = {obj} where id={obj[0]};")
            Database.done()
            return True
        except Exception as e:
            Logger.fail(str(e))
            return False


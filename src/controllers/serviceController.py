#!/usr/bin/env python
# -*- coding: utf-8 -*-

from xmlrpc.client import Server
from Cheese.cheeseController import CheeseController as cc
from Cheese.ErrorCodes import Error

from src.repositories.serviceRepository import ServiceRepository

#@controller /services;
class ServiceController(cc):

    #@post /getServices;
    @staticmethod
    def getServices(server, path, auth):
        services = ServiceRepository.findServices()
        data = []
        for serv in services:
            data.append(serv.toJson())

        return cc.createResponse({"RESPONSE": data}, 200)

    #@post /doYouKnowMe;
    @staticmethod
    def doYouKnowMe(server, path, auth):
        args = cc.readArgs(server)

        #bad request
        cc.checkJson(["name", "port", "icon", "color"], args)

        name = args["name"]
        port = args["port"]
        icon = args["icon"]
        color = args["color"]

        service = ServiceRepository.findByName(name)
        if (service == None):
            service = ServiceRepository.model()
            service.setAttrs(name=name, port=port, icon=icon, color=color)
            ServiceRepository.save(service)
        else:
            service.port = port
            service.icon = icon
            service.color = color
            ServiceRepository.update(service)

        return cc.createResponse({"OK": "OK"}, 200)

        
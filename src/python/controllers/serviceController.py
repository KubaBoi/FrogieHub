#!/usr/bin/env python
# -*- coding: utf-8 -*-

from xmlrpc.client import Server
from cheese.modules.cheeseController import CheeseController
from cheese.ErrorCodes import Error
from cheese.modules.cheeseRepository import CheeseRepository

from python.repositories.serviceRepository import ServiceRepository
from python.models.Service import Service

#@controller /services
class ServiceController(CheeseController):

    #@get /getServices
    @staticmethod
    def getServices(server, path, auth):
        services = ServiceRepository.findServices()
        data = []
        for serv in services:
            data.append(serv.toJson())

        response = CheeseController.createResponse({"RESPONSE": data}, 200)
        CheeseController.sendResponse(server, response)

    #@get /doYouKnowMe
    @staticmethod
    def doYouKnowMe(server, path, auth):
        
        #bad request
        if (not CheeseController.validateJson(["name", "port", "icon"], auth)):
            Error.sendCustomError(server, "Bad request", 400)
            return

        name = auth["name"].replace("_", " ")
        port = auth["port"]
        icon = auth["icon"].replace("_", " ")

        service = ServiceRepository.findByName(name)
        if (service == None):
            id = ServiceRepository.findNewId()
            service = Service(id, name, port, icon)
            ServiceRepository.save(service)
        else:
            service.port = port
            service.icon = icon
            ServiceRepository.update(service)

        response = CheeseController.createResponse({"OK": "OK"}, 200)
        CheeseController.sendResponse(server, response)

        
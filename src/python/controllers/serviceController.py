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

    #@post /getServices
    @staticmethod
    def getServices(server, path, auth):
        services = ServiceRepository.findServices()
        data = []
        for serv in services:
            data.append(serv.toJson())

        response = CheeseController.createResponse({"RESPONSE": data}, 200)
        CheeseController.sendResponse(server, response)

    #@post /doYouKnowMe
    @staticmethod
    def doYouKnowMe(server, path, auth):
        args = auth["pathArgs"]

        #bad request
        if (not CheeseController.validateJson(["name", "port", "icon", "color"], args)):
            Error.sendCustomError(server, "Bad request", 400)
            return

        name = args["name"].replace("_", " ")
        port = args["port"]
        icon = args["icon"].replace("_", " ")
        color = args["color"]

        service = ServiceRepository.findByName(name)
        if (service == None):
            id = ServiceRepository.findNewId() + 1
            service = Service(id, name, port, icon, color)
            ServiceRepository.save(service)
        else:
            service.port = port
            service.icon = icon
            service.color = color
            ServiceRepository.update(service)

        response = CheeseController.createResponse({"OK": "OK"}, 200)
        CheeseController.sendResponse(server, response)

        
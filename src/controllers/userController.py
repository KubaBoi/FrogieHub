#!/usr/bin/env python
# -*- coding: utf-8 -*-

import io
import cgi
import os

from Cheese.resourceManager import ResMan
from Cheese.cheeseController import CheeseController
from Cheese.ErrorCodes import Error
from src.controllers.authenticationController import AuthenticationController

from src.repositories.passwordRepository import PasswordRepository
from src.repositories.userRepository import UserRepository

#controller /users;
class UserController(CheeseController):

    PASSWORD_DURATION = 432000 # 5 days

    #@post /createUser;
    @staticmethod
    def createUser(server, path, auth):
        args = auth["args"]

        # bad json
        if (not CheeseController.validateJson(["USER_NAME", "PASSWORD", "EMAIL"], args)):
            CheeseController.sendResponse(server, Error.BadJson)
            return

        # OK
        userName = args["USER_NAME"]
        password = args["PASSWORD"]
        email = args["EMAIL"]

        if (not UserRepository.validateUserName(userName)):
            Error.sendCustomError(server, "Name is already taken", 409)
            return

        userId = UserRepository.findNewId()
        passId = PasswordRepository.findNewId()

        user = User(userId, userName, email, 0)
        UserRepository.save(user)
        passW = Password(passId, userId, password, AuthenticationController.getTime(UserController.PASSWORD_DURATION))
        PasswordRepository.save(passW)

        response = CheeseController.createResponse({"USER": user.toJson()}, 200)
        CheeseController.sendResponse(server, response)

    #@post /getUser;
    @staticmethod
    def getUser(server, path, auth):
        if (auth == None):
            return
        args = auth["args"]

        # bad json
        if (not CheeseController.validateJson(["USER_ID"], args)):
            CheeseController.sendResponse(server, Error.BadJson)
            return

        # OK
        userId = args["USER_ID"]

        user = UserRepository.findUserById(userId)
        if (user == None):
            Error.sendCustomError(server, "Unknown user", 404)
            return
        
        response = CheeseController.createResponse({"USER": user.toJson()}, 200)
        CheeseController.sendResponse(server, response)

    #@post /getUserByName;
    @staticmethod
    def getUserByName(server, path, auth):
        if (auth == None):
            return
        args = auth["args"]

        # bad json
        if (not CheeseController.validateJson(["USER_NAME"], args)):
            CheeseController.sendResponse(server, Error.BadJson)
            return

        # OK
        userName = args["USER_NAME"]

        user = UserRepository.findUserByName(userName)
        if (user == None):
            Error.sendCustomError(server, "Uknown user", 404)
            return

        response = CheeseController.createResponse({"USER": user.toJson()}, 200)
        CheeseController.sendResponse(server, response)    

    @staticmethod
    def deal_post_data(server):
        ctype, pdict = cgi.parse_header(server.headers['Content-Type'])
        pdict['boundary'] = bytes(pdict['boundary'], "utf-8")
        pdict['CONTENT-LENGTH'] = int(server.headers['Content-Length'])
        if ctype == 'multipart/form-data':
            form = cgi.FieldStorage( fp=server.rfile, headers=server.headers, environ={'REQUEST_METHOD':'POST', 'CONTENT_TYPE':server.headers['Content-Type'], })
            try:
                for (dirpath, dirnames, filenames) in os.walk(ResMan.web() + "/pictures"):
                    pictureId = len(filenames)

                if isinstance(form["picture"], list):
                    for record in form["picture"]:
                        open(ResMan.web() + f"/pictures/{pictureId}.png", "wb").write(record.file.read())
                else:
                    open(ResMan.web() + f"/pictures/{pictureId}.png", "wb").write(form["picture"].file.read())
            except IOError:
                    return None
        return pictureId, int(form["userId"].file.read())
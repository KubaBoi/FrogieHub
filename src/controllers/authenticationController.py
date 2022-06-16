#!/usr/bin/env python
# -*- coding: utf-8 -*-

import string
import random

from Cheese.cheeseController import CheeseController
from Cheese.ErrorCodes import Error
from Cheese.Logger import Logger

from src.repositories.userRepository import UserRepository
from src.repositories.passwordRepository import PasswordRepository
from src.repositories.tokenRepository import TokenRepository

#controller /authentication;
class AuthenticationController(CheeseController):

    TOKEN_LENGTH = 20
    TOKEN_DURATION = 3600 # 1 hour

    #@post /login;
    @staticmethod
    def login(server, path, auth):
        args = auth["args"]

        # bad json
        if (not CheeseController.validateJson(["USER_NAME", "PASSWORD"], args)):
            CheeseController.sendResponse(server, Error.BadJson)
            return

        userName = args["USER_NAME"]
        password = args["PASSWORD"]
        ip = CheeseController.getClientAddress(server)

        # password check
        user = UserRepository.findUserByCredentials(userName, password, CheeseController.getTime())
        if (user == None):
            user = UserRepository.findUserByName(userName)
            if (user != None):
                if (PasswordRepository.findValidPassword(user.id)):
                    CheeseController.sendResponse(server, Error.OldPass)
                    return
            CheeseController.sendResponse(server, Error.BadCred)
            return

        # OK
        token = AuthenticationController.generateToken(user.id, ip)

        response = CheeseController.createResponse(
            {
                "USER": user.toJson(),
                "TOKEN": token
            }, 200
        )

        CheeseController.sendResponse(server, response)


    #@post /getUserByToken;
    @staticmethod
    def getUserByToken(server, path, auth):
        if (auth == None):
            return
        token = auth["token"]
        ip = auth["ip"]

        user = UserRepository.findUserByIpAndToken(ip, token)
        
        response = CheeseController.createResponse({"USER": user.toJson()}, 200)

        CheeseController.sendResponse(server, response)

    #@post /authorizeToken;
    @staticmethod
    def authorizeToken(server, path, auth):
        if (auth != None):
            if (auth["token"] != "serviceToken"):
                AuthenticationController.updateToken(auth["ip"], auth["token"])
            response = CheeseController.createResponse({"OK": "OK"}, 200)
            CheeseController.sendResponse(server, response)

    #METHODS

    @staticmethod
    def generateToken(userId, ip):
        token = TokenRepository.findTokenByIdAndIpAndActive(userId, ip, CheeseController.getTime())

        if (token == None):

            tokenString = ''.join(random.SystemRandom().choice(string.ascii_uppercase + string.digits) for _ in range(AuthenticationController.TOKEN_LENGTH))
            while (not TokenRepository.validateTokenUnique(token)):
                tokenString = ''.join(random.SystemRandom().choice(string.ascii_uppercase + string.digits) for _ in range(AuthenticationController.TOKEN_LENGTH))

            token = TokenRepository.model()
            token.token = tokenString
            token.userId
            token = Token(tokenId, token, userId, ip, CheeseController.getTime(AuthenticationController.TOKEN_DURATION))

            TokenRepository.save(token)
            return token.token
        return token.token

    @staticmethod
    def updateToken(ip, token):
        token = TokenRepository.findToken(token, ip, AuthenticationController.getTime())
        token.end_time = AuthenticationController.getTime(AuthenticationController.TOKEN_DURATION)
        TokenRepository.update(token)


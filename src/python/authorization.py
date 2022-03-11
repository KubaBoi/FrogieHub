#!/usr/bin/env python
# -*- coding: utf-8 -*-

from urllib.parse import unquote
from cheese.modules.cheeseController import CheeseController
from cheese.ErrorCodes import Error
from cheese.Logger import Logger

from python.repositories.tokenRepository import TokenRepository
from python.repositories.userRepository import UserRepository

#@authorization enabled
class Authorization:

    @staticmethod
    def authorize(server, path, method):
        if (path.startswith("/users/uploadProfilePicture")):
            return None
        Logger.bold("donry")
        args = CheeseController.readArgs(server)
        pathArgs = CheeseController.getArgs(server.path)
        Logger.bold("dobry?")
        if (path == "/" or path.endswith(".css") or path.endswith(".js") or path.endswith(".ico") or path.endswith(".jpg") or path.endswith(".png")):
            return None
        elif (path.startswith("/authentication/login")):
            return {"args": args}
        elif (path.startswith("/users/createUser")):
            return {"args": args}
        else:
            token = Authorization.getToken(server, args)
            if (Authorization.authorizeByToken(server, token)):
                ip = CheeseController.getClientAddress(server)
                Logger.bold("tady to proslo")
                return {
                    "user": UserRepository.findUserByIpAndToken(ip, token),
                    "token": token,
                    "ip": ip,
                    "args": args,
                    "pathArgs": pathArgs
                }
            return -1


    @staticmethod
    def getToken(server, args):
        # bad json
        if (not CheeseController.validateJson(["TOKEN"], args)):
            CheeseController.sendResponse(server, Error.BadJson)
            return None

        return args["TOKEN"]

    @staticmethod
    def authorizeByToken(server, token):
        if (token == "serviceToken"): return True
        Logger.fail(str(token) + str(CheeseController.getClientAddress(server)) + str(CheeseController.getTime()))
        return TokenRepository.authorizeYourselfByToken(token, CheeseController.getClientAddress(server), CheeseController.getTime())
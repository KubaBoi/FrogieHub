
import os

from cheese.modules.cheeseController import CheeseController
from cheese.Logger import Logger
from cheese.resourceManager import ResMan
from cheese.appSettings import Settings
from cheese.ErrorCodes import Error

class AdminManager:

    @staticmethod
    def controller(server):
        if (server.path.endswith(".js") or
            server.path.endswith(".css") or
            server.path.endswith(".png")):
            pass
        elif (not AdminManager.authorizeAsAdmin(server)):
            AdminManager.__sendFile(server, "/admin/login.html")
            return

        if (server.path == "/admin"):
            AdminManager.__sendFile(server, "/admin/index.html") 
            return
        elif (server.path == "/admin/createUser"):
            AdminManager.__createUser(server)
            return
        elif (server.path == "/admin/logs"):
            AdminManager.__showLogs(server)
            return 
        AdminManager.__sendFile(server, server.path)        
        

    @staticmethod
    def authorizeAsAdmin(server):
        cookies = CheeseController.getCookies(server)
        if (not CheeseController.validateJson(["adminName", "adminPass"], cookies)):
            return False
        for user in Settings.adminSettings["adminUsers"]:
            if (user["name"] == cookies["adminName"] and
                user["password"] == cookies["adminPass"]):
                return True
        return False


    # PRIVATE METHODS

    @staticmethod
    def __sendFile(server, file):
        file = ResMan.joinPath(ResMan.cheese(), file)
        if (not os.path.exists(file)):
            with open(f"{ResMan.error()}/error404.html", "rb") as f:
                CheeseController.sendResponse(server, (f.read(), 404))
            return

        with open(f"{file}", "r", encoding="utf-8") as f:
            CheeseController.sendResponse(server, (bytes(f.read(), "utf-8"), 200), "text/html")

    @staticmethod
    def __createUser(server):
        pass

    @staticmethod
    def __showLogs(server):
        CheeseController.sendResponse(server, Logger.serveLogs(server), "text/html")

        
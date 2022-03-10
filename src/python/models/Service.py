from operator import iconcat
from cheese.modules.cheeseModel import CheeseModel

#@model
class Service(CheeseModel):

    def __init__(self, id=None, name=None, port=None, icon=None):
        self.id = id
        self.name = name
        self.port = port
        self.icon = icon

    def toJson(self):
        response = {
            "ID": self.id,
            "NAME": self.name,
            "PORT": self.port,
            "ICON": self.icon,
        }
        return response
from concurrent.futures import thread
import threading
import requests
import json
import time
import math

maxReq = 150
ok = 0
error = 0

def sendReq():
    global ok, error
    try:
        ret = requests.post("http://localhost/authentication/authorizeToken", data=json.dumps({"TOKEN":"serviceToken"}))
        if (ret.status_code == 200):
            ok += 1
            print("OK")
        else:
            error += 1
            print(ret.status_code, ret.text)
    except Exception as e:
        error += 1
        print(str(e))

tm = time.time()

threads = []
for i in range(maxReq):
    threads.append(threading.Thread(target=sendReq))

for x in threads:
    x.start()

for x in threads:
    x.join()

print(10*"=" + "DONE" + 10*"=")
print(f"test's time: {math.floor((time.time() - tm) * 1000)/1000} seconds")
print(f"sended requests: {maxReq}")
print(f"success: {ok}")
print(f"fail: {error}")
print(f"success rate: {(ok/maxReq)*100}%")
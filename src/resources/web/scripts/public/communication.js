debug = true;
function sendPost(url, jsonRequest, output, callback) {
    var xmlHttp = new XMLHttpRequest(); 
    
    var date = new Date();
    if (output) console.log("SENDING", date.getTime(), url, jsonRequest);

    xmlHttp.onreadystatechange = function() {
        if (this.readyState == 4) {
            json = JSON.parse(this.responseText);
            if(output) console.log("RESPONSE", date.getTime(), url, json);
            if(callback) callback(json);
        }
    };
    xmlHttp.open("POST", url);
    xmlHttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlHttp.send(jsonRequest);
}

function sendGet(url, output, callback) {
    var xmlHttp = new XMLHttpRequest();

    var date = new Date();
    if (output) console.log("SENDING", date.getTime(), url);

    xmlHttp.onreadystatechange = function() { 
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
            json = JSON.parse(this.responseText);
            if(output) console.log("RESPONSE", date.getTime(), url, json);
            if(callback) callback(json);
        }
    }
    xmlHttp.open("GET", url); // true for asynchronous 
    xmlHttp.setRequestHeader("Content-Type", "text/plain;charset=UTF-8;");
    xmlHttp.send();
}
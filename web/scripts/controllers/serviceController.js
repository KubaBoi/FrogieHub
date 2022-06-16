function getServices() {
    var url = "/services/getServices";
    var request = JSON.stringify(
        { 
            "TOKEN": getCookie("token"),
        }
    );
    
    return new Promise(resolve => {
        sendPost(url, request, debug, function(response){
            resolve(response);
        });  
    });
}

function alive(url) {
    url += "/alive";
    
    return new Promise(resolve => {
        sendGet(url, debug, function(response){
            resolve(response);
        });  
    });
}
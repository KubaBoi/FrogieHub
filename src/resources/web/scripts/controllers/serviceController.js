function getServices() {
    var url = "/services/getServices";
    
    return new Promise(resolve => {
        sendGet(url, debug, function(response){
            resolve(response);
        });  
    });
}
function createUser(userName, password, email) {
    var url = "/users/createUser";
    var request = JSON.stringify(
        { 
            "USER_NAME": userName,
            "PASSWORD": password,
            "EMAIL": email 
        }
    );
    
    return new Promise(resolve => {
        sendPost(url, request, debug, function(response){
            resolve(response);
        });  
    });
}
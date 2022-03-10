if (getCookie("username") != "") 
    document.querySelector("#fname").value = getCookie("username");
if (getCookie("password") != "") 
    document.querySelector("#fpass").value = getCookie("password");

async function loginButton() {
    loginResponse = await login();
    if (loginResponse.ERROR == null) {
        setCookie("username", document.getElementById("fname").value, 5);
        setCookie("password", document.getElementById("fpass").value, 5);

        connectedUser = loginResponse.USER;
        token = loginResponse.TOKEN;

        buildServiceTable();

        document.getElementById("loginScreen").style.animation = "mainLoginAnim 1s forwards";
        document.getElementById("mainScreen").style.animation = "mainScreenAnim 1s forwards";
    }
}

function registerButton() {
    document.getElementById("loginScreen").style.animation = "registerLogin 1s forwards";
    document.getElementById("registerScreen").style.animation = "registerRegisterScreen 1s forwards";
}

async function register() {
    var name = document.querySelector("#rname");
    var nB = true;
    var mail = document.querySelector("#rmail");
    var mB = true;
    var pass1 = document.querySelector("#rpass1");
    var p1B = true;
    var pass2 = document.querySelector("#rpass2");
    var pass = true;

    if (name.value == "") {
        nB = false;
        name.setAttribute("class", "badInput");
    }
    else {
        name.setAttribute("class", "input");
    }
    if (mail.value == "") {
        mB = false;
        mail.setAttribute("class", "badInput");
    }
    else {
        mail.setAttribute("class", "input");
    }
    if (pass1.value == "") {
        p1B = false;
        pass1.setAttribute("class", "badInput");
    }
    else {
        pass1.setAttribute("class", "input");
    }
    if (pass1.value != pass2.value && p1B) {
        pass = false;
        pass1.setAttribute("class", "badInput");
        pass2.setAttribute("class", "badInput");
    }
    else {
        pass1.setAttribute("class", "input");
        pass2.setAttribute("class", "input");
    }

    if (nB && mB && pass) {
        var user = await createUser(name.value, pass1.value, mail.value);
        if (user.ERROR == null) {
            document.getElementById("loginScreen").style.animation = "registeredLogin 1s forwards";
            document.getElementById("registerScreen").style.animation = "registeredRegisterScreen 1s forwards";    
        }
    }
}
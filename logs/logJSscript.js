function run() {
  var aR = getCookie("logArCookie");
  var aS = getCookie("logAsCookie");

  document.getElementById("aR").checked = (aR == "false") ? false : true;
  document.getElementById("aS").checked = (aS == "false") ? false : true;
  setInterval(reload, 5000);
}

function reload() {
  setCookie("logArCookie", document.querySelector("#aR").checked);
  setCookie("logAsCookie", document.querySelector("#aS").checked);

  if(document.querySelector("#aR").checked) {
    if(document.querySelector("#aS").checked) {
      window.scrollTo(0,document.body.scrollHeight);
    }
    location.reload();
  }
}


function setCookie(cname, cvalue, exdays) {
    const d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    let expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(";");
    for(let i = 0; i <ca.length; i++) {
      let c = ca[i];
      while (c.charAt(0) == " ") {
        c = c.substring(1);
      }
      if (c.indexOf(name) == 0) {
        return c.substring(name.length, c.length);
      }
    }
    return "";
}
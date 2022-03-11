async function buildServiceTable() {
    response = await getServices();
    if (!response.ERROR) {
        for (let i = 0; i < response.RESPONSE.length; i++) {
            createService(response.RESPONSE[i]);
        }
    }
}

function createService(service) {
    center = document.querySelector("#center");

    div = document.createElement("div");
    a = document.createElement("a");
    img = document.createElement("img");
    desc = document.createElement("div");

    div.setAttribute("class", "gallery");
    desc.setAttribute("class", "desc");

    a.setAttribute("target", "_blank");
    a.setAttribute("href", "http://" + location.host + ":" + service.PORT);

    img.setAttribute("src", "http://" + location.host + ":8000/files/" + service.ICON);
    img.setAttribute("alt", service.ICON);
    img.setAttribute("width", "180px");
    img.setAttribute("height", "180px");

    desc.innerHTML = service.NAME;

    a.appendChild(img);
    div.appendChild(a);
    div.appendChild(desc);

    center.appendChild(div);

    services.push({"DIV": img, "URL": "http://" + location.host + ":" + service.PORT});
    fetch("http://" + location.host + ":" + service.PORT + "/alive")
        .then(
            (response) => {
                img.setAttribute("class", "img");
            },
            (err) => {
                img.setAttribute("class", "dead");
            }
          );
}

setInterval(checkLife, 30000);
async function checkLife() {
    for (let i = 0; i < services.length; i++) {
        fetch(services[i].URL + "/alive")
        .then(
            (response) => {
                services[i].DIV.setAttribute("class", "img");
            },
            (err) => {
                services[i].DIV.setAttribute("class", "dead");
            }
          );
    }
}
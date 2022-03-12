async function buildServiceTable() {
    response = await getServices();
    if (!response.ERROR) {
        for (let i = 0; i < response.RESPONSE.length; i++) {
            createService(response.RESPONSE[i]);
        }
        checkLife();
        setInterval(checkLife, 30000);
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
    img.setAttribute("class", "img");

    desc.innerHTML = service.NAME;

    a.appendChild(img);
    div.appendChild(a);
    div.appendChild(desc);

    center.appendChild(div);

    services.push({"DIV": img, "URL": "http://" + location.host + ":" + service.PORT});
}

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
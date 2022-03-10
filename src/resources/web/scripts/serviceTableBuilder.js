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
    img.setAttribute("width", "300px");
    img.setAttribute("height", "300px");

    desc.innerHTML = service.NAME;

    a.appendChild(img);
    div.appendChild(a);
    div.appendChild(desc);

    center.appendChild(div);
}
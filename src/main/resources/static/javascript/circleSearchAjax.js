$(document).ready(function () {

    $("#circle-search-form").submit(function (event) {
        event.preventDefault();

        circle_fire_ajax_submit();
    });
    circle_fire_ajax_submit(); // Calls the function after loading the page to fill the table with all care circles
});

function circle_fire_ajax_submit() {

    var searchData = {};
    searchData["keywords"] = $('#circleKeywordsBox').val();

    var csrfToken = $("meta[name='_csrf']").attr('content');
    var csrfHeader = $("meta[name='_csrf_header']").attr('content');

    $.ajax(
        {
            type: "POST",
            contentType : "application/json",
            url: "/siteAdmin/circleClientOverview/searchList",
            data: JSON.stringify(searchData),
            dataType: "json",
            cache: false,
            timeout: 10000,

            complete: function (data) {
                fillTable(data);
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            error: function (e) {
                console.log(e)
            }
        });
}

function fillTable(resultData) {
    let new_tbody = document.createElement('tbody');

    if (resultData["responseJSON"]["circles"].length === 0) {
        let td = document.createElement('td');
        let tr = document.createElement('tr');
        td.textContent = "No care circles found";
        tr.appendChild(td);
        new_tbody.append(tr);
    } else {
        resultData["responseJSON"]["circles"].forEach(circle => {
            let clientList = circle["clientList"];
            let tr = document.createElement('tr');
            let tdName = document.createElement('td');
            let tdClient = document.createElement('td');

            if (clientList.length === 0) {
              let liClient = document.createElement('li');
              liClient.textContent = "No clients in this care circle found";
              tdClient.appendChild(liClient)
            } else {
                clientList.forEach(client => {
                let liClient = document.createElement('li');
                liClient.textContent = client["userName"];
                tdClient.appendChild(liClient);
            })}

            let aCircle = document.createElement('a');
            let hrefCircle = document.createAttribute('href');
            tdName.classList.add('circleLink');
            hrefCircle.value = "/circles/" + circle["circleId"];  // edit url
            aCircle.setAttributeNode(hrefCircle);
            aCircle.text = circle["circleName"];
            tdName.appendChild(aCircle);
            tr.appendChild(tdName);
            tr.appendChild(tdClient);
            new_tbody.append(tr);

        });
    }

    let old_tbody = document.getElementById("circleResultTable").tBodies.item(0);
    document.getElementById("circleResultTable").replaceChild(new_tbody, old_tbody);
}

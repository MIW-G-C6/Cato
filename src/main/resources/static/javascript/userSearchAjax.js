$(document).ready(function () {

    $("#search-form").submit(function (event) {
        event.preventDefault();
        fire_ajax_submit();
    });
    fire_ajax_submit(); // Calls the function after loading the page to fill the table with all users
});

function fire_ajax_submit() {

    var searchData = {};
    searchData["keywords"] = $("#keywordsBox").val();

    var csrfToken = $("meta[name='_csrf']").attr('content');
    var csrfHeader = $("meta[name='_csrf_header']").attr('content');

    $.ajax(
        {
            type: "POST",
            contentType : "application/json",
            url: "/siteAdminDashboard/searchList",
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

    resultData["responseJSON"]["users"].forEach(user => {
        let tr = document.createElement('tr');
        let tdName = document.createElement('td');
        let tdEmail = document.createElement('td');
        let tdEdit = document.createElement('td');
        let tdDelete = document.createElement('td');
        let aUser = document.createElement('a');
        let aEdit = document.createElement('a');
        let aDelete = document.createElement('a');
        let hrefUser = document.createAttribute('href');
        let hrefEdit = document.createAttribute('href');
        let hrefDelete = document.createAttribute('href');
        let imgEdit = document.createElement('img');
        let imgDelete = document.createElement('img');
        let imgHeightEdit = document.createAttribute('height');
        let imgWidthEdit = document.createAttribute('width');
        let imgHeightDelete = document.createAttribute('height');
        let imgWidthDelete = document.createAttribute('width');
        let editSrc = document.createAttribute('src');
        let deleteSrc = document.createAttribute('src');
        editSrc.value = "/css/images/edit-icon.svg";
        deleteSrc.value = "/css/images/delete-icon.svg";
        imgHeightEdit.value = "20";
        imgWidthEdit.value = "20";
        imgHeightDelete.value = "20";
        imgWidthDelete.value = "20";
        imgEdit.setAttributeNode(editSrc);
        imgEdit.setAttributeNode(imgHeightEdit);
        imgEdit.setAttributeNode(imgWidthEdit);
        imgDelete.setAttributeNode(deleteSrc);
        imgDelete.setAttributeNode(imgHeightDelete);
        imgDelete.setAttributeNode(imgWidthDelete);
        hrefUser.value = "profilepage/" + user["userId"];
        hrefEdit.value = "users/edit/" + user["userId"];
        hrefDelete.value = "users/delete/" + user["userId"];
        aUser.setAttributeNode(hrefUser);
        aEdit.setAttributeNode(hrefEdit);
        aDelete.setAttributeNode(hrefDelete);
        aUser.text = user["name"];
        tdEmail.textContent = user["email"];
        let editClass = document.createAttribute('class');
        editClass.value = "edit";
        let deleteClass = document.createAttribute('class');
        deleteClass.value = "delete";
        aEdit.appendChild(imgEdit);
        aDelete.appendChild(imgDelete);
        tdName.appendChild(aUser);
        tdEdit.appendChild(aEdit);
        tdDelete.appendChild(aDelete);
        tr.appendChild(tdName);
        tr.appendChild(tdEmail);
        tr.appendChild(tdEdit);
        tr.appendChild(tdDelete);
        new_tbody.append(tr);
    });

    let old_tbody = document.getElementById("resultTable").tBodies.item(0);
    console.log(old_tbody);
    document.getElementById("resultTable").replaceChild(new_tbody, old_tbody);
}
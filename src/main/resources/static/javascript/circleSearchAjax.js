document.getElementById("deleteCircleModal").innerHTML =`
    <div class="modal-content" id="deleteThisCircleModal">
            <div class="modal-header">
                <h2 class="modal-title">Warning!</h2>
                <span class="close">&times;</span>
            </div>
            <div class="modal-body">
                <p>Are you sure you want to delete:</p>
                <span id="circleNameSpan" ></span>
            </div>
            <div class="modal-footer">
                <button type="button" class="catoButton" id="noBtn">No</button>
                <button type="button" class="btn btn-primary catoYesWarningButton" id="circleModalYesButton">Yes</button>
            </div>
        </div>`;

var modal = document.getElementById("deleteThisCircleModal");

var modalCloseBtn = document.getElementsByClassName("close")[0];

var modalNoBtn = document.getElementById("noBtn");

var circleModalYesBtn = document.getElementById("circleModalYesButton")



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
            let tr = document.createElement('tr');
            let tdName = document.createElement('td');
            let tdEdit = document.createElement('td');
            let tdDelete = document.createElement('td');
            let aCircle = document.createElement('a');
            let aEdit = document.createElement('a');
            let aDelete = document.createElement('a');
            let hrefCircle = document.createAttribute('href');
            let hrefEdit = document.createAttribute('href');
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
            hrefCircle.value = "/circles/" + circle["circleId"];  // edit url
            hrefEdit.value = "/circles/options/edit/" + circle["circleId"];     // edit url
            aCircle.setAttributeNode(hrefCircle);
            aEdit.setAttributeNode(hrefEdit);
            aDelete.classList.add('pointer');
            aDelete.onclick = function () {
                circleModalYesBtn.onclick = function() {
                    window.location = "/circles/delete/" + circle["circleId"];
                }
                $('#deleteCircleModal').modal('show');
                document.getElementById("circleNameSpan").innerHTML=circle["circleName"];
            }
            aCircle.text = circle["circleName"];
            let editClass = document.createAttribute('class');
            editClass.value = "edit";
            let deleteClass = document.createAttribute('class');
            deleteClass.value = "delete";
            aEdit.appendChild(imgEdit);
            aDelete.appendChild(imgDelete);
            tdName.appendChild(aCircle);
            tdEdit.appendChild(aEdit);
            tdDelete.appendChild(aDelete);
            tr.appendChild(tdName);
            tr.appendChild(tdEdit);
            tr.appendChild(tdDelete);
            new_tbody.append(tr);
        });
    }

    let old_tbody = document.getElementById("circleResultTable").tBodies.item(0);
    document.getElementById("circleResultTable").replaceChild(new_tbody, old_tbody);
}

modalCloseBtn.onclick = function() {
    $('#deleteCircleModal').modal('hide');
}

modalNoBtn.onclick = function() {
    $('#deleteCircleModal').modal('hide');
}

window.onclick = function(event) {
    if (event.target === modal) {
        $('#deleteCircleModal').modal('hide');
    }
}

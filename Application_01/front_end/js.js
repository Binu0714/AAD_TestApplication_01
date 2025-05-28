$(document).ready(function (){
    clearFeilds();
});

function clearFeilds() {
    $('#eventId').val('');
    $('#eventName').val('');
    $('#eventDate').val('');
    $('#eventPlace').val('');
    $('#eventDescription').val('');
}

function refreshTable(data){
    const tbody = $('#eventTable tbody');
    tbody.empty();
    data.forEach(event => {
        tbody.append(`
            <tr>
                 <td>${event.eid}</td>
                 <td>${event.ename}</td>
                 <td>${event.edescription}</td>
                 <td>${event.edate}</td>
                 <td>${event.eplace}</td>
            </tr>
        `);
    });
}

$('#get-btn').click(function (){
    $.ajax({
        url: 'http://localhost:8080/Application_01_Web_exploded/event',
        method: 'GET',
        success: function (response) {
            refreshTable(response);
        },
        error: function () {
            alert("Error fetching data");
        }
    });
});

$('#create-btn').click(function (action) {
    action.preventDefault();

    const event = {
        eid: $('#eventId').val(),
        ename: $('#eventName').val(),
        edescription: $('#eventDescription').val(),
        edate: $('#eventDate').val(),
        eplace: $('#eventPlace').val()
    };

    $.ajax({
        url: 'http://localhost:8080/Application_01_Web_exploded/event',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(event),

        success: function (response) {
            Swal.fire({
                icon: 'success',
                title: 'Event Created',
                text: 'Event Created successfully!',
                confirmButtonText: 'OK'
            });
            clearFeilds();
            $('#get-btn').click();
        },
        error: function () {
            Swal.fire({
                icon: 'error',
                title: 'Unable to Create',
                text: 'Unable to create new Event.Please check your input values and try again.',
            });
        }
    });
});

$('#update-btn').click(function (action) {
    const event = {
        eid: $('#eventId').val(),
        ename: $('#eventName').val(),
        edescription: $('#eventDescription').val(),
        edate: $('#eventDate').val(),
        eplace: $('#eventPlace').val()
    };
    $.ajax({
        url: 'http://localhost:8080/Application_01_Web_exploded/event',
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(event),
        success: function (response) {
            Swal.fire({
                icon: 'success',
                title: 'Event Updated',
                text: 'Event updated successfully!',
                confirmButtonText: 'OK'
            });
            clearFeilds();
            $('#get-btn').click();
        },
        error: function () {
            Swal.fire({
                icon: 'error',
                title: 'Unable to Update',
                text: 'Unable to Update Event.Please check your input values and try again.',
            });
        }
    });
});

$('#eventTable').on('click', 'tr', function () {
    $('#eventTable tr').removeClass('selected-row');
    $(this).addClass('selected-row');

    const tds = $(this).find('td');
    $('#eventId').val(tds.eq(0).text());
    $('#eventName').val(tds.eq(1).text());
    $('#eventDescription').val(tds.eq(2).text());
    $('#eventDate').val(tds.eq(3).text());
    $('#eventPlace').val(tds.eq(4).text());
});

$('#delete-btn').click(function (action) {
    const eid = $('#eventId').val();
    if (!eid) {
        Swal.fire({
            icon: 'error',
            title: 'Event ID Fieled is null',
            text: 'Unable to Delete Event.Please check your input values and try again.',
        });
        return;
    }
    $.ajax({
        url: 'http://localhost:8080/Application_01_Web_exploded/event',
        method: 'DELETE',
        contentType: 'application/json',
        data: JSON.stringify({ eid }),
        success: function (response) {
            Swal.fire({
                icon: 'success',
                title: 'Event Deleted',
                text: 'Event Deleted successfully!',
                confirmButtonText: 'OK'
            });
            clearFeilds();
            $('#get-btn').click();
        },
        error: function () {
            Swal.fire({
                icon: 'error',
                title: 'Unable to delete',
                text: 'Unable to Delete Event.Please check your input values and try again.',
            });
        }
    });
});

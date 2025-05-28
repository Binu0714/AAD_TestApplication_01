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
            $('#get-btn').click();
        },
        error: function () {
            alert("Error creating event");
        }
    });
});
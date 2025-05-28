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
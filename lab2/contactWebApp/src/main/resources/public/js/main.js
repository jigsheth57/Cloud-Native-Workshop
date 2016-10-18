var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#notifications").show();
    }
    else {
        $("#notifications").hide();
    }
    $("#messages").html("");
}

function connect() {
    var socket = new SockJS('/stomp');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/message', function (data) {
        	console.log('data: ' + data);
            showGreeting(JSON.parse(data.body));
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function showGreeting(message) {
	var messageList = $("#messages");
	messageList.append("<tr>");
	messageList.append("<td>" + message.Status
			+ "</td>");
	messageList.append("<td>" + message.Date
			+ "</td>");
	messageList.append("<td>"
			+ message.Contact.Id + "</td>");
	messageList.append("<td>"
			+ message.Contact.firstName
			+ "&nbsp;"
			+ message.Contact.lastName
			+ "</td>");
	messageList.append("<td>"
			+ message.Contact.title + "</td>");
	messageList.append("<td>"
			+ message.Contact.email + "</td>");
	messageList.append("<td>"
			+ message.Contact.phone.type + ": " + message.Contact.phone.value + "</td>");
	messageList.append("</tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
});


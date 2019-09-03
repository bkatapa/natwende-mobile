/**
 * Seat reservation updater websocket
 */

var uri = "ws://localhost:8080/natwende/seatUpdater";
console.log(uri);
var websocket = null;
var message = "";

function openConnection() {
    websocket = new WebSocket(this.uri);
    websocket.onmessage = function (event) { // alert(event.data);
        var jsonObj = event.data;
        if (jsonObj.constructor !== {}.constructor) {
            jsonObj = JSON.parse(jsonObj);
        }
        console.log(jsonObj.coordinates);
        console.log(jsonObj.action);
        console.log(jsonObj.message);
        if (jsonObj.message !== 'undefined' && jsonObj.message !== null) {
            jsonObj.message.severity = jsonObj.action === 'reverse' ? 'warn' : 'info';
            handleMessage(jsonObj.message);
        }
        updateSeats(jsonObj);
    };
    websocket.onopen = function () {
        var msg = 'trip_id:' + document.getElementById('trip-id').value;
        sendData(msg);
    };
}

function closeConnection() {
    websocket.close();
}

function sendMessage() {
    var msg = document.getElementById('trip-id').value;
    websocket.send(msg);
}

function sendData(msg) {
    websocket.send(msg);
}
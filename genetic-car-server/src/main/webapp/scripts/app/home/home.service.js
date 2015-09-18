angular.module("gen.home.service", [])
    .service("ChampionsService", [ '$q', function($q) {

    var service = {};
    var listener = $q.defer();
    var socket = {
        client: null,
        stomp: null
    };
    var messageIds = [];

    var prefixUrl = location.port == 9000 ? '//localhost:8080' : '';
    service.SOCKET_URL = prefixUrl + "/status/champions";
    service.CHAMPIONS_TOPIC = "/topic/champions";
    service.CHAMPIONS_BROKER = "/app/status/champions";

    service.receive = function() {
        return listener.promise;
    };

    service.send = function(message) {
        var id = Math.floor(Math.random() * 1000000);
        socket.stomp.send(service.CHAMPIONS_BROKER, {
            priority: 9
        }, JSON.stringify({
            message: message,
            id: id
        }));
        messageIds.push(id);
    };

    var onClose = function() {
        console.log("close websocket");
    };

    var getMessage = function(data) {
        var car = JSON.parse(data);
        var message = {
            car: car
        };
        if (_.some(messageIds, message.id)) {
            message.self = true;
            messageIds = _.remove(messageIds, message.id);
        }
        return message;
    };

    var startListener = function(frame) {
        console.log('Connected, start listener ', frame);
        socket.stomp.subscribe(service.CHAMPIONS_TOPIC, function(data) {
            console.log('Data received', data);
            listener.notify(getMessage(data.body));
        });
    };

    service.connect = function() {
        if (!socket.client) {
            socket.client = new SockJS(service.SOCKET_URL);
            socket.stomp = Stomp.over(socket.client);
            socket.stomp.connect({}, startListener);
            socket.stomp.onclose = onClose;
        }
    };

    service.disconnect = function() {
        console.log("call disconnect");
        if (socket.stomp) {
            socket.stomp.disconnect();
            socket = {
                client: null,
                stomp: null
            };
            console.log("end disconnect");
        }
    };

    return service;

}]);

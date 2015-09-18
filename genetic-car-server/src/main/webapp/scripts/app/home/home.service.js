angular.module("gen.home.service", [])
    .service("ChampionsService", [ '$q', '$log', function($q, $log) {

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
        $log.info("close websocket");
    };

    var getMessage = function(data) {
        var champion = JSON.parse(data);
        var message = {
            champion: champion
        };
        if (_.some(messageIds, message.id)) {
            message.self = true;
            messageIds = _.remove(messageIds, message.id);
        }
        return message;
    };

    var startListener = function(frame) {
        socket.stomp.subscribe(service.CHAMPIONS_TOPIC, function(data) {
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
        if (socket.stomp) {
            socket.stomp.disconnect();
            socket = {
                client: null,
                stomp: null
            };
        }
    };

    return service;

}]);

'use strict';

angular.module('gen.config', [])
    .config(['$provide', function ($provide) {
        var serverPath = location.port == 9000 ? '//localhost:8080' : '';
        $provide.constant('config', {
            SIMULATION_PATH: serverPath + '/simulation',
            SWAGGER_PATH: serverPath + '/swagger-ui.html',
            WALL_SOCKET_URL: serverPath + '/status/champions',
            WALL_CHAMPIONS_TOPIC: "/topic/champions",
            Wall_CHAMPIONS_BROKER: "/app/status/champions"
        });
    }]);

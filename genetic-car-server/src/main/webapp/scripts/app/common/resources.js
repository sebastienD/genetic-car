'use strict';

angular.module('gen.resources', ['gen.config', 'ngResource'])

    .factory('SimulationResource', ['$resource', 'config', function ($resource, config) {
        var prefixUrl = location.port == 9000 ? '//localhost:8080' : '';
        return $resource(config.SIMULATION_PATH + '/champions/:team', {}, {
            getAllChampions: {
                method: 'GET',
                isArray: true,
                url: prefixUrl + config.SIMULATION_PATH + '/champions'
            },
            getChampionForTeam: {
                method: 'GET',
                isArray: false,
                url: prefixUrl + config.SIMULATION_PATH + '/champions/:team'
            }
        });
    }])

;


'use strict';

angular.module('gen.resources', ['gen.config', 'ngResource'])

    .factory('SimulationResource', ['$resource', 'config', function ($resource, config) {
        return $resource(config.SIMULATION_PATH + '/champions/:team', {}, {
            getAllChampions: {
                method: 'GET',
                isArray: true
            },
            getChampionForTeam: {
                method: 'GET',
                isArray: false
            }
        });
    }])

;


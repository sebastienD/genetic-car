'use strict';

angular.module('gen.config', [])
    .config(['$provide', function ($provide) {
        $provide.constant('config', {
            SIMULATION_PATH: '/simulation'
        });
    }]);

(function () {
    'use strict';
    angular.module('gen', [
        // gen's specifics module
        'gen.config', 'gen.home',
        // Third party's modules
        'ui.router', 'ngSanitize'
    ])

        .config(['$stateProvider', '$urlRouterProvider', '$httpProvider',
            function ($stateProvider, $urlRouterProvider, $httpProvider) {
                //
                // For any unmatched url, redirect to home
                $urlRouterProvider.otherwise('home');

            }])
        .run(['$log', '$rootScope', '$state',
            function ($log, $rootScope, $state) {

                $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams) {
                    $log.debug('state should change to state ' + toState.name);
                });

                $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState) {
                    $log.debug('state change with success from ' + fromState.name + ' to state ' + toState.name);
                });

            }
        ]);
})();

'use strict';

angular.module('gen.final', ['ui.router'])

    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('final', {
            url: '/final',
            templateUrl: 'scripts/app/final/final-tpl.html',
            controller: 'FinalController'
        });
    }])

    .controller('FinalController', ['$scope', function($scope) {


    }])

;
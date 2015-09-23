'use strict';

angular.module('gen.final', ['ui.router', 'gen.final.directives', 'gen.resources'])

    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('final', {
            url: '/final',
            templateUrl: 'scripts/app/final/final-tpl.html',
            controller: 'FinalController'
        });
    }])

    .controller('FinalController', ['$scope', 'SimulationResource', function($scope, SimulationResource) {

        retrieveChampions();

        function retrieveChampions() {
            SimulationResource.getAllChampions().$promise.then(function(champions) {
                $scope.champions = champions;
            });
        }

    }])

;
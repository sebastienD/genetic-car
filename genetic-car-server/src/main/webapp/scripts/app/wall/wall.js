'use strict';

angular.module('gen.wall', ['ui.router', 'gen.wall.service', 'gen.car.directives', 'gen.resources'])

    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('wall', {
            url: '/wall',
            templateUrl: 'scripts/app/wall/wall-tpl.html',
            controller: 'WallController'
        });
    }])

    .controller('WallController', ['$scope', 'ChampionsService', 'SimulationResource', function($scope, ChampionsService, SimulationResource) {

        $scope.connected = false;
        $scope.connect = connect;
        $scope.disconnect = disconnect;
        $scope.retrieveChampions = retrieveChampions;

        function retrieveChampions() {
            SimulationResource.getAllChampions().$promise.then(function(champions) {
                $scope.champions = champions;
            });
        }

        function connect() {
            $scope.champions = [];
            ChampionsService.connect();
            $scope.connected = true;
        };

        function disconnect() {
            $scope.champions = [];
            ChampionsService.disconnect();
            $scope.connected = false;
        }

        connect();

        ChampionsService.receive().then(null, null,
            function(message) {
                _.remove($scope.champions, function(champion) {
                    return champion.statistic.team == message.champion.statistic.team;
                });
                $scope.champions.push(message.champion);
        });

        $scope.$on("$destroy", function(){
            ChampionsService.disconnect();
        });
    }])

;
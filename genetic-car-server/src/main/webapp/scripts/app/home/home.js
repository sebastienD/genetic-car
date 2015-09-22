'use strict';

angular.module('gen.home', ['ui.router', 'gen.home.service', 'gen.car.directives', 'gen.resources'])

    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('home', {
            url: '/home',
            templateUrl: 'scripts/app/home/home-tpl.html',
            controller: 'HomeController'
        });
    }])

    .controller('HomeController', ['$scope', 'ChampionsService', 'SimulationResource', function($scope, ChampionsService, SimulationResource) {

        connect();

        $scope.connect = connect;
        $scope.disconnect = disconnect;
        $scope.retrieveChampions = retrieveChampions;
        $scope.connected = false;

        function retrieveChampions() {
            SimulationResource.getAllChampions().$promise.then(function(champions) {
                $scope.champions = champions;
            });
        }

        function connect() {
            $scope.champions = [];
            ChampionsService.connect();
        };

        function disconnect() {
            $scope.champions = [];
            ChampionsService.disconnect();
        }

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
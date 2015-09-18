'use strict';

angular.module('gen.home', ['ui.router', 'gen.home.service', 'gen.car.directives'])

    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('home', {
            url: '/home',
            templateUrl: 'scripts/app/home/home-tpl.html',
            controller: 'HomeController'
        });
    }])

    .controller('HomeController', ['$scope', 'ChampionsService', function($scope, ChampionsService) {

        connect();

        $scope.connect = connect;
        $scope.disconnect = disconnect;

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
                    return champion.car.team == message.champion.car.team;
                });
                $scope.champions.push(message.champion);
        });

        $scope.$on("$destroy", function(){
            ChampionsService.disconnect();
        });
    }])

;
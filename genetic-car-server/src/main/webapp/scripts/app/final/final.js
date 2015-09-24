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

        $scope.fullWindow = fullWindow;
        $scope.wrapperClass = 'final';

        function retrieveChampions() {
            SimulationResource.getAllChampions().$promise.then(function(champions) {
                $scope.champions = champions;
            });
        }

        function fullWindow() {
            if ($scope.wrapperClass == 'final') {
                $scope.wrapperClass = 'final-fullscreen';
                $scope.$broadcast("fullscreen-final", true);
            } else {
                $scope.wrapperClass = 'final';
                $scope.$broadcast("fullscreen-final", false);
            }
        }

        angular.element(window).on('resize', function () {
            if ($scope.wrapperClass == 'final-fullscreen') {
                $scope.$broadcast("fullscreen-final", true);
            }
        })

    }])

;
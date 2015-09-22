'use strict';

angular.module('gen.home', ['ui.router', 'gen.config'])

    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('home', {
            url: '/home',
            templateUrl: 'scripts/app/home/home-tpl.html',
            controller: 'HomeController'
        });
    }])

    .controller('HomeController', ['$scope', 'config', function($scope, config) {

        $scope.swaggerPath = config.SWAGGER_PATH;

    }])

;
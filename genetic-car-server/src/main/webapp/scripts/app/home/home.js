'use strict';

angular.module('gen.home', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('home', {
            url: '/home',
            templateUrl: 'scripts/app/home/home-tpl.html'
        });
    }]);
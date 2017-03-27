var http = require('http');
var lodash = require('lodash')
var async = require('asyncawait/async');
var await = require('asyncawait/await');
var rand = require('./random.js');

var team = 'RED'; //RED, YELLOW, BLUE, GREEN, ORANGE, PURPLE
var host = 'genetic-car.herokuapp.com';
//var host = 'localhost';
var port = 80;
//var port = 8080;

var randomCar = function() {
    return {
        chassis: {
            vecteurs: [
                rand.randomChassisAxis(),
                0,
                rand.randomChassisAxis(),
                rand.randomChassisAxis(),
                0,
                rand.randomChassisAxis(),
                -1 * rand.randomChassisAxis(),
                rand.randomChassisAxis(),
                -1 * rand.randomChassisAxis(),
                0,
                -1 * rand.randomChassisAxis(),
                -1 * rand.randomChassisAxis(),
                0,
                -1 * rand.randomChassisAxis(),
                rand.randomChassisAxis(),
                -1 * rand.randomChassisAxis()
            ],
            densite: rand.randomChassisDensity()
        },
        wheel1: {
            radius: rand.randomWheelRadius(),
            density: rand.randomWheelDensity(),
            vertex: rand.randomVertex()
        },
        wheel2: {
            radius: rand.randomWheelRadius(),
            density: rand.randomWheelDensity(),
            vertex: rand.randomVertex()
        }
    }
}

var cars = [];
for (var i=0; i < 20; i++) {
    cars.push(randomCar());
}

var jsonString = JSON.stringify(cars)

var headers = {
    'Content-Type': 'application/json',
    'Content-Length': Buffer.byteLength(jsonString, 'utf8')
};

var options = {
    host: host,
    port: port,
    path: '/simulation/evaluate/' + team,
    method: 'POST',
    headers: headers
};

var request = http.request(options, function(res) {
    console.log('status: ', res.statusCode);
    res.setEncoding('utf8');
    res.on('data', function (chunk) {
        if (res.statusCode == 200) {
            var cars = JSON.parse(chunk);
            var champion = cars.reduce(function(previous,current) {
                return previous.score > current.score ? previous:current;
            });
            console.log('champion (car): ', champion.car);
            console.log('champion (score): ', champion.score);
        } else {
            console.log('erreur: ', chunk);
        }
    });
});

request.on('error', function(e) {
    console.log('problem with request: ' + e.message);
});

request.write(jsonString);
request.end();
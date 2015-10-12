var CHASSIS_MIN_AXIS = 0.1;
var CHASSIS_MAX_AXIS = 1.1;

var CHASSIS_MIN_DENSITY = 30;
var CHASSIS_MAX_DENSITY = 300;

var WHEEL_MIN_RADIUS = 0.2;
var WHEEL_MAX_RADIUS = 0.5;

var WHEEL_MIN_DENSITY = 40;
var WHEEL_MAX_DENSITY = 100;

var VERTEX_MAX_VALUE = 7;

function random (low, high) {
    return Math.random() * (high - low) + low;
}

function randomInt (low, high) {
    return Math.floor(Math.random() * (high - low + 1) + low);
}

exports.randomChassisAxis = function () {
    return random(CHASSIS_MIN_AXIS, CHASSIS_MAX_AXIS);
}

exports.randomChassisDensity = function () {
    return random(CHASSIS_MIN_DENSITY, CHASSIS_MAX_DENSITY);
}

exports.randomWheelRadius = function () {
    return random(WHEEL_MIN_RADIUS, WHEEL_MAX_RADIUS);
}

exports.randomWheelDensity = function () {
    return random(WHEEL_MIN_DENSITY, WHEEL_MAX_DENSITY);
}

exports.randomVertex = function () {
    return randomInt(0, VERTEX_MAX_VALUE);
}
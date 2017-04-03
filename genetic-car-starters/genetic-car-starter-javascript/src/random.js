const CHASSIS_MIN_AXIS = 0.1;
const CHASSIS_MAX_AXIS = 1.1;

const CHASSIS_MIN_DENSITY = 30;
const CHASSIS_MAX_DENSITY = 300;

const WHEEL_MIN_RADIUS = 0.2;
const WHEEL_MAX_RADIUS = 0.5;

const WHEEL_MIN_DENSITY = 40;
const WHEEL_MAX_DENSITY = 100;

const VERTEX_MAX_VALUE = 7;

export const random = (low, high) => (Math.random() * (high - low)) + low;
export const randomInt = (low, high) => Math.floor(random(low, high));
export const randomChassisAxis = () => random(CHASSIS_MIN_AXIS, CHASSIS_MAX_AXIS);
export const randomChassisDensity = () => random(CHASSIS_MIN_DENSITY, CHASSIS_MAX_DENSITY);
export const randomWheelRadius = () => random(WHEEL_MIN_RADIUS, WHEEL_MAX_RADIUS);
export const randomWheelDensity = () => random(WHEEL_MIN_DENSITY, WHEEL_MAX_DENSITY);
export const randomVertex = () => randomInt(0, VERTEX_MAX_VALUE);

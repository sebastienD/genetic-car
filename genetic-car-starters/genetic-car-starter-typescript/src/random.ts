import {
    CHASSIS_MAX_AXIS,
    CHASSIS_MAX_DENSITY,
    CHASSIS_MIN_AXIS,
    CHASSIS_MIN_DENSITY,
    VERTEX_MAX_VALUE,
    WHEEL_MAX_DENSITY,
    WHEEL_MAX_RADIUS,
    WHEEL_MIN_DENSITY,
    WHEEL_MIN_RADIUS
} from "./constants";

export class Random {

    public static random(low: number, high: number) {
        return (Math.random() * (high - low)) + low;
    }

    public static randomInt(low: number, high: number) {
        return Math.floor(Random.random(low, high));
    }

    public static randomChassisAxis() {
        return Random.random(CHASSIS_MIN_AXIS, CHASSIS_MAX_AXIS);
    }

    public static randomChassisDensity() {
        return Random.random(CHASSIS_MIN_DENSITY, CHASSIS_MAX_DENSITY);
    }

    public static randomWheelRadius() {
        return Random.random(WHEEL_MIN_RADIUS, WHEEL_MAX_RADIUS);
    }

    public static randomWheelDensity() {
        return Random.random(WHEEL_MIN_DENSITY, WHEEL_MAX_DENSITY);
    }

    public static randomVertex() {
        return Random.randomInt(0, VERTEX_MAX_VALUE);
    }
}
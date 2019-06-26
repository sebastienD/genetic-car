import {Random} from "./random";

export class Car {

    public chassis: Chassis;
    public wheel1: Wheel;
    public wheel2: Wheel;

    constructor(chassis: Chassis, wheel1: Wheel, wheel2: Wheel) {
        this.chassis = chassis;
        this.wheel1 = wheel1;
        this.wheel2 = wheel2;
    }

    public static random() {
        return new Car(
            Chassis.random(),
            Wheel.random(),
            Wheel.random(),
        );
    }
}

export class Chassis {
    public vecteurs: number[] = [];
    public densite: number;

    constructor(vecteurs: number[], densite: number) {
        this.vecteurs = vecteurs;
        this.densite = densite;
    }

    public static random(): Chassis {
        return new Chassis(
            [
                Random.randomChassisAxis(),
                0,
                Random.randomChassisAxis(),
                Random.randomChassisAxis(),
                0,
                Random.randomChassisAxis(),
                -1 * Random.randomChassisAxis(),
                Random.randomChassisAxis(),
                -1 * Random.randomChassisAxis(),
                0,
                -1 * Random.randomChassisAxis(),
                -1 * Random.randomChassisAxis(),
                0,
                -1 * Random.randomChassisAxis(),
                Random.randomChassisAxis(),
                -1 * Random.randomChassisAxis()
            ],
            Random.randomChassisDensity()
        );
    }
}

export class Wheel {
    public radius: number;
    public density: number;
    public vertex: number;

    constructor(radius: number, density: number, vertex: number) {
        this.radius = radius;
        this.density = density;
        this.vertex = vertex;
    }

    public static random(): Wheel {
        return new Wheel(
            Random.randomWheelRadius(),
            Random.randomWheelDensity(),
            Random.randomVertex(),
        );
    }
}
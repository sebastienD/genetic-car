import {
  randomChassisAxis,
  randomChassisDensity,
  randomWheelRadius,
  randomVertex,
  randomWheelDensity,
} from './random';

export const randomCar = () => [
  randomChassisAxis(),
  0,
  randomChassisAxis(),
  randomChassisAxis(),
  0,
  randomChassisAxis(),
  -1 * randomChassisAxis(),
  randomChassisAxis(),
  -1 * randomChassisAxis(),
  0,
  -1 * randomChassisAxis(),
  -1 * randomChassisAxis(),
  0,
  -1 * randomChassisAxis(),
  randomChassisAxis(),
  -1 * randomChassisAxis(),
  randomChassisDensity(),
  randomWheelRadius(),
  randomWheelDensity(),
  randomVertex(),
  randomWheelRadius(),
  randomWheelDensity(),
  randomVertex(),
];

export const toView = car => ({
  chassis: {
    vecteurs: car.slice(0, 16),
    densite: car[16],
  },
  wheel1: {
    radius: car[17],
    density: car[18],
    vertex: car[19],
  },
  wheel2: {
    radius: car[20],
    density: car[21],
    vertex: car[22],
  },
});

export const fromView = view => Array.of(
  ...view.car.chassis.vecteurs, view.car.chassis.densite,
  view.car.wheel1.radius, view.car.wheel1.density, view.car.wheel1.vertex,
  view.car.wheel2.radius, view.car.wheel2.density, view.car.wheel2.vertex);

export const fullWheelBuilder = (radius, density, vertex) => ({ radius, density, vertex });

export const buildCar = (chassis, wheel1, wheel2) => ({ chassis, wheel1, wheel2 });

export const buildWheel = wheel => fullWheelBuilder(wheel.radius, wheel.density, wheel.vertex);

export const buildChassis = (vectors, density) => ({ vectors, density });

import {
  randomChassisAxis,
  randomChassisDensity,
  randomWheelRadius,
  randomVertex,
  randomWheelDensity,
} from './random';

export const randomCar = () => ({
  chassis: {
    vecteurs: [
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
    ],
    densite: randomChassisDensity(),
  },
  wheel1: {
    radius: randomWheelRadius(),
    density: randomWheelDensity(),
    vertex: randomVertex(),
  },
  wheel2: {
    radius: randomWheelRadius(),
    density: randomWheelDensity(),
    vertex: randomVertex(),
  },
});

export const fullWheelBuilder = (radius, density, vertex) => ({ radius, density, vertex });

export const buildCar = (chassis, wheel1, wheel2) => ({ chassis, wheel1, wheel2 });

export const buildWheel = wheel => fullWheelBuilder(wheel.radius, wheel.density, wheel.vertex);

export const buildChassis = (vectors, density) => ({ vectors, density });

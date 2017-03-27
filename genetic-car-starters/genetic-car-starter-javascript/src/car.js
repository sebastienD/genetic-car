const random = (low, high) => (Math.random() * (high - low)) + low;
const randomInt = (low, high) => Math.floor(random(low, high));
const randomChassisAxis = () => random(0.1, 1.1);
const randomChassisDensity = () => random(30, 300);
const randomWheelRadius = () => random(0.2, 0.5);
const randomWheelDensity = () => random(40, 100);
const randomVertex = () => randomInt(0, 7);

export default function randomCar() {
  return {
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
  };
}

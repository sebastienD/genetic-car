import {Server} from "./server";
import {Team} from "./team";
import {Car} from "./car";

const team = Team.YELLOW;
const cars: Car[] = [];

for (let i = cars.length; i < 20; i += 1) {
    cars.push(Car.random());
}

Server.evaluate(team, cars);

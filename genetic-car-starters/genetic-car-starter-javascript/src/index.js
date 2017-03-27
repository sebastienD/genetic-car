/* eslint-disable no-console */

import fetch from 'node-fetch';
import randomCar from './car';

const host = 'http://genetic-car.herokuapp.com';
// const host = 'http://localhost:8080';
const team = 'RED'; // RED, YELLOW, BLUE, GREEN, ORANGE, PURPLE
const url = `${host}/simulation/evaluate/${team}`;

async function evaluate(cars) {
  const options = {
    method: 'POST',
    body: JSON.stringify(cars),
    headers: { 'Content-Type': 'application/json' },
  };

  try {
    const response = await fetch(url, options);
    if (response.ok) {
      const data = await response.json();
      // eslint-disable-next-line no-confusing-arrow
      const champion = data.reduce((a, b) => a.score >= b.score ? a : b);
      console.log('Mon champion est ', champion);
    } else {
      console.error('request failed: ', response.status, response.statusText);
    }
  } catch (e) {
    console.error(e);
  }
}

const cars = [];
for (let i = 0; i < 20; i += 1) {
  cars.push(randomCar());
}

evaluate(cars);

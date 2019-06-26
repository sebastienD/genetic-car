import {Car} from './car';
import {SERVER_HOST} from './config';
import {Team} from './team';
import {CarScore} from './car-score';
import fetch from 'node-fetch';

export class Server {

    public static async evaluate(team: Team, cars: Car[]) {
        const url = `${SERVER_HOST}/simulation/evaluate/${team}`;
        const options = {
            method: 'POST',
            body: JSON.stringify(cars),
            headers: {'Content-Type': 'application/json'},
        };

        try {
            const response = await fetch(url, options);
            if (response.ok) {
                const carScores: CarScore[] = await response.json();
                console.log(carScores.map(c => c.score));
                // eslint-disable-next-line no-confusing-arrow
                const champion = carScores.reduce((a, b) => a.score >= b.score ? a : b);
                console.log('Mon champion est ', JSON.stringify(champion));
            } else {
                console.error('request failed: ', response.status, response.statusText);
                console.error(await response.json());
            }
        } catch (e) {
            console.error(e);
        }
    }

    public static async getCurrentChampion(team: Team): Promise<CarScore> {
        const url = `${SERVER_HOST}/simulation/champions/${team}`;
        const options = {
            method: 'GET'
        };

        try {
            const response = await fetch(url, options);
            if (response.ok) {
                const { carScore: championScore} = await response.json();

                return championScore;
            } else {
                console.error('request failed: ', response.status, response.statusText);
                console.error(await response.json());
            }
        } catch (e) {
            console.error(e);
        }
    }
}
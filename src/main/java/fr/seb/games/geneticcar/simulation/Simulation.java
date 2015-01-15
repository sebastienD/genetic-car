package fr.seb.games.geneticcar.simulation;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

/**
 * Created by sebastien on 12/01/2015.
 */
public class Simulation {

    private float timeStep = 1.0F / 60.0F;

    private int box2dfps = 60;
    private int screenfps = 60;

    private int generationSize = 20;

    private Vec2 gravity = new Vec2(0.0F, -9.81F);

    private int zoom = 70;

    private World world;

    public Simulation() {

//        floorseed = Math.seedrandom();
        World world = new World(gravity);

        Ground ground = new Ground(world);
        ground.createFloor();

        generationZero();
//        cw_runningInterval = setInterval(simulationStep, Math.round(1000/box2dfps));

    }

    private void generationZero() {
        for(int k = 0; k < generationSize; k++) {
            var car_def = Car.createRandomCar();
            car_def.index = k;
            cw_carGeneration.push(car_def);
        }
        gen_counter = 0;
        cw_deadCars = 0;
        leaderPosition = new Object();
        leaderPosition.x = 0;
        leaderPosition.y = 0;
        cw_materializeGeneration();
    }



    private void simulationStep() {
        world.Step(1/box2dfps, 20, 20);
        for(int k = 0; k < generationSize; k++) {
            if(!cw_carArray[k].alive) {
                continue;
            }
            ghost_add_replay_frame(cw_carArray[k].replay, cw_carArray[k]);
            cw_carArray[k].frames++;
            position = cw_carArray[k].getPosition();

            if(cw_carArray[k].checkDeath()) {
                cw_carArray[k].kill();
                cw_deadCars++;

                if(cw_deadCars >= generationSize) {
                    cw_newRound();
                }
                if(leaderPosition.leader == k) {
                    // leader is dead, find new leader
                    cw_findLeader();
                }
                continue;
            }
            if(position.x > leaderPosition.x) {
                leaderPosition = position;
                leaderPosition.leader = k;
            }
        }
        showDistance(Math.round(leaderPosition.x*100)/100, Math.round(leaderPosition.y*100)/100);
    }
}


package fr.seb.games.geneticcar.simulation;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebastien on 12/01/2015.
 */
public class Simulation {

    private float timeStep = 1.0F / 60.0F;

    private int box2dfps = 60;
    private int screenfps = 60;

    private static int generationSize = 20;

    private Vec2 gravity = new Vec2(0.0F, -9.81F);

    private int zoom = 70;

    private World world;

    private List<CarDefinition> allCarDefinitions = new ArrayList<>(generationSize);
    private int deadCars = 0;
    private Position leaderPosition = new Position();

    public Simulation() {

//        floorseed = Math.seedrandom();
        World world = new World(gravity);

        Ground ground = new Ground(world);
        ground.createFloor();

        generationZero();
        while (true) {
            simulationStepAndReturnDistanceOfLeader();
            if (deadCars == generationSize) {
                break;
            }
        }

    }

    private void generationZero() {
        for(int k = 0; k < generationSize; k++) {
            CarDefinition car_Definition_def = CarDefinition.createRandomCar();
            car_Definition_def.index = k;
            allCarDefinitions.add(car_Definition_def);
        }

        deadCars = 0;
        leaderPosition = new Position();
        leaderPosition.x = 0;
        leaderPosition.y = 0;
        cw_materializeGeneration();
    }

    private void cw_materializeGeneration() {
        cw_carArray = new Array();
        for(var k = 0; k < generationSize; k++) {
            cw_carArray.push(new cw_Car(allCarDefinitions.get(k)));
        }
    }

    private int simulationStepAndReturnDistanceOfLeader() {
        world.step(1 / box2dfps, 20, 20);
        for(int k = 0; k < generationSize; k++) {
            if(!cw_carArray[k].alive) {
                continue;
            }

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
        return (Math.round(leaderPosition.x*100)/100);
    }

    public static class Position {
        public int x;
        public int y;
    }
}


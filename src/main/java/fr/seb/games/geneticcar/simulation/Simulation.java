package fr.seb.games.geneticcar.simulation;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by sebastien on 12/01/2015.
 */
public class Simulation {

    private static final Logger LOGGER = LoggerFactory.getLogger(Simulation.class);

    public static final int BOX2D_FPS = 60;

    private static float TIME_STEP = 1.0F / BOX2D_FPS;
    private static int SCREEN_FPS = 60;

    private static int GENERATION_SIZE = 20;

    public static final Vec2 GRAVITY = new Vec2(0.0F, -9.81F);

    private int zoom = 70;

    private World world;

    private List<CarDefinition> allCarDefinitions = new ArrayList<>(GENERATION_SIZE);
    private int deadCars = 0;
    private Leader leader = new Leader();

    private List<Car> allCars = new ArrayList<>(GENERATION_SIZE);

    public Simulation() {

        // floorseed = Math.seedrandom();
        world = new World(GRAVITY);

        Ground ground = new Ground(world);
        ground.createFloor();

    }

    public void runSimulation() {
        int temp = 0;
        while (true) {
            simulationStep();
            if (deadCars == GENERATION_SIZE) {
                break;
            }
            if (temp++ == 10) {
                break;
            }
        }
    }

    public void showAllScores() {
        allCars.stream().forEach(car -> LOGGER.info("score car {} : {}", car, car.getScore()));
    }

    public void buildGenerationZero() {
        for(int k = 0; k < GENERATION_SIZE; k++) {
            CarDefinition car_Definition_def = CarDefinition.createRandomCar();
            //car_Definition_def.index = k;
            allCarDefinitions.add(car_Definition_def);
        }

        deadCars = 0;
        leader = new Leader();
        materializeGeneration();
    }

    private void materializeGeneration() {
        allCars = new ArrayList<>(GENERATION_SIZE);
        allCarDefinitions.stream().forEach(cardef -> allCars.add(new Car(cardef, world)));
    }

    private void simulationStep() {
        LOGGER.debug("run simulation step");
        world.step(TIME_STEP, 20, 20);

        allCars.stream().filter(car -> car.alive).forEach(car -> {
            car.frames++;

            if (car.checkDeath()) {
                car.kill();
                deadCars ++;

                if (leader.car == car) {
                    Car leaderCar = findLeader();
                    leader.car = leaderCar;
                    leader.position = new Vec2(leaderCar.getPosition());
                }

            } else if (car.getPosition().x > leader.position.x) {
                leader.car = car;
                leader.position = new Vec2(car.getPosition());
            }

            LOGGER.debug("car {} updated", car);
        });

    }

    private Car findLeader() {
        return allCars.stream().filter(car -> car.alive).max(Comparator.comparing(car -> car.getPosition().x)).get();
    }

    private static class Leader {

        private Car car;
        private Vec2 position = new Vec2();

    }

}


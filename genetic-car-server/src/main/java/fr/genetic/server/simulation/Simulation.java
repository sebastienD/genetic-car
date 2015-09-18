package fr.genetic.server.simulation;

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
    public static final Vec2 GRAVITY = new Vec2(0.0F, -9.81F);

    private static final float TIME_STEP = 1F / BOX2D_FPS;
    private static final int DEFAULT_GENERATION_SIZE = 20;

    public List<Car> allCars = new ArrayList<>(DEFAULT_GENERATION_SIZE);
    public Leader leader = new Leader();
    public int nbRunSimulation = 0;

    private World world;
    private int deadCars = 0;

    public Simulation() {
        world = new World(GRAVITY);
        Ground ground = new Ground(world);
        ground.createFloor();
    }

    public Simulation(World world) {
        this.world = world;
        Ground ground = new Ground(world);
        ground.createFloor();
    }

    public void runSimulationForTest(List<CarDefinition> carDefinitions) {
        deadCars = 0;
        leader = new Leader();
        materializeGeneration(carDefinitions);
    }

    public void runSimulation(List<CarDefinition> carDefinitions) {
        deadCars = 0;
        leader = new Leader();
        nbRunSimulation++;
        materializeGeneration(carDefinitions);

        while (true) {
            simulationStep();
            if (deadCars == allCars.size()) {
                break;
            }
        }
    }

    public void showAllScores() {
        allCars.stream().forEach(car -> LOGGER.info("score car {} : {}", car.getScore(), car));
    }

    public List<CarDefinition> buildGenerationZero() {
        List<CarDefinition> carDefintions = new ArrayList<>();
        for(int k = 0; k < DEFAULT_GENERATION_SIZE; k++) {
            CarDefinition car_Definition_def = CarDefinition.createRandomCar();
            carDefintions.add(car_Definition_def);
        }
        return carDefintions;
    }

    public List<CarDefinition> buildBestGenerationZero() {
        List<CarDefinition> carDefintions = new ArrayList<>();
        for(int k = 0; k < DEFAULT_GENERATION_SIZE; k++) {
            CarDefinition car_Definition_def = CarDefinition.createMyBestCar();
            carDefintions.add(car_Definition_def);
        }
        return carDefintions;
    }

    private void materializeGeneration(List<CarDefinition> carDefinitions) {
        allCars = new ArrayList<>(carDefinitions.size());
        carDefinitions.stream().forEach(cardef -> allCars.add(new Car(cardef, world)));
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
                    Car leaderCar = findLeader(car);
                    leader.car = leaderCar;
                    leader.position = new Vec2(leaderCar.getPosition());
                }

            } else if (car.getPosition().x > leader.position.x) {
                leader.car = car;
                leader.position = new Vec2(car.getPosition());
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("car position {} with score {}", car.getPosition(), car.getScore());
            }
        });

    }

    private Car findLeader(Car leaderCar) {
        return allCars.stream().filter(car -> car.alive).max(Comparator.comparing(car -> car.getPosition().x)).orElse(leaderCar);
    }

    public static class Leader {
        public Car car;
        private Vec2 position = new Vec2();
    }

}


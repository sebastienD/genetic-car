package fr.genetic.server.simulation;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Simulation {

    private static final Logger LOGGER = LoggerFactory.getLogger(Simulation.class);

    public static final int BOX2D_FPS = 60;
    public static final Vec2 GRAVITY = new Vec2(0.0F, -9.81F);

    private static final float TIME_STEP = 1F / BOX2D_FPS;
    private static final int DEFAULT_GENERATION_SIZE = 20;

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
        materializeGeneration(carDefinitions);
    }

    public synchronized List<Car> runSimulation(List<CarDefinition> carDefinitions) {
        deadCars = 0;
        List<Car> cars = materializeGeneration(carDefinitions);

        while (true) {
            simulationStep(cars);
            if (deadCars == cars.size()) {
                break;
            }
        }

        return cars;
    }

    public static List<CarDefinition> buildGenerationZero() {
        List<CarDefinition> carDefintions = new ArrayList<>();
        for(int k = 0; k < DEFAULT_GENERATION_SIZE; k++) {
            CarDefinition car_Definition_def = CarDefinition.createRandomCar();
            carDefintions.add(car_Definition_def);
        }
        return carDefintions;
    }

    public static List<CarDefinition> buildBestGenerationZero() {
        List<CarDefinition> carDefintions = new ArrayList<>();
        for(int k = 0; k < DEFAULT_GENERATION_SIZE; k++) {
            CarDefinition car_Definition_def = CarDefinition.createMyBestCar();
            carDefintions.add(car_Definition_def);
        }
        return carDefintions;
    }

    private List<Car> materializeGeneration(List<CarDefinition> carDefinitions) {
        return carDefinitions.stream()
                .map(carDefinition -> new Car(carDefinition, world))
                .collect(Collectors.toList());
    }

    private void simulationStep(List<Car> cars) {
        LOGGER.debug("run simulation step");
        world.step(TIME_STEP, 20, 20);

        cars.stream()
                .filter(car -> car.alive)
                .forEach(car -> {
                    car.frames++;
                    if (car.checkDeath()) {
                        car.kill();
                        deadCars++;
                    }

                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("car position {} with score {}", car.getPosition(), car.getScore());
                    }
                });

    }

}


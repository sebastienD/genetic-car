package fr.seb.games.geneticcar.simulation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Position;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import java.util.ArrayList;
import java.util.List;

import static fr.seb.games.geneticcar.simulation.Simulation.BOX2D_FPS;

/**
 * Created by sebastien on 17/01/2015.
 */
public class Car {

    private static final int MAX_CAR_HEALTH = BOX2D_FPS * 10;

    private World world;

    private Body chassis;

    private Body wheel1;
    private Body wheel2;

    private int velocityIndex = 0;
    private int health = MAX_CAR_HEALTH;
    private float maxPosition = 0F;
    private float maxPositiony = 0F;
    private float minPositiony = 0F;

    @JsonIgnore
    public int frames = 0;
    @JsonIgnore
    public boolean alive = true;

    public CarDefinition carDefinition;

    public Car(CarDefinition carDefinition, World world) {
        this.world = world;
        this.carDefinition = carDefinition;

        this.chassis = createChassis(carDefinition.vertexList, carDefinition.chassisDensity);
        this.wheel1 = createWheel(carDefinition.wheel1());
        this.wheel2 = createWheel(carDefinition.wheel2());


        float carMass = this.chassis.getMass();
        carMass += wheel1.getMass();
        carMass += wheel2.getMass();

        float torqueWheel1 = carMass * (-Simulation.GRAVITY.y / carDefinition.wheel1().radius);
        float torqueWheel2 = carMass * (-Simulation.GRAVITY.y / carDefinition.wheel2().radius);

        RevoluteJointDef jointDefinition = new RevoluteJointDef();

        createJointForWheel(jointDefinition, wheel1, carDefinition.wheel1(), torqueWheel1);
        createJointForWheel(jointDefinition, wheel2, carDefinition.wheel2(), torqueWheel2);
    }

    private void createJointForWheel(RevoluteJointDef jointDefinition, Body wheel, CarDefinition.WheelDefinition wheelDefinition, float torqueWheel) {
        Vec2 randVec2 = this.carDefinition.vertexList.get(wheelDefinition.vertex);

        jointDefinition.localAnchorA = new Vec2(randVec2);
        jointDefinition.localAnchorB = new Vec2(0F, 0F);
        jointDefinition.maxMotorTorque = torqueWheel;
        jointDefinition.motorSpeed = -CarDefinition.MOTOR_SPEED;
        jointDefinition.enableMotor = true;
        jointDefinition.bodyA = this.chassis;
        jointDefinition.bodyB = wheel;

        world.createJoint(jointDefinition);
    }

    private Body createWheel(CarDefinition.WheelDefinition wheelDefinition) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0F, 0F);

        Body body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = new CircleShape();
        fixtureDef.density = wheelDefinition.density;
        fixtureDef.friction = 1;
        fixtureDef.restitution = 0.2F;
        fixtureDef.filter.groupIndex = -1;

        body.createFixture(fixtureDef);

        return body;
    }

    private Body createChassis(List<Vec2> vertexList, float density) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0.0F, 4.0F);

        Body body = world.createBody(bodyDef);

        createChassisPart(body, vertexList.get(0), vertexList.get(1), density);
        createChassisPart(body, vertexList.get(1), vertexList.get(2), density);
        createChassisPart(body, vertexList.get(2), vertexList.get(3), density);
        createChassisPart(body, vertexList.get(3), vertexList.get(4), density);
        createChassisPart(body, vertexList.get(4), vertexList.get(5), density);
        createChassisPart(body, vertexList.get(5), vertexList.get(6), density);
        createChassisPart(body, vertexList.get(6), vertexList.get(7), density);
        createChassisPart(body, vertexList.get(7), vertexList.get(0), density);

        return body;
    }

    private void createChassisPart(Body body, Vec2 vecUn, Vec2 vecDeux, float density) {
        List<Vec2> listOfVertex = new ArrayList<>(3);

        listOfVertex.add(vecUn);
        listOfVertex.add(vecDeux);
        listOfVertex.add(new Vec2(0F, 0F));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = new PolygonShape();
        fixtureDef.density = density;
        fixtureDef.friction = 10;
        fixtureDef.restitution = 0.2F;
        fixtureDef.filter.groupIndex = -1;
        ((PolygonShape)fixtureDef.shape).set(listOfVertex.toArray(new Vec2[0]), 3);

        body.createFixture(fixtureDef);
    }

    public Vec2 getPosition() {
        return chassis.getPosition();
    }

    public boolean checkDeath() {
        Vec2 position = getPosition();

        if (position.y > maxPositiony) {
            this.maxPositiony = position.y;
        }

        if (position.y < minPositiony) {
            this.minPositiony = position.y;
        }

        if (position.x > maxPosition + 0.02F) {
            this.health = MAX_CAR_HEALTH;
            this.maxPosition = position.x;
        } else {
            if (position.x > this.maxPosition) {
                this.maxPosition = position.x;
            }
            if (Math.abs(this.chassis.getLinearVelocity().x) < 0.001) {
                this.health -= 5;
            }
            this.health --;
            if (this.health <= 0) {
                return true;
            }
        }
        return false;
    }

    public void kill() {
        world.destroyBody(this.chassis);
        world.destroyBody(this.wheel1);
        world.destroyBody(this.wheel2);

        alive = false;
    }

    public float getScore() {
        float avgSpead = (this.maxPosition / this.frames) * BOX2D_FPS;
        float position = maxPosition;

        return (Math.round(position * 100)/100) + (Math.round(avgSpead * 100)/100);
    }

    @Override
    public String toString() {
        return "Car{" +
                "health=" + health +
                ", maxPosition=" + maxPosition +
                ", maxPositiony=" + maxPositiony +
                ", minPositiony=" + minPositiony +
                ", frames=" + frames +
                ", alive=" + alive +
                ", carDefinition=" + carDefinition +
                '}';
    }
}

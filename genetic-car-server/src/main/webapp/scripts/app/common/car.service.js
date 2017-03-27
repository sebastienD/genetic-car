angular.module("gen.car.service", [])
    .service("CarService", [ '$q', function($q) {

        var box2dfps = 60;
        var max_car_health = box2dfps * 10;

        var service = {};

        service.createCarDef = function (car, team) {
            var car_def = {};

            car_def.team = team;
            car_def.wheelCount = 2;

            car_def.wheel_radius = [];
            car_def.wheel_density = [];
            car_def.wheel_vertex = [];

            car_def.wheel_radius[0] = car.wheel1.radius;
            car_def.wheel_density[0] = car.wheel1.density;
            car_def.wheel_vertex[0] = car.wheel1.vertex;
            car_def.wheel_radius[1] = car.wheel2.radius;
            car_def.wheel_density[1] = car.wheel2.density;
            car_def.wheel_vertex[1] = car.wheel2.vertex;

            car_def.chassis_density = car.chassis.densite;

            car_def.vertex_list = [];
            car_def.vertex_list.push(new b2Vec2(car.chassis.vecteurs[0] ,0));
            car_def.vertex_list.push(new b2Vec2(car.chassis.vecteurs[2], car.chassis.vecteurs[3]));
            car_def.vertex_list.push(new b2Vec2(0, car.chassis.vecteurs[5]));
            car_def.vertex_list.push(new b2Vec2(car.chassis.vecteurs[6], car.chassis.vecteurs[7]));
            car_def.vertex_list.push(new b2Vec2(car.chassis.vecteurs[8],0));
            car_def.vertex_list.push(new b2Vec2(car.chassis.vecteurs[10],car.chassis.vecteurs[11]));
            car_def.vertex_list.push(new b2Vec2(0,car.chassis.vecteurs[13]));
            car_def.vertex_list.push(new b2Vec2(car.chassis.vecteurs[14],car.chassis.vecteurs[15]));

            return car_def;
        }

        service.resetCar = function(car) {
            car.health = max_car_health;
            car.maxPosition = 0;
            car.maxPositiony = 0;
            car.minPositiony = 0;
            car.frames = 0;
            car.alive = true;
        }

        service.createCar = function(carDef, gravity, world, motorSpeed) {
            var car = {};
            car.health = max_car_health;
            car.maxPosition = 0;
            car.maxPositiony = 0;
            car.minPositiony = 0;
            car.frames = 0;
            car.car_def = carDef;
            car.alive = true;
            car.chassis = cw_createChassis(carDef.vertex_list, carDef.chassis_density, world);

            car.wheels = [];
            for (var i = 0; i < carDef.wheelCount; i++){
                car.wheels[i] = cw_createWheel(carDef.wheel_radius[i], carDef.wheel_density[i], world);
            }

            var carmass = car.chassis.GetMass();
            for (var i = 0; i < carDef.wheelCount; i++){
                carmass += car.wheels[i].GetMass();
            }
            var torque = [];
            for (var i = 0; i < carDef.wheelCount; i++){
                torque[i] = carmass * -gravity.y / carDef.wheel_radius[i];
            }

            var joint_def = new b2RevoluteJointDef();

            for (var i = 0; i < carDef.wheelCount; i++){
                var randvertex = car.chassis.vertex_list[carDef.wheel_vertex[i]];
                joint_def.localAnchorA.Set(randvertex.x, randvertex.y);
                joint_def.localAnchorB.Set(0, 0);
                joint_def.maxMotorTorque = torque[i];
                joint_def.motorSpeed = -motorSpeed;
                joint_def.enableMotor = true;
                joint_def.bodyA = car.chassis;
                joint_def.bodyB = car.wheels[i];

                world.CreateJoint(joint_def);
            }

            car.getPosition = function() {
                return car.chassis.GetPosition();
            }

            car.kill = function() {
                world.DestroyBody(car.chassis);

                for (var i = 0; i < car.wheels.length; i++){
                    world.DestroyBody(car.wheels[i]);
                }
                car.alive = false;
            }

            car.checkDeath = function() {
                // check health
                var position = car.getPosition();
                if(position.y > car.maxPositiony) {
                    car.maxPositiony = position.y;
                }
                if(position .y < car.minPositiony) {
                    car.minPositiony = position.y;
                }
                if(position.x > car.maxPosition + 0.02) {
                    car.health = max_car_health;
                    car.maxPosition = position.x;
                } else {
                    if(position.x > car.maxPosition) {
                        car.maxPosition = position.x;
                    }
                    if(Math.abs(car.chassis.GetLinearVelocity().x) < 0.001) {
                        car.health -= 5;
                    }
                    car.health--;
                    if(car.health <= 0) {
                        return true;
                    }
                }
            }

            return car;
        }


        function cw_createChassis(vertex_list, density, world) {
            var body_def = new b2BodyDef();
            body_def.type = b2Body.b2_dynamicBody;
            body_def.position.Set(0.0, 4.0);

            var body = world.CreateBody(body_def);

            cw_createChassisPart(body, vertex_list[0],vertex_list[1], density);
            cw_createChassisPart(body, vertex_list[1],vertex_list[2], density);
            cw_createChassisPart(body, vertex_list[2],vertex_list[3], density);
            cw_createChassisPart(body, vertex_list[3],vertex_list[4], density);
            cw_createChassisPart(body, vertex_list[4],vertex_list[5], density);
            cw_createChassisPart(body, vertex_list[5],vertex_list[6], density);
            cw_createChassisPart(body, vertex_list[6],vertex_list[7], density);
            cw_createChassisPart(body, vertex_list[7],vertex_list[0], density);

            body.vertex_list = vertex_list;

            return body;
        }

        function cw_createChassisPart(body, vertex1, vertex2, density) {
            var vertex_list = [];
            vertex_list.push(vertex1);
            vertex_list.push(vertex2);
            vertex_list.push(b2Vec2.Make(0,0));
            var fix_def = new b2FixtureDef();
            fix_def.shape = new b2PolygonShape();
            fix_def.density = density;
            fix_def.friction = 10;
            fix_def.restitution = 0.2;
            fix_def.filter.groupIndex = -1;
            fix_def.shape.SetAsArray(vertex_list,3);

            body.CreateFixture(fix_def);
        }

        function cw_createWheel(radius, density, world) {
            var body_def = new b2BodyDef();
            body_def.type = b2Body.b2_dynamicBody;
            body_def.position.Set(0, 0);

            var body = world.CreateBody(body_def);

            var fix_def = new b2FixtureDef();
            fix_def.shape = new b2CircleShape(radius);
            fix_def.density = density;
            fix_def.friction = 1;
            fix_def.restitution = 0.2;
            fix_def.filter.groupIndex = -1;

            body.CreateFixture(fix_def);
            return body;
        }

        return service;
    }]);
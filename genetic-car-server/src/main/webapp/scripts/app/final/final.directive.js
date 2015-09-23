'use strict';
angular.module('gen.final.directives', ['gen.car.service', 'gen.floor.service'])
.directive('carTournament', [ 'CarService', 'FloorService', '$interval', '$log', function (CarService, FloorService, $interval, $log) {

    var box2dfps = 60;
    var screenfps = 60;

    var gravity = new b2Vec2(0.0, -9.81);
    var motorSpeed = 20;
    var doSleep = true;

    var floorseed = Math.seedrandom();

    var cameraspeed = 0.05;
    var zoom = 70;

    var wheelMaxDensity = 100;
    var wheelMinDensity = 40;

    var chassisMaxDensity = 300;
    var chassisMinDensity = 30;

    function createSimulation(carDefList) {
        var world = new b2World(gravity, doSleep);
        var floorTiles = FloorService.createFloor(floorseed, world);
        var carFilledList = [];
        carDefList.forEach(function(carDef) {
            carFilledList.push(CarService.createCar(carDef, gravity, world, motorSpeed));
        });

        return {
             world: world,
             carFilledList: carFilledList,
             camera_y: 0,
             camera_x: 0,
             cameraTarget_indexCar: -1,
             last_drawn_tile: 0,
             nbDeadCars: 0,
             leader: {position:{}, index: null},
             floorTiles: floorTiles
        }
    }

    function reset(simuCtx) {
        simuCtx.camera_x = 0;
        simuCtx.camera_y = 0;
        simuCtx.carFilled = CarService.createCar(simuCtx.carFilled.car_def, gravity, simuCtx.world);
    }

    function simulationStep(simuCtx) {
        simuCtx.world.Step(1/box2dfps, 20, 20);

        simuCtx.carFilledList.filter(function(carFilled) {
            return carFilled.alive;
        }).forEach(function(carFilled, index) {
            carFilled.frames++;
            if(carFilled.checkDeath()) {
                carFilled.kill();
                simuCtx.nbDeadCars++;
                if (simuCtx.cameraTarget_indexCar == index) {
                    simuCtx.cameraTarget_indexCar = -1;
                }
            }
        });

        console.log(simuCtx.nbDeadCars, simuCtx.carFilledList.length);
        if (simuCtx.nbDeadCars == simuCtx.carFilledList.length) {
            //reset(simuCtx);
            return 'stop';
        }

        updateLeader(simuCtx);
    }

    function updateLeader(simuCtx) {
        var leadDistance = 0;

        simuCtx.carFilledList.filter(function(carFilled) {
            return carFilled.alive;
        }).forEach(function(carFilled, index) {
            var position = carFilled.getPosition();
            if(position.x > leadDistance) {
                leadDistance = position.x;
                simuCtx.leader = {
                    position: position,
                    index: index
                };
            }
        });
    }

    function cw_drawScreen(simuCtx, canvas) {
        var ctx = canvas.getContext('2d');
        ctx.clearRect(0,0,canvas.width,canvas.height);
        ctx.save();
        cw_setCameraPosition(simuCtx);
        // avant 200 et 200
        ctx.translate(150-(simuCtx.camera_x*zoom), 120+(simuCtx.camera_y*zoom));
        ctx.scale(zoom, -zoom);
        cw_drawFloor(simuCtx, ctx);
        cw_drawCars(simuCtx, ctx);
        ctx.restore();
    }

    function cw_setCameraPosition(simuCtx) {
        var cameraTargetPosition = simuCtx.cameraTarget_indexCar > -1 ?
            simuCtx.carFilledList[simuCtx.cameraTarget_indexCar].getPosition() :
            simuCtx.leader.position;
        var diff_y = simuCtx.camera_y - cameraTargetPosition.y;
        var diff_x = simuCtx.camera_x - cameraTargetPosition.x;
        simuCtx.camera_y -= cameraspeed * diff_y;
        simuCtx.camera_x -= cameraspeed * diff_x;
    }

    function cw_drawFloor(simuCtx, ctx) {
        ctx.strokeStyle = "#000";
        ctx.fillStyle = "#666";
        ctx.lineWidth = 1/zoom;
        ctx.beginPath();

        outer_loop:
            for(var k = Math.max(0,simuCtx.last_drawn_tile-20); k < simuCtx.floorTiles.length; k++) {
                var b = simuCtx.floorTiles[k];
                for (var f = b.GetFixtureList(); f; f = f.m_next) {
                    var s = f.GetShape();
                    var shapePosition = b.GetWorldPoint(s.m_vertices[0]).x;
                    if((shapePosition > (simuCtx.camera_x - 5)) && (shapePosition < (simuCtx.camera_x + 10))) {
                        cw_drawVirtualPoly(b, s.m_vertices, s.m_vertexCount, ctx);
                    }
                    if(shapePosition > simuCtx.camera_x + 10) {
                        simuCtx.last_drawn_tile = k;
                        break outer_loop;
                    }
                }
            }
        ctx.fill();
        ctx.stroke();
    }

    function cw_drawVirtualPoly(body, vtx, n_vtx, ctx) {
        // set strokestyle and fillstyle before call
        // call beginPath before call

        var p0 = body.GetWorldPoint(vtx[0]);
        ctx.moveTo(p0.x, p0.y);
        for (var i = 1; i < n_vtx; i++) {
            var p = body.GetWorldPoint(vtx[i]);
            ctx.lineTo(p.x, p.y);
        }
        ctx.lineTo(p0.x, p0.y);
    }

    function cw_drawCars(simuCtx, ctx) {

        simuCtx.carFilledList.filter(function(carFilled) {
            return carFilled.alive;
        }).forEach(function(carFilled, index) {

            var myCarPos = carFilled.getPosition();

            if (myCarPos.x < (simuCtx.camera_x - 5)) {
                // too far behind, don't draw
                return;
            }

            ctx.strokeStyle = "#444";
            ctx.lineWidth = 1 / zoom;

            for (var i = 0; i < carFilled.wheels.length; i++) {
                var b = carFilled.wheels[i];
                for (var f = b.GetFixtureList(); f; f = f.m_next) {
                    var s = f.GetShape();
                    var color = Math.round(255 - (255 * (f.m_density - wheelMinDensity)) / wheelMaxDensity).toString();
                    var rgbcolor = "rgb(" + color + "," + color + "," + color + ")";
                    cw_drawCircle(b, s.m_p, s.m_radius, b.m_sweep.a, rgbcolor, ctx);
                }
            }

            var densitycolor = Math.round(100 - (70 * ((carFilled.car_def.chassis_density - chassisMinDensity) / chassisMaxDensity))).toString() + "%";

            ctx.strokeStyle = "#c44";
            //ctx.fillStyle = "#fdd";
            //ctx.fillStyle = "hsl(0,50%,"+densitycolor+")";

            //override fillStyle
            var team = carFilled.car_def.team;
            ctx.fillStyle = chooseColorCar(team, densitycolor);

            ctx.beginPath();
            var b = carFilled.chassis;
            for (var f = b.GetFixtureList(); f; f = f.m_next) {
                var s = f.GetShape();
                cw_drawVirtualPoly(b, s.m_vertices, s.m_vertexCount, ctx);
            }
            ctx.fill();
            ctx.stroke();
        });
    };

    // http://hslpicker.com/#ffec3d
    function chooseColorCar(team, densitycolor) {
        var hslColor;
        if (team == "BLUE") {
            hslColor = "hsl(207,90%,"+densitycolor+")";
        } else if (team == "RED") {
            hslColor = "hsl(4,90%,"+densitycolor+")";
        } else if (team == "GREEN") {
            hslColor = "hsl(122,39%,"+densitycolor+")";
        } else if (team == "ORANGE") {
            hslColor = "hsl(36,100%,"+densitycolor+")";
        } else if (team == "YELLOW") {
            hslColor = "hsl(54,100%,"+densitycolor+")";
        } else if (team == "PURPLE") {
            hslColor = "hsl(291,64%,"+densitycolor+")";
        } else {
            hslColor = "hsl(0,50%,"+densitycolor+")";
        }
        return hslColor;
    };

    function cw_drawCircle(body, center, radius, angle, color, ctx) {
        var p = body.GetWorldPoint(center);
        ctx.fillStyle = color;

        ctx.beginPath();
        ctx.arc(p.x, p.y, radius, 0, 2*Math.PI, true);

        ctx.moveTo(p.x, p.y);
        ctx.lineTo(p.x + radius*Math.cos(angle), p.y + radius*Math.sin(angle));

        ctx.fill();
        ctx.stroke();
    }

    return {
        restrict: 'A',
        scope: {
            'champions': '=champions'
        },
        link: function (scope, element) {
            var carDefList = [];
            scope.champions.forEach(function(champion) {
                carDefList.push(CarService.createCarDef(champion.carScore.car, champion.statistic.team));
            });

            /*
             {"team":"YELLOW","wheelCount":2,"wheel_radius":[0.3132888,0.28024596],"wheel_density":[46.594337,86.05009],"wheel_vertex":[1,6],"chassis_density":227.20547,"vertex_list":[{"x":0.45587727,"y":0},{"x":0.20841739,"y":0.41840616},{"x":0,"y":0.9718805},{"x":-0.6338814,"y":0.90924895},{"x":-0.14117411,"y":0},{"x":-1.0216295,"y":-1.0968173},{"x":0,"y":-0.7995364},{"x":0.95801544,"y":-0.83756405}]}
             {"team":"GREEN","wheelCount":2,"wheel_radius":[0.38508964,0.2602971],"wheel_density":[53.847446,88.03694],"wheel_vertex":[7,1],"chassis_density":59.570404,"vertex_list":[{"x":0.21390203,"y":0},{"x":0.91141015,"y":0.19213328},{"x":0,"y":0.5951359},{"x":-0.329379,"y":0.5491548},{"x":-0.26029918,"y":0},{"x":-0.36057684,"y":-0.5953901},{"x":0,"y":-0.24638876},{"x":0.43393752,"y":-1.0529757}]}
             */

            var simuCtx = createSimulation(carDefList);

            var runningInterval = $interval(
                function() {
                    var ret = simulationStep(simuCtx);
                    if (ret === 'stop') {
                        scope.stop = true;
                    }
                },
                Math.round(1000/box2dfps)
            );

            var drawInterval = $interval(
                function() {
                    cw_drawScreen(simuCtx, element[0]);
                },
                Math.round(1000/screenfps)
            );

            scope.$watch('stop', function(newValue, oldValue) {
                if (newValue) {
                    $log.info('stop received');
                    $interval.cancel(runningInterval);
                    $interval.cancel(drawInterval);
                }
            });

            element.on('$destroy', function() {
                $interval.cancel(runningInterval);
                $interval.cancel(drawInterval);
                simuCtx.carFilledList.forEach(function(carFilled) {
                    carFilled.kill();
                });
            });
        }
    };

}]);



angular.module("gen.floor.service", [])
    .service("FloorService", [ '$q', function($q) {

        var maxFloorTiles = 200;
        var groundPieceWidth = 1.5;
        var groundPieceHeight = 0.15;

        var service = {};

        function cw_createFloorTile(position, angle, world) {
            var body_def = new b2BodyDef();

            body_def.position.Set(position.x, position.y);
            var body = world.CreateBody(body_def);
            var fix_def = new b2FixtureDef();
            fix_def.shape = new b2PolygonShape();
            fix_def.friction = 0.5;

            var coords = [];
            coords.push(new b2Vec2(0,0));
            coords.push(new b2Vec2(0,-groundPieceHeight));
            coords.push(new b2Vec2(groundPieceWidth,-groundPieceHeight));
            coords.push(new b2Vec2(groundPieceWidth,0));

            var center = new b2Vec2(0,0);

            var newcoords = cw_rotateFloorTile(coords, center, angle);
            fix_def.shape.SetAsArray(newcoords);

            body.CreateFixture(fix_def);
            return body;
        }

        function cw_rotateFloorTile(coords, center, angle) {
            var newcoords = [];
            for(var k = 0; k < coords.length; k++) {
                var nc = {};
                nc.x = Math.cos(angle)*(coords[k].x - center.x) - Math.sin(angle)*(coords[k].y - center.y) + center.x;
                nc.y = Math.sin(angle)*(coords[k].x - center.x) + Math.cos(angle)*(coords[k].y - center.y) + center.y;
                newcoords.push(nc);
            }
            return newcoords;
        }

        service.createFloor = function (floorseed, world) {
            var last_tile = null;
            var tile_position = new b2Vec2(-5,0);
            var cw_floorTiles = [];
            Math.seedrandom(floorseed);
            for(var k = 0; k < maxFloorTiles; k++) {
                // keep old impossible tracks if not using mutable floors
                last_tile = cw_createFloorTile(tile_position, (Math.random()*3 - 1.5) * 1.5*k/maxFloorTiles, world);
                cw_floorTiles.push(last_tile);
                var last_fixture = last_tile.GetFixtureList();
                var last_world_coords = last_tile.GetWorldPoint(last_fixture.GetShape().m_vertices[3]);
                tile_position = last_world_coords;
            }
            return cw_floorTiles;
        };

        return service;

    }]);
package fr.genetic.server.simulation;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ground {

    private float groundPieceWidth = 1.5F;
    private float groundPieceHeight = 0.15F;

    private float maxFloorTiles = 200F;

    private World world;

    private List<Vec2> newcoords;

    public Ground(World world) {
        this.world = world;
    }

    public  void createFloor() {
        Body lastTile;
        Vec2 tilePosition = new Vec2(-5F,0F);
        for(int k = 0; k < maxFloorTiles; k++) {
            lastTile = createFloorTile(tilePosition, ((Random.next()*3F-1.5F) * 1.8F * (float)k / maxFloorTiles));
            tilePosition = lastTile.getWorldPoint(newcoords.get(3));
        }
    }

    private Body createFloorTile(Vec2 position, float angle) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position = new Vec2(position.x, position.y);

        Body body = world.createBody(bodyDef);

        Vec2[] coords = {
                new Vec2(0,0),
                new Vec2(0,-groundPieceHeight),
                new Vec2(groundPieceWidth,-groundPieceHeight),
                new Vec2(groundPieceWidth,0)};

        Vec2 center = new Vec2(0,0);

        newcoords = cw_rotateFloorTile(coords, center, angle);

        PolygonShape shape = new PolygonShape();
        shape.set(newcoords.toArray(new Vec2[0]), newcoords.size());

        FixtureDef fix_def = new FixtureDef();
        fix_def.friction = 0.5F;
        fix_def.shape = shape;

        body.createFixture(fix_def);

        return body;
    }

    private List<Vec2> cw_rotateFloorTile(Vec2[] coords, Vec2 center, float angle) {
        return Arrays.stream(coords)
                .map(coord -> {
                    Vec2 nc = new Vec2();
                    nc.x = new BigDecimal(Math.cos(angle)*(coord.x - center.x) - Math.sin(angle)*(coord.y - center.y) + center.x).floatValue();
                    nc.y = new BigDecimal(Math.sin(angle)*(coord.x - center.x) + Math.cos(angle)*(coord.y - center.y) + center.y).floatValue();
                    return nc;
                })
                .collect(Collectors.toList());
    }

}

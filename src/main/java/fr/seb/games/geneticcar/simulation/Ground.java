package fr.seb.games.geneticcar.simulation;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebastien on 12/01/2015.
 */
public class Ground {

    private float groundPieceWidth = 1.5F;
    private float groundPieceHeight = 0.15F;

    private int maxFloorTiles = 200;

    private World world;

    public Ground(World world) {
        this.world = world;
    }

    public  void createFloor() {
        Body lastTile;
        Vec2 tilePosition = new Vec2(-5,0);
        List<Body> floorTiles = new ArrayList<>();

//        Math.seedrandom(floorseed);
        for(int k = 0; k < maxFloorTiles; k++) {


            lastTile = createFloorTile(tilePosition, (Random.next(-1.5F, 3F) * 1.5F * k / maxFloorTiles));

            floorTiles.add(lastTile);
            //Fixture last_fixture = lastTile.getFixtureList();
            //tilePosition = lastTile.getWorldPoint(last_fixture.getShape().m_vertices[3]);
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

        List<Vec2> newcoords = cw_rotateFloorTile(coords, center, angle);

        PolygonShape shape = new PolygonShape();
        shape.set(newcoords.toArray(new Vec2[0]), coords.length);

        FixtureDef fix_def = new FixtureDef();
        fix_def.friction = 0.5F;
        fix_def.shape = shape;

        body.createFixture(fix_def);

        return body;
    }




    private List<Vec2> cw_rotateFloorTile(Vec2[] coords, Vec2 center, float angle) {
        List<Vec2> newcoords = new ArrayList<>();
        for(int k = 0; k < coords.length; k++) {
            Vec2 nc = new Vec2();
            nc.x = new Float(Math.cos(angle)*(coords[k].x - center.x) - Math.sin(angle)*(coords[k].y - center.y) + center.x);
            nc.y = new Float(Math.sin(angle)*(coords[k].x - center.x) + Math.cos(angle)*(coords[k].y - center.y) + center.y);
            newcoords.add(nc);
        }
        return newcoords;
    }



}

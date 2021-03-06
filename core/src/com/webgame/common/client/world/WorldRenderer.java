package com.webgame.common.client.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.webgame.common.client.Configs;

import static com.webgame.common.client.Configs.PPM;

public class WorldRenderer {

	public World world;
	public Box2DDebugRenderer worldRenderer;

	private TmxMapLoader loader;
	private OrthogonalTiledMapRenderer mapRenderer;
	private TiledMap map;
	
	private OrthographicCamera cam;

	public WorldRenderer(World world, OrthographicCamera cam) {
		this.cam = cam;
		this.world = world;

		loader = new TmxMapLoader();
		map = loader.load(Configs.MAPS_FOLDER + "/map.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1/PPM);
		worldRenderer = new Box2DDebugRenderer();

		BodyDef bdef = new BodyDef();
		PolygonShape ps = new PolygonShape();
		FixtureDef fdef = new FixtureDef();

		Body body;
		

		for (MapObject mObject : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) mObject).getRectangle();

			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2)/PPM, (rect.getY() + rect.getHeight() / 2)/PPM);
			
			body = world.createBody(bdef);

			ps.setAsBox((rect.getWidth() / 2)/PPM, (rect.getHeight() / 2)/PPM);
			
			fdef.shape = ps;
			

			body.createFixture(fdef);
		}
		
		TiledMapTileLayer layer=(TiledMapTileLayer) map.getLayers().get(0);

		//so if we need to remove the tile in layer at position 0,1

		layer.getCell(0, 1).setTile(null);
	}

	public void render() {
		mapRenderer.setView(cam);
		mapRenderer.render();
		worldRenderer.render(world, cam.combined);
	}
}

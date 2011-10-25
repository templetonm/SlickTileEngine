import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class MapRenderComponent extends RenderComponent {
	private static final int GROUND_LAYER = 0;
	private static final int BG_LAYER = 1;
	private static final int FG_LAYER = 2;
	private static final int ATTRIBUTE_LAYER = 3;
	private final int scrollSpeed;
	private final TiledMap tiledMap;
	private SlidingComponent slidingComp;
	private Vector2f offset;
	
	// TODO: add scrollingComponent
	public MapRenderComponent(String id, TiledMap tiledMap, int scrollSpeed, SlidingComponent slidingComp) throws SlickException {
		super(id);
		//map = new TiledMap("resources/demo_map.tmx");
		this.tiledMap = tiledMap;
		this.scrollSpeed = scrollSpeed;
		this.slidingComp = slidingComp;
		offset = new Vector2f(0,0);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		Input in = gc.getInput();
		if (in.isKeyDown(Input.KEY_UP) && !slidingComp.slidingUp()) {
			offset.y += scrollSpeed;
		} else if (in.isKeyDown(Input.KEY_DOWN) && !slidingComp.slidingDown()) {
			offset.y -= scrollSpeed;
		}
		
		if (in.isKeyDown(Input.KEY_RIGHT) && !slidingComp.slidingRight()) {
			offset.x -= scrollSpeed;
		} else if (in.isKeyDown(Input.KEY_LEFT) && !slidingComp.slidingLeft()) {
			offset.x += scrollSpeed;
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) {
		
	}
	
	// TODO it would be nice if we could combine BG and GROUND
	public void renderBackground(GameContainer gc, StateBasedGame sb, Graphics g) {
		tiledMap.render((int)offset.x, (int)offset.y, GROUND_LAYER);
		tiledMap.render((int)offset.x, (int)offset.y, BG_LAYER);
	}
	
	public void renderForeground(GameContainer gc, StateBasedGame sb, Graphics g) {
		tiledMap.render((int)offset.x, (int)offset.y, FG_LAYER);
	}

}

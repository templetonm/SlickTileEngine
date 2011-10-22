import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/*
 * 
 */

public class MapRenderComponent extends RenderComponent {
	private static final int GROUND_LAYER = 0;
	private static final int BG_LAYER = 1;
	private static final int FG_LAYER = 2;
	private static final int ATTRIBUTE_LAYER = 3;
	private final TiledMap map;
	private final PlayerRenderComponent playerRender;
	private Vector2f offset;
	
	// TODO: Split out player rendering from player sliding
	public MapRenderComponent(String id, PlayerRenderComponent playerRender) throws SlickException {
		super(id);
		map = new TiledMap("resources/demo_map.tmx");
		offset = new Vector2f(0,0);
		this.playerRender = playerRender;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		Input in = gc.getInput();
		if (in.isKeyDown(Input.KEY_UP) && !playerRender.slidingUp()) {
			offset.y += 6;
		} else if (in.isKeyDown(Input.KEY_DOWN) && !playerRender.slidingDown()) {
			offset.y -= 6;
		}
		
		if (in.isKeyDown(Input.KEY_RIGHT) && !playerRender.slidingRight()) {
			offset.x -= 6;
		} else if (in.isKeyDown(Input.KEY_LEFT) && !playerRender.slidingLeft()) {
			offset.x += 6;
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) {
		
	}
	
	// TODO it would be nice if we could combine BG and GROUND
	public void renderBackground(GameContainer gc, StateBasedGame sb, Graphics g) {
		map.render((int)offset.x, (int)offset.y, GROUND_LAYER);
		map.render((int)offset.x, (int)offset.y, BG_LAYER);
	}
	
	public void renderForeground(GameContainer gc, StateBasedGame sb, Graphics g) {
		map.render((int)offset.x, (int)offset.y, FG_LAYER);
	}

}

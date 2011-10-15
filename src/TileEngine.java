
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class TileEngine extends BasicGame {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	// Player speed
	private static final int SPEED = 6;
	private TiledMap map;
	private Image player;
	private int offX = 0;
	private int offY = 0;
	
	public TileEngine() {
		super("TileEngine");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		container.setVSync(true);
		// Hide the mouse
		container.setMouseGrabbed(true);
		map = new TiledMap("resources/demo_map1.tmx");
		// TODO: Use animations and spritesheets
		player = new Image("resources/player.png");
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		if (container.getInput().isKeyDown(Input.KEY_UP)) { offY += SPEED; }
		if (container.getInput().isKeyDown(Input.KEY_DOWN)) { offY -= SPEED; }
		if (container.getInput().isKeyDown(Input.KEY_LEFT)) { offX += SPEED; }
		if (container.getInput().isKeyDown(Input.KEY_RIGHT)) { offX -= SPEED; }
		if (container.getInput().isKeyDown(Input.KEY_ESCAPE)) { container.exit(); }
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		map.render(offX, offY);
		// Draw the player in the center of the screen		
		player.draw(WIDTH/2-player.getWidth()/2,HEIGHT/2-player.getHeight()/2);
	}
	
	public static void main(String[] args) 
			throws SlickException {
		AppGameContainer app = new AppGameContainer(new TileEngine());
	    app.setDisplayMode(WIDTH, HEIGHT, false);
	    app.start();
	}

}

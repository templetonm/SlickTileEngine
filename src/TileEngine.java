import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class TileEngine extends BasicGame {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static final int GROUND_LAYER = 0;
	private static final int BG_LAYER = 1;
	private static final int FG_LAYER = 2;
	private static final int PLAYERPOSITIONOFFYUP = 200;
	private static final int PLAYERPOSITIONOFFYDOWN = 400;
	private static final int PLAYERPOSITIONOFFXLEFT = 200;
	private static final int PLAYERPOSITIONOFFXRIGHT = 600;
	// Player speed
	private static final int SPEED = 6;
	private TiledMap map;
	private Image player;
	private int playerX = 5;
	private int playerY = 5;
	private int offX;
	private int offY;
	private String mapName;
	private int [] playerPosition = null;
	
	public TileEngine() {
		super("TileEngine");
	}

	@Override
	public void init(GameContainer c) throws SlickException {
		c.setVSync(true);
		// Hide the mouse
		//container.setMouseGrabbed(true);
		map = new TiledMap("resources/demo_map.tmx");
		// TODO: Use animations and spritesheets
		player = new Image("resources/player.png");
		// Start the player off at 5,5
		offX = WIDTH/2 - player.getWidth()/2 - playerX * player.getWidth();
		offY = HEIGHT/2 - player.getHeight()/2 - playerY * player.getHeight();
		mapName = map.getMapProperty("Name", "");
		// Initial position is center of screen.
		playerPosition = new int[] {WIDTH/2-player.getWidth()/2, HEIGHT/2-player.getHeight()/2};
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		if (container.getInput().isKeyDown(Input.KEY_UP)) {
			if (playerPosition[1] < PLAYERPOSITIONOFFYUP) {
				offY += SPEED;
			}
			else {
				playerPosition[1] -= SPEED;
			}
		}
		
		if (container.getInput().isKeyDown(Input.KEY_DOWN)) {
			if (playerPosition[1] > PLAYERPOSITIONOFFYDOWN) {
				offY -= SPEED;
			}
			else {
				playerPosition[1] += SPEED;
			}
		}
		
		if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
			if (playerPosition[0] < PLAYERPOSITIONOFFXLEFT) {
				offX += SPEED;
			}
			else {
				playerPosition[0] -= SPEED;
			}
		}
		
		if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
			if (playerPosition[0] > PLAYERPOSITIONOFFXRIGHT) {
				offX -= SPEED;
			}
			else {
				playerPosition[0] += SPEED;
			}
		}
		
		if (container.getInput().isKeyDown(Input.KEY_ESCAPE)) { container.exit(); }
	}

	@Override
	public void render(GameContainer c, Graphics g) throws SlickException {
		// TODO: Only render necessary tiles
		map.render(offX, offY, GROUND_LAYER);
		map.render(offX, offY, BG_LAYER);
		player.draw(playerPosition[0],playerPosition[1]);
		map.render(offX, offY, FG_LAYER);
		
		// Draw map name
		g.setColor(Color.green);
		int lineWidth = c.getDefaultFont().getWidth(mapName);
		g.drawString(mapName, WIDTH/2 - lineWidth/2, 0);
		
		// Draw player coordinates
		g.setColor(Color.yellow);
		String coords = "(" + playerX + "," + playerY + ")";
		lineWidth = c.getDefaultFont().getWidth(coords);
		g.drawString(coords, WIDTH - lineWidth - 5, 0);
		
	}
	
	public static void main(String[] args) 
			throws SlickException {
		AppGameContainer app = new AppGameContainer(new TileEngine());
	    app.setDisplayMode(WIDTH, HEIGHT, false);
	    app.start();
	}

}

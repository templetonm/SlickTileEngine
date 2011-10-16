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
	// Engine constants
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static final boolean FULLSCREEN = false;
	private static final int PLAYER_SPEED = 6;
	// Tiled constants
	private static final int GROUND_LAYER = 0;
	private static final int BG_LAYER = 1;
	private static final int FG_LAYER = 2;
	private static final int ATTRIBUTE_LAYER = 3;
	// Sliding constants
	private static final int SLIDINGPOSITIONOFFYUP = 200;
	private static final int SLIDINGPOSITIONOFFYDOWN = 400;
	private static final int SLIDINGPOSITIONOFFXLEFT = 200;
	private static final int SLIDINGPOSITIONOFFXRIGHT = 600;
	// Member variables
	private int slidingPositionX;
	private int slidingPositionY;
	private TiledMap map;
	private Image player;
	private int playerX;
	private int playerY;
	private int offX;
	private int offY;
	private String mapName;
	
	public TileEngine() {
		super("TileEngine");
	}

	@Override
	public void init(GameContainer c) throws SlickException {
		c.setVSync(true);
		// Hide the mouse
		if (FULLSCREEN) c.setMouseGrabbed(true);
		map = new TiledMap("resources/demo_map.tmx");
		// TODO: Use animations and spritesheets
		player = new Image("resources/player.png");
		// Start the player off at 5, 5
		playerX = 5 * player.getWidth();
		playerY = 5 * player.getHeight();
		offX = WIDTH/2 - player.getWidth()/2 - playerX;
		offY = HEIGHT/2 - player.getHeight()/2 - playerY;
		mapName = map.getMapProperty("Name", "");
		// Initial position is center of screen
		slidingPositionX = WIDTH/2-player.getWidth()/2;
		slidingPositionY = HEIGHT/2-player.getHeight()/2;
	}
	
	private int getTileX(int x) {
		return (int)Math.round((float)x / (float)player.getWidth());
	}
	
	private int getTileY(int y) {
		return (int)Math.round((float)y / (float)player.getHeight());
	}
	
	private boolean isBlocked(int tileX, int tileY) {
		boolean blocked = false;
		int tileId = map.getTileId(tileX, tileY, ATTRIBUTE_LAYER);
		if (map.getTileProperty(tileId, "Wall", "false").equals("true")) {
			blocked = true;
		}
		return blocked;
	}
	
	private boolean blocked(int x, int y) {
		boolean blocked = false;
		// Bounds checking
		if (x < 0) return true;
		if (x > map.getWidth() * player.getWidth() - player.getWidth()) return true;
		if (y < 0) return true;
		if (y > map.getHeight() * player.getHeight() - player.getHeight()) return true;
		
		// WALL checking
		// Bottom left
		if (isBlocked(getTileX(x - player.getWidth() / 2),getTileY(y + player.getHeight()/2))) {
			blocked = true;
		}
		// Bottom right
		if (isBlocked(getTileX(x + player.getWidth()/2), getTileY(y + player.getHeight()/2))) {
			blocked = true;
		}
		// Top left
		if (isBlocked(getTileX(x - player.getWidth()/2), getTileY(y - player.getHeight()/2))) {
			blocked = true;
		}
		// Top right
		if (isBlocked(getTileX(x + player.getWidth()/2), getTileY(y - player.getHeight()/2))) {
			blocked = true;
		}
		return blocked;
	}

	@Override
	public void update(GameContainer c, int delta) throws SlickException {
		int tempPlayerX = playerX;
		int tempPlayerY = playerY;
		int tempOffX = offX;
		int tempOffY = offY;
		int tempSlidingPositionX = slidingPositionX;
		int tempSlidingPositionY = slidingPositionY;
		
		// The && in these are bound checking blocks..
		if (c.getInput().isKeyDown(Input.KEY_UP)) {
			if (slidingPositionY < SLIDINGPOSITIONOFFYUP) {
				tempOffY += PLAYER_SPEED;
			} else {
				tempSlidingPositionY -= PLAYER_SPEED;
			}
			tempPlayerY -= PLAYER_SPEED;
		} else if (c.getInput().isKeyDown(Input.KEY_DOWN)) {
			if (slidingPositionY > SLIDINGPOSITIONOFFYDOWN) {
				tempOffY -= PLAYER_SPEED;
			} else {
				tempSlidingPositionY += PLAYER_SPEED;
			}
			tempPlayerY += PLAYER_SPEED;
		}
		
		if (c.getInput().isKeyDown(Input.KEY_LEFT )) {
			if (slidingPositionX < SLIDINGPOSITIONOFFXLEFT) {
				tempOffX += PLAYER_SPEED;
			} else {
				tempSlidingPositionX -= PLAYER_SPEED;
			}
			tempPlayerX -= PLAYER_SPEED;
		} else if (c.getInput().isKeyDown(Input.KEY_RIGHT)) {
			if (slidingPositionX > SLIDINGPOSITIONOFFXRIGHT) {
				tempOffX -= PLAYER_SPEED;
			} else {
				tempSlidingPositionX += PLAYER_SPEED;
			}
			tempPlayerX += PLAYER_SPEED;
		}
		
		// TODO make this function the same and not look like shit
		if (!blocked(tempPlayerX, tempPlayerY)) {
			playerX = tempPlayerX;
			offX = tempOffX;
			slidingPositionX = tempSlidingPositionX;
			playerY = tempPlayerY;
			offY = tempOffY;
			slidingPositionY = tempSlidingPositionY;
		} else if (!blocked(playerX, tempPlayerY)) {
			playerY = tempPlayerY;
			offY = tempOffY;
			slidingPositionY = tempSlidingPositionY;
		} else if (!blocked(tempPlayerX, playerY)) {
			playerX = tempPlayerX;
			offX = tempOffX;
			slidingPositionX = tempSlidingPositionX;
		}
		
		if (c.getInput().isKeyDown(Input.KEY_ESCAPE)) { c.exit(); }
	}

	@Override
	public void render(GameContainer c, Graphics g) throws SlickException {
		// TODO: Only render necessary tiles
		map.render(offX, offY, GROUND_LAYER);
		map.render(offX, offY, BG_LAYER);
		player.draw(slidingPositionX, slidingPositionY);
		map.render(offX, offY, FG_LAYER);
		
		// Draw map name
		g.setColor(Color.green);
		int lineWidth = c.getDefaultFont().getWidth(mapName);
		g.drawString(mapName, WIDTH/2 - lineWidth/2, 0);
		
		// Draw player coordinates
		g.setColor(Color.yellow);
		int playerTileX = getTileX(playerX);
		int playerTileY = getTileY(playerY);
		String coords = "(" + playerTileX + "," + playerTileY + ")";
		lineWidth = c.getDefaultFont().getWidth(coords);
		g.drawString(coords, WIDTH - lineWidth - 5, 0);
	}
	
	public static void main(String[] args) 
			throws SlickException {
		AppGameContainer app = new AppGameContainer(new TileEngine());
	    app.setDisplayMode(WIDTH, HEIGHT, FULLSCREEN);
	    app.start();
	}

}

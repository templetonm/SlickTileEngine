import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
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
	private Animation player;
	private int playerX;
	private int playerY;
	private int offX;
	private int offY;
	private String mapName;
	private SpriteSheet sheet;
	
	public TileEngine() {
		super("TileEngine");
	}

	@Override
	public void init(GameContainer c) throws SlickException {
		c.setVSync(true);
		// Hide the mouse
		if (FULLSCREEN) c.setMouseGrabbed(true);
		map = new TiledMap("resources/demo_map.tmx");
		// Setup the player animation
		sheet = new SpriteSheet(new Image("resources/spritesheet.png"),32, 32);
		player = new Animation(true);
		for (int frame=0;frame<17;frame++) {
			player.addFrame(sheet.getSprite(frame,0), 100);
		}
		player.stop();
		
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
		
		
		if (c.getInput().isKeyDown(Input.KEY_UP)) {
			if (slidingPositionY < SLIDINGPOSITIONOFFYUP) {
				tempOffY += PLAYER_SPEED;
			} else {
				tempSlidingPositionY -= PLAYER_SPEED;
			}
			tempPlayerY -= PLAYER_SPEED;
			if (player.isStopped() || (player.getFrame() != 1 && player.getFrame() != 2)) {
				player.setCurrentFrame(1);
				player.stopAt(2);
				player.start();
			}
			
		} else if (c.getInput().isKeyDown(Input.KEY_DOWN)) {
			if (slidingPositionY > SLIDINGPOSITIONOFFYDOWN) {
				tempOffY -= PLAYER_SPEED;
			} else {
				tempSlidingPositionY += PLAYER_SPEED;
			}
			tempPlayerY += PLAYER_SPEED;
			if (player.isStopped() || (player.getFrame() != 5 && player.getFrame() != 6)) {
				player.setCurrentFrame(5);
				player.stopAt(6);
				player.start();
			}
		}
		
		if (c.getInput().isKeyDown(Input.KEY_LEFT )) {
			if (slidingPositionX < SLIDINGPOSITIONOFFXLEFT) {
				tempOffX += PLAYER_SPEED;
			} else {
				tempSlidingPositionX -= PLAYER_SPEED;
			}
			tempPlayerX -= PLAYER_SPEED;
			if (!c.getInput().isKeyDown(Input.KEY_DOWN) && !c.getInput().isKeyDown(Input.KEY_UP)) {
				if (player.isStopped() || (player.getFrame() != 8 && player.getFrame() != 9)) {
					player.setCurrentFrame(8);
					player.stopAt(9);
					player.start();
				}
			}
			
		} else if (c.getInput().isKeyDown(Input.KEY_RIGHT)) {
			if (slidingPositionX > SLIDINGPOSITIONOFFXRIGHT) {
				tempOffX -= PLAYER_SPEED;
			} else {
				tempSlidingPositionX += PLAYER_SPEED;
			}
			tempPlayerX += PLAYER_SPEED;
			if (!c.getInput().isKeyDown(Input.KEY_DOWN) && !c.getInput().isKeyDown(Input.KEY_UP)) {
				if (player.isStopped() || (player.getFrame() != 12 && player.getFrame() != 13)) {
					player.setCurrentFrame(12);
					player.stopAt(13);
					player.start();
				}
			}
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

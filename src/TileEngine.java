
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
	private static final int PLAYERPOSITIONOFFYUP = 200;
	private static final int PLAYERPOSITIONOFFYDOWN = 400;
	private static final int PLAYERPOSITIONOFFXLEFT = 200;
	private static final int PLAYERPOSITIONOFFXRIGHT = 600;
	// Player speed
	private static final int SPEED = 6;
	private TiledMap map;
	private Image player;
	private int offX = 0;
	private int offY = 0;
	private int [] playerPosition = null;
	
	public TileEngine() {
		super("TileEngine");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		container.setVSync(true);
		// Hide the mouse
		//container.setMouseGrabbed(true);
		map = new TiledMap("resources/demo_map1.tmx");
		// TODO: Use animations and spritesheets
		player = new Image("resources/player.png");

		// Initial position is center of screen.
		playerPosition = new int[] {WIDTH/2-player.getWidth()/2, HEIGHT/2-player.getHeight()/2};
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		if (container.getInput().isKeyDown(Input.KEY_UP))
		{
			if (playerPosition[1] < PLAYERPOSITIONOFFYUP)
			{
				offY += SPEED;
			}
			else
			{
				playerPosition[1] -= SPEED;
			}
		}
		
		if (container.getInput().isKeyDown(Input.KEY_DOWN))
		{
			if (playerPosition[1] > PLAYERPOSITIONOFFYDOWN)
			{
				offY -= SPEED;
			}
			else
			{
				playerPosition[1] += SPEED;
			}
		}
		
		if (container.getInput().isKeyDown(Input.KEY_LEFT))
		{
			if (playerPosition[0] < PLAYERPOSITIONOFFXLEFT)
			{
				offX += SPEED;
			}
			else
			{
				playerPosition[0] -= SPEED;
			}
		}
		
		if (container.getInput().isKeyDown(Input.KEY_RIGHT))
		{
			if (playerPosition[0] > PLAYERPOSITIONOFFXRIGHT)
			{
				offX -= SPEED;
			}
			else
			{
				playerPosition[0] += SPEED;
			}
		}
		
		if (container.getInput().isKeyDown(Input.KEY_ESCAPE)) { container.exit(); }
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		map.render(offX, offY);
		// Draw the player in the center of the screen		
		player.draw(playerPosition[0],playerPosition[1]);
	}
	
	public static void main(String[] args) 
			throws SlickException {
		AppGameContainer app = new AppGameContainer(new TileEngine());
	    app.setDisplayMode(WIDTH, HEIGHT, false);
	    app.start();
	}

}

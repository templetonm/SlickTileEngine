import java.awt.Dimension;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class PlayerRenderComponent extends RenderComponent {
	private final Animation player;
	// The window you can slide in
	private final Rectangle slidingRect;
	// Your position in the sliding window
	private Vector2f slidingPos;
	private boolean slidingUp;
	private boolean slidingDown;
	private boolean slidingLeft;
	private boolean slidingRight;
	
	public PlayerRenderComponent(String id, Animation player, Dimension screen) {
		super(id);
		this.player = player;

		// Center of the screen
		setSliding(true);
		slidingPos = new Vector2f();
	    slidingPos.x = screen.width/2 - player.getWidth()/2;
		slidingPos.y = screen.height/2 - player.getHeight()/2;
		slidingRect = new Rectangle(200, 200, 600, 400);
	}

	public boolean slidingUp() {
		return slidingUp;
	}
	
	public boolean slidingDown() {
		return slidingDown;
	}
	
	public boolean slidingLeft() {
		return slidingLeft;
	}
	
	public boolean slidingRight() {
		return slidingRight;
	}
	
	private void setSliding(boolean sliding) {
		slidingUp = sliding;
		slidingDown = sliding;
		slidingLeft = sliding;
		slidingRight = sliding;
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) {
		player.draw(slidingPos.x, slidingPos.y);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		Input in = gc.getInput();
		
		setSliding(false);
		if (in.isKeyDown(Input.KEY_UP)) {
			if (slidingPos.y > slidingRect.getY()) {
				slidingPos.y -= 6;
				slidingUp = true;
			}
			
		} else if (in.isKeyDown(Input.KEY_DOWN)) {
			if (slidingPos.y < slidingRect.getHeight()) {
				slidingPos.y += 6;
				slidingDown = true;
			}
		}
		
		if (in.isKeyDown(Input.KEY_LEFT)) {
			if (slidingPos.x > slidingRect.getX()) {
				slidingPos.x -= 6;
				slidingLeft = true;
			}
		} else if (in.isKeyDown(Input.KEY_RIGHT)) {
			if (slidingPos.x < slidingRect.getWidth()) {
				slidingPos.x += 6;
				slidingRight = true;
			}
		}
	}
}

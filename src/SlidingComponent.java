import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class SlidingComponent extends Component {
	// The window in which you can slide in
	private final Rectangle slidingRect;
	private final int slidingSpeed;
	// Your position in the sliding window
	private Vector2f slidingPos;
	private boolean slidingUp;
	private boolean slidingDown;
	private boolean slidingLeft;
	private boolean slidingRight;

	public SlidingComponent(String id, Rectangle slidingRect, Vector2f slidingPos, int slidingSpeed) {
		super(id);
		this.slidingRect = slidingRect;
		this.slidingPos = slidingPos;
		this.slidingSpeed = slidingSpeed;
		/*
		slidingPos = new Vector2f();
	    slidingPos.x = screen.width/2 - player.getWidth()/2;
		slidingPos.y = screen.height/2 - player.getHeight()/2;
		slidingRect = new Rectangle(200, 200, 600, 400);
		*/
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		Input in = gc.getInput();
		
		this.setSliding(false);
		if (in.isKeyDown(Input.KEY_UP)) {
			if (slidingPos.y > slidingRect.getY()) {
				slidingPos.y -= slidingSpeed;
				this.slidingUp = true;
			}
			
		} else if (in.isKeyDown(Input.KEY_DOWN)) {
			if (slidingPos.y < slidingRect.getHeight()) {
				slidingPos.y += slidingSpeed;
				this.slidingDown = true;
			}
		}
		
		if (in.isKeyDown(Input.KEY_LEFT)) {
			if (slidingPos.x > slidingRect.getX()) {
				slidingPos.x -= slidingSpeed;
				this.slidingLeft = true;
			}
		} else if (in.isKeyDown(Input.KEY_RIGHT)) {
			if (slidingPos.x < slidingRect.getWidth()) {
				slidingPos.x += slidingSpeed;
				this.slidingRight = true;
			}
		}
	}
	public Vector2f getSlidingPos() {
		return slidingPos;
	}

	public void setSlidingPos(Vector2f slidingPos) {
		this.slidingPos = slidingPos;
	}
	
	private void setSliding(boolean sliding) {
		this.slidingUp = sliding;
		this.slidingDown = sliding;
		this.slidingLeft = sliding;
		this.slidingRight = sliding;
	}

	public Rectangle getSlidingRect() {
		return slidingRect;
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
}

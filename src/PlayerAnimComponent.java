import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

/*
 * We can probably make this class more generic like AnimationComponent
 */
public class PlayerAnimComponent extends Component {
	private final Animation player;

	public PlayerAnimComponent(String id, Animation player) {
		super(id);
		this.player = player;
		player.stop();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		Input in = gc.getInput();
		boolean animate = false;
		int newFrame = player.getFrame();
		
		// TODO fix spritesheet so idle is the first frame
		if (in.isKeyDown(Input.KEY_UP)) {
			newFrame = 0;
			animate = true;
		} else if (in.isKeyDown(Input.KEY_DOWN)) {
			newFrame = 4;
			animate = true;
		}
		
		if (in.isKeyDown(Input.KEY_LEFT)) {
			newFrame = 8;
			animate = true;
		} else if (in.isKeyDown(Input.KEY_RIGHT)) {
			newFrame = 12;
			animate = true;
		}
		
		if (player.getFrame() != newFrame && 
			player.getFrame() != newFrame+1 || 
			player.isStopped()) {
			player.setCurrentFrame(newFrame);
			
			if (animate) {
				player.stopAt(newFrame+1);
				player.start();
				animate = false;
			}
		}
	}

}

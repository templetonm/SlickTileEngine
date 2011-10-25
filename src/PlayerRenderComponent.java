import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class PlayerRenderComponent extends RenderComponent {
	private final Animation player;
	private SlidingComponent slidingComp = null;
	
	public PlayerRenderComponent(String id, Animation player) {
		super(id);
		this.player = player;
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) {
		if (this.slidingComp == null) {
			this.slidingComp = (SlidingComponent)owner.getComponent("PlayerSliding");
		}
		Vector2f slidingPos = this.slidingComp.getSlidingPos();
		player.draw(slidingPos.x, slidingPos.y);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
	}


}

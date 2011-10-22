import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


public class Entity {
	private final String id;
	private final HashMap<String, Component> components;
	private Vector2f position;
	private RenderComponent renderComponent;
	private boolean isRenderable = false;
	
	public Entity(String id) {
		this.id = id;
		components = new HashMap<String,Component>();
		setPosition(new Vector2f(0, 0));
	}
	
	public void addComponent(Component component) {
		if (component instanceof RenderComponent) {
			renderComponent = (RenderComponent)component;
			isRenderable = true;
		}
		component.setOwnerEntity(this);
		components.put(component.getId(), component);
	}
	
	public Component getComponent(String id) {
		return components.get(id);
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getPosition() {
		return position;
	}

	public String getId() {
		return id;
	}
	
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		for (Component component : components.values()) {
			component.update(gc, sb, delta);
		}
	}
	
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws Exception {
		if (isRenderable) {
			renderComponent.render(gc, sb, g);
		} else {
			throw new Exception(id + " entity is not renderable");
		}
	}
}

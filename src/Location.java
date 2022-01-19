import java.awt.*;
import java.util.Random;

public class Location extends ProgramObject{
    private Handler handler;
    private Color color;

    public Location(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        Random random = new Random();
        color = new Color(random.nextInt(0,255),random.nextInt(0,255),random.nextInt(0,255));
        this.handler = handler;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 10, 10);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect((int) x, (int) y, 10, 10);
    }
}

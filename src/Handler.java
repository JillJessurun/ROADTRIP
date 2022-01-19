import java.awt.*;
import java.util.LinkedList;

public class Handler {
    LinkedList<ProgramObject> locations = new LinkedList<ProgramObject>();

    public void tick(){
        for (int i = 0; i < locations.size(); i++) {
            ProgramObject tempObject = locations.get(i);
            tempObject.tick();
        }
    }

    public void render(Graphics g){
        for (int i = 0; i < locations.size(); i++) {
            ProgramObject tempObject = locations.get(i);
            tempObject.render(g);
        }
    }

    public void addObject(ProgramObject object){
        this.locations.add(object);
    }

    public void removeObject(ProgramObject object){
        this.locations.remove(object);
    }

    public void drawLines(Graphics g){
        for (int i = 0; i < locations.size(); i++) {
            ProgramObject location1 = locations.get(i);
            ProgramObject location2;

            //break when arrived at the last location
            if (i + 1 != locations.size()) {
                location2 = locations.get(i + 1);
            }else{
                break;
            }

            //draw line between 2 locations
            g.drawLine((int)location1.x + 5, (int)location1.y + 5, (int)location2.x + 5, (int)location2.y + 5);
        }
    }
}
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class SavedPopUp extends MouseAdapter {

    private Main main;
    private PlanningRoute planningRoute;
    private Handler handler;

    public SavedPopUp(Main main, PlanningRoute planningRoute, Handler handler){
        this.main = main;
        this.planningRoute = planningRoute;
        this.handler = handler;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        //home button savedpopup
        if (mouseOver(mx, my, 538,430,170, 35) && main.programState == Main.STATE.SavedPopUp) {
            main.programState = Main.STATE.Menu;
            planningRoute.locationCounter = 0;
            planningRoute.addingLocation = false;
            planningRoute.removingLocation = false;
            planningRoute.buttonAvailable = true;
            planningRoute.takingSS = false;

            //empty the locations array
            handler.locations = getNewList();
        }
    }

    public void tick(){

    }

    public void render(Graphics g){
        Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 18);
        Font savedFont = new Font("Comic Sans MS", Font.BOLD, 60);
        Color color = new Color(113, 255, 0, 76);

        //saved box
        g.setColor(color);
        g.fillRect(0,0, Main.WIDTH, Main.HEIGHT);
        g.setColor(Color.black);
        g.setFont(savedFont);
        g.drawString("Saved succesfully!", 360,330);

        //home button
        g.setFont(buttonFont);
        g.setColor(Color.white);
        g.fillRect(538,430,170, 35);
        g.setColor(Color.black);
        g.drawRect(538,430,170, 35);
        g.drawString("Home", 600,454);
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public LinkedList<ProgramObject> getNewList(){
        return new LinkedList<>();
    }
}

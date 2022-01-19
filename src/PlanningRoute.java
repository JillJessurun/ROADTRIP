import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class PlanningRoute extends MouseAdapter {

    //instances
    private BufferedImage bufferedImage;
    private Handler handler;
    private Main main;

    //variables
    private boolean addingLocation = false;
    private boolean removingLocation = false;
    private boolean buttonAvailable = true;
    private int locationCounter = 0;

    //constructor
    public PlanningRoute(BufferedImage bufferedImage, Main main, Handler handler){
        this.bufferedImage = bufferedImage;
        this.main = main;
        this.handler = handler;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        // map clicked
        if (mouseOver(mx, my, 0,0, Main.WIDTH, Main.HEIGHT) && main.programState == Main.STATE.PlanningRoute && addingLocation) {
            handler.addObject(new Location(getMouseXposition() - 334, getMouseYposition() - 137, ID.Location, handler));
            locationCounter++;
            addingLocation = false;
        }

        // map clicked
        if (mouseOver(mx, my, 0,0, Main.WIDTH, Main.HEIGHT) && main.programState == Main.STATE.PlanningRoute && removingLocation) {
            System.out.println("you clicked on the map! :D");
            for (int i = 0; i < handler.locations.size(); i++) {
                ProgramObject location = handler.locations.get(i);
                //check if the mouseclick is on a location
                if (mouseOver(mx, my, (int)location.x, (int)location.y, 10, 10)){
                    handler.removeObject(location);
                }
            }
            removingLocation = false;
        }

        // add location button planningroute
        if (mouseOver(mx, my, 1070,25, 170, 35) && main.programState == Main.STATE.PlanningRoute && !addingLocation && buttonAvailable) {
            addingLocation = true;
            buttonAvailable = false;
        }

        // remove location button planningroute
        if (mouseOver(mx, my, 1070,75, 170, 35) && main.programState == Main.STATE.PlanningRoute && !addingLocation && buttonAvailable) {
            removingLocation = true;
            buttonAvailable = false;
        }

        // home button planningroute
        if (mouseOver(mx, my, 1070,745, 170, 35) && main.programState == Main.STATE.PlanningRoute && !addingLocation) {
            locationCounter = 0;
            main.programState = Main.STATE.Menu;
        }
    }

    public void tick(){
        if (!addingLocation && !buttonAvailable && !removingLocation){
            buttonAvailable = true;
        }
    }

    public void render(Graphics g) throws IOException, FontFormatException {
        //setups
        Graphics2D g2d = (Graphics2D) g;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font textFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\Like Snow.ttf")).deriveFont(35f);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\Like Snow.ttf")));
        Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 18);
        Color color = new Color(248, 239, 210);
        g.setColor(Color.black);
        g.setFont(textFont);

        //background
        g.drawImage(bufferedImage, 0,0,null);

        //add location button
        if (!addingLocation && !removingLocation) {
            g.setColor(Color.white);
            g.fillRect(1070, 25, 170, 35);
            g.setColor(Color.black);
            g.drawRect(1070, 25, 170, 35);
            g.setFont(buttonFont);
            g.drawString("+ add location", 1094, 48);

            //remove location button
            g.setColor(Color.white);
            g.fillRect(1070, 75, 170, 35);
            g.setColor(Color.black);
            g.drawRect(1070, 75, 170, 35);
            g.setFont(buttonFont);
            g.drawString("- remove location", 1080, 98);

            //quit button
            g.setColor(Color.white);
            g.fillRect(1070, 745, 170, 35);
            g.setColor(Color.black);
            g.drawRect(1070, 745, 170, 35);
            g.setFont(buttonFont);
            g.drawString("Quit", 1134, 768);
        }
        if (locationCounter >= 2) {
            handler.drawLines(g);
        }
    }

    public static float getMouseXposition(){
        Point point = MouseInfo.getPointerInfo().getLocation();
        return (float) point.getX();
    }

    public static float getMouseYposition(){
        Point point = MouseInfo.getPointerInfo().getLocation();
        return (float) point.getY();
    }

    public static boolean mouseHover(float x, float y, int width, int height){
        if (getMouseXposition() > (x + width)){
            return false;
        }else if (getMouseXposition() < x){
            return false;
        }else if (getMouseYposition() > (y + height)){
            return false;
        }else if (getMouseYposition() < y){
            return false;
        }else{
            return true;
        }
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

    public Color getRandomColor(){
        Random random = new Random();
        return new Color(random.nextInt(0,255),random.nextInt(0,255),random.nextInt(0,255));
    }
}

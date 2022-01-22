import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;
import java.util.Random;

public class PlanningRoute extends MouseAdapter {

    //instances
    private BufferedImage bufferedImage;
    private Handler handler;
    private Main main;
    private JFrame frame;
    private File file;
    private File file2;

    //variables
    public boolean addingLocation = false;
    public boolean removingLocation = false;
    public boolean changingColor = false;
    public boolean buttonAvailable = true;
    public int locationCounter = 0;
    public boolean takingSS = false;

    //constructor
    public PlanningRoute(BufferedImage bufferedImage, Main main, Handler handler, JFrame frame, File file, File file2){
        this.frame = frame;
        this.bufferedImage = bufferedImage;
        this.main = main;
        this.handler = handler;
        this.file = file;
        this.file2 = file2;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        // map clicked adding a location
        if (mouseOver(mx, my, 0,0, Main.WIDTH, Main.HEIGHT) && main.programState == Main.STATE.PlanningRoute && addingLocation) {
            handler.addObject(new Location(getMouseXposition() - 334, getMouseYposition() - 137, ID.Location, handler));
            locationCounter++;
            addingLocation = false;
        }

        // map clicked removing a location
        if (mouseOver(mx, my, 0,0, Main.WIDTH, Main.HEIGHT) && main.programState == Main.STATE.PlanningRoute && removingLocation) {
            for (int i = 0; i < handler.locations.size(); i++) {
                ProgramObject location = handler.locations.get(i);
                //check if the mouseclick is on a location
                if (mouseOver(mx, my, (int)location.x, (int)location.y, 10, 10)){
                    handler.removeObject(location);
                }
            }
            removingLocation = false;
        }

        // map clicked changing a color
        if (mouseOver(mx, my, 0,0, Main.WIDTH, Main.HEIGHT) && main.programState == Main.STATE.PlanningRoute && changingColor) {
            for (int i = 0; i < handler.locations.size(); i++) {
                ProgramObject location = handler.locations.get(i);
                //check if the mouseclick is on a location
                if (mouseOver(mx, my, (int)location.x, (int)location.y, 10, 10)){
                    handler.changeColorLocation(location);
                }
            }
            changingColor = false;
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

        // change color button planningroute
        if (mouseOver(mx, my, 1070, 125, 170, 35) && main.programState == Main.STATE.PlanningRoute && !addingLocation && buttonAvailable) {
            changingColor = true;
            buttonAvailable = false;
        }

        // home button planningroute
        if (mouseOver(mx, my, 1070,745, 170, 35) && main.programState == Main.STATE.PlanningRoute && !addingLocation) {
            locationCounter = 0;
            handler.locations = getNewList();
            main.programState = Main.STATE.Menu;
        }

        // save roadtrip button planningroute
        if (mouseOver(mx, my, 30, 25, 170, 35) && main.programState == Main.STATE.PlanningRoute && !addingLocation) {

            takingSS = true;

            //take screenshot
            Robot robot = null;
            try {
                robot = new Robot();
            } catch (AWTException ex) {
                ex.printStackTrace();
            }
            Point locationFrame = frame.getLocation();
            Rectangle rect = new Rectangle(locationFrame.x + 10,locationFrame.y + 40,Main.WIDTH -20,Main.HEIGHT -60);
            assert robot != null;
            BufferedImage bufferedImage = robot.createScreenCapture(rect);

            main.routeCounter++;
            String path = "ss" + main.routeCounter + ".png";
            String fileName = "C:\\Users\\pc\\IdeaProjects\\ROADTRIP\\ROADTRIP\\src\\SavedRouteImages\\" + path;
            File file = new File(fileName);
            try {
                ImageIO.write(bufferedImage, "png", file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            //save routelist in text file
            BufferedWriter output = null;
            try {
                output = new BufferedWriter(new FileWriter(this.file, true));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                assert output != null;
                output.newLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                output.append("").append(fileName + "\n");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                output.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            //increment routecounter by 1
            BufferedWriter output2 = null;
            try {
                output2 = new BufferedWriter(new FileWriter(this.file2, true));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                assert output2 != null;
                output2.newLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                output2.append("" + main.routeCounter);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                output2.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            main.programState = Main.STATE.SavedPopUp;
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
        Font buttonFont2 = new Font("Comic Sans MS", Font.BOLD, 36);
        g.setColor(Color.black);
        g.setFont(textFont);

        //background
        g.drawImage(bufferedImage, 0,0,null);

        //add location button
        if (!addingLocation && !removingLocation && !takingSS && !changingColor) {
            //add location button
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

            //change color button
            g.setColor(Color.white);
            g.fillRect(1070, 125, 170, 35);
            g.setColor(Color.black);
            g.drawRect(1070, 125, 170, 35);
            g.setFont(buttonFont);
            g.drawString("change color", 1103, 148);

            //quit button
            g.setColor(Color.white);
            g.fillRect(1070, 745, 170, 35);
            g.setColor(Color.black);
            g.drawRect(1070, 745, 170, 35);
            g.setFont(buttonFont);
            g.drawString("Quit", 1134, 768);

            //save roadtrip button
            g.setColor(Color.white);
            g.fillRect(30, 25, 170, 35);
            g.setColor(Color.black);
            g.drawRect(30, 25, 170, 35);
            g.setFont(buttonFont);
            g.drawString("Save roadtrip", 56, 48);
        }

        if (addingLocation && !takingSS){
            g.setColor(Color.black);
            g.setFont(buttonFont2);
            g.drawString("~ Click on the map to add a location ~", 290,60);
        }

        if (removingLocation && !takingSS){
            g.setColor(Color.black);
            g.setFont(buttonFont2);
            g.drawString("~ Click on a location to remove it ~", 320,60);
        }

        if (changingColor && !takingSS){
            g.setColor(Color.black);
            g.setFont(buttonFont2);
            g.drawString("~ Click on a location to change the color ~", 260,60);
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

    public LinkedList<ProgramObject> getNewList(){
        return new LinkedList<>();
    }

    public void changeMap(BufferedImage map){
        this.bufferedImage = map;
    }
}

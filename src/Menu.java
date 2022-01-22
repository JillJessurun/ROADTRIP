import java.awt.*;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;

public class Menu extends MouseAdapter {

    //instances
    private BufferedImage bufferedImage;
    private Main main;
    private ViewRoutes viewRoutes;
    public Image image;
    public Image image2;
    public Image image3;
    private BufferedImage map1;
    private BufferedImage map2;
    private BufferedImage map3;
    private BufferedImage map4;
    private BufferedImage map5;
    private BufferedImage map6;
    private PlanningRoute planningRoute;

    //variables
    private int selectedRoute = 1;
    private String map = "Europe";

    //constructor
    public Menu(BufferedImage bufferedImage, Main main, ViewRoutes viewRoutes, BufferedImage map2, PlanningRoute planningRoute, BufferedImage map1, BufferedImage map3, BufferedImage map4, BufferedImage map5, BufferedImage map6) {
        this.bufferedImage = bufferedImage;
        this.main = main;
        this.viewRoutes = viewRoutes;
        this.map2 = map2;
        this.planningRoute = planningRoute;
        this.map1 = map1;
        this.map3 = map3;
        this.map4 = map4;
        this.map5 = map5;
        this.map6 = map6;

        image = Toolkit.getDefaultToolkit().createImage("C:\\Users\\pc\\IdeaProjects\\ROADTRIP\\ROADTRIP\\src\\Images\\pad.gif");
        image2 = Toolkit.getDefaultToolkit().createImage("C:\\Users\\pc\\IdeaProjects\\ROADTRIP\\ROADTRIP\\src\\Images\\pad2.gif");
        image3 = Toolkit.getDefaultToolkit().createImage("C:\\Users\\pc\\IdeaProjects\\ROADTRIP\\ROADTRIP\\src\\Images\\auto.gif");
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        //new route button menu
        if (mouseOver(mx, my, 500,260,253,45) && main.programState == Main.STATE.Menu) {
            main.programState = Main.STATE.PlanningRoute;
        }

        //view routes button menu
        if (mouseOver(mx, my, 470,360,305,45) && main.programState == Main.STATE.Menu) {
            viewRoutes.pathSavedRoute = "C:\\Users\\pc\\IdeaProjects\\ROADTRIP\\ROADTRIP\\src\\SavedRouteImages\\" + "ss" + selectedRoute + ".png";
            main.programState = Main.STATE.ViewRoutes;
        }

        //route change button menu
        if (mouseOver(mx, my, 1085,760, 50,25) && main.programState == Main.STATE.Menu) {
            selectedRoute++;
            if (selectedRoute > main.routeCounter){
                selectedRoute = 1;
            }
        }

        //change map button menu
        if (mouseOver(mx, my, 1090,730, 50,25) && main.programState == Main.STATE.Menu) {
            if (map.equals("Europe")){
                map = "North-America";
                planningRoute.changeMap(map2);
            }else if (map.equals("North-America")){
                map = "South-America";
                planningRoute.changeMap(map3);
            }else if (map.equals("South-America")){
                map = "Africa";
                planningRoute.changeMap(map4);
            }else if (map.equals("Africa")){
                map = "Asia";
                planningRoute.changeMap(map5);
            }else if (map.equals("Asia")){
                map = "Australia";
                planningRoute.changeMap(map6);
            }else if (map.equals("Australia")){
                map = "Europe";
                planningRoute.changeMap(map1);
            }
        }

        //quit button menu
        if (mouseOver(mx, my, 562,560,113,45) && main.programState == Main.STATE.Menu) {
            System.exit(0);
        }
    }

    public void tick(){

    }

    public void render(Graphics g) throws IOException, FontFormatException {
        //setups
        Graphics2D g2d = (Graphics2D) g;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font titleFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\Like Snow.ttf")).deriveFont(70f);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\Like Snow.ttf")));
        Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 50);
        Font buttonFont2 = new Font("Comic Sans MS", Font.BOLD, 18);
        Font madeByJillJessurunFont = new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 16);

        //background
        g.drawImage(bufferedImage, 0,0,null);

        //title
        g.setColor(Color.black);
        g.setFont(titleFont);
        g.drawString("ROADTRIP  PLANNER", 210, 160);

        //buttons
        g.setFont(buttonFont);
        g.drawString("New route", 500, 300);
        //g.drawRect(500,260,253,45);
        if (map.equals("Europe")){
            g.drawString("Map: " + map, 470, 500);
        }else if (map.equals("North-America")){
            g.drawString("Map: " + map, 374, 500);
        }else if (map.equals("South-America")){
            g.drawString("Map: " + map, 374, 500);
        }else if (map.equals("Africa")){
            g.drawString("Map: " + map, 475, 500);
        }else if (map.equals("Asia")){
            g.drawString("Map: " + map, 505, 500);
        }else if (map.equals("Australia")){
            g.drawString("Map: " + map, 440, 500);
        }
        //g.drawRect(470,460,305,45);
        g.drawString("View route " + selectedRoute, 470, 400);
        //g.drawRect(470,360,305,45);
        g.drawString("Quit", 565, 600);
        //g.drawRect(562,560,113,45);

        //map choosing
        g.setFont(buttonFont2);
        g.drawString(" - change route map - click        to change -", 840,750);
        g.setColor(Color.orange);
        g.drawString("HERE", 1090,750);
        //g.drawRect(1090,730, 50,25);

        //route choosing
        g.setFont(buttonFont2);
        g.setColor(Color.black);
        g.drawString(" - route " + selectedRoute + " selected - click        to change -", 840,780);
        g.setColor(Color.orange);
        g.drawString("HERE", 1085,780);
        //g.drawRect(1085,760, 50,25);

        //made by jill jessurun
        g.setFont(madeByJillJessurunFont);
        g.setColor(Color.black);
        g.drawString("Version: 6.9 - Jill Jessurun - 1/2022", 20, 780);

        //gifs
        g.drawImage(image, 150, 215, null);
        g.drawImage(image2, 900, 230, null);
        g.drawImage(image3, 450, 615, null);

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
}

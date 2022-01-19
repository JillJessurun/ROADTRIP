import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

public class Menu extends MouseAdapter {

    //instances
    private BufferedImage bufferedImage;
    private Main main;

    //variables

    //constructor
    public Menu(BufferedImage bufferedImage, Main main){
        this.bufferedImage = bufferedImage;
        this.main = main;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        //new route button menu
        if (mouseOver(mx, my, 500,290,253,45) && main.programState == Main.STATE.Menu) {
            main.programState = Main.STATE.PlanningRoute;
        }

        //view routes button menu
        if (mouseOver(mx, my, 482,390,284,45) && main.programState == Main.STATE.Menu) {
            main.programState = Main.STATE.ViewRoutes;
        }

        //quit button menu
        if (mouseOver(mx, my, 562,490,113,45) && main.programState == Main.STATE.Menu) {
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
        Font madeByJillJessurunFont = new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 16);

        //background
        g.drawImage(bufferedImage, 0,0,null);

        //title
        g.setColor(Color.black);
        g.setFont(titleFont);
        g.drawString("ROADTRIP  PLANNER", 210, 160);

        //buttons
        g.setFont(buttonFont);
        g.drawString("New route", 500, 330);
        //g.drawRect(500,290,253,45);
        g.drawString("View routes", 482, 430);
        //g.drawRect(482,390,284,45);
        g.drawString("Quit", 565, 530);
        //g.drawRect(562,490,113,45);

        //made by jill jessurun
        g.setFont(madeByJillJessurunFont);
        g.drawString("Version: 6.9 - Jill Jessurun - 1/2022", 20, 780);

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
}

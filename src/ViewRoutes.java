import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ViewRoutes extends MouseAdapter {

    //instances
    private BufferedImage bufferedImage;
    private Main main;
    private Scanner scanner;
    private File file;

    //route image
    private BufferedImage route;
    private static BufferedImage routeLoad;
    private BufferedImageLoader loader;

    //variables
    public String pathSavedRoute = "C:\\Users\\pc\\IdeaProjects\\ROADTRIP\\ROADTRIP\\src\\SavedRouteImages\\ss1.png";
    public boolean routePainted = false;

    //constructor
    public ViewRoutes(BufferedImage bufferedImage, Main main, File file) throws IOException {
        this.bufferedImage = bufferedImage;
        this.main = main;
        this.file = file;
        scanner = new Scanner(file);
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        //home button viewroutes
        if (mouseOver(mx, my, 565, 700,110,45) && main.programState == Main.STATE.ViewRoutes) {
            //changePathRight = true;
            routePainted = false;
            main.programState = Main.STATE.Menu;
        }
    }

    public void tick() throws IOException {
        if (!routePainted){
            loader = new BufferedImageLoader();
            routeLoad = loader.loadImage(pathSavedRoute);
            Image image = new Image(routeLoad);
            route = image.grabImage();
            route = image.resizeImage(route, 1000, 600);
            routePainted = true;
        }
    }

    public void render(Graphics g){
        //setups
        Font madeByJillJessurunFont = new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 16);
        Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 50);

        //background
        g.drawImage(bufferedImage, 0,0,null);

        //box
        g.drawImage(route,130,60,null);

        //button
        //g.setColor(Color.white);
        //g.fillRect(542,620,153,65);
        //g.setColor(Color.black);
        //g.drawRect(542,620,153,65);
        g.setFont(buttonFont);
        g.drawString("Home", 565, 740);
        //g.drawRect(565, 700,110,45);

        //made by jill jessurun
        g.setFont(madeByJillJessurunFont);
        g.drawString("Version: 6.9 - Jill Jessurun - 1/2022", 20, 780);
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

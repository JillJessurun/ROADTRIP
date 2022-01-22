/*
author: Jill Jessurun
date: januari 2022
goal: creating a road trip planning program

further development:
- fill in name when adding a location
- saved as route (number)! in savedpopup
- when in route view screen, text to inform which route is displayed
- sound! music!
- when quitting from planning screen, ask "are you sure?"
- delete route function

note:
when removing saved routes update all these spots:
- SavedRouteImages package
- update routeCounter.txt to the correct amount of images in the SavedRouteImages package
- delete the correct routes with the correct names in savedRoutes.txt
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main extends Canvas implements Runnable{

    //instances
    private Handler handler;
    private Thread thread;
    private Menu menu;
    private BufferedImageLoader loader;
    private PlanningRoute planningRoute;
    private SavedPopUp savedPopUp;
    private ViewRoutes viewRoutes;
    private JFrame frame;
    private File file;
    private File file2;
    private Scanner scanner;

    //variables
    public static final int WIDTH = 1281;
    public static final int HEIGHT = 840;
    private boolean running = true;
    private boolean menuCreated = false;
    public int routeCounter = 0;

    //images
    private BufferedImage background;
    private static BufferedImage imageLoad;

    private BufferedImage background2;
    private static BufferedImage imageLoad2;

    private BufferedImage background3;
    private static BufferedImage imageLoad3;

    private BufferedImage background4;
    private static BufferedImage imageLoad4;

    private BufferedImage background5;
    private static BufferedImage imageLoad5;

    private BufferedImage background6;
    private static BufferedImage imageLoad6;

    private BufferedImage background7;
    private static BufferedImage imageLoad7;

    //pages
    public enum STATE {
        Menu,
        PlanningRoute,
        ViewRoutes,
        SavedPopUp
    }

    //states
    public STATE programState = STATE.Menu;

    public Main() throws IOException {
        //bufferedimageloader
        loader = new BufferedImageLoader();

        //images
        imageLoad = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\ROADTRIP\\ROADTRIP\\src\\Images\\roadtrip.jpg");
        imageLoad2 = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\ROADTRIP\\ROADTRIP\\src\\Images\\map.jpg");
        imageLoad3 = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\ROADTRIP\\ROADTRIP\\src\\Images\\map2.png");
        imageLoad4 = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\ROADTRIP\\ROADTRIP\\src\\Images\\map3.png");
        imageLoad5 = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\ROADTRIP\\ROADTRIP\\src\\Images\\map4.png");
        imageLoad6 = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\ROADTRIP\\ROADTRIP\\src\\Images\\map5.png");
        imageLoad7 = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\ROADTRIP\\ROADTRIP\\src\\Images\\map6.png");

        Image image = new Image(imageLoad);
        Image image2 = new Image(imageLoad2);
        Image image3 = new Image(imageLoad3);
        Image image4 = new Image(imageLoad4);
        Image image5 = new Image(imageLoad5);
        Image image6 = new Image(imageLoad6);
        Image image7 = new Image(imageLoad7);

        background = image.grabImage();
        background2 = image2.grabImage();
        background3 = image3.grabImage();
        background4 = image4.grabImage();
        background5 = image5.grabImage();
        background6 = image6.grabImage();
        background7 = image7.grabImage();

        image.resizeImage(background3, 1281, 840);
        image.resizeImage(background4, 1281, 840);
        image.resizeImage(background5, 1281, 840);
        image.resizeImage(background6, 1281, 840);
        image.resizeImage(background7, 1281, 840);

        //files
        file = new File("C:\\Users\\pc\\IdeaProjects\\ROADTRIP\\ROADTRIP\\src\\savedRoutes.txt");
        file2 = new File("C:\\Users\\pc\\IdeaProjects\\ROADTRIP\\ROADTRIP\\src\\routeCounter.txt");

        //read readcounter text file, get highest number
        BufferedReader reader = new BufferedReader(new FileReader(file2));
        while(true) {
            String line = reader.readLine();
            if (line != null && (!line.isEmpty())) {
                int lineNumber = Integer.parseInt(line.trim());
                if (lineNumber > routeCounter) {
                    routeCounter = Integer.parseInt(line.trim());
                }
            }else{
                break;
            }
        }
        //System.out.println(routeCounter);

        //scanner
        scanner = new Scanner(file);

        //window
        Window window = new Window(WIDTH, HEIGHT, "ROADTRIP PLANNER", this);
        frame = window.frame;

        //handler stuff
        handler = new Handler();

        //viewroutes
        viewRoutes = new ViewRoutes(background, this, file);

        //planningroute screen
        planningRoute = new PlanningRoute(background2, this, handler, frame, file, file2);

        //menu
        menu = new Menu(background, this, viewRoutes, background3, planningRoute, background2, background4, background5, background6, background7);
        menuCreated = true;

        //savedpopop
        savedPopUp = new SavedPopUp(this, planningRoute, handler);

        //listeners
        //this.addKeyListener(new KeyInput(handler));
        this.addMouseListener(savedPopUp);
        this.addMouseListener(menu);
        this.addMouseListener(planningRoute);
        this.addMouseListener(viewRoutes);
    }

    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        try {
            thread.join();
            running = false;
        }catch (Exception e){
            System.out.println("Something went terribly wrong i dunno what sorry");
        }
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                try {
                    tick();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                delta--;
            }
            if(running){
                try {
                    render();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (FontFormatException e) {
                    e.printStackTrace();
                }
            }
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() throws IOException {
        if (menuCreated) {
            if (programState == STATE.Menu) {
                menu.tick();
            }
        }
        if (programState == STATE.PlanningRoute) {
            planningRoute.tick();
            handler.tick();
        }
        if (programState == STATE.SavedPopUp) {
            planningRoute.tick();
            handler.tick();
            savedPopUp.tick();
        }
        if (programState == STATE.ViewRoutes) {
            viewRoutes.tick();
        }
    }

    private void render() throws IOException, FontFormatException {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        if (menuCreated) {
            if (programState == STATE.Menu) {
                menu.render(g);
            }
        }
        if (programState == STATE.PlanningRoute) {
            planningRoute.render(g);
            handler.render(g);
        }
        if (programState == STATE.SavedPopUp) {
            planningRoute.render(g);
            handler.render(g);
            savedPopUp.render(g);
        }
        if (programState == STATE.ViewRoutes) {
            viewRoutes.render(g);
        }

        g.dispose();
        bs.show();
    }

    //clamp method: if the var is at the max, it stays at the max (same with the min)
    public static float clamp(float var, float min, float max){
        if(var >= max){
            return var = max;
        }else if(var <= min){
            return var = min;
        }else{
            return var;
        }
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }
}
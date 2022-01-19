import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main extends Canvas implements Runnable{

    //instances
    private Handler handler;
    private Thread thread;
    private HUD hud;
    private Menu menu;
    private BufferedImageLoader loader;
    private PlanningRoute planningRoute;

    //variables
    public static final int WIDTH = 1281;
    public static final int HEIGHT = 840;
    private boolean running = true;

    //images
    private BufferedImage background;
    private static BufferedImage imageLoad;

    private BufferedImage background2;
    private static BufferedImage imageLoad2;

    //pages
    public enum STATE {
        Menu,
        PlanningRoute,
        ViewRoutes
    }

    //states
    public STATE programState = STATE.Menu;

    public Main() throws IOException {
        //bufferedimageloader
        loader = new BufferedImageLoader();

        //images
        imageLoad = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\ROADTRIP\\ROADTRIP\\src\\Images\\roadtrip.jpg");
        imageLoad2 = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\ROADTRIP\\ROADTRIP\\src\\Images\\map.jpg");

        Image image = new Image(imageLoad);
        Image image2 = new Image(imageLoad2);

        background = image.grabImage();
        background2 = image2.grabImage();

        //window
        new Window(WIDTH, HEIGHT, "ROADTRIP PLANNER", this);

        //handler stuff
        handler = new Handler();

        //menu
        menu = new Menu(background, this);

        //planningroute screen
        planningRoute = new PlanningRoute(background2, this, handler);

        //listeners
        //this.addKeyListener(new KeyInput(handler));
        this.addMouseListener(menu);
        this.addMouseListener(planningRoute);

        //hud
        hud = new HUD();
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
                tick();
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

    private void tick(){
        if (programState == STATE.Menu) {
            menu.tick();
        }
        if (programState == STATE.PlanningRoute) {
            planningRoute.tick();
            handler.tick();
            //hud.tick();
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

        if (programState == STATE.Menu) {
            menu.render(g);
        }
        if (programState == STATE.PlanningRoute) {
            planningRoute.render(g);
            handler.render(g);
            //hud.render(g);
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
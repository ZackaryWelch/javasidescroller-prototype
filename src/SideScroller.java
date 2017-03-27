import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class SideScroller extends JPanel
{
	public static final long serialVersionUID = 1L;
    static BufferedImage sprites1, sprites2, sprites3;
    static JFrame frame = new JFrame("SideScroller");
    static boolean wPressed, altL, altR;
    static JLabel guy = new JLabel();
	static JLabel enemy = new JLabel();
    static Random rand = new Random();
    static int r = ((rand.nextInt(16))*16)+64;
    static Color night = new Color(50, 50, 50);
    static Color skyBlue = new Color(164, 211, 238);
    static java.util.List<Integer> randoms = new ArrayList<Integer>();
    static java.util.List<Point> cloudsL = new ArrayList<Point>(11);
    static java.util.List<Dimension> cloudsD = new ArrayList<Dimension>(11);
    static java.util.List<Point> stars = new ArrayList<Point>(11);
    //Rectangle characterRect = new Rectangle(xLocation, yLocation+1, 16, 32);
    static java.util.List<Rectangle> grassRectangles = new ArrayList<Rectangle>();
    Rectangle grassRect;
    
    public SideScroller()
    {
        KeyStroke k = KeyStroke.getKeyStroke(KeyEvent.VK_W, 0);
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(k, k.toString());
        getActionMap().put(k.toString(), new AbstractAction()
        {
			public static final long serialVersionUID = 1L;
            @Override
			public void actionPerformed(ActionEvent e)
            {
                guy.setIcon(new ImageIcon(sprit(1)[4]));
                wPressed = true;
            }
        });
        KeyStroke k1 = KeyStroke.getKeyStroke(KeyEvent.VK_A, 0);
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(k1, k1.toString());
        getActionMap().put(k1.toString(), new AbstractAction()
        {
			public static final long serialVersionUID = 1L;
            @Override
			public void actionPerformed(ActionEvent e)
            {
                //if(altL)
                //{
                   guy.setIcon(new ImageIcon(sprit(1)[1])); 
                //}else
                //{
                //   guy.setIcon(new ImageIcon(sprit(1)[1]));
                //}
                guy.setLocation(guy.getX()-2, guy.getY());
                wPressed = false;
                altL = true;
                altR = false;
            }
        }); 
        KeyStroke k2 = KeyStroke.getKeyStroke(KeyEvent.VK_S, 0);
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(k2, k2.toString());
        getActionMap().put(k2.toString(), new AbstractAction()
        {
			public static final long serialVersionUID = 1L;
            @Override
			public void actionPerformed(ActionEvent e)
            {
                guy.setIcon(new ImageIcon(sprit(1)[3]));
                wPressed = false;
                guy.setLocation(guy.getX(), grassRectangles.get(getClosestRectangle()).y-32);
            }
        }); 
        KeyStroke k3 = KeyStroke.getKeyStroke(KeyEvent.VK_D, 0);
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(k3, k3.toString());
        getActionMap().put(k3.toString(), new AbstractAction()
        {
			public static final long serialVersionUID = 1L;
            @Override
			public void actionPerformed(ActionEvent e)
            {
                //if(altR)
                //{
                    guy.setIcon(new ImageIcon(sprit(1)[0]));
                //}else
                //{
                //    guy.setIcon(new ImageIcon(sprit(1)[0]));
                //}
                guy.setLocation(guy.getX()+2, guy.getY());
                wPressed = false;
                altR = true;
                altL = false;
            }
        });
        KeyStroke k4 = KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0);
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(k4, k4.toString());
        getActionMap().put(k4.toString(), new AbstractAction()
        {
			public static final long serialVersionUID = 1L;
            @Override
			public void actionPerformed(ActionEvent e)
            {
                randoms.clear();
                for(int foo = 1; foo < frame.getWidth()/4; foo++)
                {
					r = ((rand.nextInt(16))*16)+64;
                    randoms.add(r);
                }
                cloudsL.clear();
                for(int bar = 1; bar < frame.getWidth()/8; bar++)
                {
                    cloudsL.add(new Point(rand.nextInt(frame.getWidth()), rand.nextInt(48)));
//                    if(bar > 1)
//                    {
//                        System.out.println(cloudsL.get(bar-1));
//                    }
                }
                cloudsD.clear();
                for(int dog = 1; dog < 12; dog++)
                {
                    cloudsD.add(new Dimension(rand.nextInt(16)+16, (rand.nextInt(16)+16)/2));
                }
                stars.clear();
                for(int cat = 1; cat < 33; cat++)
                {
                    stars.add(new Point(rand.nextInt(frame.getWidth()), rand.nextInt(64)+1));
                }
                grassRectangles.clear();
                repaint();
            }
        });
        KeyStroke k5 = KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0);
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(k5, k5.toString());
        getActionMap().put(k5.toString(), new AbstractAction()
        {
			public static final long serialVersionUID = 1L;
            @Override
			public void actionPerformed(ActionEvent e)
            {
                frame.setResizable(!frame.isResizable());
            }
        });
        if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) <= 12)
        {
            setBackground(skyBlue);
        }
        else if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) > 12)
        {
            setBackground(night);
        }
        setLayout(null);
        add(guy);
		add(enemy);
        guy.setBounds(new Rectangle(0, r-31, 16, 32));
		enemy.setBounds(new Rectangle(frame.getWidth()-r, r-31, 16, 16));
        guy.setIcon(new ImageIcon(sprit(1)[0]));
		enemy.setIcon(new ImageIcon(sprit(3)[0]));
    }
    
    public static void main(String[] args)
    {
        try
        {
            sprites1 = ImageIO.read(new File("Texturesheet.png")); //Loads block textures
        }catch(IOException e)
        {
            System.err.println("Textures did not load");
        }
        try
        {
            sprites2 = ImageIO.read(new File("Charactersheet.png")); //Loads character textures
        }catch(IOException e)
        {
            System.err.println("Characters did not load");
        }
		try
        {
            sprites3 = ImageIO.read(new File("Enemysheet.png")); //Loads enemy textures
        }catch(IOException e)
        {
            System.err.println("Monsters did not load");
        }
        frame.add(new SideScroller()); //Window loading
        frame.setSize(640, 384);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setFocusable(true);
        for(int foo = 1; foo < frame.getWidth()/8; foo++) //Creates the random numbers
        {
			r = ((rand.nextInt(16))*16)+64;
            randoms.add(r);
        }
        frame.validate();
		for(int bar = 1; bar < 12; bar++) //Creates random lengths/dimensions for the stars and clouds
        {
            cloudsL.add(new Point(rand.nextInt(frame.getWidth()), rand.nextInt(48)));
            cloudsD.add(new Dimension(rand.nextInt(16)+16, (rand.nextInt(16)+16)/2));
            stars.add(new Point(rand.nextInt(frame.getWidth()), rand.nextInt(64)+1));
        }
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
				
                for(int x = 1; x < SideScroller.frame.getWidth()/16; x++)
                {
                    if(guy.getY()+31 > grassRectangles.get(x).getY() && SideScroller.wPressed
                        && guy.getX() < (16*x)-1  && guy.getX() > (16*x)-17)
                    {
                         guy.setLocation(grassRectangles.get(x).x, grassRectangles.get(x).y-32); //Set location is w is pressed
                    }
                    if(guy.getY()+32 < grassRectangles.get(x).getY() && guy.getX()
                            > (16*x)-1 && guy.getX() > (16*x)-17)
                    {
                        guy.setLocation(grassRectangles.get(x).x, grassRectangles.get(x).y-32); //Set location if fall
                    }
                }
				int ran = rand.nextInt(24);
				enemy.setLocation((int)grassRectangles.get(ran).getX(),(int)grassRectangles.get(ran).getY()-16);
                /*if(guy.getY()+15 == grassRectangles.get(0).getY())
                {
                    System.out.println("up");
                    System.out.println(guy.getY());
                    System.out.println(grassRectangles.get(0).getY());
                }*/
            }
        });
    }

    public static BufferedImage[] sprit(int x) //Creates the sprite arrays
    {
		BufferedImage[] sprites = null;
		if(x==1) //Character
		{
		    sprites = new BufferedImage[8];
			for (int i = 0; i < 2; i++)
			{
				for (int j = 0; j < 4; j++)
				{
					sprites[(i * 2) + j] = sprites2.getSubimage(j * 16, i * 32, 16, 32); //Separates sheet into blocks of 16 by 32 to put into the array
				}
			}
		}else if(x==2) //Blocks
		{
			sprites = new BufferedImage[2];
			for (int i = 0; i < 1; i++)
			{
				for (int j = 0; j < 2; j++)
				{
					sprites[(i * 2) + j] = sprites1.getSubimage(j * 16, i * 16, 16, 16); //Separates sheet into blocks of 16 by 16 to put into the array
				}
			}
		}else if(x==3) //Enemies
		{
			sprites = new BufferedImage[2];
			for (int i = 0; i < 1; i++)
			{
				for (int j = 0; j < 2; j++)
				{
					sprites[(i * 2) + j] = sprites3.getSubimage(j * 16, i * 16, 16, 16); //Separates sheet into blocks of 16 by 16 to put into the array
				}
			}
		}
		return sprites;
    }
	
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g.drawImage(sprit(2)[0], 0, r, null); //Draws the inital grass block
        for(int z = 1; z < frame.getHeight()/16; z++) //Draws the intial dirt blocks
        {
            g.drawImage(sprit(2)[1], 0, r+(z*16), null);
        }
        g2.setColor(new Color(255, 255, 255, 0)); // Sets the color of the rectangle to be completely transparent
        grassRect = new Rectangle(0, r, 14, 15); //Creates the inital bounds rectangle
        g2.draw(grassRect);
        grassRectangles.add(grassRect);
        for(int q = 1; q < frame.getWidth()/16; q++)//Loop the width in screen/16
        {
            g.drawImage(sprit(2)[0], q*16, randoms.get(q), null); //Grass block at a random height
            grassRect = new Rectangle(q*16, randoms.get(q), 14, 15); //Draws a bounds rectangle around that grass block
            g2.draw(grassRect);
            grassRectangles.add(grassRect); // Adds the bounds rectangle to the array
            for(int z = 1; z < frame.getHeight()/16; z++) //Draws dirt blocks under each grass block
            {
                /*TexturePaint tp = new TexturePaint(sprit(2)[1], //Test for gradient
                  new Rectangle(0, 0, 15, 16));
                g2.setPaint(tp); 
                g2.drawRect(q*16, randoms.get(q)+(z*16), 15, 16);
                g2.fillRect(q*16, randoms.get(q)+(z*16), 15, 16);*/
                g.drawImage(sprit(2)[1], q*16, randoms.get(q)+(z*16), null);
            }
        }
        getBoundries(); //Checks boundries
        if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) <= 12) //Clouds if in the day time
        {
            for(int c = 1; c < 10; c++)
            {
                g2.setColor(new Color(255, 255, 255, 100)); //Opaque milky white
                RoundRectangle2D rect = new RoundRectangle2D.Float(cloudsL.get(c).x,
                        cloudsL.get(c).y, cloudsD.get(c).width, cloudsD.get(c).height, 2, 2); // Creates the clouds with rounded edges and random locations/sizes
                g2.draw(rect);
                g2.fill(rect);
            }
        }else //If nighttime
        {
            for(int s = 1; s < 11; s++)
            {
                g2.setColor(new Color(255, 255, 255, 125)); //Opaque milky white
                Rectangle rect = new Rectangle(stars.get(s), new Dimension(3, 3)); //Stars
                g2.draw(rect);
                g2.fill(rect);
            }
        }
        for(int x = 1, y = 1; x < frame.getWidth()/16; x++, y++)
        {
            if(guy.getY()+31 > grassRectangles.get(x).getY() && wPressed
                && guy.getX() < (16*x) && guy.getX() > (16*x)-16 && altR && !altL)
            {
                wPressed = false;
                guy.setLocation(grassRectangles.get(x).x, grassRectangles.get(x).y-32);
            }
            if(guy.getY()+31 > grassRectangles.get(x-1).getY() && wPressed
                && guy.getX() < (16*(x)) && guy.getX() > (16*(x))-16 && altL && !altR)
            {
                wPressed = false;
                guy.setLocation(grassRectangles.get(x-1).x, grassRectangles.get(x-1).y-32);
            }
            if(guy.getY()+32 < grassRectangles.get(y).getY() && !wPressed
                && guy.getX() < (16*y) && guy.getX() > (16*y)-4 && altR && !altL)
            {
                guy.setLocation(grassRectangles.get(y).x, grassRectangles.get(y).y-32);
            }
            if(guy.getY()+32 < grassRectangles.get(y-1).getY() && !wPressed
                && guy.getX() > (16*(y-1)) && guy.getX() < (16*(y-1))+4 && altL && !altR)
            {
                guy.setLocation(grassRectangles.get(y-1).x, grassRectangles.get(y-1).y-32);
            }
        }
		if(guy.getX() < enemy.getX())
		{
			enemy.setLocation((int)enemy.getX()-rand.nextInt(4), enemy.getY());
		}else if(enemy.getX() < guy.getX())
		{
			enemy.setLocation((int)enemy.getX()+rand.nextInt(4), enemy.getY());
		}
    }

    public void getBoundries() //Creates boundaries for the player to move around. If the player is outside the boundaries, they are bounced back in.
    {
        if(guy.getX() > frame.getWidth()-15)
        {
            guy.setLocation(frame.getWidth()-19, guy.getY());
        }        
        if(guy.getX() < 0)
        {
           guy.setLocation(1, guy.getY());
        }       
        if(guy.getY() > frame.getHeight()-50)
        {
            guy.setLocation(guy.getX(), frame.getHeight()-1);
        }        
        if(guy.getY() < 0)
        {
            guy.setLocation(guy.getX(), r-32);
        }
    }
    
    public int getClosestRectangle()
    {
        int i = 0;
        for(int x = 0; x < frame.getWidth()/16; x++)
        {
            if(guy.getY()+32 < grassRectangles.get(x).getY() && guy.getX() >
                    (x*16) && guy.getX() > (16*x)-16 && guy.getBounds().intersects(grassRectangles.get(x).getBounds()))
            {
                 i = x;              
            }
        }
        return i;
    }
}
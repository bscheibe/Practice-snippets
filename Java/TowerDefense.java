import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class Model {

	private ArrayList<Enemy> enemies;
	private Node first;
	private MyInt score;
	private int flipper;
	private ArrayList<Tower> towers;
	private MyInt power;
	private int frequency;
	private MyInt health;

	public static void main(String[] args) {	

		Model model = new Model(5);

		View view = new View(model.enemies,model.first, 					model.score, model.towers, 						model.power);

		while (model.health.getValue() > 0) {
			model.tick();
			view.repaint();
			try {
				Thread.sleep(14);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		view.weLost();
		
		view.repaint();

	}



	public Model(int gameSize) {
		
		frequency = 100;
		first = new Node(0,0);	
		score = new MyInt(0);		
		power = new MyInt(320);
		enemies = new ArrayList<Enemy>();		
		towers = new ArrayList<Tower>();	
		health = new MyInt(3);
		
		Random rand = new Random();

		Node temp = first;		
		int tempx;	
		int tempy;
		for (int i = 0; i < 5; i++) {		
			tempx = 0;			
			tempy = 0;			
			while (tempx == 0 && 
				(tempy == 0 || tempy == 300)) {	
				tempx =	rand.nextInt(400); 					tempy = rand.nextInt(300); 
			}
			temp.addNext(new Node(tempx, tempy));
			temp = temp.getNext();
		}
		
//		for (int i = 0; i < 6; i++) 	
//			towers.add(new Tower(rand.nextInt(350),
//			rand.nextInt(250)));	
		
		flipper = 0;

	}

	void tick() {
		
		if (power.getValue() < 320)
			power.addValue(1);

		if (flipper%frequency == 0) 
			enemies.add(new Enemy(first));
		
		if (flipper%81 == 0) {
			for (Tower t : towers) {
				t.attack(enemies);
			}
		}
		
		if (flipper == 240) {
			if (frequency > 2)
				frequency = (int)(frequency * .7)+2;
			flipper = 0;
		}
		else {	
			flipper++;	
		}

		ArrayList<Enemy> removeThese = new ArrayList<Enemy>();
		
		for (Enemy e : enemies) {
			e.move();
			if (!e.isAlive()) {		
				if (e.deathTimer > 0)
					e.deathTimer--;
				else {					
					removeThese.add(e);
					score.addValue(1);
				}				
			}		
			if (e.isEscaped()) {		
				removeThese.add(e);			
				health.addValue(-1);
			}			
		}
		
		enemies.removeAll(removeThese);
		
	}

	public MyInt getScore() {
		return score;
	}

}



class Enemy{

	private int xloc;
	private int yloc;	
	private Node target;	
	private boolean escaped;
	public Color color;	
	private boolean isAlive;
	int deathTimer;	
	int attackx;	
	int attacky;	

	public boolean isEscaped() {
		return escaped;
	}

	public void escape() {
		escaped = true;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public Enemy(Node target) {

		xloc = 0;
		yloc = 0;	
		this.target = target;	
		escaped = false;	
		isAlive = true;	
		color = new Color(0,255,0);
				//(int)(Math.random()*100)+155,
				//(int)(Math.random()*100)+155,
				//(int)(Math.random()*100)+155);
	}

	public void move() {

		if (xloc > target.getXloc()) 
			xloc--;
		
		if (xloc < target.getXloc()) 
			xloc++;

		if (yloc > target.getYloc()) 
			yloc--;
		
		if (yloc < target.getYloc()) 
			yloc++;

		else if (xloc == target.getXloc() && 
				yloc == target.getYloc()) {
			if (target.hasNext) {
				target = target.getNext();
				return;
			}
			escaped = true;
		}
	}

	public void destroy(int twrx, int twry) {
		
		isAlive = false;	
		attackx = twrx;		
		attacky = twry;		
		deathTimer = 20;		
	}
	
	public int getXloc() {
		return xloc;
	}

	public int getYloc() {
		return yloc;
	}
	
}



class MyInt {
	
	private int value;
	
	public MyInt(int value) {
		this.value = value;
	}
	
	public void addValue(int i) {
		value += i;
	}
	
	public int getValue() {
		return value;
	}
	
}

class Tower {
	
	private int xloc;	
	private int yloc;
	
	public Tower(int x, int y) {
		xloc = x;
		yloc = y;
	}
	
	public void attack(ArrayList<Enemy> nems) {
		for (Enemy e : nems) {
			if (Math.pow((e.getXloc()-xloc)-5,2)+
				Math.pow((e.getYloc()-yloc)-5,2) < 1600) {
				e.destroy(xloc,yloc);
				return;
			}
		}
	}
	
	public int getXloc() {
		return xloc;
	}
	
	public int getYloc() {
		return yloc;
	}
	
}

class Node {

	private int xloc;
	private int yloc;
	private Node next;
	boolean hasNext;

	public Node(int xloc, int yloc) {
		this.xloc = xloc;
		this.yloc = yloc;
		hasNext = false;
	}

	public int getXloc() {
		return xloc;
	}

	public int getYloc() {
		return yloc;
	}

	

	public Node getNext() {
		return next;
	}

	

	public void addNext(Node n) {
		next = n;
		hasNext = true;
	}

}



class View extends JFrame  implements MouseListener{

	private static final long serialVersionUID = 1L;
	private ArrayList<Enemy> enemies;
	private Node first;
	GamePanel panel;
	MyInt power;
	ArrayList<Tower> towers;
	boolean isGameOver;

	

	public View(ArrayList<Enemy> enemies, Node first, MyInt score, 				ArrayList<Tower> towers, MyInt power) {

		this.towers = towers;	
		isGameOver = false;
		this.enemies = enemies;
		this.first = first;
		this.power = power;
		
		panel = new GamePanel();
		getContentPane().add(panel);
		
		panel.enemies = enemies;
		panel.first = first;	
		panel.score = score;	
		panel.power = power;		
		panel.towers = towers;	
		panel.setBackground(Color.black);

		setBackground(Color.black);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,300);

	    panel.setPreferredSize(new Dimension(411,311));
	    panel.setOpaque(true);
	    
	    pack();  
	    addMouseListener(this);
	    setVisible(true);

	}

	

	public void weLost() {
		isGameOver = true;
		panel.isGameOver = true;
	}



	void paintComponent(Graphics g) {
		super.paintComponents(g);
	}

	

	public void redraw() {
	}



	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}



	public Node getFirst() {
		return first;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("test");
		if (power.getValue() == 320) {
			towers.add(new Tower(e.getX()-15,e.getY()-35));
			power.addValue(-320);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {	
	}

}



class GamePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	ArrayList<Enemy> enemies;	
	ArrayList<Tower> towers;
	Node first;	
	MyInt score;	
	MyInt power;
	boolean isGameOver;	

	public void paintComponent(Graphics g) {
	
		super.paintComponent(g);
		
		if (isGameOver) {
			g.setFont(new Font("Verdana", Font.BOLD, 35));
			g.setColor(Color.WHITE);
			g.drawString("GAME OVER!", 19, 161);
			g.setColor(Color.RED);
			g.drawString("GAME OVER!", 20, 160);
		}
		
		g.setFont(new Font("Verdana", Font.BOLD, 15));	
		g.setColor(Color.LIGHT_GRAY);		
		g.drawString(""+score.getValue(), 380, 310);

		for (Enemy e : enemies) {			
			g.setColor(e.color);
			g.drawRect(e.getXloc(), e.getYloc(), 10, 10);	
			if (!e.isAlive()) {
				g.setColor(Color.CYAN);
				g.drawLine(e.getXloc()+5, e.getYloc()+5, 					e.attackx+5, e.attacky+5);
			}
		}
		
		for (Tower t : towers) {		
			g.setColor(Color.YELLOW);			
			g.drawRect(t.getXloc(), t.getYloc(), 10, 10);
			g.setColor(Color.RED);			
			g.drawOval(t.getXloc()-35, t.getYloc()-35, 80, 80);
		}
		
		Node temp = first;
		g.setColor(Color.blue);
		
		while (temp.hasNext) {
			g.drawOval(temp.getXloc(), temp.getYloc(), 4, 4);
			g.drawLine(temp.getXloc()+2, temp.getYloc()+2,
					temp.getNext().getXloc()+2, 						temp.getNext().getYloc()+2);
					temp = temp.getNext();
		}
		
		g.drawOval(temp.getXloc(), temp.getYloc(), 4, 4);
		g.setColor(Color.LIGHT_GRAY);		
		int temppower = power.getValue();		
		g.drawString("Power: ", 0, 310);
		
		if (temppower%80 > 40) {
			g.setColor(Color.RED);
			g.drawString("CHARGING", 60, 310);
		}
		
		if (temppower == 320) {
			g.setColor(Color.GREEN);
			g.drawString("CHARGED", 60, 310);
		}
		
		g.setColor(Color.LIGHT_GRAY);		
		g.drawRect(1, 290, 320, 4);		
		g.setColor(Color.pink);
		
		if (temppower == 320)
			g.setColor(Color.green);
		
		g.drawRect(1, 290, temppower, 4);

	}
	
}



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
	
	private boolean play = false;
	private int score=0;
	
	private Timer timer;
	private int speed= 8 ;//ball speed#########################################################################################important
	private int playerX=310;
	private int ballposX=120;
	private int ballposY=350;
	private int ballXdir=-1;
	private int ballYdir=-2;
	
	private int row=20;//---------------------------set width
	private int collum=20;//---------------------------set length
	private int setnum=row*collum;
	private int Bricks=setnum;
	private MapGenerator map;
	
	public Gameplay(){
		map = new MapGenerator(row,collum);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(speed,this);
		timer.start();
		
	}
	
	public void paint(Graphics g){
		//background
		g.setColor(Color.black);
		g.fillRect(1,1,692,592);
		//draw map
		map.draw((Graphics2D)g);
		
		
		//labels
		//g.setColor(Color.black);
	//	g.setFont(new Font("serif",Font.BOLD, 25));
		//g.drawString("goal",310,560);
		
		//g.setColor(Color.red);
		//g.fillRect(310,560,200,18);
		//g.fillRect(310,10,69,30);
		
		
		//borders
		g.setColor(Color.yellow);
		g.fillRect(0,0,3,592);
		g.fillRect(0,0,692,3);
		g.fillRect(691,0,3,592);
		g.fillRect(66,611,50,50);
		//scores
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD, 25));
		g.drawString(""+score, 590, 30);
		//paddle
		g.setColor(Color.green);
		g.fillRect(playerX,550,100,8);
		//ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX,ballposY,20,20);
		
		if(Bricks<=0){
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.YELLOW);
			g.setFont(new Font("serif",Font.BOLD, 30));
			g.drawString("You Won"+" "+ score, 260, 300);
			
			g.setFont(new Font("serif",Font.BOLD, 20));
			g.drawString("Press the Spacebar to restart", 230, 350);
}
		
		if(ballposY>570){//-------------------add xpos in goal   
			play=false;
					ballXdir=0;
					ballYdir=0;
					g.setColor(Color.YELLOW);
					g.setFont(new Font("serif",Font.BOLD, 30));
					g.drawString("Game over, Score:"+" "+ score, 190, 300);
					
					g.setFont(new Font("serif",Font.BOLD, 20));
					g.drawString("Press the Spacebar to restart", 230, 350);
		}
		
		g.dispose();
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
	timer.start();
	if(play){
		if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))){
			ballYdir= -ballYdir;
			
			
		}
		
	A:	for(int i=0;i<map.map.length;i++){
			for(int j=0;j<map.map[0].length;j++){
				if(map.map[i][j]>0){
					int brickX=j*map.brickWidth+80;
					int brickY=i*map.brickHeight+50;
					int brickWidth=map.brickWidth;
					int brickHeight=map.brickHeight;
					
					Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
					Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
					Rectangle brickRect = rect ;
					
					if(ballRect.intersects(brickRect)){
						map.setBrickValue(0, i, j);
						Bricks--;
						score+=5;
						
						if(ballposX+19<=brickRect.x || ballposX +1 >= brickRect.x +brickRect.width){
							ballXdir= -ballXdir;
						}else{
							ballYdir= -ballYdir;
						}
						break A;
						
					}
					
				}
				
				
			}
				
		}
		
		ballposX+=ballXdir;
		ballposY+=ballYdir;
		
		if(ballposX<0){
			ballXdir = -ballXdir;
		}
		if(ballposY<0){
			ballYdir = -ballYdir;
		}
		
		if(ballposX>670){
			ballXdir = -ballXdir;
		}
		
	}
	
	repaint();
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
	

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			if(playerX >=600){
				playerX=600;
			}
			else{
				moveRight();
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			
			if(playerX <10){
				playerX=10;
			}
			else{
				moveLeft();
			}
			
		}
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			if(!play){
				play=true;
				ballposX=120;
				ballposY=350;
				ballXdir=-1;
				ballYdir=-2;
				playerX=310;
				score=0;
				Bricks=setnum;
				map = new MapGenerator(3,7);
				repaint();
			}
		}
		
	}
	public void moveRight(){
			play=true;
			playerX+=20;
		}
	public void moveLeft(){
		play=true;
		playerX-=20;
	}
	
	

}

package FoodEat;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class FoodEat2 extends JFrame{
	
	private Image bufferImage;
	private Graphics screenGraphic;
	
	private Clip clip; 
	
	private Image backgroundImage = new ImageIcon(FoodEat2.class.getResource("../images2/BS.jpg")).getImage();
	private Image JMS = new ImageIcon(FoodEat2.class.getResource("../images2/stone.jpg")).getImage();
	private Image CH = new ImageIcon(FoodEat2.class.getResource("../images2/CH2.jpg")).getImage();
	
	
	private int JMSY,JMSX;
	private int JMSWidth =  JMS.getWidth(null)/3;
	private int JMSHeight = JMS.getHeight(null)/3;
	
	private int CHX, CHY;
	private int CHWidth =  CH.getWidth(null);
	private int CHHeight = CH.getHeight(null);
	
	private int score;
	
	private boolean up, down, left, right;
	
	public FoodEat2() {
		
		setTitle("민석이 음식 먹기 게임");
		setVisible(true);
		setSize(600,600);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				
				case KeyEvent.VK_W:			
					up= true;
					break;
				case KeyEvent.VK_S:			
					down= true;
					break;
				case KeyEvent.VK_A:			
					left= true;
					break;
				case KeyEvent.VK_D:			
					right = true;
					break;				
				}
			}
			
			public void keyReleased(KeyEvent e) {
				switch(e.getKeyCode()) {
				
				case KeyEvent.VK_W:			
					up= false;
					break;
				case KeyEvent.VK_S:			
					down= false;
					break;
				case KeyEvent.VK_A:			
					left= false;
					break;
				case KeyEvent.VK_D:			
					right = false;
					break;				
				}
			}		
		});
		Init();
		while(true) {
			
			try {
				Thread.sleep(20);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			KeyProcess();
			crashCheck();
		}
		
	}
	
	public void Init() {
		score = 0;		
		JMSX = (500 - JMSWidth)/2;
		JMSY = (500 - JMSHeight)/2;
		
		CHX = (int)(Math.random()*470)+30;
		CHY = 30 + (int)(Math.random()*470);	// 점수 초기화, 플레이어와 코인 위치 설정
		System.out.println(CHX+"/"+CHY);
				
	}
	
	public void KeyProcess() {
		if(up && JMSY - 3> 30 ) JMSY-=3;
		if(down && JMSY + JMSHeight + 3 < 500 ) JMSY+=3;
		if(left && JMSX - 3>0) JMSX-=3;
		if(right && JMSX + JMSWidth + 3< 500 ) JMSX+=3;
	}
	
	public void crashCheck() {
		if(JMSX + JMSWidth >= CHX && CHX + CHWidth >= JMSX && JMSY + JMSHeight >= CHY && CHY + CHHeight >= JMSY) {
			score+=100;
			CHX = (int)(Math.random()*470)+30;
			CHY = 30 + (int)(Math.random()*470);
		}
			
	}
	
	
	public void playSound(String pathName, boolean isLoop) {
		try {
			clip = AudioSystem.getClip();
			File audioFile = new File(pathName);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip.open(audioStream);
			clip.start();
			if(isLoop)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			
		} catch(LineUnavailableException e) {
			e.printStackTrace();
		} catch(UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
			
		
	}
	
	
	
	public void paint(Graphics g) {
		bufferImage = createImage(600,600);
		screenGraphic = bufferImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(bufferImage, 0, 0, null);
	}
	

	public void screenDraw(Graphics g) {
		
		g.drawImage(backgroundImage, 0,0, null);
		g.drawImage(JMS, JMSX,JMSY, null);
		g.drawImage(CH, CHX,CHY, null);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Korea", Font.BOLD, 30));
		g.drawString("닭다리 수 : "+score, 30,80);
		this.repaint();
		
	}
	
	

	

	public static void main(String[] args) {
		
		new FoodEat2();
		
	}

}

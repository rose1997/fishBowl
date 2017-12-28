/*
 作業六
 學號：104403511
 系級：資管三A
 姓名：郭源芯
 */
import java.awt.Image;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class Turtle extends JLabel implements Runnable{
	private int frmW, frmH, x, y, r1, lr, lrS, lrR;
	private Random rand = new Random();
	private int size=rand.nextInt(50)+50;
	private ImageIcon[][] imgIcon= {{new ImageIcon("w2.png")},
			{new ImageIcon("w.png")}};
	private Image[][] scaledImgIcon= {{imgIcon[0][0].getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT)},
			{imgIcon[1][0].getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT)}};
	private ImageIcon[][] newIcon= {{new ImageIcon(scaledImgIcon[0][0])},
			{new ImageIcon(scaledImgIcon[1][0])}};
	private boolean dirflag =true;
	private boolean dropflag =true;
	private Timer t1; 
	public Turtle(int x, int y,int frmH, int frmW) {
		this.frmH=frmH;
		this.frmW=frmW;
		this.x=x;
		this.y=y;
		lr=rand.nextInt(2);
		lrR=rand.nextInt(500)+50;
		lrS=rand.nextInt(10)+5;
		if(lr == 1) {
			this.dirflag=false;
		}
		this.setIcon(newIcon[lr][r1]);
		this.setBounds(x,y,this.getIcon().getIconWidth() ,this.getIcon().getIconHeight());		
	}
	@Override
	public void run() {
		t1=new Timer(rand.nextInt(500)+50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Turtle.this.dropflag) {  //let turtle drop to the bottom
					if((y+Turtle.this.getIcon().getIconHeight()+60)<frmH) {
						y+=10;
						Turtle.this.setLocation(x,y);
					}else {
						Turtle.this.dropflag=!Turtle.this.dropflag;
					}
				}else { 
					if(Turtle.this.dirflag) {  //make it move left
						if((x-10)<0 || x==lrR) {
							Turtle.this.dirflag=!Turtle.this.dirflag;
							lr=1;
							Turtle.this.setIcon(newIcon[lr][r1]);
							x-=lrS;
							lrS=rand.nextInt(10)+5;
							lrR=rand.nextInt(500)+50;
						}
						x-=lrS;
						Turtle.this.setLocation(x,y);
					}else if(!Turtle.this.dirflag){  //make it move right
						if((x+Turtle.this.getIcon().getIconWidth()+10)>frmW || x==lrR) {
							Turtle.this.dirflag=true;
							lr=0;
							Turtle.this.setIcon(newIcon[lr][r1]);
							x-=lrS;
							lrS=rand.nextInt(10)+5;
							lrR=rand.nextInt(500)+50;
						}
						x+=lrS;
						Turtle.this.setLocation(x,y);
					}					
				}
			}
		});
		t1.start();
	}
}

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

public class Fish extends JLabel implements Runnable{
	private int frmW, frmH, x, y, r1, lr, ud, lrS, udS, lrR, udR;
	private Random rand = new Random();
	private int size=rand.nextInt(50)+50;
	private ImageIcon[][] imgIcon= {{new ImageIcon("2.png"), new ImageIcon("4.png"),new ImageIcon("6.png")},
			{new ImageIcon("1.png"), new ImageIcon("3.png"), new ImageIcon("5.png")}};
	private Image[][] scaledImgIcon= {{imgIcon[0][0].getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT),
		imgIcon[0][1].getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT),
		imgIcon[0][2].getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT)},
			{imgIcon[1][0].getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT),
				imgIcon[1][1].getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT),
				imgIcon[1][2].getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT)}};
	private ImageIcon[][] newIcon= {{new ImageIcon(scaledImgIcon[0][0]),new ImageIcon(scaledImgIcon[0][1]),new ImageIcon(scaledImgIcon[0][2])},
			{new ImageIcon(scaledImgIcon[1][0]),new ImageIcon(scaledImgIcon[1][1]),new ImageIcon(scaledImgIcon[1][2])}};
	private boolean dirflag =true;
	private boolean udflag = true;
	private Timer t1; 

	public Fish(int x, int y,int frmH, int frmW) {
		this.frmH=frmH;
		this.frmW=frmW;
		this.x=x;
		this.y=y;
		lr=rand.nextInt(2);
		ud=rand.nextInt(2);
		lrR=rand.nextInt(500)+50;
		udR=rand.nextInt(500)+50;
		udS=rand.nextInt(10)+5;
		lrS=rand.nextInt(10)+5;
		if(lr == 1) {
			this.dirflag=false;
		}
		if(ud == 1) {
			this.udflag=false;
		}
		this.setIcon(newIcon[lr][r1=rand.nextInt(3)]);
		this.setBounds(x,y,this.getIcon().getIconWidth() ,this.getIcon().getIconHeight());
	}
	@Override
	public void run() {
		t1=new Timer(rand.nextInt(500)+50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Fish.this.dirflag && Fish.this.udflag) { //left and up
					if((x-10)<0 || x==lrR) {
						Fish.this.dirflag=!Fish.this.dirflag;
						lr=1;
						Fish.this.setIcon(newIcon[lr][r1]);
						y-=udS;
						x-=lrS;
						lrS=rand.nextInt(10)+5;
						lrR=rand.nextInt(500)+50;
					}
					if((y-20)<0 || y==udR) {
						Fish.this.udflag=!Fish.this.udflag;
						y+=udS;
						x+=lrS;
						udS=rand.nextInt(10)+5;
						udR=rand.nextInt(500)+50;
					}
					x-=lrS;
					y-=udS;
					Fish.this.setLocation(x,y);
				}else if(!Fish.this.dirflag && Fish.this.udflag){ //right and up
					if((x+Fish.this.getIcon().getIconWidth()+10)>frmW || x==lrR) {
						Fish.this.dirflag=!Fish.this.dirflag;
						lr=0;
						Fish.this.setIcon(newIcon[lr][r1]);
						x-=lrS;
						y-=udS;
						lrS=rand.nextInt(10)+5;
						lrR=rand.nextInt(500)+50;
					}
					if((y-20)<0 || y==udR) {
						Fish.this.udflag=!Fish.this.udflag;
						y+=udS;
						x+=lrS;
						udS=rand.nextInt(10)+5;
						udR=rand.nextInt(500)+50;
					}
					x+=lrS;
					y-=udS;
					Fish.this.setLocation(x,y);
				}else if(!Fish.this.dirflag && !Fish.this.udflag){ //right and down
					if((x+Fish.this.getIcon().getIconWidth()+10)>frmW || x==lrR) {
						Fish.this.dirflag=!Fish.this.dirflag;
						lr=0;
						Fish.this.setIcon(newIcon[lr][r1]);
						x-=lrS;
						y+=udS;
						lrS=rand.nextInt(10)+5;
						lrR=rand.nextInt(500)+50;
					}
					if((y+Fish.this.getIcon().getIconHeight()+80)>frmH || y==udR) {
						Fish.this.udflag=!Fish.this.udflag;
						y-=udS;
						x+=lrS;
						udS=rand.nextInt(10)+5;
						udR=rand.nextInt(500)+50;
					}
					x+=lrS;
					y+=udS;
					Fish.this.setLocation(x,y);
				}else if(Fish.this.dirflag && !Fish.this.udflag) { //left and down
					if((x-10)<0 || x==lrR) {
						Fish.this.dirflag=!Fish.this.dirflag;
						lr=1;
						Fish.this.setIcon(newIcon[lr][r1]);
						y+=udS;
						x+=lrS;
						lrS=rand.nextInt(10)+5;
						lrR=rand.nextInt(500)+50;
					}
					if((y+Fish.this.getIcon().getIconHeight()+80)>frmH || y==udR) {
						Fish.this.udflag=!Fish.this.udflag;
						y-=udS;
						x-=lrS;
						udS=rand.nextInt(10)+5;
						udR=rand.nextInt(500)+50;
					}
					x-=lrS;
					y+=udS;
					Fish.this.setLocation(x,y);
				}
			}
		});
		t1.start();
	}
}

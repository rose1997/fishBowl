/*
 作業六
 學號：104403511
 系級：資管三A
 姓名：郭源芯
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class fishBowl extends JFrame{
	private Container bowl;
	private ImagePanel bk = new ImagePanel();
	private JPanel toolPanel = new JPanel(new GridLayout(3,2,5,5));
	private JButton addFish = new JButton("新增魚");
	private JButton addTurtle = new JButton("新增烏龜");
	private JButton rmS = new JButton("移除選取");
	private JButton rmA = new JButton("移除全部");
	private JPanel statusBar = new JPanel(new BorderLayout(2,2));
	private JLabel CurrentStatus = new JLabel("目前狀態：無");
	private JLabel count = new JLabel("魚數量：0  烏龜數量：0");
	private int imgW, imgH;
	private int x, y;
	private ArrayList <Fish> fishList = new ArrayList<Fish>();
	private ArrayList <Turtle> TurtleList = new ArrayList<Turtle>();
	private ArrayList <Thread> threadList = new ArrayList<Thread>();
	private boolean addFishFlag = false;
	private boolean addTurtleFlag = false;
	private boolean selectFlag = false;
	
	public fishBowl() {
		imgW = bk.getImgWidth();
		imgH = bk.getImgHeight();
		this.setBounds(300,100,imgW,imgH+60);
		this.setResizable(false);
		bk.setLayout(null);
		toolPanel.add(addFish);
		toolPanel.add(rmS);
		toolPanel.add(addTurtle);
		toolPanel.add(rmA);
		
		addFish.addActionListener(new ActionListener() {  //click the button of adding a fish
			@Override
			public void actionPerformed(ActionEvent e) {
				addFishFlag = true;
				addTurtleFlag = false;
				selectFlag = false;
				CurrentStatus.setText("目前狀態：新增魚");
			}
		});
		addTurtle.addActionListener(new ActionListener() { //click the button of adding a turtle
			@Override
			public void actionPerformed(ActionEvent e) {
				addTurtleFlag = true;
				addFishFlag = false;
				selectFlag = false;
				CurrentStatus.setText("目前狀態：新增烏龜");
			}
		});
		
		rmA.addActionListener(new ActionListener() { //click the button of removing all the items
			@Override
			public void actionPerformed(ActionEvent e) {
				addFishFlag = false;
				addTurtleFlag = false;
				selectFlag = false;
				CurrentStatus.setText("目前狀態：空的魚缸");
				fishList.clear();
				TurtleList.clear();
				count.setText("魚數量："+fishList.size()+"  烏龜數量："+TurtleList.size());
				bk.removeAll();
				bk.repaint();
			}
		});
		
		rmS.addActionListener(new ActionListener() { //click the button of removing the selected one
			@Override
			public void actionPerformed(ActionEvent e) {
				CurrentStatus.setText("目前狀態：選擇消除目標");
				selectFlag=true;
				addFishFlag = false;
				addTurtleFlag = false;
			}
		});
		
		MouseHandler handler = new MouseHandler();
		bk.addMouseListener(handler);
		bk.addMouseMotionListener(handler);
		
		statusBar.add(CurrentStatus,BorderLayout.WEST);
		statusBar.add(count,BorderLayout.EAST);
		toolPanel.add(statusBar);
		bowl=this.getContentPane();
		bowl.setLayout(new BorderLayout(3,3));
		bowl.add(bk, BorderLayout.CENTER);
		bowl.add(toolPanel, BorderLayout.NORTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		fishBowl newBowl = new fishBowl();
		newBowl.setVisible(true);
	}
	
	private class MouseHandler extends MouseAdapter implements MouseListener, MouseMotionListener 
	{
		// handle event when user drags mouse with button clicked
		@Override
		public void mouseDragged(MouseEvent event)
		{}
		@Override
		public void mouseEntered(MouseEvent event)
		{}
		@Override
		public void mouseExited(MouseEvent event)
		{}
	    @Override
	    public void mouseClicked(MouseEvent event)
	    {
	    	if(addFishFlag) {
	    		x= event.getX();  //get x
	    		y= event.getY(); // get y
				fishList.add(new Fish(x, y, imgH, imgW)); 
				bk.add(fishList.get(fishList.size()-1)).addMouseListener(new Handler());
				threadList.add(new Thread(fishList.get(fishList.size()-1)));
				threadList.get(threadList.size()-1).start();
				count.setText("魚數量："+fishList.size()+"  烏龜數量："+TurtleList.size());
	    	}else if (addTurtleFlag) {
	    		x= event.getX();
	    		y= event.getY();
				TurtleList.add(new Turtle(x, y, imgH, imgW));
				bk.add(TurtleList.get(TurtleList.size()-1)).addMouseListener(new Handler());
				threadList.add(new Thread(TurtleList.get(TurtleList.size()-1)));
				threadList.get(threadList.size()-1).start();
				count.setText("魚數量："+fishList.size()+"  烏龜數量："+TurtleList.size());
	    	}
	    }   
	    @Override
	    public void mousePressed(MouseEvent event)
	    {} 
	    @Override
	    public void mouseReleased(MouseEvent event)
	    {}
	    @Override
	    public void mouseMoved(MouseEvent event)
	    {} 
	}
	
	private class Handler extends MouseAdapter{  //to get and delete the one you clicked on
		@Override
	    public void mouseClicked(MouseEvent event)
	    {
			if(selectFlag) {
	    		JLabel getItem = (JLabel)event.getSource();
	    		if(event.getSource() instanceof Fish) {
	    			fishList.remove(event.getSource());
	    			count.setText("魚數量："+fishList.size()+"  烏龜數量："+TurtleList.size());
	    		}else if(event.getSource() instanceof Turtle) {
	    			TurtleList.remove(event.getSource());
	    			count.setText("魚數量："+fishList.size()+"  烏龜數量："+TurtleList.size());
	    		}
	    		bk.remove(getItem);
	    		bk.repaint();
	    	}
	    }
	}
}
class ImagePanel extends JPanel{  //to change the background image
	private BufferedImage image;
	private int imgW,imgH;
	public ImagePanel() {
		try {
			image=ImageIO.read(new File("s076924362-300.jpg"));
			imgW=image.getWidth();
			imgH=image.getHeight();
		}catch(IOException ex) {
			System.out.println("IOException: "+ex.toString());
		}
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image,0,0,null);
	}
	public int getImgWidth() {
		return imgW;
	}
	public int getImgHeight() {
		return imgH;
	}	
}

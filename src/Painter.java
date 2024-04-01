	import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Painter extends JPanel implements MouseMotionListener,MouseListener{
	private Point firstPoint;
	private Point lastPoint;
	//private JPanel canvas = new JPanel();
	private ArrayList<LinePoints> line = new ArrayList<>();
	private ArrayList<RectPoints> rect = new ArrayList<>();
	private ArrayList<PenPoints> pen = new ArrayList<>();
	private ArrayList<OvalPoints> oval = new ArrayList<>();
	//private ArrayList<Object> buffer = new ArrayList<>();
	
	Painter(){
		firstPoint = new Point();
		lastPoint = new Point();
		super.setBounds(0,50,900,495);
		super.setBackground(Color.WHITE);
		addMouseMotionListener(this);
		addMouseListener(this);
		repaint();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		//Graphics g = Painter.this.getGraphics();
;		if(e.getX() <= 900 & e.getY() <= 495) {
			if(e.getX() > 0 & e.getY() > 0) {
				MyJFrame.statusLabel.setText(String.format("滑鼠指標 :(%d,%d)",e.getX(),e.getY()));
			}else {
				MyJFrame.statusLabel.setText("");
			}
		}else {
			MyJFrame.statusLabel.setText("");
		}
		
		switch(MyJFrame.currentTool) {
			case "直線" :
				lastPoint = e.getPoint();
				
				
				break;
			case "筆刷" :
				lastPoint = e.getPoint();
				pen.add(new PenPoints(e.getPoint(),MyJFrame.paintsize,MyJFrame.foreGround));
				//repaint();
				break;
			case "橢圓形" :
				lastPoint = e.getPoint();
				//oval.add(new OvalPoints(firstPoint,e.getPoint(),new BasicStroke(MyJFrame.paintsize),MyJFrame.foreGround,MyJFrame.backGround,MyJFrame.fillcheck));
				//repaint();
				break;
			case "矩形" :
				lastPoint = e.getPoint();
				//repaint();
				break;
		}
		//penPoints.add(lastPoint);
		repaint();
		//System.out.println("test");
		//e.consume();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		MyJFrame.statusLabel.setText(String.format("滑鼠指標 :(%d,%d)",e.getX(),e.getY()));
	}
	
	
	public void mousePressed(MouseEvent e) {
		firstPoint = e.getPoint();
		//Graphics g = super.getGraphics();
	}
	
	public void recover() {
		switch(MyJFrame.currentTool) {
			case "直線" :
				if(!line.isEmpty()) {
					line.remove(line.size() - 1);
					//repaint();
				}
				repaint();
				
				break;
			case "筆刷" :
				if(!pen.isEmpty()) {
					pen.remove(pen.size() - 1);
					//repaint();
				}
				repaint();
				
				break;
			case "橢圓形" :
				if(!oval.isEmpty()) {
					oval.remove(oval.size() - 1);
					//repaint();
				}
				repaint();
				
				break;
			case "矩形" :
				if(!rect.isEmpty()) {
					rect.remove(rect.size() - 1);
					//repaint();
				}
				repaint();
				break;
		}
		//repaint();
	}
	
	public void clearCanvas() {
		line.clear();
		pen.clear();
		oval.clear();
		rect.clear();
		firstPoint = new Point();
		lastPoint = new Point();
		
		repaint();
		MyJFrame.clear = false;
	}
	
	public void paintComponent(Graphics g) {
		
		//g = Painter.this.getGraphics();
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		for(PenPoints points : pen) {
			g2.setStroke(new BasicStroke(points.size));
			g2.setColor(points.fore);
			g2.drawLine(points.x,points.y,points.x,points.y);
		}
		for(LinePoints lines : line) {
			if(MyJFrame.fillcheck) {
			g2.setStroke(lines.size);
			g2.setColor(lines.foreground);
			g2.drawLine(lines.firstPoint.x,lines.firstPoint.y,lines.lastPoint.x,lines.lastPoint.y);
			}else {
				g2.setStroke(lines.size);
				g2.setColor(lines.foreground);
				g2.drawLine(lines.firstPoint.x,lines.firstPoint.y,lines.lastPoint.x,lines.lastPoint.y);
			}
		
		}
		if(MyJFrame.currentTool == "直線" & MyJFrame.fillcheck == true) {
			g2.setStroke(new BasicStroke(MyJFrame.paintsize));
			g2.setColor(MyJFrame.foreGround);
			g2.drawLine(firstPoint.x,firstPoint.y,lastPoint.x,lastPoint.y);
		}else if(MyJFrame.currentTool == "直線") {
			g2.setStroke(new BasicStroke(MyJFrame.paintsize,BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, new float[]{9}, 0));
			g2.setColor(MyJFrame.foreGround);
			g2.drawLine(firstPoint.x,firstPoint.y,lastPoint.x,lastPoint.y);
		}
		for(OvalPoints o : oval) {
			g2.setStroke(o.size);
			g2.setColor(o.fore);
			
			if(o.fillcheck == true) {
				g2.setColor(o.back);
				g2.fillOval(o.firstPoint.x,o.firstPoint.y,o.width,o.height);
				g2.setColor(o.fore);
				g2.drawOval(o.firstPoint.x,o.firstPoint.y,o.width,o.height);
			}else{
				g2.drawOval(o.firstPoint.x,o.firstPoint.y,o.width,o.height);
			}
		}if(MyJFrame.currentTool == "橢圓形") {
			if(MyJFrame.fillcheck) {
				g2.setColor(MyJFrame.backGround);
				g2.fillOval(Math.min(firstPoint.x,lastPoint.x),Math.min(firstPoint.y,lastPoint.y),Math.abs(firstPoint.x - lastPoint.x),Math.abs(firstPoint.y - lastPoint.y));
				g2.setColor(MyJFrame.foreGround);
				g2.drawOval(Math.min(firstPoint.x,lastPoint.x),Math.min(firstPoint.y,lastPoint.y),Math.abs(firstPoint.x - lastPoint.x),Math.abs(firstPoint.y - lastPoint.y));
			}else {
				g2.drawOval(Math.min(firstPoint.x,lastPoint.x),Math.min(firstPoint.y,lastPoint.y),Math.abs(firstPoint.x - lastPoint.x),Math.abs(firstPoint.y - lastPoint.y));
			}
		}
		for(RectPoints r : rect) {
			g2.setStroke(r.size);
			g2.setColor(r.fore);
			if(r.fillcheck == true) {
				g2.setColor(r.back);
				g2.fillRect(r.firstPoint.x,r.firstPoint.y,r.width,r.height);
				g2.setColor(r.fore);
				g2.drawRect(r.firstPoint.x,r.firstPoint.y,r.width,r.height);
			}else{
				g2.drawRect(r.firstPoint.x,r.firstPoint.y,r.width,r.height);
			}
		}
		if(MyJFrame.currentTool == "矩形") {
			if(MyJFrame.fillcheck) {
				g2.setColor(MyJFrame.backGround);
				g2.fillRect(Math.min(firstPoint.x,lastPoint.x),Math.min(firstPoint.y,lastPoint.y),Math.abs(firstPoint.x - lastPoint.x),Math.abs(firstPoint.y - lastPoint.y));
				g2.setColor(MyJFrame.foreGround);
				g2.drawRect(Math.min(firstPoint.x,lastPoint.x),Math.min(firstPoint.y,lastPoint.y),Math.abs(firstPoint.x - lastPoint.x),Math.abs(firstPoint.y - lastPoint.y));
			}else {
				g2.drawRect(Math.min(firstPoint.x,lastPoint.x),Math.min(firstPoint.y,lastPoint.y),Math.abs(firstPoint.x - lastPoint.x),Math.abs(firstPoint.y - lastPoint.y));
			}
		}
		
	}

	public void mouseReleased(MouseEvent e) {
		
		switch(MyJFrame.currentTool) {
		case "直線" :
			if(MyJFrame.fillcheck) {
				line.add(new LinePoints(firstPoint,e.getPoint(),new BasicStroke(MyJFrame.paintsize),MyJFrame.foreGround));
			}else {
				line.add(new LinePoints(firstPoint,e.getPoint(),new BasicStroke(MyJFrame.paintsize,BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0),MyJFrame.foreGround));
				
			}
			repaint();
			break;
		case "筆刷" :
			
			break;
		case "橢圓形" :
			oval.add(new OvalPoints(firstPoint,e.getPoint(),new BasicStroke(MyJFrame.paintsize),MyJFrame.foreGround,MyJFrame.backGround,MyJFrame.fillcheck));
			
			repaint();
			break;
		case "矩形" :
			rect.add(new RectPoints(firstPoint,e.getPoint(),new BasicStroke(MyJFrame.paintsize),MyJFrame.foreGround,MyJFrame.backGround,MyJFrame.fillcheck));
			
			repaint();
			break;
		}
		//repaint();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("滑鼠走了");
		MyJFrame.statusLabel.setText(String.format("滑鼠指標 :(%d,%d)",0,0));
	}
	
	public class PenPoints extends Point{	//筆刷繪圖
		public int size;
		public Color fore;
		PenPoints(Point p,int size,Color c){
			super(p);
			this.size = size;
			fore = c;
		}
	}
	public class LinePoints{	//直線
		public Color foreground;
		public BasicStroke size;
		public Point firstPoint;
		public Point lastPoint;
		LinePoints(Point f,Point l,BasicStroke size,Color fore){
			firstPoint = f;
			lastPoint = l;
			this.size = size;
			foreground = fore;
		}
	}
	
	public class OvalPoints {
		public Color back;
		public Color fore;
		public BasicStroke size;
		public boolean fillcheck;
		public int width,height;
		public Point firstPoint;
		public Point lastPoint;
		OvalPoints(Point f,Point l,BasicStroke size,Color fore,Color back,boolean fillcheck){
			this.back = back;
			this.fore = fore;
			this.size = size;
			this.fillcheck = fillcheck;
			firstPoint = f;
			lastPoint = l;
			this.width = Math.abs(l.x - f.x);
			this.height = Math.abs(l.y - f.y);
			
			if(l.x < f.x) {
				this.firstPoint.x = l.x;
			}
			if(l.y < f.y) {
				this.firstPoint.y = l.y; 
			}
		
		}
	}
	public class RectPoints{
		public Color back;
		public Color fore;
		public BasicStroke size;
		public boolean fillcheck;
		public int width,height;
		public Point firstPoint;
		public Point lastPoint;
		RectPoints(Point f,Point l,BasicStroke size,Color fore,Color back,boolean fillcheck){
			firstPoint = f;
			lastPoint = l;
			this.back = back;
			this.fore = fore;
			this.fillcheck = fillcheck;
			this.size = size;
			this.width = Math.abs(l.x - f.x);
			this.height = Math.abs(l.y - f.y);
			if(l.x < f.x) {
				this.firstPoint.x = l.x;
			}if(l.y < f.y) {
				this.firstPoint.y = l.y;
			}
			
		}
		
	}
	
}

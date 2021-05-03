package me.hobrin.imageeditor;
/*	Arnold 		*/
/*	subclass	*/

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import me.hobrin.imageeditor.util.GeometryUtil;

public class Viewer extends JInternalFrame implements InternalFrameListener, MouseMotionListener, MouseListener {

	int active = 1;
	int winNum = 0;
	public BufferedImage img;
	PanleDrw p;
	
	public int mouseX;
	public int mouseY;
	
	public Point a;
	public Point b;
	
	public ProspecT prospecT;
	
	public boolean dragging = false;
	public EditingImage editing;
	
	public Viewer(int x, int y, int width, int height, BufferedImage g1, EditingImage editing)
	{
		super("Tools", true, true, true, true);
		Container c = this.getContentPane();
		img = g1;
		this.editing = editing;

		p = new PanleDrw(img);
		c.add(p);

		setBounds(x, y, width, height);
		setVisible(true);
		addInternalFrameListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		winNum = ++ImageProcessing.winNumber;
		ImageProcessing.currentWin = winNum;
		this.setTitle(winNum + "");
		ImageProcessing.openFrameCount++;
		ImageProcessing.lbtxtwin.setText(ImageProcessing.openFrameCount + "");

	}
	public void updateImage(BufferedImage image) {
		img = ImageProcessing.deepCopy(image);
		
		Graphics g = img.getGraphics();
		
		if (this.editing.draggingImage != null) {
			DraggingImage dragging = this.editing.draggingImage;
			
			g.drawImage(dragging.img, dragging.x, dragging.y, null);
			
			Rectangle circumference = dragging.rect();
			g.setColor(Color.gray);
			g.drawRect(circumference.x, circumference.y, circumference.width, circumference.height);
		} else if (this.editing.shouldShowSelection()) {
			Rectangle sel = this.editing.selection;
			g.setColor(Color.gray);
			g.drawRect(sel.x, sel.y, sel.width, sel.height);
		}
		
		p.repaintPanelImage(img);
	}

	public void internalFrameClosing(InternalFrameEvent e) {
		System.out.println("closing.... 0");
		active = 0;
		//this.prospecT.allimages.remove(this.winNum);
		ImageProcessing.currentWin = -1;
	}

	public void internalFrameClosed(InternalFrameEvent e) {
		ImageProcessing.openFrameCount--;
		ImageProcessing.lbtxtwin.setText(ImageProcessing.openFrameCount + "");
		active = 0;
	}

	public void internalFrameOpened(InternalFrameEvent e) {

		ImageProcessing.currentWin = winNum;
		ImageProcessing.lbtxtcurd.setText(winNum + "");
		ImageProcessing.lbtxtwin.setText(ImageProcessing.openFrameCount + "");
		active = 1;
	}

	public int fromScreenX(int x) {
		return x-5;
	}
	public int fromScreenY(int y) {
		return y-25;
	}
	
	public void internalFrameIconified(InternalFrameEvent e) {

	}

	public void internalFrameDeiconified(InternalFrameEvent e) {

		ImageProcessing.currentWin = winNum;
		ImageProcessing.lbtxtcurd.setText(winNum + "");
		active = 1;
	}

	public void internalFrameActivated(InternalFrameEvent e) {
		ImageProcessing.currentWin = winNum;
		ImageProcessing.lbtxtcurd.setText(winNum + "");
		active = 1;
	}

	public void internalFrameDeactivated(InternalFrameEvent e) {
		active = 0;
	}
	
	public Point startDragDraggingImg = null;
	
	public void mouseDragged(MouseEvent e) {
		this.dragging = true;
		this.updateMousePos(e);
		
		this.b = new Point(this.mouseX, this.mouseY);
		
		if (this.startDragDraggingImg != null) {
			DraggingImage dragging = this.editing.draggingImage;
			
			dragging.x = this.b.x+this.startDragDraggingImg.x;
			dragging.y = this.b.y+this.startDragDraggingImg.y;
			
			this.editing.selection = null;
			
			this.updateImage(this.editing.getCurrentVersion());
		} else {
			editing.updateSelection(GeometryUtil.getRectArea(a, b));
			prospecT.onDragging(a, b);
		}
		/*ImageProcessing.fromx2.setText(this.mouseX + "");
		ImageProcessing.fromy2.setText(this.mouseY + "");*/
	}
	
	private void updateMousePos(MouseEvent e) {
		this.mouseX = fromScreenX(e.getX());
		this.mouseY = fromScreenY(e.getY());
	}
	
	public void mouseReleased(MouseEvent e) {
		this.dragging = false;
		prospecT.onRelease();
		this.startDragDraggingImg = null;
	}
	
	public void mouseMoved(MouseEvent e) {
		this.updateMousePos(e);
		if (active == 1) {
			ImageProcessing.txtX.setText(mouseX + "");
			ImageProcessing.txtY.setText(mouseY + "");
		}

	}
	
	public void mousePressed(MouseEvent e) {
		this.updateMousePos(e);
		this.a = new Point(this.mouseX, this.mouseY);
		
		DraggingImage dragging = this.editing.draggingImage;
		if (dragging != null) {
			if (dragging.rect().contains(this.a)) {
				this.startDragDraggingImg = dragging.getPointLoc();
				this.startDragDraggingImg.translate(-a.x, -a.y); //relative to mouse.
			} else {
				//lock dragging image, clicked beside image.
				this.editing.lockDraggingImage();
			}
		} else {
			this.prospecT.onClick(a);
		}
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentHidden(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}
}

class PanleDrw extends JPanel {
	public BufferedImage panting;

	public PanleDrw(BufferedImage g2) {
		panting = g2;
		repaint();
	}

	public void repaintPanelImage(BufferedImage g3) {
		panting = g3;
		repaint();
	}

	public void paint(Graphics g) {
		try {
			g.setColor(Color.white);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.drawImage(panting, 0, 0, null);
		} catch (Exception e) {
			
		}
	}
}
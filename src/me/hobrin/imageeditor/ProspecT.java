package me.hobrin.imageeditor;
/*	Arnold Java Image */

/*	Main class	*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import me.hobrin.imageeditor.function.IFunction;
import me.hobrin.imageeditor.function.areas.FunctionFlip;
import me.hobrin.imageeditor.function.areas.FunctionLine;
import me.hobrin.imageeditor.function.areas.FunctionRectangle;
import me.hobrin.imageeditor.function.areas.FunctionText;
import me.hobrin.imageeditor.function.brushes.FunctionCircularBrush;
import me.hobrin.imageeditor.function.inputs.IParam;
import me.hobrin.imageeditor.function.refactor.FunctionFlipXY;
import me.hobrin.imageeditor.function.refactor.FunctionRotate;
import me.hobrin.imageeditor.function.refactor.FunctionScale;
import me.hobrin.imageeditor.function.replace.FunctionReplace;
import me.hobrin.imageeditor.util.ClipboardUtil;
import me.hobrin.imageeditor.util.ColorPicker;
import me.hobrin.keymouse.Keyboard;

@SuppressWarnings("serial")
public class ProspecT extends ImageProcessing
		implements ActionListener, MouseMotionListener, MouseListener, AdjustmentListener {
	JMenuBar JMBar;
	JMenu JMfile, JMedit, JMwindow, JMwindows, JMabout;
	JMenuItem JMIOpen, JMIclose, JMIsave, JMIsaveAs, JMIexit;
	JMenuItem JMIundo, JMIredo, JMIcopy, JMIpaste;
	JMenuItem JMchangeColor, JMmeanFilter, JMmedianFilter, JMscale, JMsetColorXY;
	JMenuItem JMItools, JMIinfo;
	JToolBar toolsPanel;
	public JDesktopPane desktop;
	JButton JBchangeColor, JBmeanFilter, JBmedianFilter, JBscale, JBsetColorXY;
	JLabel lbR, lbG, lbB, lbXYrotXY;
	JLabel LBwidthscale, LBhightscale, lbXrotX, lbYrotY, LBChangeImag;
	@SuppressWarnings("rawtypes")
	JComboBox Chwidthscale, Chhightscale;
	JButton JBold, JBAply, JBchangeColorGetColor, JBRGB, JBmeanFilters, JBmedianFilters, JBscales, JBChangeImag,
			JBsetColorXYs;
	ColorPicker colPickerPrim;
	JTextField txtR, txtG, txtB;
	JLabel fromto1, fromto2, fromto3, fromto4, fromto5;
	int ScrenW, ScrenH;
	JLabel JLBlogo;
	JInternalFrame tools, info;

	public IFunction currentFunction;
	
	public static ProspecT prospec;
	
	public static void main(String args[]) {
		
		Keyboard.init();
		
		prospec = new ProspecT();
		prospec.openImage("C:\\Users\\marku\\Downloads\\vpnnoconnect.png");

	}

	public List<IFunction> functions = new ArrayList<IFunction>();

	public ProspecT() {
		super("Rs Image Editor 2.0");
		this.initFunctions();
		this.componentinit();
		setSize(ScrenW, ScrenH);
		setVisible(true);
		Image IconPro = Toolkit.getDefaultToolkit().getImage("icon/photo.png");

		setIconImage(IconPro);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void initFunctions() {
		this.functions.add(new FunctionScale());
		this.functions.add(new FunctionCircularBrush());
		this.functions.add(new FunctionFlip());
		this.functions.add(new FunctionRotate());
		this.functions.add(new FunctionFlipXY());
		this.functions.add(new FunctionReplace());
		this.functions.add(new FunctionRectangle());
		this.functions.add(new FunctionLine());
		this.functions.add(new FunctionText());
	}

	public void componentinit() {
		Container container = getContentPane();
		container.setBackground(new Color(0xB2B2FC));
		desktop = new JDesktopPane();
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

		container.add(desktop, BorderLayout.CENTER);
		desktop.setBackground(new Color(240, 240, 245));

		tools = new JInternalFrame("Tools", false, false, false, false);
		tools.setBounds(5, 5, 155, this.functions.size()*25+10);
		tools.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
		this.disableDragability(tools);
		tools.setVisible(true);
		desktop.add(tools, JDesktopPane.PALETTE_LAYER);
		tools.setLayout(null);

		/*
		 * JBchangeColor = new JButton("Change Color");
		 * JBchangeColor.setToolTipText("Change Color");
		 * JBchangeColor.addActionListener(this); JBchangeColor.setBounds(2, 5, 150,
		 * 20); tools.add(JBchangeColor);
		 * 
		 * JBmedianFilter = new JButton("Median Filter");
		 * JBmedianFilter.setToolTipText("Median Filter");
		 * JBmedianFilter.addActionListener(this); JBmedianFilter.setBounds(2, 29, 150,
		 * 20); tools.add(JBmedianFilter);
		 * 
		 * JBscale = new JButton("Scale"); JBscale.setToolTipText("Scale");
		 * JBscale.addActionListener(this); JBscale.setBounds(2, 53, 150, 20);
		 * tools.add(JBscale);
		 * 
		 * JBmeanFilter = new JButton("Mean Filter");
		 * JBmeanFilter.setToolTipText("Mean Filter");
		 * JBmeanFilter.addActionListener(this); JBmeanFilter.setBounds(2, 77, 150, 20);
		 * tools.add(JBmeanFilter);
		 * 
		 * JBsetColorXY = new JButton("Set Color");
		 * JBsetColorXY.setToolTipText("Set Color");
		 * JBsetColorXY.addActionListener(this); JBsetColorXY.setBounds(2, 101, 150,
		 * 20); tools.add(JBsetColorXY);
		 * 
		 */
		for (int i = 0; i < this.functions.size(); i++) {
			IFunction function = this.functions.get(i);
			tools.add(function.init(this, i));
		}
		info = new JInternalFrame("Position", false, false, false, false);
		ScrenW = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth());
		ScrenH = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight());

		info.setBounds(5, this.functions.size()*25+10+10, 155, 170);
		info.putClientProperty("JInternalFrame.isPalette", Boolean.FALSE);
		info.setVisible(true);
		desktop.add(info, JDesktopPane.PALETTE_LAYER);
		this.disableDragability(info);
		info.setLayout(null);

		JLabel lbinfo = new JLabel("Cursor Positions");
		lbinfo.setBounds(20, 10, 180, 20);
		info.add(lbinfo);

		JLabel lbx = new JLabel("X");
		lbx.setBounds(10, 40, 20, 20);
		info.add(lbx);

		JLabel lby = new JLabel("Y");
		lby.setBounds(10, 65, 20, 20);
		info.add(lby);

		txtX = new JTextField();
		txtX.setBounds(35, 40, 75, 20);
		txtX.setEditable(false);
		info.add(txtX);

		txtY = new JTextField();
		txtY.setBounds(35, 65, 75, 20);
		txtY.setEditable(false);
		info.add(txtY);

		JLabel lbcurd = new JLabel("Current Win");
		lbcurd.setBounds(10, 90, 84, 20);
		info.add(lbcurd);

		JLabel lbwin = new JLabel("Total Win");
		lbwin.setBounds(10, 110, 83, 20);
		info.add(lbwin);

		lbtxtcurd = new JLabel("0");
		lbtxtcurd.setBounds(100, 90, 80, 20);
		info.add(lbtxtcurd);

		lbtxtwin = new JLabel("0");
		lbtxtwin.setBounds(100, 110, 80, 20);
		info.add(lbtxtwin);

		colPickerPrim = new ColorPicker(this);
		colPickerPrim.setBounds(110, 100, 20, 20);
		colPickerPrim.setColor(Color.green);

		info.add(colPickerPrim);

		///////// JMenuBar ////////
		JMBar = new JMenuBar();
		JMfile = new JMenu("File");
		JMfile.setMnemonic('F');

		ImageIcon iconOpen = new ImageIcon("icon/IMPORT2.png");
		JMIOpen = new JMenuItem("Open", iconOpen);
		JMIOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		JMIOpen.addActionListener(this);
		JMfile.add(JMIOpen);
		
		JMenuItem newBlanco = new JMenuItem("New");
		newBlanco.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		newBlanco.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//while true loop is for invalid answer.
				JLabel error = null;
				while (true) {
					JTextField field1 = new JTextField("1024");
			        JTextField field2 = new JTextField("768");
			        JPanel panel = new JPanel(new GridLayout(0, 1));
			        if (error != null) {
			        	panel.add(error);
			        	panel.add(new JLabel(""));
			        }
			        panel.add(new JLabel("Width:"));
			        panel.add(field1);
			        panel.add(new JLabel("Height:"));
			        panel.add(field2);
			        
			        int result = JOptionPane.showConfirmDialog(null, panel, "Test",
			            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			        
			        if (result == JOptionPane.OK_OPTION) {
			        	try {
				        	int width = Integer.parseInt(field1.getText());
				        	int height = Integer.parseInt(field2.getText());
				        	
				        	BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
							ProspecT.this.openImage(img);
							break;
			        	} catch (NumberFormatException exc) {
			        		error = new JLabel("Please input a non decimal number for width and height.");
			        		
			        		continue;
			        	}
			        } else {
			            System.out.println("Cancelled");
			            break;
			        }
				}
			}
		});
		JMfile.add(newBlanco);

		ImageIcon iconClose = new ImageIcon("icon/CLOSE_16.gif");
		JMIclose = new JMenuItem("Close", iconClose);
		JMIclose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		JMIclose.addActionListener(this);
		JMfile.add(JMIclose);

		ImageIcon iconSave = new ImageIcon("icon/SAVE.png");
		JMIsave = new JMenuItem("Save", iconSave);
		JMIsave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		JMIsave.addActionListener(this);
		JMfile.add(JMIsave);

		ImageIcon iconSaveAs = new ImageIcon("icon/SAVE-AS.png");
		JMIsaveAs = new JMenuItem("Save As", iconSaveAs);
		JMIsaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
		JMIsaveAs.addActionListener(this);
		JMfile.add(JMIsaveAs);

		JMfile.addSeparator();

		ImageIcon iconExit = new ImageIcon("icon/CANCL_16.gif");
		JMIexit = new JMenuItem("Exit", iconExit);
		JMIexit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		JMIexit.addActionListener(this);
		JMfile.add(JMIexit);
		JMBar.add(JMfile);

		JMedit = new JMenu("Edit");
		JMedit.setMnemonic('E');
		ImageIcon iconUndo = new ImageIcon("icon/UNDO_16.gif");
		JMIundo = new JMenuItem("Undo", iconUndo);
		JMIundo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		JMIundo.addActionListener(this);
		JMedit.add(JMIundo);

		ImageIcon iconRedo = new ImageIcon("icon/REDO_16.gif");
		JMIredo = new JMenuItem("Redo", iconRedo);
		JMIredo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
		JMIredo.addActionListener(this);
		JMedit.add(JMIredo);
		
		JMenuItem copySel = new JMenuItem("Copy");
		copySel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		copySel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//copy selection.
				EditingImage editing = ProspecT.this.getEditingImage();
				
				if (editing == null)  {
					System.out.println("No image to copy.");
					return;
				}
				
				Rectangle rect = editing.selection;
				if (editing.selection == null) {
					System.out.println("No selectoin to copy.");
					return;
				}
				BufferedImage img = editing.getCurrentVersion().getSubimage(rect.x, rect.y, rect.width, rect.height);
				ClipboardUtil.copy(img);
			}
		});
		JMedit.add(copySel);
		
		JMenuItem paste = new JMenuItem("Paste");
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		paste.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//paste from clipboard.
				BufferedImage img = ClipboardUtil.paste();
				
				if (img == null) {
					System.out.println("No image in clipboard found.");
					return;
				}
				
				EditingImage editing = ProspecT.this.getEditingImage();
				if (editing == null) {
					//load new image.
					ProspecT.this.openImage(img);
				} else {
					editing.updateDraggingImage(new DraggingImage(img, 0, 0));
				}
			}
		});
		JMedit.add(paste);
		
		JMBar.add(JMedit);

		JMwindow = new JMenu("Window");
		JMwindow.setMnemonic('W');

		JMenuItem selectAll = new JMenuItem("Select All", null);
		selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		selectAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EditingImage editing = ProspecT.this.getEditingImage();
				BufferedImage img = editing.getCurrentVersion();

				editing.updateSelection(new Rectangle(0, 0, img.getWidth(), img.getHeight()));
				System.out.println("Selecting all (ctrl+a)");
			}
		});
		JMwindow.add(selectAll);

		ImageIcon coloricon = new ImageIcon("icon/cc.gif");
		JMchangeColor = new JMenuItem("Change Color", coloricon);
		JMchangeColor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		JMchangeColor.addActionListener(this);
		JMwindow.add(JMchangeColor);

		ImageIcon Mficon = new ImageIcon("icon/mf.gif");
		JMmeanFilter = new JMenuItem("Mean", Mficon);
		JMmeanFilter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		JMmeanFilter.addActionListener(this);
		JMwindow.add(JMmeanFilter);

		ImageIcon Meficon = new ImageIcon("icon/mef.gif");
		JMmedianFilter = new JMenuItem("Median", Meficon);
		JMmedianFilter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));
		JMmedianFilter.addActionListener(this);
		JMwindow.add(JMmedianFilter);

		JMscale = new JMenuItem("Scale", null);
		JMscale.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		JMscale.addActionListener(this);
		JMwindow.add(JMscale);

		JMsetColorXY = new JMenuItem("Draw Rect", null);
		JMsetColorXY.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
		JMsetColorXY.addActionListener(this);
		JMwindow.add(JMsetColorXY);

		JMBar.add(JMwindow);

		setJMenuBar(JMBar);

		toolsPanel = new JToolBar("ToolsPanel");
		toolsPanel.setBackground(new Color(0xEFE8EF));
		container.add(toolsPanel, BorderLayout.NORTH);

		JBold = new JButton("  Reset  ");
		JBold.addActionListener(this);

		JBAply = new JButton(" Set It ");
		JBAply.addActionListener(this);

		lbR = new JLabel("R");
		lbG = new JLabel("G");
		lbB = new JLabel("B");

		txtR = new JTextField("B");
		txtG = new JTextField("R");
		txtB = new JTextField("100");

		JBRGB = new JButton(" Change ");
		JBRGB.addActionListener(this);

		JBmeanFilters = new JButton("Mean Filter");
		JBmeanFilters.addActionListener(this);

		JBmedianFilters = new JButton("Median Filter");
		JBmedianFilters.addActionListener(this);

		LBwidthscale = new JLabel("Width");
		LBhightscale = new JLabel("Height");

		Chwidthscale = new JComboBox();
		for (int i = 1; i <= 20; i++)
			Chwidthscale.addItem(i + "");
		Chhightscale = new JComboBox();
		for (int i = 1; i <= 20; i++)
			Chhightscale.addItem(i + "");

		JBscales = new JButton("Scale");
		JBscales.addActionListener(this);

		lbrotX = new JLabel("0");
		lbYrotY = new JLabel(",");
		lbrotY = new JLabel("1");
		lbXYrotXY = new JLabel(")");

		fromto1 = new JLabel("from (");
		fromto2 = new JLabel(",");
		fromto3 = new JLabel(") to (");
		fromto4 = new JLabel(",");
		fromto5 = new JLabel(")");
		fromx1 = new JLabel("0");
		fromy1 = new JLabel("0");
		fromx2 = new JLabel("20");
		fromy2 = new JLabel("20");

		JBsetColorXYs = new JButton("Draw an Rectangle");
		JBsetColorXYs.addActionListener(this);
	}

	private void disableDragability(JInternalFrame frame) {
		BasicInternalFrameUI ui = (BasicInternalFrameUI) frame.getUI();
		Component northPane = ui.getNorthPane();
		MouseMotionListener[] motionListeners = (MouseMotionListener[]) northPane
				.getListeners(MouseMotionListener.class);

		for (MouseMotionListener listener : motionListeners) {
			northPane.removeMouseMotionListener(listener);
		}
	}

	public Color getPrimaryColor() {
		return this.colPickerPrim.getColor();
	}

	BufferedImage bufImage(String ImageName) {
		try {
			BufferedImage img1 = ImageIO.read(new File(ImageName));
			return img1;
		} catch (Exception e) {
			return null;
		}
	}

	public void openImage(String strIm) {
		BufferedImage im = bufImage(strIm);
		this.openImage(im);
	}
	public void openImage(BufferedImage im) {
		System.out.println("Opening new image.");
		try {
			if (im == null)
				return; // image not present or something.
			EditingImage img = new EditingImage(im, this, null);

			img.viewer.prospecT = this;
			allimages.add(img);

			desktop.add(img.viewer, JDesktopPane.PALETTE_LAYER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isBypassingSelection() {
		return Keyboard.isKeyPressed(KeyEvent.VK_SHIFT);
	}
	public void onClick(Point clickPoint) {
		if (this.currentFunction != null) {
			if (this.currentFunction.activateOnClick() && !this.isBypassingSelection()) {
				EditingImage editing = this.getEditingImage();
				BufferedImage img = ImageProcessing.deepCopy(editing.getCurrentVersion());
				
				BufferedImage result = this.currentFunction.apply(img, editing);
				if (result == null)
					result = img;
				
				editing.update(result);
			}
		}
	}
	public void onDragging(Point a, Point b) {
		if (this.currentFunction != null) {
			if (this.currentFunction.activateOnHold() && !this.isBypassingSelection()) {
				EditingImage editing = this.getEditingImage();

				BufferedImage img;
				if (editing.unsaved == null || !this.currentFunction.hasUnsavedBuffer()) {
					img = ImageProcessing.deepCopy(editing.getCurrentVersion());
				} else {
					img = editing.unsaved;
				}
				BufferedImage img2 = this.currentFunction.apply(img, editing);
				if (img2 == null)
					img2 = img;

				// don't save, however do display.
				editing.viewer.updateImage(img2);

				editing.unsaved = img2;
			}
		}
	}

	public void onRelease() {
		EditingImage img = this.getEditingImage();
		if (img != null && !this.isBypassingSelection()) {
			if (this.currentFunction != null) {
				img.updateSelection(null);
			}
			if (img.unsaved != null) {
				img.update(img.unsaved);
				img.unsaved = null;
			}
		}
	}
	/*
	 * private void enableone(JMenuItem jm1, JButton jb1) {
	 * 
	 * ToolsPanel.removeAll(); ToolsPanel.add(JBold);
	 * 
	 * if (jb1 == JBchangeColor) { ToolsPanel.add(lbR); ToolsPanel.add(txtR);
	 * ToolsPanel.add(lbG); ToolsPanel.add(txtG); ToolsPanel.add(lbB);
	 * ToolsPanel.add(txtB); ToolsPanel.add(JBRGB); }
	 * 
	 * else if (jb1 == JBmeanFilter) { ToolsPanel.add(JBmeanFilters); }
	 * 
	 * else if (jb1 == JBmedianFilter) {
	 * System.out.print("\nMedian filter.................................");
	 * ToolsPanel.add(JBmedianFilters); } else if (jb1 == JBscale) {
	 * 
	 * ToolsPanel.add(LBwidthscale); ToolsPanel.add(Chwidthscale);
	 * ToolsPanel.add(LBhightscale); ToolsPanel.add(Chhightscale);
	 * ToolsPanel.add(JBscales); }
	 * 
	 * else if (jb1 == JBsetColorXY) { ToolsPanel.add(fromto1);
	 * ToolsPanel.add(fromx1); ToolsPanel.add(fromto2); ToolsPanel.add(fromy1);
	 * ToolsPanel.add(fromto3); ToolsPanel.add(fromx2); ToolsPanel.add(fromto4);
	 * ToolsPanel.add(fromy2); ToolsPanel.add(fromto5);
	 * ToolsPanel.add(JBsetColorXYs); }
	 * 
	 * ToolsPanel.add(JBAply); }
	 */

	/*
	 * public void updatesetColorXY(String x, String y, String toX, String toY) {
	 * int temp = getIndexOfNumImage(currentWin); Viewer vo = (Viewer)
	 * allimages.elementAt(temp); int corX = Integer.parseInt(x); int corY =
	 * Integer.parseInt(y); int cortoX = Integer.parseInt(toX); int cortoY =
	 * Integer.parseInt(toY); int w = Math.abs(cortoX - corX); int h =
	 * Math.abs(cortoY - corY); vo.updateImage(setColorToXY(vo.img, corX, corY,
	 * cortoX, cortoY)); }
	 */

	public void enable(IFunction function) {
		toolsPanel.removeAll();
		this.toolsPanel.add(new JLabel(""));
		toolsPanel.repaint();
		
		if (function == this.currentFunction) {
			//deselect
			this.currentFunction = null;
			return;
		}
		this.currentFunction = function;

		for (int i = 0; i < function.params.length; i++) {
			IParam<?> param = function.params[i];
			toolsPanel.add(new JLabel(param.label));
			toolsPanel.add(param.genGetJField());
		}
		if (function.hasActButton()) {
			toolsPanel.add(function.getActivationButton());
		}
		// this.setVisible(false);
		this.setVisible(true);
	}

	public void adjustmentValueChanged(AdjustmentEvent e) {
		try {

		} catch (Exception ex) {

		}
	}

	public EditingImage getEditingImage() {
		//int temp = getIndexOfNumImage(currentWin);
		try {
			return allimages.get(currentWin);
		} catch (ArrayIndexOutOfBoundsException exc) {
			return null;
		}
	}

	public void revertImage() {
		EditingImage img = this.getEditingImage();
		img.setUsedVersion(img.currentVersion + 1);
		repaint();
	}

	public void unrevertImage() {
		EditingImage img = this.getEditingImage();

		img.setUsedVersion(img.currentVersion - 1);
		repaint();
	}

	public void ClosedImage() {
		EditingImage img = this.getEditingImage();

		try {
			img.viewer.setClosed(true);
		} catch (Exception ex) {

		}
	}

	public void saveImages() {
		EditingImage img = this.getEditingImage();

		try {
			ImageIO.write(img.getCurrentVersion(), "jpeg", img.path.toFile());
		} catch (Exception e) {
			this.saveAsimages();
		}
	}

	public void saveAsimages() {
		EditingImage img = this.getEditingImage();
		
		File file;
		if (img.path == null) {
			file = null;
			
		} else {
			file = img.path.toFile();
		}

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setSelectedFile(file);
		int returnVal = fileChooser.showSaveDialog(this);
		if (returnVal == JFileChooser.CANCEL_OPTION) {
			return;
		} else if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			if (file.getName().indexOf('.') == -1)
				file = new File(file.getAbsolutePath() + ".jpeg");

			try {
				ImageIO.write(img.getCurrentVersion(), "jpeg", file);
			} catch (Exception e) {
			}
		}
	}

	public void messageDialogExit() {
		System.exit(0);
	}

	public void actionPerformed(ActionEvent event) {

		Object source = event.getSource();

		if (event.getSource() == JMIOpen)
			openImage(fileChoose());

		/**************************
		 * event cant do without Image
		 *****************************************/

		else if (event.getSource() == JMIclose)
			ClosedImage();
		else if (event.getSource() == JMIsave)
			saveImages();
		else if (event.getSource() == JMIsaveAs)
			saveAsimages();
		else if (event.getSource() == JMIundo)
			this.revertImage();
		else if (event.getSource() == JMIredo)
			this.unrevertImage();
		else if (event.getSource() == JBold)
			revertImage();

		for (IFunction function : this.functions) {
			if (function.getListButton() == source) {
				this.enable(function);
				break;
			}
			if (function.getActivationButton() == source) {
				EditingImage editing = this.getEditingImage();

				BufferedImage inp = ImageProcessing.deepCopy(editing.getCurrentVersion());
				BufferedImage out = function.apply(inp, editing);

				if (out == null) {
					out = inp; // ifunction was lazy and didn't return itself.
				}
				editing.update(out);
				break;
			}
		}
		/*
		 * if (event.getSource() == JBmedianFilters) updatemedianFilters(); else if
		 * (event.getSource() == JBRGB) updatechabgeColor(); else if (event.getSource()
		 * == JBmeanFilters) updatemeanFilters(); else if (event.getSource() ==
		 * JBscales) updatescale(Chwidthscale.getSelectedItem().toString(),
		 * Chhightscale.getSelectedItem().toString());
		 * 
		 * else if (event.getSource() == JBsetColorXYs)
		 * updatesetColorXY(fromx1.getText(), fromy1.getText(), fromx2.getText(),
		 * fromy2.getText());
		 * 
		 * else if ((event.getSource() == JMchangeColor) || (event.getSource() ==
		 * JBchangeColor)) enableone(JMchangeColor, JBchangeColor);
		 * 
		 * else if ((event.getSource() == JMmeanFilter) || (event.getSource() ==
		 * JBmeanFilter)) enableone(JMmeanFilter, JBmeanFilter);
		 * 
		 * else if ((event.getSource() == JMmedianFilter) || (event.getSource() ==
		 * JBmedianFilter)) enableone(JMmedianFilter, JBmedianFilter);
		 * 
		 * else if ((event.getSource() == JMscale) || (event.getSource() == JBscale))
		 * enableone(JMscale, JBscale);
		 * 
		 * else if ((event.getSource() == JMsetColorXY) || (event.getSource() ==
		 * JBsetColorXY)) enableone(JMsetColorXY, JBsetColorXY);
		 */

	}

	private int getIndexOfNumImage(int ind) {
		return (ind < this.allimages.size() && ind >= 0) ? ind : -1;
	}

	public String fileChoose() {
		JFileChooser fc = new JFileChooser();
		fc.setAcceptAllFileFilterUsed(true);
		int returnVal = fc.showDialog(this, "Open Image");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile().toString();
		} else {
			return "-1";
		}
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
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
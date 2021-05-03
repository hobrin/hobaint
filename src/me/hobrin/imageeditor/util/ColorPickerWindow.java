package me.hobrin.imageeditor.util;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;

public class ColorPickerWindow extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6620896304165457679L;
	private final Robot robot;
	private final ColorPicker picker;
	
	
	
	public ColorPickerWindow(Color col, ColorPicker picker) throws AWTException {
		super("", false, true, false, false);
		
		robot = new Robot();
		this.picker = picker;
		
		this.setBounds(5, 5, 170, 170);
		this.putClientProperty("JInternalFrame.isPalette", Boolean.FALSE);
		this.setLayout(null);
		
		this.setColor(col);
		
		JButton pickColor = new JButton("Copy Color From Screen");
		pickColor.setBounds(5, 5, 160, 20);
		pickColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//pressed
				new Thread() {
					@Override
					public void run() {
						try {
							//5 seconds.
							for (int i = 0; i < 100; i++) {
								Thread.sleep(50);
								setColor(mouseColAtScreen());
							}
							//animation
							Color col = picker.getColor();
							for (int i = 0; i < 2; i++) {
								
								setColor(Color.white);
								Thread.sleep(10);
								setColor(Color.black);
								Thread.sleep(10);
							}
							/*int i = 0;
							for (; i < 150; i+=3) {
								Thread.sleep(5);
								setColor(new Color(col.getRed() + i, col.getGreen() + i, col.getBlue() + i));
							}
							for (; i >= 0; i-=3) {
								Thread.sleep(5);
								setColor(new Color(col.getRed() + i, col.getGreen() + i, col.getBlue() + i));
							}*/
							ColorPickerWindow.this.setColor(col);
						} catch (Exception exc) {
							
						}
					}
				}.start();
			}
		});
		this.add(pickColor);
		
		JButton done = new JButton("Done");
		done.setBounds(5, 25, 160, 20);
		done.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ColorPickerWindow.this.setVisible(false);
			}
		});
		this.add(done);
		
	}
	
	public void setColor(Color color) {
		this.setBackground(color);
		this.picker.setColor(color);
	}
	
	public Color mouseColAtScreen() {
		Point m = MouseInfo.getPointerInfo().getLocation();
		return robot.getPixelColor(m.x, m.y);
	}
}

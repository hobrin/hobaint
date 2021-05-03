package me.hobrin.imageeditor.util;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDesktopPane;

import me.hobrin.imageeditor.ProspecT;

public class ColorPicker extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2016347201757977096L;
	private Color color;
	private ProspecT prospec;
	
	public ColorPicker(ProspecT prospec) {
		super();
		this.prospec = prospec;
		
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//pressed
				System.out.println("Picking color!");
				try {
					ColorPickerWindow wind = new ColorPickerWindow(color, ColorPicker.this);
					ColorPicker.this.prospec.desktop.add(wind, JDesktopPane.PALETTE_LAYER);
					wind.setVisible(true);
					prospec.repaint();
				} catch (AWTException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		this.setBackground(this.color);
	}
}

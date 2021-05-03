package me.hobrin.imageeditor.function;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import me.hobrin.imageeditor.EditingImage;
import me.hobrin.imageeditor.ProspecT;
import me.hobrin.imageeditor.function.inputs.IParam;

public abstract class IFunction {
	
	public final String name;
	public final String descr;
	public final IParam<?>[] params;
	
	private ProspecT prospecT;
	
	public IFunction(String name, String descr, IParam<?>... params) {
		this.name = name;
		this.descr = descr;
		this.params = params;
	}
	
	private JButton listButton;
	private JButton activateButton;
	
	public static Color getPrimaryColor() {
		return ProspecT.prospec.getPrimaryColor();
	}
	
	public JButton init(ProspecT prospecT, int i) {
		this.prospecT = prospecT;
		if (listButton == null) {
			this.listButton = new JButton(name);
			this.listButton.setToolTipText(descr);
			this.listButton.addActionListener(prospecT);
			this.listButton.setBounds(2, 5+24*i, 150, 20);
			
			this.activateButton = new JButton(name);
			this.activateButton.setToolTipText(descr);
			this.activateButton.addActionListener(prospecT);
		}
		return this.listButton;
	}
	public JButton getListButton() {
		return this.listButton;
	}
	public JButton getActivationButton() {
		return this.activateButton;
	}
	
	public abstract BufferedImage apply(BufferedImage image, EditingImage editing);
	
	public abstract boolean activateOnHold();
	public boolean activateOnClick() {
		return false;
	}
	
	public boolean hasActButton() {
		return !activateOnHold();
	}
	public boolean hasUnsavedBuffer() {
		return false;
	}
	public boolean shouldShowSelection() {
		return true;
	}
}

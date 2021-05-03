package me.hobrin.imageeditor;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class EditingImage {
	
	public int currentVersion = 0;
	/* first index is newest image in history */
	public List<BufferedImage> history = new ArrayList<>();
	public BufferedImage unsaved; //while brushing a memory.
	
	public Viewer viewer;
	ProspecT prospec;
	public Path path;
	
	public Rectangle selection = null;
	public DraggingImage draggingImage = null;
	
	public EditingImage(BufferedImage img, ProspecT prospec, Path path) {
		this.history.add(img);
		this.prospec = prospec;
		this.path = path;
		
		this.viewer = new Viewer(180, 50, img.getWidth()+11, img.getHeight()+34, img, this);
	}
	
	public BufferedImage getCurrentVersion() {
		return this.history.get(currentVersion);
	}
	public void setUsedVersion(int version) {
		if (version >= 0 && version < history.size()) {
			this.currentVersion = version;
			this.viewer.updateImage(this.history.get(this.currentVersion));
		}
	}
	public boolean shouldShowSelection() {
		return this.selection != null && (prospec.currentFunction == null || prospec.currentFunction.shouldShowSelection());
	}
	public void updateSelection(Rectangle sel) {
		this.selection = sel;
		if (this.selection != null) {
			//make selection box draw again.
			viewer.updateImage(this.getCurrentVersion());
		}
	}
	/*
	 * add a new(est) version.
	 */
	public void update(BufferedImage img) {
		//remove all previous versions before current version.
		for (int i = 0; i < this.currentVersion; i++) {
			this.history.remove(0);
		}
		
		this.history.add(0, img);
		this.currentVersion = 0;
		viewer.updateImage(img);
	}
	public void updateDraggingImage(DraggingImage dragging) {
		this.draggingImage = dragging;
		
		this.viewer.updateImage(this.getCurrentVersion());
	}
	public void lockDraggingImage() {
		if (this.draggingImage == null)
			return;
		
		BufferedImage img = ImageProcessing.deepCopy(this.getCurrentVersion());
		BufferedImage sub = this.draggingImage.img;
		
		int relX = this.draggingImage.x;
		int relY = this.draggingImage.y;
		
		for (int x = 0; x < sub.getWidth(); x++) {
			for (int y = 0; y < sub.getHeight(); y++) {
				try {
					img.setRGB(x+relX, y+relY, sub.getRGB(x, y));
				} catch (ArrayIndexOutOfBoundsException exc) {
					//ignore, cough cough.
				}
			}
		}
		//has to happen before updating image because of rendering.
		this.draggingImage = null;

		this.update(img);
	}
}

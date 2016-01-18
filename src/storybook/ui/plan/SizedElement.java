package storybook.ui.plan;

import storybook.model.hbn.entity.Chapter;
import storybook.model.hbn.entity.Part;
import storybook.model.hbn.entity.Scene;

public class SizedElement {
	Object element;
	int size;
	int maxSize;
	
	public Object getElement() {
		return element;
	}
	/**
	 * @param element
	 */
	public void setElement(Object element) {
		this.element = element;
		if (element instanceof Part) {
			Part part = (Part)element;
			maxSize = part.getObjectiveChars();
		} else if (element instanceof Chapter) {
			Chapter chapter = (Chapter)element;
			maxSize = chapter.getObjectiveChars();
		}
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(int size) {
		this.maxSize = size;
	}
	
	public String toString() {
		if (element instanceof Part) {
			Part part = (Part)element;
			maxSize = part.getObjectiveChars();
			return (part.getName() + "    (" + size + "/" + maxSize +")");
		} else if (element instanceof Chapter) {
			Chapter chapter = (Chapter)element;
			maxSize = chapter.getObjectiveChars();
			return chapter.getTitle() + "    (" + size + "/" + maxSize +")";
		} else if (element instanceof Scene) {
			Scene scene = (Scene)element;
			return scene.getTitle() + "    (" + size + ")";
		} else if (element instanceof String) {
			return (String)element + "    (" + size + "/" + maxSize +")";
		}
		return "";
	}
}

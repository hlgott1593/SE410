/*
 Storybook: Open Source software for novelists and authors.
 Copyright (C) 2008 - 2012 Martin Mustun

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package storybook.ui.dialog.copy;

import java.util.List;

import org.hibernate.Session;

import storybook.controller.BookController;
import storybook.model.EntityUtil;
import storybook.model.handler.AbstractEntityHandler;
import storybook.model.hbn.entity.AbstractEntity;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.MainFrame;
import storybook.ui.edit.CbPanelDecorator;

/**
 * @author martin
 *
 */
public abstract class AbstractCopier<ELEMENT extends AbstractEntity> {

    private MainFrame mainFrame;

	public AbstractCopier(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public void showDialog() {
		SwingUtil.showModalDialog(new CopyDialog<ELEMENT>(this), mainFrame);
	}
	
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	@SuppressWarnings("unchecked")
	protected ELEMENT copy(MainFrame destination, ELEMENT elt) {

		AbstractEntityHandler entityHandler = getEntityHandler(destination);
		AbstractEntity newElt = entityHandler.createNewEntity();
		BookController destCtrl = destination.getBookController();
		EntityUtil.copyEntityProperties(destination, elt, newElt);
		prepareTransfer(mainFrame, destination, (ELEMENT)elt, (ELEMENT)newElt);
		destCtrl.newEntity(newElt);
		copySpecialInformation(mainFrame, destination, elt, (ELEMENT)newElt);
		return (ELEMENT) newElt;
	}
	
	/**
	 * Prepare transfer of an entity. Sometimes there are higher level objects (Group, Category...)
	 * that must be copied first, before copying the entity itself.
	 * Derived classes have to do it.
	 * 
	 * @param origin origin mainFrame, from where comes the entity to copy
	 * @param destination destination mainFrame, where to store the newly created entity
	 * @param originElt entity to copy
	 * @param destElt newly created entity
	 */
	protected abstract void prepareTransfer(MainFrame origin, MainFrame destination, ELEMENT originElt, ELEMENT destElt);

	/**
	 * Complete transfer of an entity. Sometimes there are special information (Attributes...)
	 * that must be copied to complete the copy of the entity.
	 * Derived classes have to do it.
	 * 
	 * @param origin origin mainFrame, from where comes the entity to copy
	 * @param destination destination mainFrame, where to store the newly created entity
	 * @param originElt entity to copy
	 * @param destElt newly created entity
	 */
	protected abstract void copySpecialInformation(MainFrame origin, MainFrame destination, ELEMENT originElt, ELEMENT destElt);
	
	/**
	 * How to get all entities of the type from the origin project. 
	 * @param session opened current session
	 * @param origin origin mainFrame
	 * @return the list of all entities in the project
	 */
	protected abstract List<ELEMENT> getAllElements(Session session, MainFrame origin);
	
	/**
	 * Get decorator for the entity list.
	 * @return the decorator
	 */
	protected abstract CbPanelDecorator getDecorator();
	
	/**
	 * Get entity handler
	 * @param mainFrame origin mainFrame
	 * @return the entity handler
	 */
	protected abstract AbstractEntityHandler getEntityHandler(MainFrame mainFrame);
}



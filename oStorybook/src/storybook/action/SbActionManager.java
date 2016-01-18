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
package storybook.action;

import java.awt.Component;
import java.awt.Event;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;

import org.hibernate.Session;
import storybook.SbConstants;
import storybook.SbApp;
import storybook.SbConstants.PreferenceKey;
import storybook.controller.BookController;
import storybook.model.DbFile;
import storybook.model.BookModel;
import storybook.model.PreferenceModel;
import storybook.model.hbn.dao.PartDAOImpl;
import storybook.model.hbn.dao.PreferenceDAOImpl;
import storybook.model.hbn.entity.Part;
import storybook.model.hbn.entity.Preference;
import storybook.toolkit.I18N;
import storybook.toolkit.IOUtil;
import storybook.toolkit.PrefUtil;
import storybook.toolkit.swing.SwingUtil;
import storybook.ui.MainFrame;

import com.sun.jaf.ui.ActionManager;
import com.sun.jaf.ui.UIFactory;
import java.io.IOException;
import storybook.ui.MainMenu;

/**
 * @author martin
 *
 */
public class SbActionManager implements PropertyChangeListener {

//	private final static int MENUBAR_INDEX_FILE = 0;
//	private final static int MENUBAR_INDEX_PARTS = 6;
//	private final static int MENUBAR_INDEX_WINDOW = 9;
	private ActionHandler actionHandler;
	private ActionManager actionManager;
	private final MainFrame mainFrame;
	private MainMenu mainMenu;

	public SbActionManager(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public SbActionManager(MainFrame mainFrame,boolean withInit) {
		this.mainFrame = mainFrame;
		init();
	}

	public void init() {
		SbApp.trace("SbActionManager.init()");
		initActions();
		initUiFactory();
		if (mainFrame.isBlank()) {
			//disableActionsForBlank(mainFrame.getJMenuBar());
			mainMenu.setMenuForBlank();
		}
	}

	private void initActions() {
		SbApp.trace("SbActionManager.initActions()");
		actionManager = new ActionManager();
		ActionManager.setInstance(actionManager);
		registerActions();
	}

	private void registerActions() {
		SbApp.trace("SbActionManager.registerActions()");
		actionHandler = new ActionHandler(mainFrame);
		actionManager.registerCallback("save-command", actionHandler, "handleSave");
	}

	private void initUiFactory() {
		SbApp.trace("SbActionManager.initUiFactory()");
		//UIFactory.setActionManager(ActionManager.getInstance());
		//UIFactory factory = UIFactory.getInstance();
		mainMenu=new MainMenu(mainFrame);
		JMenuBar menubar = mainMenu.menuBar;
		if (menubar != null) {
			//menubar.addPropertyChangeListener(this);
			reloadRecentMenu(menubar);
			reloadPartMenu(menubar);
			reloadWindowMenu(menubar);
			Preference pref = PrefUtil.get(PreferenceKey.TRANSLATOR_MODE, false);
			if (pref != null && pref.getBooleanValue()) {
				JMenuItem item = new JMenuItem(new RunAttesoroAction());
				menubar.add(item);
			}
			mainFrame.setJMenuBar(menubar);
		} else {
			System.err.println("General error : unable to load main menu");
		}
		//JToolBar toolBar = factory.createToolBar("main-toolbar");
		JToolBar toolBar=mainMenu.toolBar;
		if (toolBar != null) {
			toolBar.setName(SbConstants.ComponentName.TB_MAIN.toString());
			mainFrame.setMainToolBar(toolBar);
		}

		mainFrame.invalidate();
		mainFrame.validate();
		mainFrame.pack();
		mainFrame.repaint();
	}

	private void reloadWindowMenu(JMenuBar menubar) {
		SbApp.trace("SbActionManager.reloadWindowMenu(" + menubar.getName() + ")");
		//JMenu menu = menubar.getMenu(MENUBAR_INDEX_WINDOW);
		//JMenu miLoad = (JMenu) menu.getItem(0);
		JMenu miLoad=mainMenu.windowLoadLayout;
		miLoad.removeAll();
		PreferenceModel model = SbApp.getInstance().getPreferenceModel();
		Session session = model.beginTransaction();
		PreferenceDAOImpl dao = new PreferenceDAOImpl(session);
		List<Preference> pref = dao.findAll();
		for (Preference preference : pref) {
			if (preference.getKey().startsWith(PreferenceKey.DOCKING_LAYOUT.toString())) {
				String name = preference.getStringValue();
				if (SbConstants.BookKey.LAST_USED_LAYOUT.toString().equals(name))
					continue;
				LoadDockingLayoutAction act = new LoadDockingLayoutAction(mainFrame, name);
				JMenuItem item = new JMenuItem(act);
				miLoad.add(item);
			}
		}
		model.commit();
	}

	/*private int findRecent(JMenu menu) {
		int rc = 2;
		for (int i = 0; i < menu.getItemCount(); i++) {
			if (menu.getItem(i).getLabel().equals(I18N.getMsg("msg.file.open.recent")))
				return (i);
		}
		return (rc);
	}*/

	private void reloadRecentMenu(JMenuBar menubar) {
		SbApp.trace("SbActionManager.reloadRecentMenu(" + menubar.getName() + ")");
		//JMenu menu = menubar.getMenu(MENUBAR_INDEX_FILE);
		//JMenu miRecent = (JMenu) menu.getItem(findRecent(menu));
		JMenu miRecent=mainMenu.fileOpenRecent;
		miRecent.removeAll();
		List<DbFile> list = PrefUtil.getDbFileList();
		for (DbFile dbFile : list) {
			OpenFileAction act = new OpenFileAction(dbFile.getName() + " ["
					+ dbFile.toString() + "]", dbFile);
			JMenuItem item = new JMenuItem(act);
			miRecent.add(item);
		}
		miRecent.addSeparator();
		JMenuItem item = new JMenuItem(new ClearRecentFilesAction(actionHandler));
		miRecent.add(item);
	}

	private void reloadPartMenu(JMenuBar menubar) {
		SbApp.trace("SbActionManager.reloadPartMenu(" + menubar.getName() + ")");
		BookModel model = mainFrame.getBookModel();
		if (model == null)
			return;
		//JMenu menu = menubar.getMenu(MENUBAR_INDEX_PARTS);
		//JMenuItem miPreviousPart = menu.getItem(0);
		//JMenuItem miNextPart = menu.getItem(1);
		//JMenuItem miParts = menu.getItem(2);
		JMenu menu=mainMenu.menuParts;
		JMenuItem miPreviousPart=mainMenu.partPrevious;
		JMenuItem miNextPart=mainMenu.partNext;
		Part currentPart = mainFrame.getCurrentPart();
		Session session = model.beginTransaction();
		PartDAOImpl dao = new PartDAOImpl(session);
		List<Part> parts = dao.findAll();
		model.commit();
		menu.removeAll();
		int pos = 0;
		ButtonGroup group = new ButtonGroup();
		for (Part part : parts) {
			Action action = new ChangePartAction(I18N.getMsg("msg.common.part") + " " + part.getNumberName(), actionHandler, part);
			JRadioButtonMenuItem rbmi = new JRadioButtonMenuItem(action);
			SwingUtil.setAccelerator(rbmi, KeyEvent.VK_0 + part.getNumber(), Event.ALT_MASK);
			if (currentPart.getId().equals(part.getId()))
				rbmi.setSelected(true);
			group.add(rbmi);
			menu.insert(rbmi, pos);
			++pos;
		}
		menu.insertSeparator(pos++);
		menu.insert(miPreviousPart, pos++);
		menu.insert(miNextPart, pos++);
		menu.insertSeparator(pos++);
		//menu.insert(miParts, pos++);
	}

	private void selectPartMenu(JMenuBar menubar) {
		SbApp.trace("SbActionManager.selectPartMenu(" + menubar.getName() + ")");
		//JMenu menu = menubar.getMenu(MENUBAR_INDEX_PARTS);
		JMenu menu=mainMenu.menuParts;
		Component[] comps = menu.getMenuComponents();
		for (Component comp : comps) {
			if (comp instanceof JRadioButtonMenuItem) {
				JRadioButtonMenuItem rbmi = (JRadioButtonMenuItem) comp;
				ChangePartAction action = (ChangePartAction) rbmi.getAction();
				if (action.getPart().getId().equals(mainFrame.getCurrentPart().getId())) {
					rbmi.setSelected(true);
					return;
				}
			}
		}
	}

	public void reloadMenuToolbar() {
//		boolean maximized = mainFrame.isMaximized();
//		int width = mainFrame.getWidth();
//		int height = mainFrame.getHeight();
		init();
//		if (maximized) {
//			mainFrame.setMaximized();
//		} else {
//			mainFrame.setSize(width, height);
//		}
	}

	/*
	public void disableActionsForBlank(JMenuBar menubar) {
		SbApp.trace("SbActionManager.disableActionsForBlank(" + menubar.getName() + ")");
		String[] actionNames = {
			"file-menu-command",
			"new-command",
			"open-command",
			"recent-menu-command",
			"recent-clear-command",
			"exit-command",
			"edit-menu-command",
			"view-menu-command",
			"main-entities-menu-command",
			"secondary-entities-menu-command",
			"new-entity-menu-command",
			"parts-menu-command",
			"charts-menu-command",
			"tools-menu-command",
			"langtool-command",
			"window-menu-command",
			"preferences-command",
			"help-menu",
			"report-bug-command",
			"doc-command",
			"faq-command",
			"homepage-command",
			"facebook-command",
			"check-update-command",
			"about-command",
			"trace-command"
		};
		List<String> list = Arrays.asList(actionNames);
		Set<String> ids = actionManager.getActionIDs();
		for (String id : ids) {
			if (!list.contains(id))
				actionManager.getAction(id).setEnabled(false);
		}
	}*/

	public ActionHandler getActionController() {
		SbApp.trace("SbActionManager.getActionController()");
		return actionHandler;
	}

	public ActionManager getActionManager() {
		SbApp.trace("SbActionManager.getActionManager()");
		return actionManager;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		SbApp.trace("SbActionManager.propertyChange(" + evt.getPropertyName() + "::" + evt.getNewValue() + ")");
		String propName = evt.getPropertyName();
		if (BookController.PartProps.NEW.check(propName)
				|| BookController.PartProps.UPDATE.check(propName)
				|| BookController.PartProps.DELETE.check(propName)) {
			reloadMenuToolbar();
			return;
		}
		if (BookController.PartProps.CHANGE.check(propName))
			selectPartMenu(mainFrame.getJMenuBar()); //return;
	}

	public ActionHandler getActionHandler() {
		SbApp.trace("SbActionManager.getActionHandler()");
		return actionHandler;
	}
}

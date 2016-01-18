package storybook.ui.dialog.copy;

import java.util.List;

import org.hibernate.Session;

import storybook.model.handler.AbstractEntityHandler;
import storybook.model.handler.ItemEntityHandler;
import storybook.model.hbn.dao.ItemDAOImpl;
import storybook.model.hbn.entity.Item;
import storybook.ui.MainFrame;
import storybook.ui.edit.CbPanelDecorator;
import storybook.ui.edit.ItemCbPanelDecorator;

public class ItemCopier extends AbstractCopier<Item> {

	public ItemCopier(MainFrame mainFrame) {
		super(mainFrame);
	}

	@Override
	protected void prepareTransfer(MainFrame origin, MainFrame destination, Item originElt, Item destElt) {
	}

	@Override
	protected void copySpecialInformation(MainFrame origin, MainFrame destination, Item originElt, Item destElt) {
	}

	@Override
	protected List<Item> getAllElements(Session session, MainFrame origin) {
		ItemDAOImpl dao = new ItemDAOImpl(session);
		List<Item> ret = dao.findAll();
		
		return ret;
	}
	
	@Override
	protected CbPanelDecorator getDecorator() {
		return new ItemCbPanelDecorator();
	}

	@Override
	protected AbstractEntityHandler getEntityHandler(MainFrame mainFrame) {
		return new ItemEntityHandler(mainFrame);
	}

}

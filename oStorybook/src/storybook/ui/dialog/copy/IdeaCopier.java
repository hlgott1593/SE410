package storybook.ui.dialog.copy;

import java.util.List;

import org.hibernate.Session;

import storybook.model.handler.AbstractEntityHandler;
import storybook.model.handler.IdeaEntityHandler;
import storybook.model.hbn.dao.IdeaDAOImpl;
import storybook.model.hbn.entity.Idea;
import storybook.ui.MainFrame;
import storybook.ui.edit.CbPanelDecorator;
import storybook.ui.edit.IdeaCbPanelDecorator;

public class IdeaCopier extends AbstractCopier<Idea> {

	public IdeaCopier(MainFrame mainFrame) {
		super(mainFrame);
	}

	@Override
	protected void prepareTransfer(MainFrame origin, MainFrame destination, Idea originElt, Idea destElt) {
	}

	@Override
	protected void copySpecialInformation(MainFrame origin, MainFrame destination, Idea originElt, Idea destElt) {
	}

	@Override
	protected List<Idea> getAllElements(Session session, MainFrame origin) {
		IdeaDAOImpl dao = new IdeaDAOImpl(session);
		List<Idea> ret = dao.findAll();
		
		return ret;
	}
	
	@Override
	protected CbPanelDecorator getDecorator() {
		return new IdeaCbPanelDecorator();
	}

	@Override
	protected AbstractEntityHandler getEntityHandler(MainFrame mainFrame) {
		return new IdeaEntityHandler(mainFrame);
	}

}

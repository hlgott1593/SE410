package storybook.ui.dialog.copy;

import java.util.List;

import org.hibernate.Session;

import storybook.model.BookModel;
import storybook.model.handler.AbstractEntityHandler;
import storybook.model.handler.CategoryEntityHandler;
import storybook.model.hbn.dao.CategoryDAOImpl;
import storybook.model.hbn.entity.Category;
import storybook.ui.MainFrame;
import storybook.ui.edit.CbPanelDecorator;

public class CategoryCopier extends AbstractCopier<Category> {

	public CategoryCopier(MainFrame mainFrame) {
		super(mainFrame);
	}

	@Override
	protected void prepareTransfer(MainFrame origin, MainFrame destination, Category originElt, Category destElt) {

		Category sup = originElt.getSup();
		if (sup != null) {
			BookModel destinationModel = destination.getBookModel();
			Session destinationSession = destinationModel.beginTransaction();
			List<Category> cats = new CategoryDAOImpl(destinationSession).findAll();
			boolean found = false;
			for (Category cat : cats) {
				if (cat.getName().equals(sup.getName())) {
					found = true;
					destElt.setSup(cat);
					break;
				}
			}

			if (!found) {
                Category destSup = copy(destination, sup);
				destElt.setSup(destSup);
			}
		}
	}

	@Override
	protected void copySpecialInformation(MainFrame origin, MainFrame destination, Category originElt, Category destElt) {
	}

	@Override
	protected List<Category> getAllElements(Session session, MainFrame origin) {
		CategoryDAOImpl dao = new CategoryDAOImpl(session);
		List<Category> ret = dao.findAll();
		
		return ret;
	}
	
	@Override
	protected CbPanelDecorator getDecorator() {
		return null;
	}

	@Override
	protected AbstractEntityHandler getEntityHandler(MainFrame mainFrame) {
		return new CategoryEntityHandler(mainFrame);
	}

}

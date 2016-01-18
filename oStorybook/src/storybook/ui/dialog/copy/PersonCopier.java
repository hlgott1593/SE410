package storybook.ui.dialog.copy;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import storybook.model.BookModel;
import storybook.model.EntityUtil;
import storybook.model.handler.AbstractEntityHandler;
import storybook.model.handler.AttributeEntityHandler;
import storybook.model.handler.PersonEntityHandler;
import storybook.model.hbn.dao.CategoryDAOImpl;
import storybook.model.hbn.dao.PersonDAOImpl;
import storybook.model.hbn.entity.Attribute;
import storybook.model.hbn.entity.Category;
import storybook.model.hbn.entity.Person;
import storybook.ui.MainFrame;
import storybook.ui.edit.CbPanelDecorator;
import storybook.ui.edit.PersonCbPanelDecorator;

public class PersonCopier extends AbstractCopier<Person> {

	public PersonCopier(MainFrame mainFrame) {
		super(mainFrame);
	}

	@Override
	protected void prepareTransfer(MainFrame origin, MainFrame destination, Person originElt, Person destElt) {
		
		setCategory(destination, originElt, destElt);
		
		destElt.setAttributes(new ArrayList<Attribute>());
	}
	
	private void setCategory(MainFrame destination, Person originElt, Person destElt) {
		BookModel destinationModel = destination.getBookModel();
		Session destinationSession = destinationModel.beginTransaction();
		Category categ = originElt.getCategory();
		List<Category> cats = new CategoryDAOImpl(destinationSession).findAll();
		destinationSession.close();

		boolean found = false;
		for (Category cat : cats) {
			if (cat.getName().equals(categ.getName())) {
				found = true;
				destElt.setCategory(cat);
				break;
			}
		}

		if (!found) {
            Category destCat = new CategoryCopier(getMainFrame()).copy(destination, categ);
			destElt.setCategory(destCat);
		}
	}

	@Override
	protected void copySpecialInformation(MainFrame origin, MainFrame destination, Person originElt, Person destElt) {
		AttributeEntityHandler handler = new AttributeEntityHandler(destination);
		List<Attribute> attributes = EntityUtil.getEntityAttributes(origin, originElt);
		BookModel model = origin.getBookModel();
		List<Attribute> newAttributes = new ArrayList<Attribute>();
		for (Attribute attribute : attributes) {
			Session oriSession = model.beginTransaction();
			oriSession.refresh(attribute);
			String key = attribute.getKey();
			String value = attribute.getValue();

			Attribute attr = (Attribute) handler.createNewEntity();
			attr.setKey(key);
			attr.setValue(value);
			newAttributes.add(attr);
			model.commit();
		}
		EntityUtil.setEntityAttributes(destination, destElt, newAttributes );
	}

	@Override
	protected List<Person> getAllElements(Session session, MainFrame origin) {
		PersonDAOImpl dao = new PersonDAOImpl(session);
		List<Person> ret = dao.findAll();
		
		return ret;
	}
	
	@Override
	protected CbPanelDecorator getDecorator() {
		return new PersonCbPanelDecorator();
	}

	@Override
	protected AbstractEntityHandler getEntityHandler(MainFrame mainFrame) {
		return new PersonEntityHandler(mainFrame);
	}

}

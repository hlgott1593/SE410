package storybook.ui.dialog.copy;

import java.util.List;

import org.hibernate.Session;

import storybook.model.BookModel;
import storybook.model.handler.AbstractEntityHandler;
import storybook.model.handler.LocationEntityHandler;
import storybook.model.hbn.dao.LocationDAOImpl;
import storybook.model.hbn.entity.Location;
import storybook.ui.MainFrame;
import storybook.ui.edit.CbPanelDecorator;
import storybook.ui.edit.LocationCbPanelDecorator;

public class LocationCopier extends AbstractCopier<Location> {

	public LocationCopier(MainFrame mainFrame) {
		super(mainFrame);
	}

	@Override
	protected void prepareTransfer(MainFrame origin, MainFrame destination, Location originElt, Location destElt) {
		
		setLocation(destination, originElt, destElt);
	}

	private void setLocation(MainFrame destination, Location originElt, Location destElt) {
		Location site = originElt.getSite();
		if (site != null) {
			BookModel destinationModel = destination.getBookModel();
			Session destinationSession = destinationModel.beginTransaction();
			List<Location> sites = new LocationDAOImpl(destinationSession).findAll();
			destinationSession.close();

			boolean found = false;
			for (Location loc : sites) {
				if (loc.getName().equals(site.getName())) {
					found = true;
					destElt.setSite(loc);
					break;
				}
			}

			if (!found) {
				Location destCat = new LocationCopier(getMainFrame()).copy(destination, site);
				destElt.setSite(destCat);
			}
		}
	}

	@Override
	protected void copySpecialInformation(MainFrame origin, MainFrame destination, Location originElt, Location destElt) {
	}

	@Override
	protected List<Location> getAllElements(Session session, MainFrame origin) {
		LocationDAOImpl dao = new LocationDAOImpl(session);
		List<Location> ret = dao.findAll();
		
		return ret;
	}
	
	@Override
	protected CbPanelDecorator getDecorator() {
		return new LocationCbPanelDecorator();
	}

	@Override
	protected AbstractEntityHandler getEntityHandler(MainFrame mainFrame) {
		return new LocationEntityHandler(mainFrame);
	}

}

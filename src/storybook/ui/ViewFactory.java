
/*
 Storybook: Open Source software for novelists and authors.
 Copyright (C) 2008 - 2012 Martin Mustun, 2015 FaVdB

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
package storybook.ui;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import net.infonode.docking.View;
import net.infonode.docking.util.StringViewMap;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.TableColumnExt;
import storybook.SbApp;
import storybook.SbConstants.ViewName;
import storybook.model.hbn.entity.Internal;
import storybook.toolkit.BookUtil;
import storybook.toolkit.I18N;
import storybook.ui.chart.GanttChart;
import storybook.ui.chart.OccurrenceOfLocationsChart;
import storybook.ui.chart.OccurrenceOfPersonsChart;
import storybook.ui.chart.PersonsByDateChart;
import storybook.ui.chart.PersonsBySceneChart;
import storybook.ui.chart.StrandsByDateChart;
import storybook.ui.chart.WiWWChart;
import storybook.ui.edit.EntityEditor;
import storybook.ui.memoria.MemoriaPanel;
import storybook.ui.panel.AbstractPanel;
import storybook.ui.panel.BlankPanel;
import storybook.ui.panel.attributes.AttributesViewPanel;
import storybook.ui.panel.book.BookPanel;
import storybook.ui.panel.chrono.ChronoPanel;
import storybook.ui.panel.info.InfoPanel;
import storybook.ui.panel.manage.ManagePanel;
import storybook.ui.panel.navigation.NavigationPanel;
import storybook.ui.panel.reading.ReadingPanel;
import storybook.ui.panel.tree.TreePanel;
import storybook.ui.plan.PlanPanel;
import storybook.ui.table.AbstractTable;
import storybook.ui.table.CategoryTable;
import storybook.ui.table.ChapterTable;
import storybook.ui.table.GenderTable;
import storybook.ui.table.IdeaTable;
import storybook.ui.table.InternalTable;
import storybook.ui.table.ItemLinkTable;
import storybook.ui.table.ItemTable;
import storybook.ui.table.LocationTable;
import storybook.ui.table.PartTable;
import storybook.ui.table.PersonTable;
import storybook.ui.table.RelationshipTable;
import storybook.ui.table.SceneTable;
import storybook.ui.table.StrandTable;
import storybook.ui.table.TagLinkTable;
import storybook.ui.table.TagTable;
import storybook.ui.table.TimeEventTable;

/**
 * @author martin
 *
 */
public class ViewFactory {

	//private boolean trace=false;
	private final MainFrame mainFrame;
	private final StringViewMap viewMap;
	private boolean initialisation;

	public ViewFactory(MainFrame mainFrame) {
		SbApp.trace("ViewFactory(mainFrame)");
		this.mainFrame = mainFrame;
		viewMap = new StringViewMap();
	}

	public void setInitialisation() {
		initialisation = true;
	}

	public void resetInitialisation() {
		initialisation = false;
	}

	public SbView getView(ViewName viewName) {
		SbApp.trace("ViewFactory.getView(" + viewName.name() + ")");
		SbView view = (SbView) viewMap.getView(viewName.toString());
		if (view != null) {
			return view;
		}
		if (viewName == ViewName.SCENES) {
			return getScenesView();
		} else if (viewName == ViewName.CHAPTERS) {
			return getChaptersView();
		} else if (viewName == ViewName.PARTS) {
			return getPartsView();
		} else if (viewName == ViewName.LOCATIONS) {
			return getLocationsView();
		} else if (viewName == ViewName.PERSONS) {
			return getPersonsView();
		} else if (viewName == ViewName.RELATIONSHIPS) {
			return getRelationshipsView();
		} else if (viewName == ViewName.GENDERS) {
			return getGendersView();
		} else if (viewName == ViewName.CATEGORIES) {
			return getCategoriesView();
		} else if (viewName == ViewName.STRANDS) {
			return getStrandsView();
		} else if (viewName == ViewName.IDEAS) {
			return getIdeasView();
		} else if (viewName == ViewName.TAGS) {
			return getTagsView();
		} else if (viewName == ViewName.ITEMS) {
			return getItemView();
		} else if (viewName == ViewName.TAGLINKS) {
			return getTagLinksView();
		} else if (viewName == ViewName.ITEMLINKS) {
			return getItemLinksView();
		} else if (viewName == ViewName.INTERNALS) {
			return getInternalsView();
		} else if (viewName == ViewName.CHRONO) {
			return getChronoView();
		} else if (viewName == ViewName.BOOK) {
			return getBookView();
		} else if (viewName == ViewName.MANAGE) {
			return getManageView();
		} else if (viewName == ViewName.READING) {
			return getReadingView();
		} else if (viewName == ViewName.MEMORIA) {
			return getMemoriaView();
		} else if (viewName == ViewName.EDITOR) {
			return getEditorView();
		} else if (viewName == ViewName.TREE) {
			return getTreeView();
		} else if (viewName == ViewName.INFO) {
			return getQuickInfoView();
		} else if (viewName == ViewName.NAVIGATION) {
			return getNavigationView();
		} else if (viewName == ViewName.CHART_PERSONS_BY_DATE) {
			return getChartPersonsByDate();
		} else if (viewName == ViewName.CHART_PERSONS_BY_SCENE) {
			return getChartPersonsByScene();
		} else if (viewName == ViewName.CHART_WiWW) {
			return getChartWiWW();
		} else if (viewName == ViewName.CHART_STRANDS_BY_DATE) {
			return getChartStrandsByDate();
		} else if (viewName == ViewName.CHART_OCCURRENCE_OF_PERSONS) {
			return getChartOccurrenceOfPersons();
		} else if (viewName == ViewName.CHART_OCCURRENCE_OF_LOCATIONS) {
			return getChartOccurrenceOfLocations();
		} else if (viewName == ViewName.CHART_GANTT) {
			return getChartGantt();
		} else if (viewName == ViewName.ATTRIBUTES) {
			return getAttributesView();
		} else if (viewName == ViewName.PLAN) {
			return getPlanView();
		} else if (viewName == ViewName.TIMEEVENT) {
			return getTimeEventView();
		}

		return null;
	}

	public SbView getView(String viewName) {
		SbApp.trace("ViewFactory.getView(" + viewName + ")");
		return (SbView) viewMap.getView(viewName);
	}

	public void loadView(SbView view) {
		if (view == null) {
			return;
		}
		SbApp.trace("ViewFactory.loadView(" + view.getName() + ")");
		AbstractPanel comp = new BlankPanel(mainFrame);
		boolean isTable = false;
		if (ViewName.CHRONO.compare(view)) {
			comp = new ChronoPanel(mainFrame);
		} else if (ViewName.BOOK.compare(view)) {
			comp = new BookPanel(mainFrame);
		} else if (ViewName.MANAGE.compare(view)) {
			comp = new ManagePanel(mainFrame);
		} else if (ViewName.READING.compare(view)) {
			comp = new ReadingPanel(mainFrame);
		} else if (ViewName.MEMORIA.compare(view)) {
			comp = new MemoriaPanel(mainFrame);
		} else if (ViewName.SCENES.compare(view)) {
			comp = new SceneTable(mainFrame);
			isTable = true;
		} else if (ViewName.CHAPTERS.compare(view)) {
			comp = new ChapterTable(mainFrame);
			isTable = true;
		} else if (ViewName.PARTS.compare(view)) {
			comp = new PartTable(mainFrame);
			isTable = true;
		} else if (ViewName.LOCATIONS.compare(view)) {
			comp = new LocationTable(mainFrame);
			isTable = true;
		} else if (ViewName.PERSONS.compare(view)) {
			comp = new PersonTable(mainFrame);
			isTable = true;
		} else if (ViewName.RELATIONSHIPS.compare(view)) {
			comp = new RelationshipTable(mainFrame);
			isTable = true;
		} else if (ViewName.GENDERS.compare(view)) {
			comp = new GenderTable(mainFrame);
			isTable = true;
		} else if (ViewName.CATEGORIES.compare(view)) {
			comp = new CategoryTable(mainFrame);
			isTable = true;
		} else if (ViewName.STRANDS.compare(view)) {
			comp = new StrandTable(mainFrame);
			isTable = true;
		} else if (ViewName.IDEAS.compare(view)) {
			comp = new IdeaTable(mainFrame);
			isTable = true;
		} else if (ViewName.TAGS.compare(view)) {
			comp = new TagTable(mainFrame);
			isTable = true;
		} else if (ViewName.ITEMS.compare(view)) {
			comp = new ItemTable(mainFrame);
			isTable = true;
		} else if (ViewName.TAGLINKS.compare(view)) {
			comp = new TagLinkTable(mainFrame);
			isTable = true;
		} else if (ViewName.ITEMLINKS.compare(view)) {
			comp = new ItemLinkTable(mainFrame);
			isTable = true;
		} else if (ViewName.CHART_PERSONS_BY_DATE.compare(view)) {
			comp = new PersonsByDateChart(mainFrame);
		} else if (ViewName.CHART_PERSONS_BY_SCENE.compare(view)) {
			comp = new PersonsBySceneChart(mainFrame);
		} else if (ViewName.CHART_WiWW.compare(view)) {
			comp = new WiWWChart(mainFrame);
		} else if (ViewName.CHART_STRANDS_BY_DATE.compare(view)) {
			comp = new StrandsByDateChart(mainFrame);
		} else if (ViewName.CHART_OCCURRENCE_OF_PERSONS.compare(view)) {
			comp = new OccurrenceOfPersonsChart(mainFrame);
		} else if (ViewName.CHART_OCCURRENCE_OF_LOCATIONS.compare(view)) {
			comp = new OccurrenceOfLocationsChart(mainFrame);
		} else if (ViewName.CHART_GANTT.compare(view)) {
			comp = new GanttChart(mainFrame);
		} else if (ViewName.ATTRIBUTES.compare(view)) {
			comp = new AttributesViewPanel(mainFrame);
		} else if (ViewName.TREE.compare(view)) {
			comp = new TreePanel(mainFrame);
		} else if (ViewName.INFO.compare(view)) {
			comp = new InfoPanel(mainFrame);
		} else if (ViewName.NAVIGATION.compare(view)) {
			comp = new NavigationPanel(mainFrame);
		} else if (ViewName.INTERNALS.compare(view)) {
			comp = new InternalTable(mainFrame);
		} else if (ViewName.PLAN.compare(view)) {
			comp = new PlanPanel(mainFrame);
		} else if (ViewName.TIMEEVENT.compare(view)) {
			comp = new TimeEventTable(mainFrame);
			isTable = true;
		}
		comp.initAll();
		view.load(comp);
		if (isTable && !initialisation) {
			loadTableDesign(view);
		}
	}

	public void unloadView(SbView view) {
		SbApp.trace("ViewFactory.unloadView(" + view.getName() + ")");
		boolean isTable;
		isTable = false;
		if (ViewName.SCENES.compare(view)) {
			isTable = true;
		} else if (ViewName.CHAPTERS.compare(view)) {
			isTable = true;
		} else if (ViewName.PARTS.compare(view)) {
			isTable = true;
		} else if (ViewName.LOCATIONS.compare(view)) {
			isTable = true;
		} else if (ViewName.PERSONS.compare(view)) {
			isTable = true;
		} else if (ViewName.RELATIONSHIPS.compare(view)) {
			isTable = true;
		} else if (ViewName.GENDERS.compare(view)) {
			isTable = true;
		} else if (ViewName.CATEGORIES.compare(view)) {
			isTable = true;
		} else if (ViewName.STRANDS.compare(view)) {
			isTable = true;
		} else if (ViewName.IDEAS.compare(view)) {
			isTable = true;
		} else if (ViewName.TAGS.compare(view)) {
			isTable = true;
		} else if (ViewName.ITEMS.compare(view)) {
			isTable = true;
		} else if (ViewName.TAGLINKS.compare(view)) {
			isTable = true;
		} else if (ViewName.ITEMLINKS.compare(view)) {
			isTable = true;
		} else if (ViewName.TIMEEVENT.compare(view)) {
			isTable = true;
		}
		if (isTable) {
			saveTableDesign(view);
		}
		view.unload();
	}

	private String getChartName(String i18nKey) {
		SbApp.trace("ViewFactory.getChartName(" + i18nKey + ")");
		return I18N.getMsg("msg.common.chart") + ": " + I18N.getMsg(i18nKey);
	}

	public SbView getChartPersonsByDate() {
		SbApp.trace("ViewFactory.getChartPersonsByDate()");
		if (isViewInitialized(ViewName.CHART_PERSONS_BY_DATE)) {
			SbView view = new SbView(getChartName("msg.menu.tools.charts.overall.character.date"));
			view.setName(ViewName.CHART_PERSONS_BY_DATE.toString());
			addExportButton(view);
//			addPrintButton(view);
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.CHART_PERSONS_BY_DATE.toString());
	}

	public SbView getChartPersonsByScene() {
		SbApp.trace("ViewFactory.getChartPersonsByScene()");
		if (isViewInitialized(ViewName.CHART_PERSONS_BY_SCENE)) {
			SbView view = new SbView(getChartName("msg.menu.tools.charts.part.character.scene"));
			view.setName(ViewName.CHART_PERSONS_BY_SCENE.toString());
			addExportButton(view);
//			addPrintButton(view);
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.CHART_PERSONS_BY_SCENE.toString());
	}

	public SbView getChartWiWW() {
		SbApp.trace("ViewFactory.getChartWiWW()");
		if (isViewInitialized(ViewName.CHART_WiWW)) {
			SbView view = new SbView(getChartName("msg.menu.tools.charts.overall.whoIsWhereWhen"));
			view.setName(ViewName.CHART_WiWW.toString());
			addExportButton(view);
//			addPrintButton(view);
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.CHART_WiWW.toString());
	}

	public SbView getChartStrandsByDate() {
		SbApp.trace("ViewFactory.getChartStrandsByDate()");
		if (isViewInitialized(ViewName.CHART_STRANDS_BY_DATE)) {
			SbView view = new SbView(getChartName("msg.menu.tools.charts.overall.strand.date"));
			view.setName(ViewName.CHART_STRANDS_BY_DATE.toString());
			addExportButton(view);
//			addPrintButton(view);
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.CHART_STRANDS_BY_DATE.toString());
	}

	public SbView getChartOccurrenceOfPersons() {
		SbApp.trace("ViewFactory.getChartOccurrenceOfPersons()");
		if (isViewInitialized(ViewName.CHART_OCCURRENCE_OF_PERSONS)) {
			SbView view = new SbView(getChartName("msg.menu.tools.charts.overall.character.occurrence"));
			view.setName(ViewName.CHART_OCCURRENCE_OF_PERSONS.toString());
			addExportButton(view);
//			addPrintButton(view);
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.CHART_OCCURRENCE_OF_PERSONS.toString());
	}

	public SbView getChartOccurrenceOfLocations() {
		SbApp.trace("ViewFactory.getChartOccurrenceOfLocations()");
		if (isViewInitialized(ViewName.CHART_OCCURRENCE_OF_LOCATIONS)) {
			SbView view = new SbView(getChartName("msg.menu.tools.charts.overall.location.occurrence"));
			view.setName(ViewName.CHART_OCCURRENCE_OF_LOCATIONS.toString());
			addExportButton(view);
//			addPrintButton(view);
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.CHART_OCCURRENCE_OF_LOCATIONS.toString());
	}

	public SbView getChartGantt() {
		SbApp.trace("ViewFactory.getChartGantt()");
		if (isViewInitialized(ViewName.CHART_GANTT)) {
			SbView view = new SbView(getChartName("msg.chart.gantt.characters.title"));
			view.setName(ViewName.CHART_GANTT.toString());
			addExportButton(view);
//			addPrintButton(view);
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.CHART_GANTT.toString());
	}

	public SbView getScenesView() {
		SbApp.trace("ViewFactory.getScenesView()");
		if (isViewInitialized(ViewName.SCENES)) {
			SbView view = new SbView(I18N.getMsg("msg.common.scenes"));
			view.setName(ViewName.SCENES.toString());
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.SCENES.toString());
	}

	public SbView getChaptersView() {
		SbApp.trace("ViewFactory.getChaptersView()");
		if (isViewInitialized(ViewName.CHAPTERS)) {
			SbView view = new SbView(I18N.getMsg("msg.common.chapters"));
			view.setName(ViewName.CHAPTERS.toString());
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.CHAPTERS.toString());
	}

	public SbView getPartsView() {
		SbApp.trace("ViewFactory.getPartsView()");
		if (isViewInitialized(ViewName.PARTS)) {
			SbView view = new SbView(I18N.getMsg("msg.common.parts"));
			view.setName(ViewName.PARTS.toString());
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.PARTS.toString());
	}

	public SbView getLocationsView() {
		SbApp.trace("ViewFactory.getLocationsView()");
		if (isViewInitialized(ViewName.LOCATIONS)) {
			SbView view = new SbView(I18N.getMsg("msg.common.locations"));
			view.setName(ViewName.LOCATIONS.toString());
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.LOCATIONS.toString());
	}

	public SbView getPersonsView() {
		SbApp.trace("ViewFactory.getPersonsView()");
		if (isViewInitialized(ViewName.PERSONS)) {
			SbView view = new SbView(I18N.getMsg("msg.common.persons"));
			view.setName(ViewName.PERSONS.toString());
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.PERSONS.toString());
	}

	public SbView getRelationshipsView() {
		SbApp.trace("ViewFactory.getRelationshipsView()");
		if (isViewInitialized(ViewName.RELATIONSHIPS)) {
			SbView view = new SbView(I18N.getMsg("msg.relationship"));
			view.setName(ViewName.RELATIONSHIPS.toString());
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.RELATIONSHIPS.toString());
	}

	public SbView getGendersView() {
		SbApp.trace("ViewFactory.getGendersView()");
		if (isViewInitialized(ViewName.GENDERS)) {
			SbView view = new SbView(I18N.getMsg("msg.common.genders"));
			view.setName(ViewName.GENDERS.toString());
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.GENDERS.toString());
	}

	public SbView getCategoriesView() {
		SbApp.trace("ViewFactory.getCategoriesView()");
		if (isViewInitialized(ViewName.CATEGORIES)) {
			SbView view = new SbView(I18N.getMsg("msg.persons.categories"));
			view.setName(ViewName.CATEGORIES.toString());
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.CATEGORIES.toString());
	}

	public SbView getStrandsView() {
		SbApp.trace("ViewFactory.getStrandsView()");
		if (isViewInitialized(ViewName.STRANDS)) {
			SbView view = new SbView(I18N.getMsg("msg.common.strands"));
			view.setName(ViewName.STRANDS.toString());
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.STRANDS.toString());
	}

	public SbView getIdeasView() {
		SbApp.trace("ViewFactory.getIdeasView()");
		if (isViewInitialized(ViewName.IDEAS)) {
			SbView view = new SbView(I18N.getMsg("msg.ideas.title"));
			view.setName(ViewName.IDEAS.toString());
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.IDEAS.toString());
	}

	public SbView getTagsView() {
		SbApp.trace("ViewFactory.getTagsView()");
		if (isViewInitialized(ViewName.TAGS)) {
			SbView view = new SbView(I18N.getMsg("msg.tags"));
			view.setName(ViewName.TAGS.toString());
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.TAGS.toString());
	}

	public SbView getItemView() {
		SbApp.trace("ViewFactory.getItemView()");
		if (isViewInitialized(ViewName.ITEMS)) {
			SbView view = new SbView(I18N.getMsg("msg.items"));
			view.setName(ViewName.ITEMS.toString());
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.ITEMS.toString());
	}

	public SbView getTagLinksView() {
		SbApp.trace("ViewFactory.getTagLinksView()");
		if (isViewInitialized(ViewName.TAGLINKS)) {
			SbView view = new SbView(I18N.getMsg("msg.tags.links"));
			view.setName(ViewName.TAGLINKS.toString());
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.TAGLINKS.toString());
	}

	public SbView getItemLinksView() {
		SbApp.trace("ViewFactory.getItemLinksView()");
		if (isViewInitialized(ViewName.ITEMLINKS)) {
			SbView view = new SbView(I18N.getMsg("msg.items.links"));
			view.setName(ViewName.ITEMLINKS.toString());
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.ITEMLINKS.toString());
	}

	public SbView getEditorView() {
		SbApp.trace("ViewFactory.getEditorView()");
		if (isViewInitialized(ViewName.EDITOR)) {
			EntityEditor editor = new EntityEditor(mainFrame);
			/* supression editor.initAll();*/
			SbView view = new SbView(I18N.getMsg("msg.common.editor"), editor);
			// view.getWindowProperties().setCloseEnabled(false);
			view.setName(ViewName.EDITOR.toString());
			// view.addListener(new DockingWindowAdapter() {
			// public void windowHidden(DockingWindow window) {
			// System.out.println("hidden");
			// }
			// });
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.EDITOR.toString());
	}

	public SbView getChronoView() {
		SbApp.trace("ViewFactory.getChronoView()");
		if (isViewInitialized(ViewName.CHRONO)) {
			SbView view = new SbView(I18N.getMsg("msg.menu.view.chrono"));
			view.setName(ViewName.CHRONO.toString());
			addOptionsButton(view);
			// addPrintButton(view);
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.CHRONO.toString());
	}

	public SbView getBookView() {
		SbApp.trace("ViewFactory.getBookView()");
		if (isViewInitialized(ViewName.BOOK)) {
			SbView view = new SbView(I18N.getMsg("msg.menu.view.book"));
			view.setName(ViewName.BOOK.toString());
			addOptionsButton(view);
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.BOOK.toString());
	}

	public SbView getManageView() {
		SbApp.trace("ViewFactory.getManageView()");
		if (isViewInitialized(ViewName.MANAGE)) {
			SbView view = new SbView(I18N.getMsg("msg.menu.view.manage"));
			view.setName(ViewName.MANAGE.toString());
			addOptionsButton(view);
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.MANAGE.toString());
	}

	public SbView getReadingView() {
		SbApp.trace("ViewFactory.getReadingView()");
		if (isViewInitialized(ViewName.READING)) {
			SbView view = new SbView(I18N.getMsg("msg.menu.view.reading"));
			view.setName(ViewName.READING.toString());
			addOptionsButton(view);
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.READING.toString());
	}

	public SbView getMemoriaView() {
		SbApp.trace("ViewFactory.getMemoriaView()");
		if (isViewInitialized(ViewName.MEMORIA)) {
			SbView view = new SbView(I18N.getMsg("msg.menu.view.pov"));
			view.setName(ViewName.MEMORIA.toString());
			addOptionsButton(view);
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.MEMORIA.toString());
	}

	public SbView getPlanView() {
		SbApp.trace("ViewFactory.getPlanView()");
		if (isViewInitialized(ViewName.PLAN)) {
			SbView view = new SbView(I18N.getMsg("msg.menu.view.plan"));
			view.setName(ViewName.PLAN.toString());
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.PLAN.toString());
	}

	public SbView getTimeEventView() {
		SbApp.trace("ViewFactory.getTimeEventView()");
		if (isViewInitialized(ViewName.TIMEEVENT)) {
			SbView view = new SbView(I18N.getMsg("msg.menu.view.timeline"));
			view.setName(ViewName.TIMEEVENT.toString());
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.TIMEEVENT.toString());
	}

	public SbView getTreeView() {
		SbApp.trace("ViewFactory.getTreeView()");
		if (isViewInitialized(ViewName.TREE)) {
			SbView view = new SbView(I18N.getMsg("msg.common.tree"));
			view.setName(ViewName.TREE.toString());
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.TREE.toString());
	}

	public SbView getQuickInfoView() {
		SbApp.trace("ViewFactory.getQuickInfoView()");
		if (isViewInitialized(ViewName.INFO)) {
			SbView view = new SbView(I18N.getMsg("msg.info.title"));
			view.setName(ViewName.INFO.toString());
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.INFO.toString());
	}

	public SbView getAttributesView() {
		SbApp.trace("ViewFactory.getAttributesView()");
		if (isViewInitialized(ViewName.ATTRIBUTES)) {
			SbView view = new SbView(I18N.getMsg("msg.common.attributes"));
			view.setName(ViewName.ATTRIBUTES.toString());
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.ATTRIBUTES.toString());
	}

	public SbView getNavigationView() {
		SbApp.trace("ViewFactory.getNavigationView()");
		if (isViewInitialized(ViewName.NAVIGATION)) {
			SbView view = new SbView(I18N.getMsg("msg.common.navigation"));
			view.setName(ViewName.NAVIGATION.toString());
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.NAVIGATION.toString());
	}

	public SbView getInternalsView() {
		SbApp.trace("ViewFactory.getInternalsView()");
		if (isViewInitialized(ViewName.INTERNALS)) {
			SbView view = new SbView("Internals");
			view.setName(ViewName.INTERNALS.toString());
			addRefreshButton(view);
			addSeparator(view);
			viewMap.addView(view.getName(), view);
		}
		return (SbView) viewMap.getView(ViewName.INTERNALS.toString());
	}

	@SuppressWarnings("unchecked")
	private void addRefreshButton(final SbView view) {
		//SbApp.trace("ViewFactory.addRefreshButton("+view.getName()+")");
		JButton bt = createMiniButton("icon.mini.refresh", "msg.common.refresh");
		bt.addActionListener((ActionEvent e) -> {
			mainFrame.setWaitingCursor();
			mainFrame.getBookController().refresh(view);
			mainFrame.setDefaultCursor();
		});
		view.getCustomTabComponents().add(bt);
	}

	@SuppressWarnings("unchecked")
	private void addOptionsButton(final SbView view) {
		//SbApp.trace("ViewFactory.addOptionsButton("+view.getName()+")");
		JButton bt = createMiniButton("icon.mini.options", "msg.common.options");
		bt.addActionListener((ActionEvent e) -> {
			mainFrame.getBookController().showOptions(view);
		});
		view.getCustomTabComponents().add(bt);
	}

	@SuppressWarnings("unchecked")
	private void addExportButton(final SbView view) {
		//SbApp.trace("ViewFactory.addExportButton("+view.getName()+")");
		JButton bt = createMiniButton("icon.mini.export", "msg.common.export");
		bt.addActionListener((ActionEvent e) -> {
			mainFrame.getBookController().export(view);
		});
		view.getCustomTabComponents().add(bt);
	}

	@SuppressWarnings({"unchecked", "unused"})
	private void addPrintButton(final SbView view) {
		//SbApp.trace("ViewFactory.addPrintButton("+view.getName()+")");
		JButton bt = createMiniButton("icon.mini.print", "msg.common.print");
		bt.addActionListener((ActionEvent e) -> {
			mainFrame.getBookController().print(view);
		});
		view.getCustomTabComponents().add(bt);
	}

	private JButton createMiniButton(String iconKey, String toolTipKey) {
		//SbApp.trace("ViewFactory.createMiniButton("+iconKey+","+toolTipKey+")");
		final JButton bt = new JButton(I18N.getIcon(iconKey));
		bt.setOpaque(false);
		bt.setBorder(null);
		bt.setBorderPainted(false);
		bt.setContentAreaFilled(false);
		bt.setToolTipText(I18N.getMsg(toolTipKey));
		return bt;
	}

	@SuppressWarnings("unchecked")
	private void addSeparator(View view) {
		//SbApp.trace("ViewFactory.addRefreshButton("+view.getName()+")");
		view.getCustomTabComponents().add(new JLabel("  "));
	}

	public StringViewMap getViewMap() {
		return viewMap;
	}

	private boolean isViewInitialized(ViewName viewName) {
		return viewMap.getView(viewName.toString()) == null;
	}

	public void saveAllTableDesign() {
		if (viewMap.getViewCount() == 0) {
			return;
		}
		for (int i = 0; i < viewMap.getViewCount(); i++) {
			saveTableDesign((SbView) viewMap.getViewAtIndex(i));
		}
	}

	private void saveTableDesign(SbView view) {
		SbApp.trace("ViewFactory.saveTableDesign(" + view.getName() + ")");
		try {
			AbstractTable comp = (AbstractTable) view.getComponent();
			JXTable table = comp.getTable();
			for (TableColumn col : table.getColumns(true)) {
				String l1 = "Table." + comp.getTableName() + "." + col.getHeaderValue();
				TableColumnExt ext = table.getColumnExt(col.getHeaderValue().toString());
				if (ext.isVisible()) {
					BookUtil.store(mainFrame, l1, Integer.toString(col.getPreferredWidth()));
				} else {
					BookUtil.store(mainFrame, l1, "hide");
				}
			}
		} catch (Exception e) {

		}
	}

	private void loadTableDesign(SbView view) {
		AbstractTable comp = (AbstractTable) view.getComponent();
		JXTable table = comp.getTable();
		for (TableColumn col : table.getColumns(true)) {
			String colName = (String) col.getHeaderValue();
			String l1 = "Table." + comp.getTableName() + "." + colName;
			if (!BookUtil.isKeyExist(mainFrame, l1)) {
				continue;
			}
			Internal internal = BookUtil.get(mainFrame, l1, "hide");
			if (internal == null) {
				continue;
			}
			TableColumnExt ext = table.getColumnExt(colName);
			if (internal.getStringValue().equals("hide")) {
				ext.setVisible(false);
			} else {
				ext.setVisible(true);
				col.setPreferredWidth(Integer.parseInt(internal.getStringValue()));
			}
		}
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
}

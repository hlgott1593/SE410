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

package storybook.ui.dialog.file;

import javax.swing.JLabel;
import javax.swing.JTextField;

import storybook.SbConstants.BookKey;
import storybook.model.DbFile;
import storybook.model.hbn.entity.Internal;
import storybook.toolkit.BookUtil;
import storybook.toolkit.I18N;
import storybook.ui.MainFrame;

@SuppressWarnings("serial")
public class ExportBookFileDialog extends AbstractFileDialog {

	private JTextField separator;

	public ExportBookFileDialog(MainFrame mainFrame) {
		super(mainFrame);
		setForceDbExtension(false);
		setTitle(I18N.getMsg("msg.file.export.book"));
		DbFile dbFile = mainFrame.getDbFile();
		file = dbFile.getFile();
		setDir(file.getParent());
		setFilename(dbFile.getName() + ".odt");
		
		setAskForOverwrite(true);
	}

	protected void addInformationLines() {

		JLabel lbName = new JLabel(I18N.getMsgColon("msg.common.title"));
		JLabel name = new JLabel();
		Internal internal = BookUtil.get(mainFrame, BookKey.TITLE, "");
		name.setText(internal.getStringValue());

		add(lbName);
		add(name);

		JLabel lbSubtitle = new JLabel(I18N.getMsgColon("msg.common.subtitle"));
		JLabel subtitle = new JLabel();
		internal = BookUtil.get(mainFrame, BookKey.SUBTITLE, "");
		subtitle.setText(internal.getStringValue());

		add(lbSubtitle);
		add(subtitle);

		JLabel lbAuthor = new JLabel(I18N.getMsgColon("msg.common.author_s"));
		JLabel author = new JLabel();
		internal = BookUtil.get(mainFrame, BookKey.AUTHOR, "");
		author.setText(internal.getStringValue());

		add(lbAuthor);
		add(author);

		JLabel lbSeparator = new JLabel("separator");
	    separator = new JTextField(30);
		separator.setText("***");
		separator.addCaretListener(this);

		add(lbSeparator);
		add(separator);
	}

	public String getSceneSeparator() {
		return separator.getText();
	}
}

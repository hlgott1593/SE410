package storybook.toolkit.swing.htmleditor;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import static javax.swing.Action.SMALL_ICON;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;
import javax.swing.text.html.HTML;

import storybook.toolkit.swing.SwingUtil;

//import shef.i18n.I18n;
import shef.ui.UIUtils;
import shef.ui.text.HTMLUtils;
import shef.ui.text.actions.HTMLTextEditAction;
import shef.ui.text.dialogs.ImageDialog;
import storybook.toolkit.I18N;

@SuppressWarnings("serial")
public class SbHTMLImageAction extends HTMLTextEditAction {

//	private static final I18n i18n = I18n.getInstance("storybook.toolkit.shef.shef");

	public SbHTMLImageAction() {
		super(I18N.getMsg("shef.image_")); //$NON-NLS-1$
		putValue(SMALL_ICON, UIUtils.getIcon(UIUtils.X16, "image.png")); //$NON-NLS-1$
		putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME));
	}

	@Override
	protected void sourceEditPerformed(ActionEvent e, JEditorPane editor) {
		ImageDialog d = createDialog(editor);
		// d.setSize(300, 300);
		d.setLocationRelativeTo(d.getParent());
		d.setVisible(true);
		if (d.hasUserCancelled())
			return;

		editor.requestFocusInWindow();
		editor.replaceSelection(d.getHTML());
	}

	@Override
	protected void wysiwygEditPerformed(ActionEvent e, JEditorPane editor) {
		// ImageDialog d = createDialog(editor);
		// // d.setSize(300, 300);
		// d.setLocationRelativeTo(d.getParent());
		// d.setVisible(true);
		// if (d.hasUserCancelled())
		// return;
		//
		// String tagText = d.getHTML();
		SbImageDialog dlg = new SbImageDialog(editor);
		SwingUtil.showModalDialog(dlg, editor);
		if (dlg.isCanceled()) {
			return;
		}
		String tagText = dlg.getHTML();
		if (editor.getCaretPosition() == editor.getDocument().getLength())
			tagText += "&nbsp;"; //$NON-NLS-1$

		editor.replaceSelection(""); //$NON-NLS-1$
		HTML.Tag tag = HTML.Tag.IMG;
		if (tagText.startsWith("<a")) //$NON-NLS-1$
			tag = HTML.Tag.A;

		HTMLUtils.insertHTML(tagText, tag, editor);
	}

	protected ImageDialog createDialog(JTextComponent ed) {
		Window w = SwingUtilities.getWindowAncestor(ed);
		ImageDialog d = null;
		if (w != null && w instanceof Frame)
			d = new ImageDialog((Frame) w);
		else if (w != null && w instanceof Dialog)
			d = new ImageDialog((Dialog) w);

		return d;
	}
}

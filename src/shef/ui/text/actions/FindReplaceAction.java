/*
 * Created on Jan 24, 2006
 *
 */
package shef.ui.text.actions;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Event;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JEditorPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import shef.ui.text.dialogs.TextFinderDialog;

import storybook.toolkit.I18N;

public class FindReplaceAction extends BasicEditAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final boolean isReplaceTab;
	private TextFinderDialog dialog;

	public FindReplaceAction(boolean isReplace) {
		super(null);
		if (isReplace) {
			putValue(NAME, I18N.getMsg("shef.replace_")); //$NON-NLS-1$
			putValue(MNEMONIC_KEY, (int) I18N.getMnemonic("shef.replace_"));
		} else {
			putValue(NAME, I18N.getMsg("shef.find_")); //$NON-NLS-1$
			putValue(MNEMONIC_KEY, (int) I18N.getMnemonic("shef.find_"));
			putValue(ACCELERATOR_KEY,
					KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK));
		}

		isReplaceTab = isReplace;
	}

	/* (non-Javadoc)
	 * @see shef.ui.text.actions.BasicEditAction#doEdit(java.awt.event.ActionEvent, javax.swing.JEditorPane)
	 */
	@Override
	protected void doEdit(ActionEvent e, JEditorPane textComponent) {
		Component c = SwingUtilities.getWindowAncestor(textComponent);
		if (dialog == null) {
			if (c instanceof Frame) {
				if (isReplaceTab) {
					dialog = new TextFinderDialog((Frame) c, textComponent, TextFinderDialog.REPLACE);
				} else {
					dialog = new TextFinderDialog((Frame) c, textComponent, TextFinderDialog.FIND);
				}
			} else if (c instanceof Dialog) {
				if (isReplaceTab) {
					dialog = new TextFinderDialog((Dialog) c, textComponent, TextFinderDialog.REPLACE);
				} else {
					dialog = new TextFinderDialog((Dialog) c, textComponent, TextFinderDialog.FIND);
				}
			} else {
				return;
			}
		}

        //if(textComponent.getSelectionStart() != textComponent.getSelectionEnd())
		//  dialog.setSearchText(textComponent.getSelectedText());
		if (!dialog.isVisible()) {
			dialog.show((isReplaceTab) ? TextFinderDialog.REPLACE : TextFinderDialog.FIND);
		}
	}

	@Override
	protected void updateContextState(JEditorPane editor) {
		if (dialog != null) {
			dialog.setJTextComponent(editor);
		}
	}

}

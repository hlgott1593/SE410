package shef.ui.text.actions;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JEditorPane;
import javax.swing.KeyStroke;
import storybook.toolkit.I18N;

/**
 * @author Bob Select all action
 */
public class SelectAllAction extends BasicEditAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public SelectAllAction() {
		super(I18N.getMsg("shef.select_all"));
		putValue(MNEMONIC_KEY, (int) I18N.getMnemonic("shef.select_all"));

		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));

		putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME));
	}

	/* (non-Javadoc)
	 * @see shef.ui.text.actions.BasicEditAction#doEdit(java.awt.event.ActionEvent, javax.swing.JEditorPane)
	 */
	@Override
	protected void doEdit(ActionEvent e, JEditorPane editor) {
		editor.selectAll();
	}
}

/*
 * Created on Nov 2, 2007
 */
package shef.ui.text.actions;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JEditorPane;
import javax.swing.KeyStroke;
import shef.ui.UIUtils;

import org.bushe.swing.action.ActionManager;
import org.bushe.swing.action.ShouldBeEnabledDelegate;
import storybook.toolkit.I18N;


/**
 * @author Bob Tantlinger
 *
 */
public class CopyAction extends BasicEditAction
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    
    public CopyAction()
    {
        super("");
        putValue(Action.NAME, I18N.getMsg("shef.copy"));
        putValue(Action.SMALL_ICON, UIUtils.getIcon(UIUtils.X16, "copy.png"));
        putValue(ActionManager.LARGE_ICON, UIUtils.getIcon(UIUtils.X24, "copy.png"));
        putValue(Action.ACCELERATOR_KEY,
            KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        putValue(MNEMONIC_KEY, (int) I18N.getMnemonic("shef.copy"));
        addShouldBeEnabledDelegate(new ShouldBeEnabledDelegate()
        {
			@Override
        	public boolean shouldBeEnabled(Action a)
            {                          
            	JEditorPane ed = getCurrentEditor();
            	return ed != null && ed.getSelectionStart() != ed.getSelectionEnd();
                //return true;
            }
        });
        putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME));
    }

    /* (non-Javadoc)
     * @see shef.ui.text.actions.BasicEditAction#doEdit(java.awt.event.ActionEvent, javax.swing.JEditorPane)
     */
	@Override
    protected void doEdit(ActionEvent e, JEditorPane editor)
    {
        editor.copy();
    }
    
	@Override
    protected void contextChanged()
    {
    	super.contextChanged();
    	this.updateEnabledState();
    }

}

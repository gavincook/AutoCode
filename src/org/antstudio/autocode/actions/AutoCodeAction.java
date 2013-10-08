package org.antstudio.autocode.actions;


import java.io.InputStream;
import java.util.Properties;

import org.antstudio.autocode.container.Container;
import org.antstudio.autocode.service.AutoCodeService;
import org.antstudio.autocode.ui.MainInterface;
import org.eclipse.core.internal.resources.File;
import org.eclipse.core.internal.resources.Project;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
/**
 * @author Gavin
 * @date 2013-10-6 上午12:26:27
 */
@SuppressWarnings("restriction")
public class AutoCodeAction implements IObjectActionDelegate {

	private Shell shell;
	
	private IWorkbenchPart part;  
	/**
	 * Constructor for Action1.
	 */
	public AutoCodeAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	    this.part = targetPart;
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		//打开并注册主界面
		MainInterface autoCodeScreen = new MainInterface();
		autoCodeScreen.init(shell);
		
		ISelection select =  part.getSite().getSelectionProvider().getSelection();
		if(TreeSelection.class.isInstance(select)){
			TreeSelection tSelect = (TreeSelection) select; 
			TreePath[] tpath =  tSelect.getPaths();
		    if(tpath[0].getLastSegment() instanceof File){
		    	Project project = (Project) tpath[0].getFirstSegment();
		    	File file = (File) tpath[0].getLastSegment();
		    	Container.register("baseDir", project.getLocation().toFile().getAbsolutePath());//项目绝对路径
		    	try {
					InputStream in = file.getContents();
					Properties p = new Properties();
					p.load(in);
					Container.register("autoCodeService", new AutoCodeService(p));
				} catch (Exception e) {
					e.printStackTrace();   
				}
		    }
		}
		
		/*MessageDialog.openInformation(
			shell,
			"AutoCode",
			"New Action was executed.");*/
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}

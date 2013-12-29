package org.antstudio.autocode.ui.event;

import java.util.HashMap;
import java.util.Map;

import org.antstudio.autocode.container.Container;
import org.antstudio.autocode.dialog.TreeDialog;
import org.antstudio.autocode.service.AutoCodeService;
import org.antstudio.autocode.sourceanalyzer.SourceAnalyze;
import org.antstudio.autocode.ui.MainInterface;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ActionAdapter extends MouseAdapter{

	private ButtonType type;
	private Shell shell;
	
	public ActionAdapter(ButtonType type){
		this.type = type;
		this.shell =  Container.get("shell",Shell.class);
	}

	@Override
	public void mouseUp(MouseEvent e) {
		switch (type) {
		case Generate://代码生成
			Map<String,Object> params = Container.get("mainInterface", MainInterface.class).getValues();
			AutoCodeService autoCodeService = Container.get("autoCodeService", AutoCodeService.class);
			if("Table".equals(params.get("type"))){//数据模式
				try {
					autoCodeService.generateCode(
							Container.get("mainInterface", MainInterface.class).getValues()
							);
				} catch ( Exception e1) {
					e1.printStackTrace();
				} 
			}else{//domain模式
				
				SourceAnalyze sa = new SourceAnalyze(params.get("domainPath").toString());
				Map<String,Object> data = new HashMap<String, Object>();
				data.putAll(params);
				try{
					data.put("columns", sa.getColumns());
					autoCodeService.generateCode(data);
				}catch (Exception ex) {
						ex.printStackTrace();
			         MessageBox messageBox = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.YES | SWT.NO);  
			         messageBox.setText("错误");  
			         messageBox.setMessage(ex.getLocalizedMessage());  
				}
				
			}
			break;
			
		case Cancel:
			this.shell.close();
			break;
		case DomainSelector:
			if(Container.get("fromProject", Boolean.class)){//从项目选择
				TreeDialog td = new TreeDialog(Display.getCurrent().getActiveShell(), Container.get("contextPath", String.class));
				Container.get("domainPath", Text.class).setText(td.open());
			}else{
				FileDialog fdg = new FileDialog(shell);
				fdg.setFilterExtensions(new String[]{"*.java"});
				Container.get("domainPath", Text.class).setText(fdg.open());
			}
			break;
		default:
			break;
		}
	}
 
}


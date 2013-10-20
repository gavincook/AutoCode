package org.antstudio.autocode.ui.event;

import java.io.IOException;
import java.net.URLClassLoader;

import org.antstudio.autocode.container.Container;
import org.antstudio.autocode.service.AutoCodeService;
import org.antstudio.autocode.ui.MainInterface;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Shell;

import freemarker.template.TemplateException;

public class ActionAdapter extends MouseAdapter{

	private ButtonType type;
	
	public ActionAdapter(ButtonType type){
		this.type = type;
	}

	@Override
	public void mouseUp(MouseEvent e) {
		if(type.equals(ButtonType.Generate)){
			try {
				Container.get("autoCodeService", AutoCodeService.class).prepareGenerateCode(
						Container.get("mainInterface", MainInterface.class).getValues()
						);
			} catch ( Exception e1) {
				e1.printStackTrace();
			} 
		}else if(type.equals(ButtonType.Cancel)){
			((Shell)Container.get("shell")).close();
		}
	}
 
}


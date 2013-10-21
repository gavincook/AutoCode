package org.antstudio.autocode.ui.event;

import java.lang.reflect.Field;
import java.util.Map;

import org.antstudio.autocode.classloader.FileSystemClassLoader;
import org.antstudio.autocode.container.Container;
import org.antstudio.autocode.service.AutoCodeService;
import org.antstudio.autocode.ui.MainInterface;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
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
		case Generate:
			Map<String,String> params = Container.get("mainInterface", MainInterface.class).getValues();
			if("Table".equals(params.get("type"))){//数据模式
				try {
					Container.get("autoCodeService", AutoCodeService.class).prepareGenerateCode(
							Container.get("mainInterface", MainInterface.class).getValues()
							);
				} catch ( Exception e1) {
					e1.printStackTrace();
				} 
			}else{//domain模式
				FileSystemClassLoader classLoader = new FileSystemClassLoader(params.get("baseDir"));
				System.out.println(params.get("baseDir"));
				try {
					System.out.println(params.get("domainName"));
					Class<?> c = classLoader.loadClass(params.get("domainName"));
					for(Field f:c.getDeclaredFields()){
						System.out.println(f.getName());
					}
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
			break;
			
		case Cancel:
			this.shell.close();
			break;
		
		case BasedirSelector:
			DirectoryDialog ddg = new DirectoryDialog(shell);
			Container.get("baseDir", Text.class).setText(ddg.open());
			Container.get("domainSelector", Button.class).setEnabled(true);
			break;
		case DomainSelector:
			FileDialog fdg = new FileDialog(shell);
			String baseDir = Container.get("baseDir", Text.class).getText().replaceAll("\\\\", "\\\\\\\\");
			fdg.setFilterPath(baseDir);
			fdg.setFilterExtensions(new String[]{"*.class"});
			Container.get("domainName", Text.class).setText(fdg.open().replaceAll(baseDir, "").replace(".class", ""));
			break;
		default:
			break;
		}
	}
 
}


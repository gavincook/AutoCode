package org.antstudio.autocode.ui;

import java.util.HashMap;
import java.util.Map;

import org.antstudio.autocode.container.Container;
import org.antstudio.autocode.ui.event.ActionAdapter;
import org.antstudio.autocode.ui.event.ButtonType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;


/**
 * @author Gavin
 * @date 2013-9-9 下午10:12:55
 */
public class MainInterface {

	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private Text packageName;
	private Combo combo;
	private Button btnCheckButton,btnCheckButton_1,btnRepository,btnJsp;
	private TabFolder tf;
	private Text baseDir,domainName;
	public void init(Shell parent){
		GridLayout layout = new GridLayout(2, false);
		Shell shell = new Shell(parent,SWT.SHEET);
		Container.register("shell", shell);
		shell.setText("Auto Code 1.0");
		shell.setSize(400, 500);
		shell.setLayout(layout);
		initComponent(shell);
		shell.open();
	}
	
	private void initComponent(final Shell shell){
			shell.setLayout(new GridLayout(2, false));
			
			/*----------------------tab--------------------------------*/
			tf = new TabFolder(shell, SWT.NONE);
			tf.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 9));
			
			//数据库模式tab
			TabItem dbTypeTab = new TabItem(tf, SWT.NONE);
			dbTypeTab.setText("Table");
			
			
			/*----------------------Domain模式tab--------------------------------*/
			Composite domainModeContainer = new Composite(tf, SWT.FILL);
			GridLayout domainLayout = new GridLayout(3, false);
			domainModeContainer.setLayout(domainLayout);
			
			//第一行
			Label baseDirLabel = new Label(domainModeContainer, SWT.NONE);
			baseDirLabel.setText("Base路径：");
			baseDirLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
			baseDir = new Text(domainModeContainer, SWT.BORDER);
			baseDir.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			Button baseDirSelector = new Button(domainModeContainer,SWT.NONE);
			baseDirSelector.setText("  选择  ");
			baseDirSelector.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			
			Container.register("baseDir", baseDir);
			Container.register("baseDirSelector", baseDirSelector);
			
			//第二行
			Label domainNameLabel = new Label(domainModeContainer, SWT.NONE);
			domainNameLabel.setText("类名:");
			domainNameLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
			domainName = new Text(domainModeContainer, SWT.BORDER);
			domainName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			Button domainSelector = new Button(domainModeContainer,SWT.NONE);
			domainSelector.setText("  选择  ");
			domainSelector.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			domainSelector.setEnabled(false);//默认为不可点击,需要先选择basedir
			
			Container.register("domainName", domainName);
			Container.register("domainSelector", domainSelector);
			
			//第三行
			Label packageLabel = new Label(domainModeContainer, SWT.NONE);
			packageLabel.setText("包名:");
			packageLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
			packageName = new Text(domainModeContainer, SWT.BORDER);
			packageName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
			
			//为两个选择按钮分别添加事件处理
			domainSelector.addMouseListener(new ActionAdapter(ButtonType.DomainSelector));
			baseDirSelector.addMouseListener(new ActionAdapter(ButtonType.BasedirSelector));
			
			//定义Domain模式tab
			TabItem domainTypeTab = new TabItem(tf, SWT.NONE);
			domainTypeTab.setText("Domain");
			domainTypeTab.setControl(domainModeContainer);
			/*----------------------/Domain模式tab--------------------------------*/
			
			
			Composite dbModeContainer = new Composite(tf, SWT.FILL);
			dbModeContainer.setLayout(new GridLayout(2, false));
			
			dbTypeTab.setControl(dbModeContainer);
			/*----------------------/tab--------------------------------*/

			Label label = new Label(dbModeContainer, SWT.SHADOW_IN | SWT.CENTER);
			label.setAlignment(SWT.RIGHT);
			GridData gd_label = new GridData(SWT.RIGHT, SWT.CENTER, false, true, 1, 1);
			gd_label.widthHint = 69;
			label.setLayoutData(gd_label);
			label.setText("数据库路径");
			
			text = new Text(dbModeContainer, SWT.BORDER);
			text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
			
			Label label_1 = new Label(dbModeContainer, SWT.NONE);
			label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, true, 1, 1));
			label_1.setText("\u7528\u6237\u540D");
			
			text_1 = new Text(dbModeContainer, SWT.BORDER);
			text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
			
			Label label_2 = new Label(dbModeContainer, SWT.NONE);
			label_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, true, 1, 1));
			label_2.setText("\u5BC6\u7801");
			
			text_2 = new Text(dbModeContainer, SWT.BORDER);
			text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
			
			Label label_3 = new Label(dbModeContainer, SWT.NONE);
			label_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, true, 1, 1));
			label_3.setText("\u9A71\u52A8");
			
			combo = new Combo(dbModeContainer, SWT.NONE);
			combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			
			Label label_4 = new Label(dbModeContainer, SWT.NONE);
			label_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, true, 1, 1));
			label_4.setText("\u8868\u540D");
			
			text_4 = new Text(dbModeContainer, SWT.BORDER);
			text_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
			
			Label label_5 = new Label(dbModeContainer, SWT.NONE);
			label_5.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, true, 1, 1));
			label_5.setText("\u5305\u8DEF\u5F84");
			
			text_5 = new Text(dbModeContainer, SWT.BORDER);
			text_5.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
			
			Label label_6 = new Label(dbModeContainer, SWT.NONE);
			label_6.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, true, 1, 1));
			label_6.setText("\u5B9E\u4F53\u540D\u79F0");
			
			text_6 = new Text(dbModeContainer, SWT.BORDER);
			text_6.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
			
			Group group = new Group(shell, SWT.NONE);
			group.setLayout(null);
			GridData gd_group = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
			gd_group.heightHint = 96;
			gd_group.widthHint = 325;
			group.setLayoutData(gd_group);
			group.setText("\u751F\u6210\u9009\u9879");
			
			btnCheckButton = new Button(group, SWT.CHECK);
			btnCheckButton.setBounds(68, 28, 98, 17);
			btnCheckButton.setText("domain");
			
			btnCheckButton_1 = new Button(group, SWT.CHECK);
			btnCheckButton_1.setBounds(198, 28, 131, 17);
			btnCheckButton_1.setText("service/serviceimpl");
			
			btnRepository = new Button(group, SWT.CHECK);
			btnRepository.setText("repository");
			btnRepository.setBounds(68, 64, 98, 17);
			
			btnJsp = new Button(group, SWT.CHECK);
			btnJsp.setText("jsp");
			btnJsp.setBounds(198, 64, 131, 17);
			
			Composite composite = new Composite(shell, SWT.NONE);
			composite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
			composite.setLayout(new GridLayout(3, false));
			
			Button btnNewButton = new Button(composite, SWT.NONE);
			GridData gd_btnNewButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_btnNewButton.widthHint = 100;
			btnNewButton.setLayoutData(gd_btnNewButton);
			btnNewButton.setText("\u751F\u6210");
			new Label(composite, SWT.NONE);
			
			Button button_2 = new Button(composite, SWT.NONE);
			GridData gd_button_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_button_2.widthHint = 100;
			button_2.setLayoutData(gd_button_2);
			button_2.setText("\u53D6\u6D88");
			
			
			Container.register("ui_dbPath", text);
			Container.register("ui_userName", text_1);
			Container.register("ui_password", text_2);
			Container.register("ui_tableName", text_4);
			Container.register("ui_package", text_5);
			Container.register("ui_entityName", text_6);
			Container.register("ui_driver", combo);
			Container.register("ui_domain", btnCheckButton);
			Container.register("ui_service", btnCheckButton_1);
			Container.register("ui_jsp", btnJsp);
			Container.register("ui_repository", btnRepository);
			
			Container.register("mainInterface", this);
			//添加事件
			btnNewButton.addMouseListener(new ActionAdapter(ButtonType.Generate));
			button_2.addMouseListener(new ActionAdapter(ButtonType.Cancel));
	}
	
	public Map<String,Object> getValues(){
		Map<String,Object> params = new HashMap<String, Object>();
		/*----------------------数据库配置信息--------------------------------*/
		params.put("dbPath", text.getText());
		params.put("userName", text_1.getText());
		params.put("password", text_2.getText());
		params.put("tableName", text_4.getText());
		params.put("package", text_5.getText());
		params.put("entityName", text_6.getText());
		params.put("driver", combo.getText());
		params.put("domain", btnCheckButton.getSelection()+"");
		params.put("service", btnCheckButton_1.getSelection()+"");
		params.put("repository", btnRepository.getSelection()+"");
		params.put("jsp", btnJsp.getSelection()+"");
		/*----------------------/数据库配置信息--------------------------------*/
		
		if("domain".equals(tf.getSelection()[0].getText())){
			params.put("package", packageName.getText());
		}
		
		params.put("type", tf.getSelection()[0].getText());//当前生成模式
		
		/*----------------------domain信息--------------------------------*/
		params.put("baseDir", baseDir.getText());
		params.put("domainName", domainName.getText().replaceAll("\\\\", ".").substring(1));
		/*----------------------/domain信息--------------------------------*/
		return params;
	}
	
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		new MainInterface().init(shell);
		while (!shell.isDisposed()){
		    if (!display.readAndDispatch()){ 
			display.sleep ();
		    }
		}
		display.dispose ();
	}
	
}

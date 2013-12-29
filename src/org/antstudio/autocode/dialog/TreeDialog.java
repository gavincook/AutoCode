package org.antstudio.autocode.dialog;

import java.io.File;

import org.antstudio.autocode.utils.Tools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
/**
 * 用于项目源码下domain选择(懒加载)
 * @author Gavin
 * @date 2013-11-23 下午8:26:20
 */
public class TreeDialog extends Dialog{

	private Shell shell;
	private String projectPath;
	private static String FOLDER_ICO = "folder_16.png",JAVA_ICO="java_16.png";
	private Tree tree;
	
	private String currentSelectSourcePath;
	public TreeDialog(Shell parent,String projectPath) {
		super(parent,SWT.NONE);
		this.shell = new Shell(parent, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		shell.setText("Domain选择");
		shell.setLayout(new GridLayout(1,false));
		shell.setSize(300,450);
		shell.setLocation(Tools.getCenterLocation(parent, new Point(300, 450)));
		this.projectPath = projectPath;
		init();
	}

	public void init(){
		tree = new Tree(shell, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gd = new GridData(SWT.FILL,SWT.FILL,true,true,1,1);
		gd.grabExcessVerticalSpace = true;
		tree.setLayoutData(gd);
		File project = new File(projectPath);
		for(File f:project.listFiles()){
			if(f.isDirectory()){
				TreeItem ti = new TreeItem(tree, SWT.NULL);
				ti.setData("path",f);
				ti.setText(f.getName());
				ti.setImage(new Image(shell.getDisplay(),this.getClass().getClassLoader().getResourceAsStream(FOLDER_ICO)));
				displayExpandIco(ti);
			}else if(f.getName().endsWith(".java")){
				TreeItem ti = new TreeItem(tree, SWT.NULL);
				ti.setText(f.getName());
				ti.setData("path",f);
				ti.setImage(new Image(shell.getDisplay(),this.getClass().getClassLoader().getResourceAsStream(JAVA_ICO)));
			}
		}
		tree.addListener(SWT.Expand, new Listener() {
			@Override
			public void handleEvent(Event event) {
				TreeItem item = (TreeItem)event.item;
				if(item.getData("loaded")==null){
					for(TreeItem i:item.getItems()){
						if(i.getData()==null){
							i.dispose();
						}
					}
					load((TreeItem)event.item);
				}
			}
		});
		addButtons();
	}
	
	/**
	 * 显示展开的图标，只对文件夹展示
	 * @param item
	 */
	private void displayExpandIco(TreeItem item){
		new TreeItem(item, SWT.NULL);//默认加一个节点，以显示父节点的展开图标
	}
	
	/**
	 * 加载当前item的子节点
	 * @param item
	 */
	private void load(TreeItem item){
		File baseDir = (File) item.getData("path");
		for(File f:baseDir.listFiles()){
			if(f.isDirectory()){
				TreeItem ti = new TreeItem(item, SWT.NULL);
				ti.setText(f.getName());
				ti.setImage(new Image(shell.getDisplay(), this.getClass().getClassLoader().getResourceAsStream(FOLDER_ICO)));
				displayExpandIco(ti);
				ti.setData("path",f);
			}else if(f.getName().endsWith(".java")){
				TreeItem ti = new TreeItem(item, SWT.NULL);
				ti.setText(f.getName());
				ti.setData("path",f);
				ti.setImage(new Image(shell.getDisplay(), this.getClass().getClassLoader().getResourceAsStream(JAVA_ICO)));
			}
		}
		item.setData("loaded", true);//标示该节点已经被加载
	}
	
	private void addButtons(){
		Composite btnGroup = new Composite(shell, SWT.NULL);
		
		RowData rd = new RowData();
		rd.width = 100;
		
		Button confirm = new Button(btnGroup,SWT.NONE );
		confirm.setText("确定");
		confirm.setLayoutData(rd);
		confirm.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				for(TreeItem ti:tree.getSelection()){
					currentSelectSourcePath = ((File)ti.getData("path")).getAbsolutePath();
					shell.close();
				}
			}
		});
		
		Button cancel = new Button(btnGroup,SWT.NONE );
		cancel.setText("取消");
		cancel.setLayoutData(rd);
		cancel.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				shell.close();
			}
		});
		GridData gd = new GridData(SWT.RIGHT,SWT.CENTER,false,false,1,1);
		btnGroup.setLayoutData(gd);
		btnGroup.setLayout(new RowLayout());
	}

	public String open(){
		shell.open();
		shell.layout();  
        Display display = shell.getDisplay();  
        while (!shell.isDisposed()) {  
            if (!display.readAndDispatch())  
                display.sleep();  
        } 
        shell.dispose();
        if(currentSelectSourcePath!=null){
        	return currentSelectSourcePath;
        }else{
        	return "";
        }
	}
}

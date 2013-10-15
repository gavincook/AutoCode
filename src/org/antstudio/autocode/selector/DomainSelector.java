package org.antstudio.autocode.selector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * 文件选择器
 * @author Gavin
 * @date 2013-10-10 下午11:07:32
 */
public class DomainSelector extends FileDialog{

	public DomainSelector(Shell parent) {
		super(parent,SWT.SINGLE);
	}

	public void analysis(){
		System.out.println(this.open());
	}
	
}

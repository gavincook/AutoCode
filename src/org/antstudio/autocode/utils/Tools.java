package org.antstudio.autocode.utils;


import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;

/**
 * 工具类
 * @author Gavin
 * @date 2013-11-24 下午6:09:32
 */
public class Tools {

	public static Point getCenterLocation(Shell shell,Point subPoint){
		Point point = shell.getLocation();
		Point size = shell.getSize();
		point.x = point.x-subPoint.x/2+size.x/2;
		point.y = point.y-subPoint.y/2+size.y/2;
		return point;
	}
	
}

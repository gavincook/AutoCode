package org.antstudio.autocode.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件系统类加载器
 * @author Gavin
 * @date 2013-10-20 下午4:07:00
 */
public class FileSystemClassLoader extends ClassLoader{
	  private String rootDir; 

	    public FileSystemClassLoader(String rootDir) { 
	    	super(getSystemClassLoader());
	        this.rootDir = rootDir; 
	    } 

	    protected Class<?> findClass(String name) throws ClassNotFoundException { 
	        byte[] classData = getClassData(name); 
	        if (classData == null) { 
	            throw new ClassNotFoundException(); 
	        } 
	        else { 
	            return defineClass(name, classData, 0, classData.length); 
	        } 
	    } 

	    private byte[] getClassData(String className) { 
	        String path = classNameToPath(className); 
	        try { 
	            InputStream ins = new FileInputStream(path); 
	            ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
	            int bufferSize = 4096; 
	            byte[] buffer = new byte[bufferSize]; 
	            int bytesNumRead = 0; 
	            while ((bytesNumRead = ins.read(buffer)) != -1) { 
	                baos.write(buffer, 0, bytesNumRead); 
	            } 
	            return baos.toByteArray(); 
	        } catch (IOException e) { 
	            e.printStackTrace(); 
	        } 
	        return null; 
	    } 

	    private String classNameToPath(String className) { 
	        return rootDir + File.separatorChar 
	                + className.replace('.', File.separatorChar) + ".class"; 
	    } 
}

package org.antstudio.autocode.sourceanalyzer;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Gavin
 * @Date Dec 26, 2013 5:14:19 PM
 * @see org.antstudio.autocode.annotation.Column
 */
public class ColumnDefinition {

    private String fieldName,aliasName,dbType,javaType;
    
    private static Map<String,String> JAVA_MAPPING_TO_DB = new HashMap<String,String>(){
        private static final long serialVersionUID = 1L;
    {
        put("String", "varchar");
        put("int", "int");
        put("Integer", "int");
        put("Long", "bigint");
        put("long", "bigint");
        put("double", "double");
        put("Double", "double");
        put("Float", "float");
        put("float", "float");
        put("Date", "date");
        put("boolean", "bit");
        put("Boolean", "bit");
    }};
    
    public ColumnDefinition(){}
    
    public ColumnDefinition(String fieldName,String aliasName,String javaType){
        this.fieldName = fieldName;
        this.aliasName = aliasName;
        this.javaType = javaType;
        this.dbType = JAVA_MAPPING_TO_DB.get(javaType);
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }
    
    public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.dbType = JAVA_MAPPING_TO_DB.get(javaType);
		this.javaType = javaType;
	}

	public String toString(){
        return "{fieldName:"+fieldName+",aliasName:"+aliasName+",dbType:"+dbType+"}";
    }
    
}

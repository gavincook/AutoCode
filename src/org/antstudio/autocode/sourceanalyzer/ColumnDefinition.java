package org.antstudio.autocode.sourceanalyzer;

/**
 * 
 * @author Gavin
 * @Date Dec 26, 2013 5:14:19 PM
 * @see org.antstudio.autocode.annotation.Column
 */
public class ColumnDefinition {

    private String fieldName,aliasName,dbType;
    
    public ColumnDefinition(){}
    
    public ColumnDefinition(String fieldName,String aliasName,String dbType){
        this.fieldName = fieldName;
        this.aliasName = aliasName;
        this.dbType = dbType;
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
    
    public String toString(){
        return "{fieldName:"+fieldName+",aliasName:"+aliasName+",dbType:"+dbType+"}";
    }
    
}

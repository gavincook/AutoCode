import javax.annotation.Resource;

import org.antstudio.autocode.annotation.Column;


public class Test {

	/* @Column
	 /*dadas*   */	@Resource private String a = "@Column//";/*ss*/
	@Deprecated
	private String no="测试字;符串";	@Column(name="id")
	
	@Resource
	
	
	
	
	
	
	
	
	
	
	
	
	private String id;
	
	@
	org
	.
	antstudio
	.
	
	autocode
	.
	annotation
	.
	Column
	
	
	
	
	
	
	
	(
			
			name
			
			=
	
			"姓名"
			
			)
	
	
	
	
	private
	
	
	
		
	String 
	
	
	
	
	name
	
	
	
	
	
	;
	
	private String test="@@@";
	
	public static void main(String[] args) {
		System.out.println(Test.class.getDeclaredFields()[0].isAnnotationPresent(Column.class));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

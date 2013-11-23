import javax.xml.bind.annotation.XmlAnyElement;

import org.antstudio.autocode.annotation.Column;


public class Test {

	@Column(name="id")
	private String id;
	
	@Column(name="ÐÕÃû")
	private String name;
	
	@XmlAnyElement
	@Column(name="ceshi")
	private String test;
	
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


import java.io.UnsupportedEncodingException;

import org.antstudio.autocode.annotation.Column;


public class Test {

	/**
	 * 你妹大阿什顿啊@Column
	 */
	@Column	(name="id")/*asdasd*///asdasdasd
	private String id;
	//sadasd
	@Column(name="名字")				/*asdasd*///asdasdasd
	private String name;//asdasdasd
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println("名".getBytes("GB2312").length);
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

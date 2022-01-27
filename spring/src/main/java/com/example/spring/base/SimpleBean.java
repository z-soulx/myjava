package base;

import annotation.Init;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * SimpleBean
 * 
 * @author skywalker
 *
 */
//开启扫描加入这个注解
@Component("simpleBean")
public class SimpleBean {
	//开启注解，不扫描只要生成bean都可以使用
	@Autowired(required = false)
	private Student student;

    public SimpleBean() {}
	
	public SimpleBean(Student student) {
		this.student = student;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public void send() {
		System.out.println(student.getName());
    	System.out.println("I am send method from SimpleBean!");
	}

    @Init
    public void init() {
        System.out.println("Init!");
    }
	
}

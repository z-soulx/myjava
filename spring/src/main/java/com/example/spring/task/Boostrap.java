package task;

import com.example.spring.base.SimpleBean;
import com.example.spring.base.SimpleBeanFactoryPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Boostrap {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config.xml");

    }
	
}

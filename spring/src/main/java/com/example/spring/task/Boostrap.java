package task;

import base.SimpleBean;
import base.SimpleBeanFactoryPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Boostrap {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config.xml");

    }
	
}

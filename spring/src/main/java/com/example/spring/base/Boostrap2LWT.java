package base;

import base.lwt.ConfigurableTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Boostrap2LWT {

	public static void main(String[] args) {
	        //jvm 加入参数
            // -javaagent:/Users/soulx/Desktop/PG/mavenRrpo/org/springframework/spring-instrument/5.1.9.RELEASE/spring-instrument-5.1.9.RELEASE.jar
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        ConfigurableTest test = new ConfigurableTest();
        test.send();

    }
	
}

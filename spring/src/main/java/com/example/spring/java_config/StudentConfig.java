package java_config;

import com.example.spring.base.Student;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotationMetadata;

/**
 * {@link base.Student}配置
 *
 * @author skywalker
 */
@Configuration
public class StudentConfig implements ImportAware {

    @Bean("student3")
    @Scope("prototype")
    public Student student() {
        Student student = new Student();
        student.setAge(22);
        student.setName("skywalker");
        return student;
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        System.out.println("importaware");
    }
}

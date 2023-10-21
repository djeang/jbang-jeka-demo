import dev.jeka.core.api.java.JkJavaProcess;
import dev.jeka.core.api.project.JkProject;
import dev.jeka.core.api.system.JkLog;
import dev.jeka.plugins.springboot.JkSpringbootProjectAdapter;

import java.nio.file.Path;

//DEPS dev.jeka:jeka-core:0.10.30
//DEPS dev.jeka:springboot-plugin:0.10.30
public class Build {

    public static void main(String[] args) {
        JkLog.setDecorator(JkLog.Style.BRACE);
        JkProject project = JkProject.of();
        project.flatFacade()
                .configureCompileDependencies(deps -> deps
                    .and("org.springframework.boot:spring-boot-starter-web")
                    .and("com.github.lalyos:jfiglet:0.0.8")
                )
                .configureTestDependencies(deps -> deps
                    .and("org.springframework.boot:spring-boot-starter-test")
                );

        JkSpringbootProjectAdapter springbootAdapter = JkSpringbootProjectAdapter.of()
                .setSpringbootVersion("3.1.4")
                .setCreateOriginalJar(false);
        springbootAdapter.configure(project);

        project.clean();
        Path bootJar = springbootAdapter.createBootJar(project); // compile, test and produce bootable jar

        System.out.println("Running server on http://localhost:8080 ...");
        JkJavaProcess.ofJavaJar(bootJar, null).setDestroyAtJvmShutdown(true).exec();
    }

}

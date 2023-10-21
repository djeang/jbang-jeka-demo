import dev.jeka.core.api.project.JkCompileLayout;
import dev.jeka.core.api.project.JkProject;
import dev.jeka.core.tool.JkBean;
import dev.jeka.core.tool.builtins.project.ProjectJkBean;
import dev.jeka.plugins.springboot.SpringbootJkBean;

// For the purpose of the demo, This file is not necessary as the project build
// configuration is described in Build.java
// However, we keep it here to generate intellij iml file, in accordance with the project dependencies
class JekaBuild extends JkBean {

    final SpringbootJkBean springbootBean = getBean(SpringbootJkBean.class);

    JekaBuild() {
        springbootBean.setSpringbootVersion("3.1.4");
        getBean(ProjectJkBean.class).lately(this::configure);
    }

    private void configure(JkProject project) {
        project.flatFacade()
            .mixResourcesAndSources()
            .setLayoutStyle(JkCompileLayout.Style.SIMPLE)
            .configureCompileDependencies(deps -> deps
                    .and("org.springframework.boot:spring-boot-starter-web")
                    .and("com.github.lalyos:jfiglet:0.0.8")
            )
            .configureTestDependencies(deps -> deps
                    .and("org.springframework.boot:spring-boot-starter-test")
            );
    }



}
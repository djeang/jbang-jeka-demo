import dev.jeka.core.api.java.JkJavaProcess;
import dev.jeka.core.api.project.JkProject;
import dev.jeka.core.tool.JkBean;
import dev.jeka.core.tool.JkDoc;
import dev.jeka.core.tool.JkInjectClasspath;
import dev.jeka.core.tool.builtins.project.ProjectJkBean;
import dev.jeka.plugins.springboot.SpringbootJkBean;

@JkInjectClasspath("dev.jeka:springboot-plugin")
class JekaBuild extends JkBean {

    final SpringbootJkBean springbootBean = getBean(SpringbootJkBean.class);

    JekaBuild() {
        springbootBean.setSpringbootVersion("3.1.4");
        getBean(ProjectJkBean.class).lately(this::configure);
    }

    private void configure(JkProject project) {
        project.flatFacade()
            .configureCompileDependencies(deps -> deps
                    .and("org.springframework.boot:spring-boot-starter-web")
            )
            .configureTestDependencies(deps -> deps
                    .and("org.springframework.boot:spring-boot-starter-test")
            );
        JkJavaProcess.ofJavaJar(project.artifactProducer.getMainArtifactPath(), null)
                .setDestroyAtJvmShutdown(true)
                .exec();
    }

    @JkDoc("Cleans, tests and creates bootable jar.")
    public void cleanPack() {
        cleanOutput(); springbootBean.projectBean.pack();
    }

}
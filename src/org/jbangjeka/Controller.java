package org.jbangjeka;

import com.github.lalyos.jfiglet.FigletFont;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
class Controller {

    @GetMapping(value = "/", produces = "text/plain")
    public String helloWorld() throws IOException {
        return FigletFont.convertOneLine("Hello JBang and JeKa");
    }

}



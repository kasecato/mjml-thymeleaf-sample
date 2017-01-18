package com.github.rkei.mjml.thymeleaf.controller;

import com.github.rkei.mjml.thymeleaf.service.HomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private final HomeService helloWorldService;
    private final ApplicationContext applicationContext;

    @Autowired
    public HomeController(HomeService helloWorldService, ApplicationContext applicationContext) {
        this.helloWorldService = helloWorldService;
        this.applicationContext = applicationContext;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void index(
            final Map<String, Object> model
            , final HttpServletRequest request
            , final HttpServletResponse response) {

        logger.debug("index() is executed!");

        // Thymeleaf Parameter
        model.put("title", helloWorldService.getTitle(""));

        // Spring Resolver
        final SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setApplicationContext(applicationContext);

        // Thymeleaf Engine
        final SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setEnableSpringELCompiler(true);
        engine.setTemplateResolver(resolver);

        // Context
        final ServletContext servletContext = request.getServletContext();
        final WebContext context = new WebContext(request, response, servletContext, request.getLocale(), model);

        // String
        final String ret = engine.process("index", context);
        logger.debug(ret);

        // Web
        try {
            engine.process("index", context, response.getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/hello/{name:.+}", method = RequestMethod.GET)
    public ModelAndView hello(@PathVariable("name") String name) {

        logger.debug("hello() is executed - $name {}", name);

        ModelAndView model = new ModelAndView();
        model.setViewName("index");

        model.addObject("title", helloWorldService.getTitle(name));

        return model;

    }

}
package com.github.rkei.mjml.thymeleaf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class HomeService {

    private static final Logger logger = LoggerFactory.getLogger(HomeService.class);

    public String getTitle(String name) {

        logger.debug("getTitle() is executed! $name : {}", name);

        if (StringUtils.isEmpty(name)) {
            return "Hello MJML!";
        } else {
            return "Hello " + name;
        }

    }

}
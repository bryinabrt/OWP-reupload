package com.ftn.PrviMavenVebProjekat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/baseURL")
public class BaseURLApliController {

    @Autowired
    private ServletContext servletContext;
    private String baseURL;

    @PostConstruct
    public void init() {
        baseURL = servletContext.getContextPath() + "/";
    }

    @GetMapping
    public Map<String, Object> baseURL() {
        Map<String, Object> odgovor = new LinkedHashMap<>();
        odgovor.put("status", "ok");
        odgovor.put("baseURL", baseURL);
        return odgovor;
    }
}

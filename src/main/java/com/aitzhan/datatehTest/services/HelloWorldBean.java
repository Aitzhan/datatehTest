package com.aitzhan.datatehTest.services;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Created by Aitzhan on 31.05.2016.
 */
@Stateless
@LocalBean
public class HelloWorldBean {
    public String sayHelloWorld() {
        return "Hello world!";
    }
}

package com.sg.flooringmastery;

import com.sg.flooringmastery.controller.Controller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @date July 1, 2019
 * @author Jack Elder
 */
public class App {
    private static final String PROD_MODE = "context-production.xml";
    private static final String TRAINING_MODE = "context-training.xml";
    
    public static void main(String[] args){
        ApplicationContext ctx = new ClassPathXmlApplicationContext(PROD_MODE);
        Controller controller = ctx.getBean("controller", Controller.class);
        controller.run();
    }
}

package com.library.appliweb.listeners;

import com.library.appliweb.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.RequestHandledEvent;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Component
public class IsAuthenticatedListener {

    @Autowired
    SecurityService securityService;

    @EventListener
    public void handleEvent (RequestHandledEvent e) {
        System.out.println("-- RequestHandledEvent --");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        
        if (!securityService.isAuthenticated()) {



        };
        System.out.println(e);
    }
}

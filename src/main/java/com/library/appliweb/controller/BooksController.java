package com.library.appliweb.controller;


import com.library.appliweb.beans.BookBean;
import com.library.appliweb.configuration.ApplicationPropertiesConfiguration;
import com.library.appliweb.proxies.BooksProxy;
import com.library.appliweb.service.EmpruntService;
import com.library.appliweb.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BooksController {

    @Autowired
    ApplicationPropertiesConfiguration appProperties;
    @Autowired
    SecurityService securityService;
    @Autowired
    private BooksProxy LivresProxy;
    @Autowired
    private EmpruntService empruntService;

    @RequestMapping("/")
    public String accueil(Model model, @RequestParam(required = false) Integer page) {
        if (!securityService.isAuthenticated()) return "security/login";

        if (page == null) page = 1;
        int size = appProperties.getLivresParPage();
        /** Attention, la numérotation des pages commence à 0 sur l'API !**/
        PagedResources<BookBean> livres = LivresProxy.listeDesLivres(page - 1, size);
        Long totalPages = livres.getMetadata().getTotalPages();
        List<Integer> pageNumbers = new ArrayList<>();
        int thePage = 1;
        while (thePage < totalPages) {
            pageNumbers.add(thePage);
            ++thePage;
        }

        model.addAttribute("livres", livres);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("currentPage", page);


        return "Accueil";
    }

    @GetMapping("/view")
    public String showOneBook(@RequestParam int bookId, Model model){

        Resource<BookBean> theBook = LivresProxy.recupererUnLivre(bookId);
        int exemplairesDispo = empruntService.findExemplairesDispo(bookId);
        model.addAttribute("livre", theBook.getContent());
        model.addAttribute("exemplairesDispo", exemplairesDispo);

        return "viewLivre";
    }
}

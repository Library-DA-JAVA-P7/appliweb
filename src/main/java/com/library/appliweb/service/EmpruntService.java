package com.library.appliweb.service;

import com.library.appliweb.beans.EmpruntBean;
import com.library.appliweb.beans.ExemplaireBean;
import com.library.appliweb.proxies.BooksProxy;
import com.library.appliweb.proxies.EmpruntsProxy;
import com.library.appliweb.proxies.ExemplaireProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class EmpruntService {

    @Autowired
    private EmpruntsProxy empruntsProxy;

    @Autowired
    private BooksProxy booksProxy;

    @Autowired
    private ExemplaireProxy exemplaireProxy;


    public Boolean isExemplaireDispo(int barcode) {
        Collection<EmpruntBean> emprunts = empruntsProxy.findByExemplaireBarcode(barcode).getContent();

        if (emprunts.isEmpty()) return true;
        Boolean returnValue = true;
        for (EmpruntBean emprunt : emprunts) {
            if (emprunt.getDateRetour() == null) {
                returnValue = false;
            }

        }
        return returnValue;
    }

    public Integer findExemplairesDispo(int bookId) {
        Collection<ExemplaireBean> exemplaireBeans = booksProxy.recupererExemplairesLivre(bookId).getContent();
        int exemplairesDispo = 0;
        for (ExemplaireBean exemplaireBean : exemplaireBeans) {

            if (isExemplaireDispo(exemplaireBean.getBarcode())) {

                exemplairesDispo++;
            }
        }

        return exemplairesDispo;
    }

    public PagedResources<EmpruntBean> getEmpruntsByUserId(String userId, int page, int size)
    {
        System.out.println("USER ID : " + userId +"\n" + "Page : " + page +"\n"+"Size : " + size);
        PagedResources<EmpruntBean> emprunts = empruntsProxy.findByUserId(userId, page,size);

        for (EmpruntBean emprunt:emprunts){
            System.out.println("Barcode : " + emprunt.getExemplaireBarcode());
            ExemplaireBean theExemplaire = exemplaireProxy.recupererExemplaire(emprunt.getExemplaireBarcode()).getContent();
            System.out.println(theExemplaire.toString());
            emprunt.setBookId(theExemplaire.getBook().getId());
            emprunt.setBookTitle(theExemplaire.getBook().getTitle());


        }


        return emprunts;
    }
}

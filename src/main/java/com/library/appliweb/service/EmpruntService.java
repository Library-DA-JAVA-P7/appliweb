package com.library.appliweb.service;

import com.library.appliweb.beans.EmpruntBean;
import com.library.appliweb.beans.ExemplaireBean;
import com.library.appliweb.proxies.BooksProxy;
import com.library.appliweb.proxies.EmpruntsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class EmpruntService {

    @Autowired
    private EmpruntsProxy empruntsProxy;

    @Autowired
    private BooksProxy booksProxy;


    public Boolean isExemplaireDispo(int barcode) {

        System.out.println("Code barre : " + barcode);
        Collection<EmpruntBean> emprunts = empruntsProxy.findByExemplaireBarcode(barcode).getContent();
        System.out.println(emprunts.toString());

        System.out.println(emprunts.toString());

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
}

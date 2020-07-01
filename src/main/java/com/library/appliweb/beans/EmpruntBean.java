package com.library.appliweb.beans;

import lombok.Data;

import java.util.Date;

@Data
public class EmpruntBean {
    private Long id;
    private String userId;
    private String exemplaireBarcode;
    private Date dateEmprunt;
    private Date dateRetour;
}

package com.example.studijske_opreme;

public class Opreme {


    private Integer id;

    private String oznaka;

    private String ime;

    private String opis;

    private String vrsta;






    public Opreme(Integer id, String oznaka, String ime, String opis, String vrsta) {
        this.id = id;
        this.oznaka = oznaka;
        this.ime = ime;
        this.opis = opis;
        this.vrsta = vrsta;

    }


    public Integer getId() {
        return id;
    }

    public String getOznaka() {
        return oznaka;
    }

    public String getIme() {
        return ime;
    }

    public String getOpis() {
        return opis;
    }

    public String getVrsta() {
        return vrsta;
    }


}

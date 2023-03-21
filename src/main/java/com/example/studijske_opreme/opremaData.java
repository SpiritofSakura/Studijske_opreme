package com.example.studijske_opreme;

public class opremaData {
    private Integer id;
    private String oznaka;
    private String ime;
    private String opis;
    private String studio;
    private String vrsta;
    private String datum_izposoje;

    public opremaData(Integer id, String oznaka, String ime, String vrsta, String studio, String opis) {
        this.id=id;
        this.oznaka = oznaka;
        this.ime = ime;
        this.opis = opis;
        this.studio = studio;
        this.vrsta = vrsta;
    }

    public opremaData(Integer id, String oznaka, String ime, String vrsta, String studio, String opis, String datum_izposoje) {
        this.id=id;
        this.oznaka = oznaka;
        this.ime = ime;
        this.opis = opis;
        this.studio = studio;
        this.vrsta = vrsta;
        this.datum_izposoje=datum_izposoje;
    }

    public Integer getId(){ return id; }
    public String getOznaka(){
        return oznaka;
    }

    public String getIme(){
        return ime;
    }

    public String getOpis(){
        return opis;
    }

    public String getStudio(){ return studio; }

    public String getVrsta(){
        return vrsta;
    }
    public String getDatum_izposoje(){
        return datum_izposoje;
    }
}

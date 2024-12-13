package com.untizio.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private IntegerProperty id;
    private StringProperty nome;
    private StringProperty cognome;
    private StringProperty dataNascita;
    private StringProperty classe;
    private List<Course> corsi;

    @JsonCreator
    public Student(@JsonProperty("id") int id, 
                   @JsonProperty("nome") String nome, 
                   @JsonProperty("cognome") String cognome, 
                   @JsonProperty("dataNascita") String dataNascita, 
                   @JsonProperty("classe") String classe) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.cognome = new SimpleStringProperty(cognome);
        this.dataNascita = new SimpleStringProperty(dataNascita);
        this.classe = new SimpleStringProperty(classe);
        this.corsi = new ArrayList<>();
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public String getCognome() {
        return cognome.get();
    }

    public void setCognome(String cognome) {
        this.cognome.set(cognome);
    }

    public StringProperty cognomeProperty() {
        return cognome;
    }

    public String getDataNascita() {
        return dataNascita.get();
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita.set(dataNascita);
    }

    public StringProperty dataNascitaProperty() {
        return dataNascita;
    }

    public String getClasse() {
        return classe.get();
    }

    public void setClasse(String classe) {
        this.classe.set(classe);
    }

    public StringProperty classeProperty() {
        return classe;
    }

    public List<Course> getCorsi() {
        return corsi;
    }

    public void aggiungiCorso(Course corso) {
        corsi.add(corso);
    }

    public void rimuoviCorso(Course corso) {
        corsi.remove(corso);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id.get() +
                ", nome=" + nome.get() +
                ", cognome=" + cognome.get() +
                ", dataNascita=" + dataNascita.get() +
                ", classe=" + classe.get() +
                ", corsi=" + corsi +
                '}';
    }
}
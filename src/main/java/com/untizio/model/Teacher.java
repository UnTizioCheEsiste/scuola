package com.untizio.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class Teacher {
    private IntegerProperty id;
    private StringProperty nome;
    private StringProperty cognome;
    private StringProperty materia;
    private List<Course> corsiInsegnati;

    public Teacher(int id, String nome, String cognome, String materia) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.cognome = new SimpleStringProperty(cognome);
        this.materia = new SimpleStringProperty(materia);
        this.corsiInsegnati = new ArrayList<>();
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

    public String getMateria() {
        return materia.get();
    }

    public void setMateria(String materia) {
        this.materia.set(materia);
    }

    public StringProperty materiaProperty() {
        return materia;
    }

    public List<Course> getCorsiInsegnati() {
        return corsiInsegnati;
    }

    public void aggiungiCorso(Course corso) {
        corsiInsegnati.add(corso);
    }

    public void rimuoviCorso(Course corso) {
        corsiInsegnati.remove(corso);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id.get() +
                ", nome=" + nome.get() +
                ", cognome=" + cognome.get() +
                ", materia=" + materia.get() +
                ", corsiInsegnati=" + corsiInsegnati +
                '}';
    }
}

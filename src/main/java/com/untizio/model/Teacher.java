package com.untizio.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Teacher {

    private final IntegerProperty id;
    private final StringProperty nome;
    private final StringProperty cognome;
    private final StringProperty materia;
    private final ObservableList<Course> corsiInsegnati;

    public Teacher() {
        this(0, null, null, null, FXCollections.observableArrayList());
    }

    @JsonCreator
    public Teacher(@JsonProperty("id") int id, @JsonProperty("nome") String nome, @JsonProperty("cognome") String cognome, @JsonProperty("materia") String materia, @JsonProperty("corsiInsegnati") List<Course> corsiInsegnati) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.cognome = new SimpleStringProperty(cognome);
        this.materia = new SimpleStringProperty(materia);
        this.corsiInsegnati = FXCollections.observableArrayList(corsiInsegnati != null ? corsiInsegnati : FXCollections.observableArrayList());
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

    public ObservableList<Course> getCorsiInsegnati() {
        return corsiInsegnati;
    }

    public void aggiungiCorso(Course corso) {
        corsiInsegnati.add(corso);
    }

    public void rimuoviCorso(Course corso) {
        corsiInsegnati.remove(corso);
    }

    public void setCorsi(List<Course> courses) {
        this.corsiInsegnati.setAll(courses != null ? courses : FXCollections.observableArrayList());
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
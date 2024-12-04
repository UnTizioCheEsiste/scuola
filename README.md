# Scuola - Gestione Scolastica

Un'applicazione per la gestione di studenti, insegnanti e corsi in una scuola. Questo progetto è stato sviluppato come esercizio per un esame universitario.

---

## Funzionalità Principali

### Gestione Studenti
- **Inserimento**: Aggiungi un nuovo studente con informazioni come nome, cognome, data di nascita e classe.
- **Modifica**: Aggiorna i dati di uno studente esistente.
- **Visualizzazione**: Cerca e visualizza le informazioni di uno studente per ID o nome.
- **Rimozione**: Elimina uno studente dal sistema.

### Gestione Insegnanti
- **Inserimento**: Aggiungi un nuovo insegnante con informazioni come nome, cognome e materia insegnata.
- **Modifica**: Aggiorna i dati di un insegnante esistente.
- **Visualizzazione**: Cerca e visualizza le informazioni di un insegnante per ID o nome.
- **Rimozione**: Elimina un insegnante dal sistema.

### Gestione Corsi
- **Creazione**: Crea un nuovo corso con nome, descrizione e informazioni sull'insegnante.
- **Modifica**: Aggiorna i dati di un corso esistente.
- **Visualizzazione**: Cerca e visualizza le informazioni di un corso per ID o nome.
- **Gestione Studenti nei Corsi**: 
  - Aggiungi o rimuovi studenti da un corso.
- **Gestione Insegnanti nei Corsi**: 
  - Assegna o rimuovi un insegnante da un corso.
- **Rimozione**: Elimina un corso dal sistema.

---

## Struttura del Progetto

### Classi Principali
1. **Studente (Student)**
   - Attributi:
     - `id`, `nome`, `cognome`, `dataNascita`, `classe`, `corsi`
   - Metodi:
     - Costruttore, Getters e Setters, `toString()`
     - `aggiungiCorso(Course corso)` e `rimuoviCorso(Course corso)`

2. **Insegnante (Teacher)**
   - Attributi:
     - `id`, `nome`, `cognome`, `materia`, `corsiInsegnati`
   - Metodi:
     - Costruttore, Getters e Setters, `toString()`
     - `aggiungiCorso(Course corso)` e `rimuoviCorso(Course corso)`

3. **Corso (Course)**
   - Attributi:
     - `id`, `nome`, `descrizione`, `insegnante`, `studentiIscritti`
   - Metodi:
     - Costruttore, Getters e Setters, `toString()`
     - `aggiungiStudente(Student studente)` e `rimuoviStudente(Student studente)`

---

## Tecnologie Utilizzate
- **Linguaggio**: Java
- **Gestione Progetto**: Maven
- **Versione Java**: 23

---

## Istruzioni per l'Installazione

1. Clona il repository:
   ```bash
   git clone https://github.com/UnTizioCheEsiste/scuola.git
   cd scuola

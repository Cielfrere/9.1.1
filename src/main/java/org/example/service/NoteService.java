package org.example.service;


public interface NoteService {
    void createNote();

    void listNotes();

    void removeNote();

    void exportNotes(String filename);

    void start();
}
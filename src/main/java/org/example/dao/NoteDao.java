package org.example.dao;

import org.example.model.Note;

import java.util.Collection;
import java.util.Set;
import java.io.*;

public interface NoteDao {
    void addNote(Note note);

    Collection<Note> getNotes(Set<String> filterLabels);

    boolean removeNote(int id);

    void exportNotes(String filename) throws IOException;

    void validateLabels(Set<String> labels) throws IllegalArgumentException;
}
package org.example;

import org.example.service.NoteService;
import org.example.service.NoteServiceImpl;

public class Main {
    public static void main(String[] args) {
        NoteService noteService = new NoteServiceImpl();
        noteService.start();
    }
}
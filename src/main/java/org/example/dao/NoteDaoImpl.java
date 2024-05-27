package org.example.dao;

import org.example.model.Note;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class NoteDaoImpl implements NoteDao {
    private List<Note> notes = new ArrayList<>();
    private Set<Integer> generatedIds = new HashSet<>();
    private static final Random random = new Random();

    private int generateId() {
        int id;
        do {
            id = random.nextInt(Integer.MAX_VALUE);
        } while (generatedIds.contains(id));
        generatedIds.add(id);
        return id;
    }

    @Override
    public void addNote(Note note) {
        note.setId(generateId());
        notes.add(note);
    }

    @Override
    public Collection<Note> getNotes(Set<String> labels) {
        if (labels == null || labels.isEmpty()) {
            return new ArrayList<>(notes);
        }
        return notes.stream()
                .filter(note -> note.getLabels().stream().anyMatch(labels::contains))
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeNote(int id) {
        return notes.removeIf(note -> note.getId() == id);
    }

    @Override
    public void exportNotes(String filename) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String formattedDateTime = now.format(formatter);
        String finalFilename = "C:\\Users\\Diu_Brando\\Desktop\\Tuca\\" + filename + "_" + formattedDateTime + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(finalFilename))) {
            for (Note note : notes) {
                writer.write(note.getId() + "#" + note.getText() + "\n");
                writer.write(String.join(";", note.getLabels()) + "\n");
                writer.write("===================\n");
            }
        }
    }

    @Override
    public void validateLabels(Set<String> labels) {
        for (String label : labels) {
            if (!label.matches("[a-zA-Z]+")) {
                throw new IllegalArgumentException("Метки должны состоять только из букв: " + label);
            }
        }
    }
    }
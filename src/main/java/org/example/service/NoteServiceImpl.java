package org.example.service;

import org.example.dao.NoteDao;
import org.example.dao.NoteDaoImpl;
import org.example.dao.Commands;
import lombok.*;
import org.example.model.Note;

import java.io.*;
import java.util.*;
import java.util.logging.*;

@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private NoteDao noteDao = new NoteDaoImpl();
    private Scanner scanner = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger(NoteServiceImpl.class.getName());

    private void validateNoteText(String text) {
        if (text.length() < 3) {
            throw new IllegalArgumentException("Текст заметки должен быть длиннее 3 символов.");
        }
    }

    @Override
    public void createNote() {
        try {
            System.out.print("Введите заметку: ");
            String text = scanner.nextLine().trim();
            validateNoteText(text);
            System.out.print("Добавить метки? Метки состоят из одного слова и могут содержать только буквы." +
                    " Для добавления нескольких меток разделяйте слова пробелом: ");
            String labelsInput = scanner.nextLine().trim();
            Set<String> labels = new HashSet<>(Arrays.asList(labelsInput.split("\\s+")));
            noteDao.validateLabels(labels);

            Note note = new Note(text, labels);
            noteDao.addNote(note);
            System.out.println("Заметка добавлена");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
            logger.log(Level.INFO, e.getMessage());
        }
    }

    private Set<String> parseLabels() {
        System.out.print("Введите метки, чтобы отобразить определенные заметки или оставьте пустым для" +
                " отображения всех заметок: ");
        String labelsInput = scanner.nextLine().trim();
        Set<String> labels = new HashSet<>();
        if (!labelsInput.isEmpty()) {
            labels = new HashSet<>(Arrays.asList(labelsInput.split("\\s+")));
            noteDao.validateLabels(labels);
        }
        return labels;
    }

    @Override
    public void listNotes() {
        try {
            Set<String> labels = parseLabels();
            Collection<Note> notes = noteDao.getNotes(labels);
            if (notes.isEmpty()) {
                System.out.println("Заметки не найдены.");
            } else {
                notes.forEach(note -> System.out.println(note.toString()));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
            logger.log(Level.INFO, e.getMessage());
        }
    }

    @Override
    public void removeNote() {
        try {
            System.out.print("Введите id удаляемой заметки: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            if (!noteDao.removeNote(id)) {
                System.out.println("Заметка не найдена.");
            } else {
                System.out.println("Заметка удалена.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ID должен быть числом.");
            logger.log(Level.INFO, "ID должен быть числом.", e);
        }
    }

    @Override
    public void exportNotes(String filename) {
        try {
            noteDao.exportNotes(filename);
            System.out.println("Заметки экспортированы в файл: " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка при экспорте заметок: " + e.getMessage());
            logger.log(Level.WARNING, "Ошибка при экспорте заметок.", e);
        }
    }

    @Override
    public void start() {
        System.out.println("Это Ваша записная книжка. Вот список доступных команд: help, note-new, note-list," +
                " note-remove, note-export, exit.");
        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().trim();
            switch (command) {
                case "help":
                    Commands.printHelp();
                    break;
                case "note-new":
                    createNote();
                    break;
                case "note-list":
                    listNotes();
                    break;
                case "note-remove":
                    removeNote();
                    break;
                case "note-export":
                    System.out.print("Введите имя файла: ");
                    String filename = scanner.nextLine().trim();
                    exportNotes(filename);
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Команда не найдена.");
                    logger.log(Level.WARNING, "Команда не найдена: " + command);
            }
        }
    }
}
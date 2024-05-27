package org.example.dao;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum Commands {
    HELP("help", "выводит на экран список доступных команд с их описанием"),
    NOTE_NEW("note-new", "создать новую заметку"),
    NOTE_LIST("note-list", "выводит все заметки на экран"),
    NOTE_REMOVE("note-remove", "удаляет заметку"),
    NOTE_EXPORT("note-export", "сохраняет все заметки в текстовый файл и выводит имя сохраненного файла"),
    EXIT("exit", "выход из приложения");

    private final String command;
    private final String description;

    public static void printHelp() {
        for (Commands cmd : Commands.values()) {
            System.out.println(cmd.getCommand() + " - " + cmd.getDescription());
        }
    }
}
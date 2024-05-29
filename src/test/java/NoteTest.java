import org.example.dao.NoteDaoImpl;
import org.example.model.Note;
import org.example.service.NoteServiceImpl;

import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Arrays;


public class NoteTest {
    @InjectMocks
    private NoteServiceImpl noteService;

    @Mock
    private Scanner scanner;

    @Mock
    private NoteDaoImpl noteDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Создание заметки из введенных данных")
    public void testCreateNote_Success() {
        when(scanner.nextLine())
                .thenReturn("test")
                .thenReturn("TEST1 TEST2");

        noteService.createNote();

        verify(noteDao, times(1)).validateLabels(new HashSet<>(Arrays.asList("TEST1", "TEST2")));
        verify(noteDao, times(1)).addNote(
                argThat(note ->
                        note.getText().equals("test") &&
                                note.getLabels().containsAll(Arrays.asList("TEST1", "TEST2"))
                )
        );
    }

    @Test
    @DisplayName("Все генерируемые id уникальны")
    public void testGenerateUniqueIds() {
        Set<Integer> generatedIds = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            Note note = new Note("Кыштымский карлик", new HashSet<>());
            noteDao.addNote(note);
            assertFalse(generatedIds.contains(note.getId()));
            generatedIds.add(note.getId());
        }
    }

}
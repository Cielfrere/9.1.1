import org.example.dao.NoteDaoImpl;
import org.example.model.Note;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class NoteTestValidation {
    private NoteDaoImpl noteDao;

    @BeforeEach
    public void setUp() {
        noteDao = new NoteDaoImpl();
    }

    @Test
    @DisplayName("Корректность ввода меток")
    public void testValidateLabels_ValidLabels() {
        Set<String> validLabels = new HashSet<>();
        validLabels.add("test");
        validLabels.add("set");

        assertDoesNotThrow(() -> noteDao.validateLabels(validLabels));
    }

    @Test
    @DisplayName("Некорректные метки не проходят")
    public void testValidateLabels_InvalidLabels() {
        Set<String> invalidLabels = new HashSet<>();
        invalidLabels.add("work1");
        invalidLabels.add("123Invalid");

        assertThrows(IllegalArgumentException.class, () -> noteDao.validateLabels(invalidLabels));
    }

    @Test
    @DisplayName("Корректность ввода id при удалении")
    public void testValidateNoteId() {
        Note note = new Note("test", new HashSet<>());
        noteDao.addNote(note);
        int id = note.getId();

        assertTrue(noteDao.removeNote(id));
        assertFalse(noteDao.removeNote(id));
    }

    @Test
    @DisplayName("Корректность тела заметки")
    public void testNoteBodyValidation() {
        Note note = new Note("Valid Note Text", new HashSet<>());
        noteDao.addNote(note);

        assertNotNull(note.getText());
        assertFalse(note.getText().isEmpty());
    }
}
import org.example.dao.NoteDao;
import org.example.dao.NoteDaoImpl;
import org.example.model.Note;
import org.example.service.NoteServiceImpl;
import org.example.service.NoteService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.mockito.*;


import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class NoteTest {
    private NoteServiceImpl noteService;
    @Mock
    private NoteDao noteDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        noteService = new NoteServiceImpl();
        noteDao = new NoteDaoImpl();
    }

    @Test
    public void testGenerateUniqueIds() {
        Set<Integer> generatedIds = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            Note note = new Note("Кыштымский карлик", new HashSet<>());
            noteDao.addNote(note);
            assertFalse(generatedIds.contains(note.getId()));
            generatedIds.add(note.getId());
        }
    }


    @Test
    public void testValidateLabels() {
        Set<String> validLabels = new HashSet<>();
        validLabels.add("work");

        Set<String> invalidLabels = new HashSet<>();
        invalidLabels.add("work");
        invalidLabels.add("123Invalid");

        noteDao.validateLabels(validLabels);

        assertThrows(IllegalArgumentException.class, () -> {
            noteDao.validateLabels(invalidLabels);
        });
    }

    @Test
    public void testValidateNoteId() {
        Note note = new Note("Древесный гузлик", new HashSet<>());
        noteDao.addNote(note);
        int id = note.getId();

        assertTrue(noteDao.removeNote(id));
        assertFalse(noteDao.removeNote(id));
    }

}
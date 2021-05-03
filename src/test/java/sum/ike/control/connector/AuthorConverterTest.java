package sum.ike.control.connector;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sum.ike.model.Author;

import java.util.List;


class AuthorConverterTest {

    private AuthorConverter authorConverter;
    private Author actualAuthor;

    @BeforeEach
    void setUp() {
        authorConverter = new AuthorConverter();
        actualAuthor = new Author(22, "first", "last");
    }

    @Test
    @DisplayName("check correctly parsing Author-object into AuthorX-Object")
    void testConvertForBook () {

        AuthorX actualAuthorX = authorConverter.convertForBook(actualAuthor);
        assertAll("authorX",
                () -> assertEquals(22, actualAuthorX.getAuthor_id()),
                () -> assertEquals("First", actualAuthorX.getFirst_name()),
                () -> assertEquals("Last", actualAuthorX.getLast_name()));
    }

}

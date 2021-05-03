package sum.ike.control.connector;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sum.ike.model.Author;


class AuthorConverterTest {

    private AuthorConverter authorConverter;
    private Author actualAuthor;

    @BeforeEach
    void setUp() {
        authorConverter = new AuthorConverter();
        actualAuthor = new Author(22, "first", "last");
    }

    @Test
    void convertForBook () {

        AuthorX actualAuthorX = authorConverter.convertForBook(actualAuthor);
        assertAll("author",
                () -> assertEquals(22, actualAuthorX.getAuthor_id()),
                () -> assertEquals("First", actualAuthorX.getFirst_name()),
                () -> assertEquals("Last", actualAuthorX.getLast_name()));
    }
}

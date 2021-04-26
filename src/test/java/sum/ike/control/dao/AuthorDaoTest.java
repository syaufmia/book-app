package sum.ike.control.dao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDaoTest {

    @Test
    void addNew () {
        AuthorDao aDao = new AuthorDao();

        aDao.addNew("Testvorname", "Testnachname");
        assertEquals(1, aDao.getLastAuthor().getAuthorId());
        aDao.addNew("Testzweivorname", "Testzweinachname");
        assertEquals(2, aDao.getLastAuthor().getAuthorId());
    }
}

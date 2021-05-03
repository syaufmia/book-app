package sum.ike.control.utils;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringTrimmerTest {

    @Test
    @DisplayName("check correct trimming of any String")
    void testTrim () {
        String expectedString = "This Text Should Be Trimmed";
        String actualString = "THIS text SHOUld be    TRIMMED   ";
        assertEquals(expectedString, StringTrimmer.trim(actualString));

    }
}

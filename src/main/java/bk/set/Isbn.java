package bk.set;

import java.io.Serializable;
import java.util.Objects;

public class Isbn implements Serializable {

    private static final long serialVersionUID = 3L;
    String isbn;
    long isbnL;

    public String getIsbn () {
        return isbn;
    }

    public long getIsbnL () {
        return isbnL;
    }

    public Isbn (String isbn) {
        this.isbn = isbn;
        this.isbnL = convertIsbnToL(isbn);

    }


    /*
    ^
(?=                       # Basic format pre-checks (lookahead):
  [0-9]{13}$              #   Require 13 digits (no separators).
 |                        #  Or:
  (?=(?:[0-9]+[-\ ]){4})  #   Require 4 separators
  [-\ 0-9]{17}$           #     out of 17 characters total.
)                         # End format pre-checks.
97[89][-\ ]?              # ISBN-13 prefix.
[0-9]{1,5}[-\ ]?          # 1-5 digit group identifier.
[0-9]+[-\ ]?[0-9]+[-\ ]?  # Publisher and title identifiers.
[0-9]                     # Check digit.
$
     */
    public long convertIsbnToL(String isbn) { //TODO: advanced ISBN Converter with  Prefix-language-publisherInfo-TitleInfo-checkDigit
    if (isbn.matches(
            "(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]$")) {
        String newIsbn = "";
        for (String element : isbn.split("-")) {
            newIsbn = newIsbn.concat(element);
            isbnL = Long.parseLong(newIsbn);
        }
    }
    else {
        System.out.print("Invalid ISBN.");
        isbnL = 0;
    }
        return isbnL;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (!(o instanceof Isbn)) return false;
        Isbn isbn1 = (Isbn) o;
        return isbnL == isbn1.isbnL &&
                isbn.equals(isbn1.isbn);
    }

    @Override
    public int hashCode () {
        return Objects.hash(isbnL);
    }
}

package nl.miwgroningen.se6.heartcoded.CaTo.dto;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Catches jason from an ajax request to get the keywords.
 */

public class CircleSearchDTO {

    private String keywords;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}

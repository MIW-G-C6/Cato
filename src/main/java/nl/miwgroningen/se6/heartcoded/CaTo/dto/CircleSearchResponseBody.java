package nl.miwgroningen.se6.heartcoded.CaTo.dto;

import java.util.List;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
 *
 * Contains a list of circles found with ajax search funtion.
 */

public class CircleSearchResponseBody {

    private String msg;
    private List<CircleClientDTO> circles;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<CircleClientDTO> getCircles() {
        return circles;
    }

    public void setCircles(List<CircleClientDTO> circles) {
        this.circles = circles;
    }
}

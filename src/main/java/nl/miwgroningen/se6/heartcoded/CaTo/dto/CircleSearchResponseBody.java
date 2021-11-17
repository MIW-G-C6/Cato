package nl.miwgroningen.se6.heartcoded.CaTo.dto;

import java.util.List;

/**
 * @author Paul Romkes <p.r.romkes@gmail.com
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

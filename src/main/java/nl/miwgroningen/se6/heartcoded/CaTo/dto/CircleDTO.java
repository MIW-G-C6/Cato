package nl.miwgroningen.se6.heartcoded.CaTo.dto;

/**
 * @author Remco Lantinga <remco_lantinga@hotmail.com>
 * DTO version of model Circle
 */
public class CircleDTO {

    private Integer circleId;

    private String circleName;

    public CircleDTO(String circleName) {
        this.circleName = circleName;
    }

    public CircleDTO() {
    }

    public Integer getCircleId() {
        return circleId;
    }

    public void setCircleId(Integer circleId) {
        this.circleId = circleId;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }
}

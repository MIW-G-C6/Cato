package nl.miwgroningen.se6.heartcoded.CaTo.dto;

import java.util.List;

/**
 * @author Remco Lantinga <remco_lantinga@hotmail.com>
 * DTO version of model Circle
 */
public class CircleClientDTO {

    private Integer circleId;

    private String circleName;

    private List<MemberDTO> clientList;

    public CircleClientDTO(Integer circleId, String circleName, List<MemberDTO> clientList) {
        this.circleId = circleId;
        this.circleName = circleName;
        this.clientList = clientList;
    }

    public CircleClientDTO() {
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

    public List<MemberDTO> getClientList() {
        return clientList;
    }

    public void setClientList(List<MemberDTO> clientList) {
        this.clientList = clientList;
    }
}

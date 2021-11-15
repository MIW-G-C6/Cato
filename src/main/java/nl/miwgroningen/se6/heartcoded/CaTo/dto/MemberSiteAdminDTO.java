package nl.miwgroningen.se6.heartcoded.CaTo.dto;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 *     DTO version of Member model with the information needed for the site admin dashboard
 */
public class MemberSiteAdminDTO {
    private Integer userId;

    private String userName;

    private Integer circleId;

    private String circleName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

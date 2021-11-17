package nl.miwgroningen.se6.heartcoded.CaTo.dto;

import java.util.List;

/**
 * @author Erwin Wegter <ewegter@gmail.com>
 *
 * Contains a list of users found with ajax search function.
 */

public class UserSearchAjaxResponseBody {

    private String msg;
    private List<UserDTO> users;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}

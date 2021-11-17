package nl.miwgroningen.se6.heartcoded.CaTo.model;

import javax.persistence.*;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Shalena Omapersad <shalenao@hotmail.com>
 *
 * Someone who can log in to the CaTo WebApp.
 */

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer userId;

    private String name;

    private String password;

    @Column(unique = true)
    private String email;

    @Lob
    private byte[] profilePicture;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Member> memberList;

    private int circleOne;

    private int circleTwo;

    private int circleThree;

    private String userRole;

    public User(Integer userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = "";
        this.circleOne = 0;
        this.circleTwo = 0;
        this.circleThree = 0;
    }

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(userRole));

        return authorityList;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCircleOne() {
        return circleOne;
    }

    public void setCircleOne(int circleOne) {
        this.circleOne = circleOne;
    }

    public int getCircleTwo() {
        return circleTwo;
    }

    public void setCircleTwo(int circleTwo) {
        this.circleTwo = circleTwo;
    }

    public int getCircleThree() {
        return circleThree;
    }

    public void setCircleThree(int circleThree) {
        this.circleThree = circleThree;
    }

    public void setAllThreeCircles(List<Integer> circleIdList, Integer circleId) {
        this.circleOne = circleId;
        this.circleTwo = circleIdList.get(0);
        this.circleThree = circleIdList.get(1);
    }

    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}

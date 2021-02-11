package com.spring.security.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_roles")
public class RoleInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 25)
    private Integer id;

    @Column(name = "username", length = 50)
    private String userName;

    @Column(name = "rolename", length = 50)
    private String role;


    @Column(name = "cms_id", length = 800)
    private String cmsId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCmsId() {
        return cmsId;
    }

    public void setCmsId(String cmsId) {
        this.cmsId = cmsId;
    }
}

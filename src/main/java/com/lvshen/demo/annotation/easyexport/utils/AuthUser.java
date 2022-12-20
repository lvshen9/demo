package com.lvshen.demo.annotation.easyexport.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-12-20 14:38
 * @since JDK 1.8
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthUser {
    private static final String CONTACT_CHAR = "#";
    private static final String PLACEHOLDER_CHAR = "{-}";
    private String id;
    private String username;
    private String userType;
    private String subjectType;
    private String subjectId;
    private String departmentId;
    private String postionId;
    private boolean superAdmin;
    private String additionalVal;
    private String mobile;
    private boolean tenantAdmin;
    private List<String> tags;

    public AuthUser() {
    }

    public AuthUser(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        if (this.superAdmin && this.userType == null) {
            //todo 需要根据实际写代码
            this.userType = "";
        }

        return this.userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSubjectType() {
        return this.subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getPostionId() {
        return this.postionId;
    }

    public void setPostionId(String postionId) {
        this.postionId = postionId;
    }

    public boolean isSuperAdmin() {
        return this.superAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        this.superAdmin = superAdmin;
        if (superAdmin) {
            //todo 需要根据实际写代码
            this.userType = "";
        }
    }

    public static AuthUser decode(String encodeString) {
        //todo 需要根据实际写代码
        return new AuthUser();
    }
}

package cn.irving.zhao.auth.service.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Irving
 * @version UserPasswordLoginInfo.java, v 0.1 2018/2/22
 */
@Entity
@Table(name = "uc_user_password_info")
@Getter
@Setter
public class UserPasswordLoginInfo {

    @Id
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private String salt;

    @Column(name = "base_id")
    private String baseId;

}

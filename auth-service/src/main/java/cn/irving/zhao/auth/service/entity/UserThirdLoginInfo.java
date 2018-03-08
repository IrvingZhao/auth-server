package cn.irving.zhao.auth.service.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Irving
 * @version UserThirdLoginInfo.java, v 0.1 2018/2/22
 */
@Entity
@Table(name = "uc_third_login_info")
@Getter
@Setter
public class UserThirdLoginInfo {

    @Id
    private String id;

    @Column(name = "platform")
    private String platform;

    @Column(name = "third_id")
    private String thirdId;

    @Column(name = "user_id")
    private String userId;

}

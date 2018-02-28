package cn.irving.zhao.auth.service.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Irving
 * @version OauthClientInfo.java, v 0.1 2018/2/22
 */
@Entity
@Table(name = "uc_oauth_client_info")
@Getter
@Setter
public class OauthClientInfo {

    @Id
    private String id;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "client_salt")
    private String clientSalt;

    @Column(name = "client_status")
    private String clientStatus;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "is_delete")
    private String isDelete;

}

package cn.irving.zhao.auth.service.entity;

import cn.irving.zhao.auth.service.constant.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户基础信息
 *
 * @author Irving
 * @version UserBaseInfo.java, v 0.1 2018/3/1
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "uc_user_base_info")
public class UserBaseInfo {
    public UserBaseInfo(String id, String mobile) {
        this.id = id;
        this.nickName = "用户" + mobile.substring(7);
        this.status = UserStatus.NORMAL;
        this.registerTime = new Date();
    }

    private String id;

    @Column(name = "nick_name")
    private String nickName;

    private String sex;

    @Column(name = "head_img")
    private String headImg;

    private UserStatus status;

    @Column(name = "register_time")
    private Date registerTime;
}

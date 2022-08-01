package com.weiho.scaffold.system.security.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.weiho.scaffold.system.entity.Avatar;
import com.weiho.scaffold.system.entity.enums.SexEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

/**
 * 用户登录后接收Security的用户信息与权限
 *
 * @author Weiho
 * @date 2022/7/29
 */
@Getter
@Setter
@ApiModel("登录授权后用户实体")
public class JwtUserVO implements UserDetails {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("头像信息")
    private Avatar avatar;

    @ApiModelProperty("用户名")
    private String username;

    @JsonIgnore
    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("性别 0-女 1-男")
    private SexEnum sex;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("状态：1启用 0禁用")
    private boolean enabled;

    @ApiModelProperty("最后修改密码时间")
    private Date lastPassResetTime;

    @ApiModelProperty("JWT创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;

    @JsonIgnore
    @ApiModelProperty("用户权限集合")
    private Collection<SimpleGrantedAuthority> authorities;

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "JwtUserVO{" +
                "id=" + id +
                ", avatar=" + avatar +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", enabled=" + enabled +
                ", lastPassResetTime=" + lastPassResetTime +
                ", createTime=" + createTime +
                ", authorities=" + authorities +
                '}';
    }
}

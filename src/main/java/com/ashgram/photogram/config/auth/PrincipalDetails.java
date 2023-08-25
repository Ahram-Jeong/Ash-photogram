package com.ashgram.photogram.config.auth;

import com.ashgram.photogram.domain.user.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {
    static final long serialVersionUID = 1L;

    private User user;
    private Map<String, Object> attributes; // OAuth2 로그인 시, attributes

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // OAuth2 로그인 시, 사용 할 생성자 추가
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // 권한은 여러 개일 수 있기 때문에, Collection으로 받음
        Collection<GrantedAuthority> collector = new ArrayList();
        collector.add(() -> { return user.getRole(); });
        return collector;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}

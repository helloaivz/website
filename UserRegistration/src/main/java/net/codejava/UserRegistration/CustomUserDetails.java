package net.codejava.UserRegistration;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }
    
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
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
    public boolean isAccountNonLocked() {
        return true;
    }
    public String getFullName() {
        return user.getFirstName() + " " + user.getLastName();
    }
    
    public Integer getLoginCount() {
        return user.getLoginCount();
    }

    public Long getId() {
        return user.getId();
    }

    //public Integer getAge() {
        //return user.intro.getAge();
    //}
    
}

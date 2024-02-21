package com.Eventhub.EventHubIntexSoft.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
  @SequenceGenerator(name = "users_seq", sequenceName = "users_user_id_seq", allocationSize = 1)
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "user_name", unique = true, nullable = false)
  private String userName;

  @Column(name = "user_email", unique = true, nullable = false)
  private String email;

  @Column(name = "password")
  private String password;

  @OneToMany(mappedBy = "roleId", fetch = FetchType.EAGER)
  private List<UserRole> roles;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.getRole()))
        .collect(Collectors.toList());
  }

  @Override
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
}

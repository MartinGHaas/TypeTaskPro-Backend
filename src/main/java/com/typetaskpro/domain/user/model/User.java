package com.typetaskpro.domain.user.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.typetaskpro.domain.project.model.Project;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "users")
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class User implements UserDetails{
  
  @Id
  @SequenceGenerator(
    initialValue = 0,
    allocationSize = 1,
    name = "user_id_generator"
  )
  @GeneratedValue(
    generator = "user_id_generator",
    strategy = GenerationType.SEQUENCE
  )
  private long id;

  @Column(
    length = 15,
    unique = true,
    nullable = false
  )
  private String username;

  @Column(nullable = false)
  @EqualsAndHashCode.Exclude
  private String password;

  @EqualsAndHashCode.Exclude
  private UserRole role;

  @ManyToMany(
    fetch = FetchType.EAGER,
    cascade = CascadeType.ALL,
    mappedBy = "administrators"
  )
  private List<Project> administratingProjects;
  
  @ManyToMany(
    fetch = FetchType.EAGER,
    cascade = CascadeType.ALL,
    mappedBy = "contributors"
  )
  private List<Project> contributingProjects;

  @OneToMany(mappedBy = "owner")
  private List<Project> ownProjects;

  public User(String username, String password, UserRole role) {
    this.username = username;
    this.password = password;
    this.role = role;
    this.administratingProjects = new ArrayList<>();
    this.contributingProjects = new ArrayList<>();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
    return List.of(new SimpleGrantedAuthority("ROLE_USER"));
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

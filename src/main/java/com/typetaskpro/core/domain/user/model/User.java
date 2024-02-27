package com.typetaskpro.core.domain.user.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.typetaskpro.core.domain.project.model.Project;
import com.typetaskpro.core.domain.user.metadata.UserMetadata;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostPersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <h1>User Entity</h1>
 * <br>
 * <p>This class represents a user entity, and a User in the application system.</p>
 * <p>It implements UserDetails interface(core Spring's Security interface).</p>
 */
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
  @ToString.Exclude
  private List<Project> administratingProjects;
  
  @ManyToMany(
    fetch = FetchType.EAGER,
    cascade = CascadeType.ALL,
    mappedBy = "contributors"
  )
  @ToString.Exclude
  private List<Project> contributingProjects;

  @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
  @ToString.Exclude
  private List<Project> ownProjects;

  @OneToOne(
    cascade = CascadeType.ALL,
    fetch = FetchType.EAGER
  )
  @JoinColumn
  private UserMetadata metadata;

  public User(String username, String password, UserRole role) {
    this.username = username;
    this.password = password;
    this.role = role;
    this.administratingProjects = new ArrayList<>();
    this.contributingProjects = new ArrayList<>();
  }

  /**
   * <h3>The metadata must be created after the persistence
   * of the User!</h3>
   * 
   * <h4>Why?</h4>
   * <p>If you attempt to create the metadata  in the user's
   * constructor, the id of the metadata will always be 0.</p>
   * 
   * <p>This is due to how spring-data-JPA interacts with the application.</p>
   * 
   * <p>The classes you get will always be relative to the content you
   * have in the database, so, if you create a new User, at least in
   * this application, the database will be responsible to create the
   * id of this user. <b>So imagine:</b></p>
   * 
   * <i>If the database is responsible to create the id, how can I
   * determine the Id of the user before its initialization?</i>
   * 
   * <p>This is why I use PostPersist in here. It <b>MUST</b>
   * to be initialized after the after the data is persisted in the database</p>
   * 
   * <p><i>Post comments: I don't really like to document code, and I
   * don't consider this to be ‘documentation’, but I really liked
   * PostPersist. Mainly because I wasn't aware of it before, so I thougth
   * it would be nice for people understand better why this is here!
   * Hope you understood!</i></p>
   * 
   * <p><i><b>if I'm wrong, correct me please!</b></i></p>
   */
  @PostPersist
  public void initMetadata() {
    this.metadata = new UserMetadata(this.id);
  }
  
  /**
   * Get the user's authorities in the application.
   * Used to grant users and admins different permissions.
   *
   * @return a collection of granted authorities.
   */
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

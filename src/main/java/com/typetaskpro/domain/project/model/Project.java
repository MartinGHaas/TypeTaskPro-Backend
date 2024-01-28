package com.typetaskpro.domain.project.model;

import java.util.ArrayList;
import java.util.List;

import com.typetaskpro.domain.device.model.Device;
import com.typetaskpro.domain.user.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "projects")
@Table(name = "projects")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Project {
  
  @Id
  @SequenceGenerator(
    initialValue = 0,
    allocationSize = 1,
    name = "project_id_generator"
  )
  @GeneratedValue(
    generator = "project_id_generator",
    strategy = GenerationType.SEQUENCE
  )
  private long id;

  @Column(
    nullable = false,
    length = 25
  )
  private String name;

  @ManyToOne
  @JoinColumn(
    name = "device"
  )
  private Device device;

  @ManyToMany(
    cascade = CascadeType.ALL,
    fetch = FetchType.EAGER
  )
  @JoinTable(
    name = "projects_administrators",
    joinColumns = {
      @JoinColumn(name = "user_id")
    },
    inverseJoinColumns = {
      @JoinColumn(name = "project_id")
    }
  )
  private List<User> administrators;
  
  @ManyToMany(
    cascade = CascadeType.ALL,
    fetch = FetchType.EAGER
  )
  @JoinTable(
    name = "projects_contributors",
    joinColumns = {
      @JoinColumn(name = "user_id")
    },
    inverseJoinColumns = {
      @JoinColumn(name = "project_id")
    }
  )
  private List<User> contributors;

  public Project(String name, Device device, User user) {
    this.name = name;
    this.device = device;
    this.administrators = new ArrayList<>();
    this.contributors = new ArrayList<>();

    this.administrators.add(user);
    this.contributors.add(user);
  }
}

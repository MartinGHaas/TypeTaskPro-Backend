package com.typetaskpro.core.repositories.image;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.typetaskpro.core.domain.image.model.TaskImage;

@Repository
public interface TaskImageRepository extends JpaRepository<TaskImage, String> {

  @Transactional
  @Query(value = "SELECT * FROM tasks_image WHERE task_id = ?1", nativeQuery = true )
  public Optional<TaskImage> getTaskImageByTaskId(String taskId);
}

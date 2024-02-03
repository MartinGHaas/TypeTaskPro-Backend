package com.typetaskpro.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.typetaskpro.core.domain.images.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
}

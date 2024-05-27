package org.webapp.gymfreaks.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.webapp.gymfreaks.core.model.BaseEntity;

@NoRepositoryBean
public interface BaseRepostitory<T extends BaseEntity, ID> extends JpaRepository<T, ID> {

}

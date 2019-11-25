package com.kbhkn.restexceptionhandler.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;

/**
 * Created Read-Only repository. We don't need any transactional operation.
 *
 * Use Package-private.
 *
 * @author Kbhkn - 9.06.2019 02:28
 */
@NoRepositoryBean
interface ReadOnlyRepository<T, ID extends Serializable> extends Repository<T, ID> {
}

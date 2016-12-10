package com.iyzico.repository;

import com.iyzico.dao.Todo;
import com.iyzico.dao.User;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Date: 10/12/2016 Time: 13:00
 *
 * @author dogukan.ozturkan (https://github.com/dogukanozturkan)
 */

@Repository
public interface TodoRepository extends PagingAndSortingRepository<Todo, Long>,
        JpaSpecificationExecutor<Todo>, QueryDslPredicateExecutor<Todo> {

    List<Todo> findAllByUser(User user);

    @Transactional
    Long deleteById(long todoId);
}

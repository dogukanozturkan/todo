package com.iyzico.repository;

import com.iyzico.dao.User;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Date: 10/12/2016 Time: 13:00
 *
 * @author dogukan.ozturkan (https://github.com/dogukanozturkan)
 */

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>,
        JpaSpecificationExecutor<User>, QueryDslPredicateExecutor<User> {

    User findOneByEmail(String email);

    @Query("select u.userType from User u where u.id = :userId")
    User.UserType findUserTypeById(@Param("userId") Long userId);
}

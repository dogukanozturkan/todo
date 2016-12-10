package com.iyzico.dao;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Date: 10/12/2016 Time: 13:00
 *
 * @author dogukan.ozturkan (https://github.com/dogukanozturkan)
 */

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@DynamicUpdate(value = true)
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -3001919008236514871L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Date createdDate = new Date();

    @LastModifiedDate
    private Date modifiedDate =new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}

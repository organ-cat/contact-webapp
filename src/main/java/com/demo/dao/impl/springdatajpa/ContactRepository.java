package com.demo.dao.impl.springdatajpa;

import com.demo.domain.Contact;
import org.springframework.data.repository.CrudRepository;

// CrudRepository<T, ID> 实现增删改查操作
// PagingAndSortingRepository<T, ID> 继承CrudRepository,同时提供分页,排序
public interface ContactRepository extends CrudRepository<Contact, Long> {
}

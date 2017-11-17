package com.demo.dao.impl.springdatajpa;

import com.demo.dao.ContactDao;
import com.demo.domain.Contact;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("springJpaDataContactDao") // 当只有一个daoImpl,@Repository即可,名字只是为了区分不同的Bean
public class ContactDaoSpringJPADataImpl implements ContactDao {
    // 当Spring Data JPA无法满足需求时,可使用JPA与之混合
    @PersistenceContext
    private EntityManager em;

    private ContactRepository contactRepository;

    @Override
    public List<Contact> findAll() {
        return Lists.newArrayList(contactRepository.findAll());
    }

    @Override
    public List<Contact> findAllWithDetail() {
        List<Contact> contacts = em.createNamedQuery(
                "Contact.findAllWithDetail", Contact.class).getResultList();
        return contacts;
    }

    @Override
    public Contact findById(Long id) {
        return contactRepository.findOne(id);
    }

    @Override
    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public void delete(Contact contact) {
        contactRepository.delete(contact);
    }

    @Autowired
    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
}

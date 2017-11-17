package com.demo.dao.impl.jpa;

import com.demo.dao.ContactDao;
import com.demo.domain.Contact;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository("jpaContactDao") // 当只有一个daoImpl,@Repository即可,名字只是为了区分不同的Bean
public class ContactDaoJPAImpl implements ContactDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Contact> findAll() {
        List<Contact> contacts = em.createNamedQuery("Contact.findAll",
                Contact.class).getResultList();
        return contacts;
    }

    @Override
    public List<Contact> findAllWithDetail() {
        List<Contact> contacts = em.createNamedQuery(
                "Contact.findAllWithDetail", Contact.class).getResultList();
        return contacts;
    }

    @Override
    public Contact findById(Long id) {
        TypedQuery<Contact> query = em.createNamedQuery("Contact.findById", Contact.class);
        query.setParameter("id", id);

        return query.getSingleResult();
    }

    // 保存/更新
    @Override
    public Contact save(Contact contact) {
        if (contact.getId() == null) {
            em.persist(contact);
        } else {
            em.merge(contact);
        }

        return contact;
    }

    @Override
    public void delete(Contact contact) {
        Contact mergedContact = em.merge(contact);
        em.remove(mergedContact);
    }
}

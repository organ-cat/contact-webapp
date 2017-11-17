package com.demo.service.impl;

import com.demo.dao.ContactDao;
import com.demo.domain.Contact;
import com.demo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service("contactService")
public class ContactServiceImpl implements ContactService {
    private ContactDao contactDao;

    @Transactional(readOnly = true) // 查找操作只读模式即可: readOnly = true
    @Override
    public List<Contact> findAll() {
        return contactDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Contact> findAllWithDetail() {
        return contactDao.findAllWithDetail();
    }

    @Override
    public Contact findById(Long id) {
        return contactDao.findById(id);
    }

    @Override
    public Contact save(Contact contact) {
        return contactDao.save(contact);
    }

    @Override
    public void delete(Contact contact) {
        contactDao.delete(contact);
    }

    @Autowired // 只有一个DaoImpl用@Autowired即可
    @Resource(name = "springJpaDataContactDao") // 因为demo中有两个DaoImpl,需要指定名字
//    @Resource(name = "jpaContactDao")
    public void setContactDao(ContactDao contactDao) {
        this.contactDao = contactDao;
    }
}

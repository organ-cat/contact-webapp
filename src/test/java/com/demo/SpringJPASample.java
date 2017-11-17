package com.demo;

import com.demo.domain.Contact;
import com.demo.domain.ContactTelDetail;
import com.demo.domain.Hobby;
import com.demo.service.ContactService;
import org.joda.time.DateTime;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.List;

public class SpringJPASample {
    public static void main(String[] args) {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:META-INF/spring/test-context.xml");
        ctx.refresh();

        ContactService contactService = ctx.getBean(
                "contactService", ContactService.class);

        Contact contact = new Contact();
        contact.setFirstName("Michael");
        contact.setLastName("Jackson");
        contact.setBirthDate(new DateTime());

        ContactTelDetail contactTelDetail =
                new ContactTelDetail("Home", "1111111111");

        contact.addContactTelDetail(contactTelDetail);

        contactTelDetail = new ContactTelDetail("Mobile", "2222222222");

        contact.addContactTelDetail(contactTelDetail);

        contactService.save(contact);

        listContactsWithDetail(contactService.findAllWithDetail());
    }

    private static void listContactsWithDetail(List<Contact> contacts) {
        System.out.println("");
        System.out.println("Listing contacts with details:");

        for (Contact contact: contacts) {
            System.out.println(contact);
            if (contact.getContactTelDetails() != null) {
                for (ContactTelDetail contactTelDetail:
                        contact.getContactTelDetails()) {
                    System.out.println(contactTelDetail);
                }
            }

            if (contact.getHobbies() != null) {
                for (Hobby hobby: contact.getHobbies()) {
                    System.out.println(hobby);
                }
            }

            System.out.println();
        }
    }
}

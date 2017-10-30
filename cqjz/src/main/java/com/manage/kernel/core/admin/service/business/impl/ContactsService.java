package com.manage.kernel.core.admin.service.business.impl;

import com.manage.kernel.core.admin.service.business.IContactsService;
import com.manage.kernel.core.model.dto.ContactsDto;
import com.manage.kernel.core.model.parser.ContactsParser;
import com.manage.kernel.jpa.entity.JzContacts;
import com.manage.kernel.jpa.repository.JzContactsRepo;
import com.manage.kernel.spring.comm.SessionHelper;
import java.util.List;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * Created by bert on 17-10-30.
 */
@Service
public class ContactsService implements IContactsService {

    @Autowired
    private JzContactsRepo contactsRepo;

    @Override
    @Transactional
    public ContactsDto contactsInfo() {
        JzContacts contacts = contactsRepo.findContactsForPublish();
        if (contacts == null) {
            contacts = new JzContacts();
            contacts.setVersion(0);
            contacts.setHistory(false);
            contacts = contactsRepo.save(contacts);

        }
        return ContactsParser.toDto(contacts);
    }

    @Override
    @Transactional
    public ContactsDto saveContacts(ContactsDto contactsDto) {

        JzContacts contacts = contactsRepo.findContactsForPublish();
        saveHistory(contacts);
        contacts.setContent(contactsDto.getContent());
        contacts.setVersion(contacts.getVersion() + 1);
        contacts.setCreatedAt(LocalDateTime.now());
        contacts.setCreatedUser(SessionHelper.user());
        contacts = contactsRepo.save(contacts);
        return ContactsParser.toDto(contacts);
    }

    private void saveHistory(JzContacts contacts) {

        List<JzContacts> histories = contactsRepo.findContactsForHistory();
        int historyCount = 50;
        if (!CollectionUtils.isEmpty(histories) && histories.size() > historyCount) {
            for (int i = 49; i < histories.size(); i++) {
                contactsRepo.delete(histories.get(i));
            }
        }

        JzContacts history = new JzContacts();
        history.setContent(contacts.getContent());
        history.setCreatedAt(contacts.getCreatedAt());
        history.setCreatedUser(contacts.getCreatedUser());
        history.setUpdatedAt(LocalDateTime.now());
        history.setCreatedUser(SessionHelper.user());
        history.setVersion(history.getVersion());
        history.setHistory(true);
        contactsRepo.save(history);
    }
}

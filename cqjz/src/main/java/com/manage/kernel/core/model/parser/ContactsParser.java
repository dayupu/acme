package com.manage.kernel.core.model.parser;

import com.manage.kernel.core.model.dto.ContactsDto;
import com.manage.kernel.jpa.entity.JzContacts;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 2017/8/26.
 */
public class ContactsParser {

    public static ContactsDto toDto(JzContacts contacts) {
        return toDto(contacts, new ContactsDto());
    }

    public static List<ContactsDto> toDtoList(List<JzContacts> entitys) {
        List<ContactsDto> dtos = new ArrayList<>();
        for (JzContacts entity : entitys) {
            dtos.add(toDto(entity, new ContactsDto()));
        }
        return dtos;
    }

    private static ContactsDto toDto(JzContacts entity, ContactsDto dto) {
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        return dto;
    }
}

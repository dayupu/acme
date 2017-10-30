package com.manage.kernel.core.admin.service.business;

import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.kernel.core.model.dto.ContactsDto;
import com.manage.kernel.core.model.dto.WatchDto;
import org.joda.time.LocalDate;

/**
 * Created by bert on 17-8-25.
 */
public interface IContactsService {

    public ContactsDto contactsInfo();

    public ContactsDto saveContacts(ContactsDto contactsDto);

}

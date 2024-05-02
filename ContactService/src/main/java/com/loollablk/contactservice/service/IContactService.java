package com.loollablk.contactservice.service;

import com.loollablk.contactservice.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author sskma
 * @Created 02/05/2024 - 2:20 AM
 * @project contactservice
 */
/*
 * Created by sskma on 5/2/2024
 *
 */

public interface IContactService {

    public Page getAllContacts(int page, int size);
    public Contact getContact(String id);


    public Contact createContact(Contact contact);

    public void deleteContact(Contact contact);
    public String uploadPhoto(String id, MultipartFile file);
}

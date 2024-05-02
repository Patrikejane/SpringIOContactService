package com.loollablk.contactservice.service;

import com.loollablk.contactservice.model.Contact;
import com.loollablk.contactservice.repository.ContactRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.loollablk.contactservice.constant.Constant.PHOTO_DIRECTORY;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


/**
 * @author sskma
 * @Created 02/05/2024 - 2:20 AM
 * @project contactservice
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class ContactServiceImpl implements IContactService{

    @Autowired
    private final ContactRepository contactRepository;

    public Page getAllContacts(int page, int size) {
        return contactRepository.findAll(PageRequest.of(page,size, Sort.by("name")));
    }

    public Contact getContact(String id){
        return contactRepository.findById(id).orElseThrow(()-> new RuntimeException("Contact Details for id : "+ id +" not Found." ));
    }


    public Contact createContact(Contact contact){
        return contactRepository.save(contact);
    }

    public void deleteContact(Contact contact){

        Optional<Contact> optionalContact = contactRepository.findById(contact.getId());
        if (optionalContact.isPresent()){
            contactRepository.delete(contact);
        }
        else{
            throw new RuntimeException("Contact Details for id : "+ optionalContact.get().getId() +" not Found." );
        }
    }

    public String uploadPhoto(String id, MultipartFile file){
        Contact contact = getContact(id);
        String photoUrl = photoFunction.apply(id, file);
        contact.setPhotoUrl(photoUrl);
        contactRepository.save(contact);

        return photoUrl;
    }

    private final Function<String, String> fileExtension = (fileName) -> Optional.of(fileName)
            .filter(name -> name.contains("."))
            .map(name -> "." + name.substring(fileName.lastIndexOf(".") + 1))
            .orElse(".png");

    private final BiFunction<String, MultipartFile, String> photoFunction = (id, image) -> {
        String filename = id + fileExtension.apply(image.getOriginalFilename());
        try {
            Path fileStorageLocation = Paths.get(PHOTO_DIRECTORY).toAbsolutePath().normalize();
            if(!Files.exists(fileStorageLocation)){
                Files.createDirectories(fileStorageLocation);
            }else {
                throw new RuntimeException("Image File Location unable to create");
            }
            Files.copy(image.getInputStream(),fileStorageLocation.resolve(id +".png"),REPLACE_EXISTING);
            return ServletUriComponentsBuilder.fromCurrentContextPath().path("/contacts/image/" + filename).toUriString();
        }catch (Exception ex){
            throw  new RuntimeException("Unable to Save the Image File");
        }
    };

}

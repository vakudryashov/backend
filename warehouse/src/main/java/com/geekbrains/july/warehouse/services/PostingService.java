package com.geekbrains.july.warehouse.services;

import com.geekbrains.july.warehouse.entities.Posting;
import com.geekbrains.july.warehouse.repositories.PostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostingService {
    @Autowired
    PostingRepository postingRepository;

    @Autowired
    UsersService usersService;

    public List<Posting> findAll() {
        return postingRepository.findAll();
    }

    public List<Posting> saveOrUpdate(Posting posting) {
        posting.setPostingDate(new Date());
        posting.setUser(usersService.findLoggedInUser().get());
        postingRepository.save(posting);
        return postingRepository.findAll();
    }

    public void deleteById(Long id) {
        postingRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return postingRepository.existsById(id);
    }
}

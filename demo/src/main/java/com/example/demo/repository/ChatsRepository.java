package com.example.demo.repository;

import com.example.demo.entities.Chat;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatsRepository extends JpaRepository<Chat,Long> {

    
}

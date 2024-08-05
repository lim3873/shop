package com.example.myselectshop.folder.repository;

import com.example.myselectshop.folder.entity.Folder;
import com.example.myselectshop.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findAllByUser(User user);
    List<Folder> findAllByUserAndNameIn(User user, List<String> folderNames);
}

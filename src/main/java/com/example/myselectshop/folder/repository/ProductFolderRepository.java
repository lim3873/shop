package com.example.myselectshop.folder.repository;

import com.example.myselectshop.folder.entity.Folder;
import com.example.myselectshop.folder.entity.ProductFolder;
import com.example.myselectshop.product.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductFolderRepository extends JpaRepository<ProductFolder, Long> {
    Optional<ProductFolder> findByProductAndFolder(Product product, Folder folder);
}


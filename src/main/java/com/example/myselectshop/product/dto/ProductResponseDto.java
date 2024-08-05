package com.example.myselectshop.product.dto;

import com.example.myselectshop.folder.dto.FolderResponseDto;
import com.example.myselectshop.folder.entity.ProductFolder;
import com.example.myselectshop.product.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProductResponseDto {

    private Long id;

    private String title;

    private String link;

    private String image;

    private int lprice;

    private int myprice;

    private List<FolderResponseDto> productFolderList = new ArrayList<>();

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.link = product.getLink();
        this.image = product.getImage();
        this.lprice = product.getLprice();
        this.myprice = product.getMyprice();
        for (ProductFolder productFolder : product.getProductFolderList()) {
            productFolderList.add(new FolderResponseDto(productFolder.getFolder()));
        }
    }

}

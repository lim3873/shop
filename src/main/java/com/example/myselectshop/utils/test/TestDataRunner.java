package com.example.myselectshop.utils.test;

import com.example.myselectshop.naver.dto.ItemDto;
import com.example.myselectshop.naver.service.NaverApiService;
import com.example.myselectshop.product.entity.Product;
import com.example.myselectshop.product.repository.ProductRepository;
import com.example.myselectshop.user.entity.User;
import com.example.myselectshop.user.entity.UserRoleEnum;
import com.example.myselectshop.user.repository.UserRepository;
import com.example.myselectshop.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.myselectshop.product.service.ProductService.MIN_MY_PRICE;

@Component
public class TestDataRunner implements ApplicationRunner {

    @Autowired
    UserService userService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    NaverApiService naverApiService;

    @Override
    public void run(ApplicationArguments args) {
        // 현재 저장된 사용자 수를 가져옵니다.
        long userCount = userRepository.count();

        // 새로운 사용자 이름을 생성합니다. 예: test1, test2, test3 등.
        String userName = "test" + (userCount + 1);

        // 테스트 User 생성
        User testUser = new User(userName, passwordEncoder.encode("0000"), userName + "@example.com", UserRoleEnum.USER);
        userRepository.save(testUser);

        // 생성된 사용자 정보 출력 (디버깅 용도)
        System.out.println("Created User: " + testUser);

        // 테스트 User 의 관심상품 등록
        // 검색어 당 관심상품 10개 등록
        createTestData(testUser, "맥북");
        createTestData(testUser, "아이폰");
        createTestData(testUser, "에어팟");

    }

    private void createTestData(User user, String searchWord) {
        // 네이버 쇼핑 API 통해 상품 검색
        List<ItemDto> itemDtoList = naverApiService.searchItems(searchWord);

        List<Product> productList = new ArrayList<>();

        for (ItemDto itemDto : itemDtoList) {
            Product product = new Product();
            // 관심상품 저장 사용자
            product.setUser(user);
            // 관심상품 정보
            product.setTitle(itemDto.getTitle());
            product.setLink(itemDto.getLink());
            product.setImage(itemDto.getImage());
            product.setLprice(itemDto.getLprice());

            // 희망 최저가 랜덤값 생성
            // 최저 (100원) ~ 최대 (상품의 현재 최저가 + 10000원)
            int myPrice = getRandomNumber(MIN_MY_PRICE, itemDto.getLprice() + 10000);

            product.setMyprice(myPrice);
            productList.add(product);
        }

        productRepository.saveAll(productList);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}

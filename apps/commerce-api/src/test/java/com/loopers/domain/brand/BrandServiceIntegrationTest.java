package com.loopers.domain.brand;

import com.loopers.infrastructure.brand.BrandJpaRepository;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import com.loopers.utils.DatabaseCleanUp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BrandServiceIntegrationTest {

    @Autowired
    private BrandService brandService;

    @Autowired
    private BrandJpaRepository brandJpaRepository;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @AfterEach
    void tearDown() {
        databaseCleanUp.truncateAllTables();
    }

    @DisplayName("브랜드를 조회할 때,")
    @Nested
    class Get {

        @DisplayName("존재하는 브랜드 ID를 주면, 해당 브랜드를 반환한다.")
        @Test
        void returnsBrand_whenValidIdIsProvided() {
            // arrange
            Brand saved = brandJpaRepository.save(
                Brand.builder().name("나이키").description("스포츠 브랜드").build()
            );

            // act
            Brand result = brandService.getBrand(saved.getId());

            // assert
            assertAll(
                () -> assertThat(result.getId()).isEqualTo(saved.getId()),
                () -> assertThat(result.getName()).isEqualTo("나이키")
            );
        }

        @DisplayName("존재하지 않는 브랜드 ID를 주면, NOT_FOUND 예외가 발생한다.")
        @Test
        void throwsNotFound_whenInvalidIdIsProvided() {
            // arrange
            Long invalidId = 999L;

            // act
            CoreException result = assertThrows(CoreException.class, () -> {
                brandService.getBrand(invalidId);
            });

            // assert
            assertThat(result.getErrorType()).isEqualTo(ErrorType.NOT_FOUND);
        }
    }
}

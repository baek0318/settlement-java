package com.pair.settlement.owner;

import com.pair.settlement.TestConfig;
import com.pair.settlement.dbutil.DatabaseInsert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@DataJpaTest
@Import({TestConfig.class, DatabaseInsert.class})
public class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private DatabaseInsert dbInsert;

    private Owner owner1;
    private Owner owner2;
    private Owner owner3;

    @BeforeEach
    void 업주저장() {
        owner1 = Owner.builder()
                .name("백승화")
                .email("peach@kakao.com")
                .phoneNumber("010-1111-2222")
                .build();
        owner2 = Owner.builder()
                .name("백승화")
                .email("peach@gmail.com")
                .phoneNumber("010-1122-2222")
                .build();
        owner3 = Owner.builder()
                .name("apple")
                .email("apple@kakao.com")
                .phoneNumber("010-1111-2222")
                .build();
        dbInsert.saveOwner(owner1);
        dbInsert.saveOwner(owner2);
        dbInsert.saveOwner(owner3);
    }

    @Test
    void QueryDsl_findById_테스트() {
        List<Owner> result = ownerRepository.findOwner(Long.toString(owner1.getId()), null, null);

        Assertions.assertThat(result).containsOnly(owner1);
    }

    @Test
    void QueryDsl_findByName_테스트() {
        List<Owner> result = ownerRepository.findOwner(null, owner1.getName(), null);

        Assertions.assertThat(result).containsOnly(owner1, owner2);
    }

    @Test
    void QueryDsl_findByEmail_테스트() {
        List<Owner> result = ownerRepository.findOwner(null,null, "peach");

        Assertions.assertThat(result).containsOnly(owner1, owner2);
    }

    @Test
    void QueyrDsl_findByNameNEmail_테스트() {
        List<Owner> result = ownerRepository.findOwner(null, owner1.getName(), "peach");

        Assertions.assertThat(result).containsOnly(owner1, owner2);
    }

    @Test
    void QueyrDsl_findByNameNEmail2_테스트() {
        List<Owner> result = ownerRepository.findOwner(null, owner1.getName(), "@gmail.com");

        Assertions.assertThat(result).containsOnly(owner2);
    }
}

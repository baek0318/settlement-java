package com.pair.settlement.owner.dto;

import com.pair.settlement.owner.Owner;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnerUpdateRequest {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

    @Builder
    public OwnerUpdateRequest(Long id, String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Owner toEntity() {
        return Owner.builder()
                .email(this.email)
                .name(this.name)
                .phoneNumber(this.phoneNumber)
                .build();
    }
}

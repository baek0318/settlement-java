package com.pair.owner.dto.request;

import com.pair.owner.Owner;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnerEnrollRequest {

    private String name;

    @Email
    private String email;

    private String phoneNumber;

    @Builder
    public OwnerEnrollRequest(String name, @Email String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Owner toEntity() {
        return Owner.builder()
                .email(email)
                .name(name)
                .phoneNumber(phoneNumber)
                .build();
    }
}

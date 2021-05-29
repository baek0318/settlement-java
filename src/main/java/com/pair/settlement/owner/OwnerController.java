package com.pair.settlement.owner;

import com.pair.settlement.owner.dto.AccountEnrollRequest;
import com.pair.settlement.owner.dto.AccountEnrollResponse;
import com.pair.settlement.owner.dto.OwnerEnrollRequest;
import com.pair.settlement.owner.dto.OwnerEnrollResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.validation.Valid;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping("")
    public ResponseEntity<OwnerEnrollResponse> enroll(@RequestBody @Valid OwnerEnrollRequest enrollRequest) {
        Long result = ownerService.enroll(enrollRequest.toEntity());

        UriComponents uriComponents = MvcUriComponentsBuilder
                .fromMethodCall(MvcUriComponentsBuilder.on(OwnerController.class).enroll(enrollRequest))
                .build();

        return ResponseEntity
                .created(uriComponents.toUri())
                .body(new OwnerEnrollResponse(result));
    }

    @PostMapping("/{owner_id}/account")
    public ResponseEntity<AccountEnrollResponse> enrollAccount(@PathVariable("owner_id") Long ownerId, @RequestBody @Valid AccountEnrollRequest request) {
        Owner owner = ownerService.findOwner(ownerId);
        Long result = ownerService.enrollAccount(owner, request.toEntity());

        UriComponents uriComponents = MvcUriComponentsBuilder
                .fromMethodCall(MvcUriComponentsBuilder.on(OwnerController.class).enrollAccount(ownerId, request))
                .build();

        return ResponseEntity
                .created(uriComponents.toUri())
                .body(new AccountEnrollResponse(result));
    }
}

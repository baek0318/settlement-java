package com.pair.settlement.owner;

import com.pair.settlement.owner.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public ResponseEntity<AccountEnrollResponse> enrollAccount(@PathVariable("owner_id") Long ownerId,
                                                               @RequestBody @Valid AccountEnrollRequest request) {
        Owner owner = ownerService.findOwner(ownerId);
        Long result = ownerService.enrollAccount(owner, request.toEntity());

        UriComponents uriComponents = MvcUriComponentsBuilder
                .fromMethodCall(MvcUriComponentsBuilder.on(OwnerController.class).enrollAccount(ownerId, request))
                .build();

        return ResponseEntity
                .created(uriComponents.toUri())
                .body(new AccountEnrollResponse(result));
    }

    @GetMapping("")
    public ResponseEntity<List<OwnerInfoResponse>> searchOwnerInfo(@RequestParam Map<String, String> params) {

        List<OwnerInfoResponse> result = ownerService
                .searchOwner(params.get("id"), params.get("name"), params.get("email"))
                .stream()
                .map(OwnerInfoResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @PutMapping("")
    public ResponseEntity<OwnerInfoResponse> update(@RequestBody OwnerUpdateRequest updateRequest) {

        Owner owner = ownerService.update(updateRequest.getId(), updateRequest.toEntity());

        return ResponseEntity.ok(new OwnerInfoResponse(owner));
    }
}

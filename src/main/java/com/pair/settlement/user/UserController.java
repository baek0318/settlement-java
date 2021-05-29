package com.pair.settlement.user;

import com.pair.settlement.user.dto.JoinRequest;
import com.pair.settlement.user.dto.JoinResponse;
import com.pair.settlement.user.dto.LoginRequest;
import com.pair.settlement.user.dto.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<JoinResponse> userJoin(@Valid @RequestBody JoinRequest joinRequest) {

        Long result = userService.userJoin(
                joinRequest.getEmail(),
                joinRequest.getNickName(),
                joinRequest.getPassword());

        UriComponents uriComponents = MvcUriComponentsBuilder
                .fromMethodCall(MvcUriComponentsBuilder.on(UserController.class).userJoin(joinRequest))
                .build();

        return ResponseEntity
                .created(uriComponents.toUri())
                .body(new JoinResponse(result));
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Long result = userService.login(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        return ResponseEntity.ok(new LoginResponse(result));
    }

}

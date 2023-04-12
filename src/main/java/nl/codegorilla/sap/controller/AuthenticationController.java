package nl.codegorilla.sap.controller;

import lombok.RequiredArgsConstructor;
import nl.codegorilla.sap.model.authentication.AuthenticationRequest;
import nl.codegorilla.sap.model.authentication.AuthenticationResponse;
import nl.codegorilla.sap.model.authentication.RegisterRequest;
import nl.codegorilla.sap.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }


}
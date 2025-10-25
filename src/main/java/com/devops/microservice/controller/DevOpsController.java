package com.devops.microservice.controller;

import com.devops.microservice.dto.RequestDTO;
import com.devops.microservice.dto.ResponseDTO;
import com.devops.microservice.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DevOpsController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/DevOps")
    public ResponseEntity<ResponseDTO> devOpsEndpoint(
            @Valid @RequestBody RequestDTO request,
            HttpServletResponse response) {

        // Add JWT to response header
        String jwt = jwtService.generateToken();
        response.setHeader("X-JWT-KWY", jwt);

        String responseMessage = String.format("Hello %s your message will be sent", request.getTo());
        ResponseDTO responseDTO = new ResponseDTO(responseMessage);

        return ResponseEntity.ok(responseDTO);
    }

    @RequestMapping(value = "/DevOps", method = {RequestMethod.GET, RequestMethod.PUT,
            RequestMethod.DELETE, RequestMethod.PATCH,
            RequestMethod.HEAD, RequestMethod.OPTIONS})
    public ResponseEntity<String> methodNotAllowed() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("ERROR");
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Healthy");
    }
}
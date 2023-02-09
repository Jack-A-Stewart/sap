package nl.codegorilla.sap.controller;

import nl.codegorilla.sap.service.IsGraduatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/isgraduated")
public class IsGraduatedController {


    IsGraduatedService isGraduatedService;

    @Autowired
    public IsGraduatedController(IsGraduatedService isGraduatedService) {
        this.isGraduatedService = isGraduatedService;
    }




}

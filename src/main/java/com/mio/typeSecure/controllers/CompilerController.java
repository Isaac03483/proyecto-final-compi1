package com.mio.typeSecure.controllers;

import java.util.List;

public class CompilerController {

    private final TSParserController controller;

    public CompilerController(final TSParserController controller) {
        this.controller = controller;
    }

    public List<String> compile(String input){
        return controller.compile(input);
    }
}

package com.mio.typeSecure.controllers;

public class CompilerController {

    private final TSParserController controller;

    public CompilerController(final TSParserController controller) {
        this.controller = controller;
    }

    public void compile(String input){
        controller.compile(input);
    }
}

package com.modesteam.urutau.controller;

import com.modesteam.urutau.annotation.View;

import br.com.caelum.vraptor.Controller;

@Controller
public class ApplicationController {
    
	@View
	public void dificultError() {
	}

	@View
	public void invalidRequest() {
	}
}

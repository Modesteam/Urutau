package com.modesteam.urutau.controller;

import br.com.caelum.vraptor.Controller;

import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.service.I18nMessageCreator;

@Controller
public class ApplicationController {
    
	@View
	public void dificultError() {
	}

	@View
	public void invalidRequest() {
	}
}

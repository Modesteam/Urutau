package com.modesteam.urutau.model.system;

import br.com.caelum.vraptor.i18n.Message;


/**
 * This enum have name of fields which contains category of {@link Message}
 */
public enum ContextPlace {
    INDEX_PANEL,
    PROJECT_PANEL,
    PROJECT_CREATE,
    KANBAN,
    REQUIREMENT_CREATE,
    // Belongs to index page
    LOGIN,
    // Used above of user registration form
    REGISTER_VALIDATOR,
    // Generic label, used to shows some succeeded action
    SUCCESS_MESSAGE,
    // Showed in modal forms
    MODAL_ERROR,
    ERROR;
    
    public String toString() {
        return super.toString().toLowerCase();
    };
}
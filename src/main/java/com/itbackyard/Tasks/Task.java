package com.itbackyard.Tasks;

import com.itbackyard.System.AppSystem;

/**
 * Class {@code Task} task model
 *
 * @author Maytham Fahmi
 * @see AppSystem
 * @since WET-EXTRACTOR 3.0
 */
public class Task {
    private String message = "";

    /**
     * Set Task message
     *
     * @param message set message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get Task message
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

}
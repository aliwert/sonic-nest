package com.aliwert.exception;


import com.aliwert.model.Album;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    private MessageType messageType;

    private String ofStatic;

    public String prepareErrorMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append(messageType.getMessage());
        if (ofStatic != null) {
            builder.append(" : " + ofStatic);
        }
        return builder.toString();
    }
}

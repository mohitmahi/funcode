package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonFormat(shape= JsonFormat.Shape.OBJECT)
public class ChatMessage {
    private String from;
    private String message;
}

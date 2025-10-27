package com.makini.university.dtos;

import com.makini.university.enums.ResponseStatusEnum;
import lombok.*;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Wednesday, 07/06/2023
 */

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse<T> {
    private String message;
    private ResponseStatusEnum status;
    private T _embedded;
    private String debugMessage;
}

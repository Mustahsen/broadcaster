package com.github.mustahsen.broadcastersample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The type Inner object dto.
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InnerObjectDto {
    private String innerObjectString;
}

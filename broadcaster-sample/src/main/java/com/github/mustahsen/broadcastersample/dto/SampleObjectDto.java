package com.github.mustahsen.broadcastersample.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * The type Sample object dto.
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SampleObjectDto {
    private String stringMember;
    private Long longMember;
    private List<Integer> integers;
    private InnerObjectDto innerObjectDto;
}

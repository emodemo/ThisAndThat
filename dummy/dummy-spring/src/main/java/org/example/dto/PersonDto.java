package org.example.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public
class PersonDto {
	final private long id;
	private Map<Long, SubjectDto> subjects = new HashMap<>();
}

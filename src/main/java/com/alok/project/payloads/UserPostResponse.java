package com.alok.project.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserPostResponse {
	
	private List<PostDto> content;
	private int pageNumber;
	private int pagesize;
	private long totalElements;
	private int totalPages;
	private boolean lastpage;

}

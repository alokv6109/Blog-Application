package com.alok.project.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//we basically want that the page when it comes it comes with values such as page number, is last oage vor not, how many total recoreds are there etc
//so basically we want to send a more appropriate data to the user rather than the usul

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {
	
	private List<PostDto> content;
	private int pageNumber;
	private int pagesize;
	private long totalElements;
	private int totalPages;
	private boolean lastpage;

}

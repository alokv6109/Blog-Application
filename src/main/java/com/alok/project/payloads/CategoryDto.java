package com.alok.project.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private Integer categoryId;
	@NotEmpty
	@Size(min = 4, message = "the min size has to be 4!!")
	private String categoryName;
	@NotEmpty
	@Size(min =10, message = "the min soze has to be 10!!")
	private String categoryDescription;
	

}

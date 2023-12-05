package com.spring5.mypro00.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class TodoDTO {
	
	private String title ;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dueDate ;
	

}

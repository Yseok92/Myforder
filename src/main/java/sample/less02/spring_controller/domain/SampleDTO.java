package sample.less02.spring_controller.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SampleDTO {

	private String name ;
	private int age;

	
	public SampleDTO() {
	      System.out.println("SampleDTO의 기본생성자1..........");
	   }
	   
   public SampleDTO(String name, int age) {
      this.name = name ;
      this.age = age ;
      System.out.println("SampleDTO의 모든 필드 초기화 생성자..........");
   }
	   
}

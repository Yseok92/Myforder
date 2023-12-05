package com.spring5.mypro00.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring5.mypro00.common.paging.domain.MyBoardPagingCreatorDTO;
import com.spring5.mypro00.common.paging.domain.MyBoardPagingDTO;
import com.spring5.mypro00.domain.MyBoardVO;
import com.spring5.mypro00.service.MyBoardService;

@Controller
@RequestMapping("/myboard")
public class MyBoardController {
	
	private MyBoardService myBoardService ;
	
//  (Setter를 이용한 주입) 
//  public MyBoardController() {
//     System.out.println("MyBoardController기본생성자");
//  }
//  
//  @Autowired
//  public void setMyBoardService(MyBoardService myBoardService) {
//     this.myBoardService = myBoardService ;
//  }
	
	
	//(단일 생성자를 이용한 주입) : 생성자가 여러 개이고, 기본 생성자가 포함되어 있으면, 무조건 기본 생성자를 사용함
	public MyBoardController(MyBoardService myBoardService) {
		this.myBoardService = myBoardService ;
		System.out.println("MyBoardContorller 의 모든 필드 초기화 생성자 입니다.");
	}

	
//	//게시물 목록 조회
//	@GetMapping("/list")
//	public String showBoardList(MyBoardPagingDTO myboardPaging, Model model) {
//		List<MyBoardVO> myBoardList = myBoardService.getBoardList(myboardPaging) ;
//		
//		model.addAttribute("myBoardList", myBoardList) ;
//		
//		System.out.println("myBoardList: " + myBoardList) ;
//		return "myboard/list" ;
//	}
	
	//게시물 목록 조회
	@GetMapping("/list")
	public String showBoardList(MyBoardPagingDTO myboardPaging, Model model) {
		MyBoardPagingCreatorDTO pagingCreator = myBoardService.getBoardList(myboardPaging) ;
		
		model.addAttribute("pagingCreator", pagingCreator) ;
		
		System.out.println("pagingCreator: " + pagingCreator) ;
		return "myboard/list" ;
	}
	
	
	//등록 페이지 호출	
	@GetMapping("/register")
	public String showBoardRegisterPage() {
		System.out.println("등록 페이지 호출...");
		return "myboard/register" ;
	}
	
	//특정 게시물 조회, 수정 후 페이지 조회
	@GetMapping("/detail")
	public String showBoardDetail(Long bno, Model model, String result, MyBoardPagingDTO myboardPaging) {
		
		MyBoardVO myboard = null ;
		
		if (result == null) {
			myboard = myBoardService.getBoard(bno);
			
		} else if (result != null) {
			
			myboard = myBoardService.getBoard2(bno) ;
		}
			
//		System.out.println("컨트롤러에 전달된 myBoard: " + myBoard);
		
		model.addAttribute("myboard", myboard) ;
		model.addAttribute("result", result) ;
		
		System.out.println("myboard: " + model.getAttribute("myboard"));
		
		return "myboard/detail" ;
	}
	
	
//	게시물 등록 처리 
	@PostMapping("/register")
	public String registerNewBoard(MyBoardVO myboard,
								   RedirectAttributes redirectAttr) {
		
		long bno = myBoardService.registerBoard(myboard) ;
		redirectAttr.addFlashAttribute("result", bno) ;
		System.out.println("result: " + redirectAttr.getFlashAttributes());
		
		return "redirect:/myboard/list" ;
//		return "redirect:/myboard/list?bno=" + bno ; // 왜 redirect?
	}
	
	
//	특정 게시물 수정 삭제 페이지 호출 GET /myboard/modify
	@GetMapping("/modify")
	public String showBoardModify(Long bno, Model model) {
		MyBoardVO myboard = myBoardService.getBoard2(bno) ;
		model.addAttribute("myboard", myboard) ;
		
		return "myboard/modify" ;
	}
	
//	특정 게시물 수정 POST /myboard/modify	
	@PostMapping("/modify")
	public String modifyBoard(MyBoardVO myboard, RedirectAttributes redirectAttr) {
		
		boolean modifyResult = myBoardService.modifyBoard(myboard) ;
		
		if(modifyResult) {
		redirectAttr.addFlashAttribute("result", "successModify") ;	 //result 라는 이름으로 successModify를 줌?
		
		} else {
			redirectAttr.addFlashAttribute("result", "failedModify") ;
		}
		
		redirectAttr.addAttribute("bno", myboard.getBno()) ;
		
		return "redirect:/myboard/detail" ;
	}
	


//	특정 게시물 삭제 POST /myboard/remove
	
	@PostMapping("/remove")
	public String removeBoard(Long bno, RedirectAttributes redirectAttr) {
		
		boolean removeResult = myBoardService.removeBoard(bno) ;
		
//		if (myBoardService.removeBoard(bno)) { 이와 같이 작성하고 removeResult 변수를 없애도 무방
		if (removeResult) {
			redirectAttr.addFlashAttribute("result", "successRemove") ; //result 라는 이름으로 successRemove를 줌
		} else {
			redirectAttr.addFlashAttribute("result", "failedRemove") ;
		}
		
		return "redirect:/myboard/list" ;
	}

			
	

}

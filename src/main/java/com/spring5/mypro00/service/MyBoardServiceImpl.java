package com.spring5.mypro00.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring5.mypro00.common.paging.domain.MyBoardPagingCreatorDTO;
import com.spring5.mypro00.common.paging.domain.MyBoardPagingDTO;
import com.spring5.mypro00.domain.MyBoardVO;
import com.spring5.mypro00.mapper.MyBoardMapper;

@Service  //서비스 (구현)클래스는 DAO 또는 mapper 인터페이스의 메서드를 호출합니다.
		  //사용되는 DAO 또는 mapper 인터페이스 타입의 필드가 필요합니다.
public class MyBoardServiceImpl implements MyBoardService {
	
	private MyBoardMapper myBoardMapper ;
	
	//방법2: 모든 필드 초기화 생성자
	public MyBoardServiceImpl(MyBoardMapper myBoardMapper) {
		this.myBoardMapper = myBoardMapper;
		System.out.println("MyBoardServiceImpl의 모든 필드 초기화생성자입니다.");
		System.out.println("myBoardMapper: " + myBoardMapper);
	}
	
	//위의 필드에 MyBoardMapper 인터페이스 주입
	//방법1:Setter 이용
//	public MyBoardServiceImpl() {
//		System.out.println("MyBoardServiceImpl의 기본생성자입니다.");
//	}
//	
//	@Autowired
//	public void setMyBoardMapper(MyBoardMapper myBoardMapper) {
//		this.myBoardMapper = myBoardMapper;
//		System.out.println("MyBoardServiceImpl의 MyBoardMapper의 Setter입니다.");	
//	}

	//게시물 목록 조회
//	@Override
//	public List<MyBoardVO> getBoardList(MyBoardPagingDTO myboardPaging) {
//		List<MyBoardVO> myBoardList = myBoardMapper.selectMyBoardList(myboardPaging) ;
//
//		return myBoardList;
//		//return myBoardMapper.selectMyBoardList() ;
//	}

	@Override
	public MyBoardPagingCreatorDTO getBoardList(MyBoardPagingDTO myboardPaging) {
				
//		long rowTotal = myBoardMapper.selectRowTotal() ;
//		
//		List<MyBoardVO> myboardList = myBoardMapper.selectMyBoardList(myboardPaging) ;
//		
//		MyBoardPagingCreatorDTO pagingCreator =
//				new MyBoardPagingCreatorDTO(rowTotal, myboardPaging, myboardList);
//		
//		
//		return pagingCreator;
		//return myBoardMapper.selectMyBoardList() ;
		
		return new MyBoardPagingCreatorDTO(myBoardMapper.selectRowTotal(myboardPaging), 
										   myboardPaging, 
										   myBoardMapper.selectMyBoardList(myboardPaging)) ;
	}

	//게시물 등록
	@Override //저장된 게시물의 bno 값을 반환
	public long registerBoard(MyBoardVO myBoard) {
		
		//System.out.println("컨트롤러 ->서비스로 전달된 myBoard: " + myBoard);
		//컨트롤러 ->서비스로 전달된 myBoard: MyBoardVO(bno=0, btitle=서비스 새글 입력  테스트 제목, ....)
		
		//long rows = myBoardMapper.insertMyBoard(myBoard) ; 
		//System.out.println("rows: " + rows); //1
		
		//System.out.println("DB 처리 후myBoard: " + myBoard);
		//DB 처리 후myBoard: MyBoardVO(bno=41, btitle=서비스 새글 입력  테스트 제목, ....)
		
		//return (rows == 1) ? myBoard.getBno() : 0;
		
		myBoardMapper.insertMyBoard(myBoard) ;
		return myBoard.getBno() ;
	}

	//특정 게시물 조회: 특정 게시물 하나의 데이터를 가져옴
	@Override
	public MyBoardVO getBoard(long bno) {
		
		int rows = myBoardMapper.updateBviewCnt(bno) ;
		
		MyBoardVO myBoard = myBoardMapper.selectMyBoard(bno);
		System.out.println("myBoard: " + myBoard);
		System.out.println("조회수: " + myBoard.getBviewCnt());
		
//		if (rows == 1) {
//			return myBoard ;
//		} else {
//			return null ;
//		}
		return (rows == 1) ? myBoard : null; 
		
	}
	
	//특정 게시물 수정 삭제 화면 호출 & 수정 후 조회페이지 호출
	@Override
	public MyBoardVO getBoard2(long bno) {
		
		MyBoardVO myBoard = myBoardMapper.selectMyBoard(bno);
		System.out.println("myBoard: " + myBoard);

		return myBoard ; 
		
	}

	//특정 게시물 수정
	@Override
	public boolean modifyBoard(MyBoardVO myBoard) {
		
		int rows = myBoardMapper.updateMyBoard(myBoard) ;
		return rows == 1 ;
		
//		return myBoardMapper.updateMyBoard(myBoard) == 1 ;
	}

	@Override
	public boolean removeBoard(long bno) {
		
		int rows = myBoardMapper.deleteMyBoard(bno);

		return (rows == 1) ;
	}

}

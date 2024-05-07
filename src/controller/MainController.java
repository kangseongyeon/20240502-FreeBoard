package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import service.FreeService;
import util.ScanUtil;
import util.View;
import view.Print;

public class MainController extends Print {
	static public Map<String, Object> sessionStorage = new HashMap<>();
	
	boolean debug = true;
	FreeService freeService = FreeService.getInstance();
	
	public static void main(String[] args) {
		new MainController().start();
	}

	private void start() {
		View view = View.HOME;
		while (true) {
			switch (view) {
			case HOME:
				view = home();
				break;
			case FREEBOARD_LIST:
				view = list();
				break;				
			case FREEBOARD_INSERT:
				view = insert();
				break;
			case FREEBOARD_UPDATE:
				view = update();
				break;
			case FREEBOARD_DETAILE:
				view = detail();
				break;				
			case FREEBOARD_DELETE:
				view = delete(); 
				break;
			default:
				break;
			}
		}
	}

	
	private View detail() {
		if (debug) System.out.println("------------- 게시판 상세 조회 -------------");
		
//		list에서 내가 입력한 게시판 번호 조회
//		boardNo : FREEBOARD_LIST에서 출력 -> 값을 넘기기 위해 값을 sessionStorage boardNo에서 넘김
//		boardNo를 서비스로 보낸 후, 서비스에 있는 보드 넘어를 dao에 보냄
//		dao에서 받은 결과값을 다시 서비스로 보냄
//		서비스에서 다시 detail에서 보냄 -> detail에서 값을 받고 출력
		
		int boardNo = (int) sessionStorage.get("boardNo");
		
		List<Object> param = new ArrayList<Object>();
		param.add(boardNo);
		Map<String, Object> detail = freeService.detail(param);
		System.out.println(detail);
		
		System.out.println("1. 게시판 수정");
		System.out.println("2. 게시판 삭제");
		System.out.println("3. 게시판 전체 출력");
		
		int sel = ScanUtil.menu();
		if (sel == 1) return View.FREEBOARD_UPDATE;
		else if (sel == 2) return View.FREEBOARD_DELETE;
		else if (sel == 3) return View.FREEBOARD_LIST;
		else return View.HOME;
	}
	
	
	private View delete() {
		if (debug) System.out.println("============= 게시판 삭제 ============= ");
		int boardNo = (int) sessionStorage.get("boardNo");
		List<Object> param = new ArrayList();
		param.add(boardNo);
		freeService.delete(param);
		
		return View.FREEBOARD_LIST;
	}
	
	
	private View update() {
		if (debug) System.out.println("============= 게시판 수정 ============= ");
		int boardNo = (int) sessionStorage.get("boardNo");
		String name = ScanUtil.nextLine("제목 : ");
		String content = ScanUtil.nextLine("내용 : ");
		
		List<Object> param = new ArrayList();
		param.add(name);
		param.add(content);
		param.add(boardNo);
		freeService.update(param);
		
		return View.FREEBOARD_DETAILE;
	}
	
	
	private View insert() {
		if (debug) System.out.println("============= 게시판 등록 =============");
//		입력 받을 값 : NAME, CONTENT, WRITER
		
		String name = ScanUtil.nextLine("제목 : ");
		String content = ScanUtil.nextLine("내용 : ");
		String writer = ScanUtil.nextLine("작성자 : ");
		List<Object> param = new ArrayList();
		param.add(name);
		param.add(content);
		param.add(writer);
				
		freeService.insert(param);
		
		return View.FREEBOARD_LIST;
	}
	
	
	private View list() {
//		debug 현재 값 : true -> 개발 완료되면 debug를 false로 바꿔서 출력이 되지 않게 만들어줌
		if (debug) System.out.println("------------- 게시판 전체 출력 -------------");
		
		// list 연결
		List<Map<String, Object>> list = freeService.list();
		// list 출력
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
		
		System.out.println("1. 게시판 상세 조회");
		System.out.println("2. 홈");
		
		int sel = ScanUtil.menu();
		if (sel == 1) {
			int boardNo = ScanUtil.nextInt("게시판 번호 입력 : ");
			sessionStorage.put("boardNo", boardNo);
			return View.FREEBOARD_DETAILE;
		}
		else if (sel == 2) return View.HOME;
		else return View.FREEBOARD_LIST;
	}
	

	private View home() {
		System.out.println("------------- 자유게시판 (홈) -------------");
		System.out.println("1. 게시판 전체 출력");
		System.out.println("2. 게시판 추가");
		
		int sel = ScanUtil.menu();
		if (sel == 1) return View.FREEBOARD_LIST;
		else if (sel == 2) return View.FREEBOARD_INSERT;
		else return View.HOME;
	}

}

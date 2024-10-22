package com.choongang.shoppingmall.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.choongang.shoppingmall.service.CategoryService;
import com.choongang.shoppingmall.service.ProductService;
import com.choongang.shoppingmall.service.UsersBoardService;
import com.choongang.shoppingmall.vo.AdminCategoryPagingVO;
import com.choongang.shoppingmall.vo.AdminUsersPagingVO;
import com.choongang.shoppingmall.vo.CategoryVO;
import com.choongang.shoppingmall.vo.FileVO;
import com.choongang.shoppingmall.vo.PagingVO;
import com.choongang.shoppingmall.vo.ProductPagingVO;
import com.choongang.shoppingmall.vo.ProductVO;
import com.choongang.shoppingmall.vo.UserPagingVO;
import com.choongang.shoppingmall.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Configuration
@Slf4j
public class AdminController {
	@Autowired
	private UsersBoardService usersBoardService;
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	
	/*@Value("${part.upload.path}")
	String filePath;*/
	@Autowired
	ResourceLoader resourceLoader;
	
	// 관리자 페이지 접근
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
	// 유저목록 페이징
	@GetMapping(value =  "/admin/users")
	public String adminUsers(@RequestParam (required = false) String field,
						@RequestParam (required = false) String search,
						@ModelAttribute UserPagingVO userPagingVO ,
						Model model) {
		AdminUsersPagingVO<UserVO> pv = usersBoardService.getUserList(userPagingVO.getCurrentPage(), userPagingVO.getSizeOfPage(), userPagingVO.getSizeOfBlock(), field, search);
		List<UserVO> list= usersBoardService.selectAll();
		model.addAttribute("pv", pv);
		model.addAttribute("upv", userPagingVO);
		model.addAttribute("field", field);
		model.addAttribute("search", search);
		model.addAttribute("list", list);
		model.addAttribute("newLine", "\n");
		model.addAttribute("br", "<br>");
		return "admin-users";
	}
	
	// 회원 상세 조회
	@GetMapping(value = "/admin/user/details")
	public String adminUserDetail(Integer user_id, Model model) {
		model.addAttribute("user_id",user_id);
		model.addAttribute("details", usersBoardService.selectByID(user_id));
		return "admin-userdetails";
	}
	// 상품 목록 페이징
	@GetMapping("/admin/products")
	public String adminProducts(@ModelAttribute ProductPagingVO productPagingVO ,Model model) {
		PagingVO<ProductVO> pv = productService.getProductList(productPagingVO.getCurrentPage(), productPagingVO.getSizeOfPage(), productPagingVO.getSizeOfBlock());
		// 카테고리 페이징
		AdminCategoryPagingVO<CategoryVO> cv = categoryService.getCategoryList(productPagingVO.getCurrentPage(), productPagingVO.getSizeOfPage(), productPagingVO.getSizeOfBlock());
		model.addAttribute("pv", pv);
		model.addAttribute("cv", cv);
		model.addAttribute("upv", productPagingVO);
		model.addAttribute("newLine", "\n");
		model.addAttribute("br", "<br>");
		model.addAttribute("cvo", new CategoryVO());
		return "admin-products";
	}
	
	// 카테고리 중복확인(숫자 1개를 넘긴다. 0이면 사용가능 0이아니면 사용 불가능)
		@GetMapping(value = "/test/categoryCheck", produces = "text/plain;charset=UTF-8")
		@ResponseBody
		public String categoryCheck(@RequestParam(required = false)String category_name) {
			return categoryService.selectCountByCategoryName(category_name)+"";
		}
		
	// Get방식일 경우 상품관리로 보내기
		@GetMapping("/categoryOk")
		public String categoryOkGet() {
			return "redirect:/admin/products";
		}
	// Post전송일때만 저장
		@PostMapping("/categoryOk")
		public String categoryOkPost(@ModelAttribute(value = "vo") CategoryVO vo) {
			categoryService.insert(vo);
			return "redirect:/admin/products";
		}
		
		@GetMapping("/updateOk")
		public String categoryUpdateOkGet() {
			return "redirect:/admin/products";
		}
		@PostMapping("/updateOk")
		public String categoryUpdateOkPost(@ModelAttribute(value = "vo") CategoryVO vo) {
			categoryService.update(vo);
			return "redirect:/admin/products";
		}
		
		@GetMapping("/deleteOk")
		public String categoryDeleteOkGet() {
			return "redirect:/admin/products";
		}
		@PostMapping("/deleteOk")
		public String categoryDeleteOkPost(@ModelAttribute(value = "vo") CategoryVO vo) {
			categoryService.deleteById(vo);
			return "redirect:/admin/products";
		}
		// 상품 등록 페이지
		@GetMapping("/admin/products/form")
		public String productForm(Model model) {
			List<CategoryVO> cglist = categoryService.selectCategory();
			model.addAttribute("cglist", cglist);
			return "admin-product-form";
		}
		// 상품등록 처리
		@GetMapping("/pdAddOk")
		public String pdAddOkGet() {
			return "redirect:/admin/products";
		}
		@PostMapping("/pdAddOk")
		public String pdAddOkPost(@RequestParam(required = false, defaultValue = "파일설명") String content,
				@RequestParam(required = false) MultipartFile[] uploadFile,
				@ModelAttribute(value = "vo") ProductVO productVO,
				@ModelAttribute CategoryVO vo, Model model, HttpServletRequest request) throws IOException{
			productVO.setImg_count(uploadFile.length);
			productService.insert(productVO);
			String filePath = request.getSession().getServletContext().getRealPath("/images");
			File file = new File(filePath); 
			if(!file.exists()) file.mkdirs();
			
			// 파일 처리
			List<FileVO> list = new ArrayList<>();
			if(uploadFile!=null && uploadFile.length>0) {
				for(MultipartFile f : uploadFile) {
					if(!f.isEmpty()) { 
						// 현재는 미사용
						FileVO fvo = new FileVO(
										UUID.randomUUID().toString(), 
										f.getOriginalFilename(),
										f.getContentType());
						list.add(fvo);
						// 파일명 중복처리
						String filename = "product-"+ productVO.getProduct_id()+"-"+ "1.jpg";
						File newFile = new File(filePath, filename);
						if(newFile.exists()) {
							for(int i=2;; i++) {
								int dot = filename.lastIndexOf("1");
								String front = filename.substring(0, dot);
								String end = filename.substring(dot+1);
								String newFileName = front + i + end;
								newFile = new File(filePath, newFileName);
								if(!newFile.exists()) {
									filename = newFileName;
									break;
								}
							}
						}
						f.transferTo(newFile); 
						log.info("=".repeat(100));
						log.info("저장 :" + newFile);
						log.info("=".repeat(100));
					}
				}
			}
			model.addAttribute("content", content);
			model.addAttribute("list", list);
			return "redirect:/admin/products";
		}
		
		
	// 문의 관리
	@GetMapping("/admin/qna")
	public String adminQna() {
		return "admin-qna";
	}
}

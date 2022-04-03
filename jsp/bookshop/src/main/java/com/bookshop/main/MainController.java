package com.bookshop.main;

import com.bookshop.common.base.BaseController;
import com.bookshop.goods.service.GoodsService;
import com.bookshop.goods.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller("mainController")
@EnableAspectJAutoProxy
public class MainController extends BaseController {

    @Autowired
    private GoodsService goodsService;

    // localhost:8080/bookshop/main/main.do 요청 시
    @RequestMapping(value = "/main/main.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView main (HttpServletResponse response, HttpServletRequest request) throws Exception{
        // 세션 생성
        HttpSession session;
        // ModelAndView 설정
        ModelAndView mav = new ModelAndView();
        String viewName = (String) request.getAttribute("viewName");
        mav.setViewName(viewName);

        // 세션 설정
        session = request.getSession();
        session.setAttribute("side_menu", "user");
        Map<String, List<GoodsVO>> goodsMap = goodsService.listGoods();
        mav.addObject("goodsMap", goodsMap);
        return mav;
    }
}

package com.bookshop.goods.controller;

import com.bookshop.common.base.BaseController;
import com.bookshop.goods.service.GoodsService;
import com.bookshop.goods.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller("goodsController")
@RequestMapping(value = "/goods")
public class GoodsControllerImpl extends BaseController implements GoodsController{

    @Autowired
    GoodsService goodsService;

    // 상품상세페이지 Controller : localhost:8080/goods/goodsDetail.do
    @RequestMapping(value = "/goodsDetail.do", method = RequestMethod.GET)
    // modelandview로 스캔
    public ModelAndView goodsDetail(@RequestParam("goods_id") String goods_id,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        String viewName = (String) request.getAttribute("viewName");
        HttpSession session = request.getSession();
        Map goodsMap = goodsService.goodsDetail(goods_id);
        ModelAndView mav = new ModelAndView();
        mav.addObject("goodsMap", goodsMap);
        GoodsVO goodsVO = (GoodsVO) goodsMap.get("goodsVO");
        addGoodsInQuick(goods_id, goodsVO, session);
        return mav;
    }

    private void addGoodsInQuick(String goods_id, GoodsVO goodsVO, HttpSession session) {
        boolean already_existed = true;
        List<GoodsVO> quickGoodsList;
        quickGoodsList = (ArrayList<GoodsVO>) session.getAttribute("quickGoodsList");

        if (quickGoodsList != null){
            if (quickGoodsList.size() < 4){
                for (int i = 0; i<quickGoodsList.size(); i++){
                    GoodsVO _goodsBean=(GoodsVO)quickGoodsList.get(i);
                    if (goods_id.equals(_goodsBean.getGoods_id())){
                        already_existed=true;
                        break;
                    }
                }
                if (already_existed=false){
                    quickGoodsList.add(goodsVO);
                }
            }
        }else {
            quickGoodsList = new ArrayList<GoodsVO>();
            quickGoodsList.add(goodsVO);
        }
        session.setAttribute("quickGoodsList", quickGoodsList);
        session.setAttribute("quickGoodsListNum",quickGoodsList.size());
    }
}

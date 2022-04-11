package com.bookshop.goods.service;

import com.bookshop.goods.dao.GoodsDAO;
import com.bookshop.goods.vo.GoodsVO;
import com.bookshop.goods.vo.ImageFileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("goodsService")
@Transactional(propagation = Propagation.REQUIRED)
public class GoodsServiceImpl implements GoodsService{

    @Autowired
    private GoodsDAO goodsDAO;

    public Map<String, List<GoodsVO>> listGoods() throws Exception{
        // 상품목록을 hashmap으로 묶어 goodsMap에 저장
        Map<String, List<GoodsVO>> goodsMap = new HashMap<String, List<GoodsVO>>();
        // 상품목록 설정
        // bestseller 목록을 가져와 리스트에 저장
        List<GoodsVO> goodsList = goodsDAO.selectGoodsList("bestseller");
        // goodsMap에 bestseller 태그를 만들고 goodsList를 수정
        goodsMap.put("bestseller", goodsList);
        // newbook 목록을 가져와 goodsList에 저장
        goodsList = goodsDAO.selectGoodsList("newbook");
        // newbook 태그를 만들고 goodsList 수정
        goodsMap.put("newbook", goodsList);
        // steadyseller 목록을 가져와 goodsList에 저장
        goodsList = goodsDAO.selectGoodsList("steadyseller");
        // steadyseller 태그를 만들고 goodsList 수정
        goodsMap.put("steadyseller", goodsList);

        return goodsMap;  // goodsMap 리턴. -- 위 내용 수정 항목들을 반영
    }

    public Map goodsDetail(String _goods_id) throws Exception {
        Map goodsMap = new HashMap<>();
        GoodsVO goodsVO = goodsDAO.selectGoodsDetail(_goods_id);
        goodsMap.put("goodsVO", goodsVO);
        List<ImageFileVO> imageList = goodsDAO.selectGoodsDetailImage(_goods_id);
        goodsMap.put("imageList", imageList);
        return goodsMap;
    }
}

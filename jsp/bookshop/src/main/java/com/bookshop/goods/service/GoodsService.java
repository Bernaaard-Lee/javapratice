package com.bookshop.goods.service;

import com.bookshop.goods.vo.GoodsVO;

import java.util.List;
import java.util.Map;

public interface GoodsService {
    public Map<String, List<GoodsVO>> listGoods() throws Exception;
}

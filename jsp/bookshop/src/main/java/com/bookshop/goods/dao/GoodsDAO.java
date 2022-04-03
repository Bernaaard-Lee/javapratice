package com.bookshop.goods.dao;

import com.bookshop.goods.vo.GoodsVO;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface GoodsDAO {
    public List<GoodsVO> selectGoodsList(String bestseller) throws DataAccessException;
}

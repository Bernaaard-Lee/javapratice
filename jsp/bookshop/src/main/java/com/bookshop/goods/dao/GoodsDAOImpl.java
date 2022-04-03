package com.bookshop.goods.dao;

import com.bookshop.goods.vo.GoodsVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("goodsDAO")
public class GoodsDAOImpl implements GoodsDAO{

    @Autowired
    private SqlSession sqlSession;

    @Override
    public List<GoodsVO> selectGoodsList(String goodsStatus) throws DataAccessException{
        // sqlsession bean으로 mappers 내 goods.xml의 select 쿼리문 호출
        List<GoodsVO> goodsList = (ArrayList)sqlSession.selectList("mapper.goods.selectGoodsList",goodsStatus);
        return goodsList;  // goodsList 리턴으로 sqlsession으로 매핑된 것을 출력
    }
}

package com.currency.qrcode.currency.mapper.opensea.mapper;

import com.currency.qrcode.currency.mapper.opensea.model.OpeasenPO;
import com.currency.qrcode.currency.model.request.ListRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpeasenMapper {

    void insert(OpeasenPO opeasenPO);

    List<OpeasenPO> listByPage(@Param("request") ListRequest listRequest);

    Integer countOpeasen(@Param("request") ListRequest listRequest);
}

package xyz.chunshengyuan.mall.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.chunshengyuan.mall.exceptions.ApiOperationException;
import xyz.chunshengyuan.mall.mapper.AddressPlusMapper;
import xyz.chunshengyuan.mall.model.po.Address;
import xyz.chunshengyuan.mall.utils.RequestContext;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author leemaster
 * @Title: AddressService
 * @Package xyz.chunshengyuan.mall.service
 * @Description:
 * @date 2019-07-1010:47
 */
@Service
public class AddressService {

    @Resource
    AddressPlusMapper addressPlusMapper;

    public List<Address> selectUserAllAddr(Long userId){
        return addressPlusMapper.selectList(
                Wrappers.<Address>query()
                .eq("user_id",userId)
        );
    }

    @Transactional
    public void createAddress(Address address) throws ApiOperationException {
        try{
            Long userId = RequestContext.get().getId();
            List<Address> list = addressPlusMapper.selectList(Wrappers.<Address>query()
                    .eq("user_id",userId));
            if (list.size() == 0){
                address.setIsDefault(1);
            }else {
                address.setIsDefault(0);
            }
            address.setUserId(userId);
            addressPlusMapper.insert(address);
        }catch (NullPointerException e){
            throw  new ApiOperationException("用户信息不存在","");
        }
    }

    @Transactional
    public void changeDefault(Long addressId) throws ApiOperationException {
        try{
            Long userId = RequestContext.get().getId();
            Address address = addressPlusMapper.selectOne(
                    Wrappers.<Address>query().eq("user_id",userId).and(
                            i -> i.eq("is_default",1)
                    )
            );
            Address address1 = addressPlusMapper.selectById(addressId);
            address.setIsDefault(0);
            address1.setIsDefault(1);
            addressPlusMapper.updateById(address1);
            addressPlusMapper.updateById(address);
        }catch (NullPointerException e){
            throw  new ApiOperationException("用户信息不存在","");
        }
    }

    public void deleteAddress(Long addressId){
        addressPlusMapper.deleteById(addressId);
    }

}

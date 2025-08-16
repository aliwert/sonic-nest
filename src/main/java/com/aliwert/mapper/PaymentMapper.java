package com.aliwert.mapper;

import com.aliwert.dto.DtoPayment;
import com.aliwert.dto.insert.DtoPaymentInsert;
import com.aliwert.dto.update.DtoPaymentUpdate;
import com.aliwert.model.Payment;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper extends BaseMapper {

    @Mapping(target = "createTime", source = "createdTime", qualifiedByName = "dateToSqlDate")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    DtoPayment toDto(Payment payment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "user", ignore = true) // handle manually in service
    @Mapping(target = "transactionId", ignore = true) // generated in service
    @Mapping(target = "status", ignore = true) // set to PENDING in service
    @Mapping(target = "paymentDate", ignore = true) // set when payment is processed
    Payment toEntity(DtoPaymentInsert dto);

    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "user", ignore = true) // handle manually in service
    @Mapping(target = "transactionId", ignore = true) // cannot be updated
    void updateEntityFromDto(DtoPaymentUpdate dto, @MappingTarget Payment payment);

    List<DtoPayment> toDtoList(List<Payment> payments);
}

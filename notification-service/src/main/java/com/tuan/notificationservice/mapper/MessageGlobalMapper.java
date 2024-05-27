package com.tuan.notificationservice.mapper;

import com.tuan.notificationservice.dto.notificationdto.MessageGlobalRequest;
import com.tuan.notificationservice.dto.notificationdto.MessageGlobalResponse;
import com.tuan.notificationservice.entity.MessageGlobal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageGlobalMapper {
    MessageGlobal toMessageGlobal(MessageGlobalRequest request);
    MessageGlobalResponse toMessageGlobalResponse(MessageGlobal messageGlobal);
}

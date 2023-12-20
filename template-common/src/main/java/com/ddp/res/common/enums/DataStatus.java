package com.ddp.res.common.enums;

import com.ddp.res.common.enums.common.IBaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DataStatus implements IBaseEnum<String> {


    NORMAL("0", "正常"),
    DISABLED("1", "禁用");

    private final String name;
    private final String value;


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}

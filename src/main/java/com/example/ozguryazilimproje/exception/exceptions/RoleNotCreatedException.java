package com.example.ozguryazilimproje.exception.exceptions;

import com.example.ozguryazilimproje.enums.Language;
import com.example.ozguryazilimproje.exception.enums.IFriendlyMessageCode;
import com.example.ozguryazilimproje.exception.utils.FriendlyMessageUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class RoleNotCreatedException extends RuntimeException{
    private  final Language language;
    private  final IFriendlyMessageCode iFriendlyMessageCode;

    public RoleNotCreatedException(Language language, IFriendlyMessageCode iFriendlyMessageCode, String message){
        super(FriendlyMessageUtils.getFriendlyMessage(language,iFriendlyMessageCode));
        this.language=language;
        this.iFriendlyMessageCode=iFriendlyMessageCode;
        log.error("[RoleNotCreatedException]-> message: {} developer message {}",FriendlyMessageUtils.getFriendlyMessage(language,iFriendlyMessageCode),message);
    }




}

package com.example.ozguryazilimproje.exception.exceptions;

import com.example.ozguryazilimproje.enums.Language;
import com.example.ozguryazilimproje.exception.enums.IFriendlyMessageCode;
import com.example.ozguryazilimproje.exception.utils.FriendlyMessageUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ImageNotSaveException extends RuntimeException {
    private final Language language;
    private  final IFriendlyMessageCode friendlyMessageCode;

    public ImageNotSaveException(Language language, IFriendlyMessageCode friendlyMessageCode,String message) {
        super(FriendlyMessageUtils.getFriendlyMessage(language,friendlyMessageCode));
        this.language = language;
        this.friendlyMessageCode = friendlyMessageCode;
        log.error("[ImageNotCreatedException]-> message: {} developer message: {}",FriendlyMessageUtils.getFriendlyMessage(language,friendlyMessageCode),message);
    }
}

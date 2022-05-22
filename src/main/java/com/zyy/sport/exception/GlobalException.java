package com.zyy.sport.exception;

import com.zyy.sport.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalException {

//    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
//    public R<String> exception(SQLIntegrityConstraintViolationException e) {
//        log.info("---- 数据表插入异常，存在重复字段名 ---- {}", e.getMessage());
//        return e.getMessage().contains("Duplicate entry") ?
//                R.errorOf("已经存在了，换个名字吧") : R.errorOf("发生未知错误");
//    }

    /**
     *  运行时异常
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public R<String> exception(RuntimeException e) {
        log.error("---- 系统运行时异常 ---- {}", e.getMessage());
        return e.getMessage().contains("Duplicate entry") ?
                R.errorOf("已经存在了，换个名字吧") : R.errorOf("系统运行时异常" + e.getMessage());
    }

    /**
     * 权限不足异常
     */
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public R<String> exception(AccessDeniedException e) {
        log.info("---- spring security权限不足 ---- {}", e.getMessage());
        return R.errorOf("权限不足");
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameNotFoundException.class)
    public R<String> exception(UsernameNotFoundException e) {
        log.info("---- 登录时异常 ---- {}", e.getMessage());
        return R.errorOf(e.getMessage());
    }
}

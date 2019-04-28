package com.spring.reactive.demo.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yjj(Yoo Ju Jin)
 * Created 26/04/2019
 *
 */

@Getter
@Setter
@Builder
@ToString
public class UserInfo {
    private String id;
    private String userName;
    private String email;
}

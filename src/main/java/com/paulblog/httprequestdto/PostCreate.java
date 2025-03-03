package com.paulblog.httprequestdto;

import com.paulblog.exception.InvalidRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.httprequestdto
 * @fileName : PostCreate
 * @date : 2025-02-16
 */
@ToString
@Getter
@Builder
public class PostCreate {

    //des 데이터 검증이 필요
    // 1. client 개발자가 깜빡할 수 있음. 실수로 값을 안보낼 수 있음.
    // 2. client bug로 인해 값이 누락될 수 있음.
    // 3. 값을 임의로 조작해서 보낼 수 있음.
    // 4. DB에 값을 저장할 때 의도치 않은 오류가 발생할 수 있음.
    // 5. 서버 개발자의 편안함을 위해서

    @NotBlank(message = "타이틀을 입력해주세요.")
    private final String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private final String content;

    public void validate() {
        if(this.title.contains("바보")){
            throw new InvalidRequest("title", "제목에 부적절한 용어를 사용할 수 없습니다.");
        }
    }
}

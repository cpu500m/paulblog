# paulblog

## 댓글 -> 테이블 모델링(comment table) ( = comment Entity)

## 비공개, 공개 여부 (상태값) -> (Enum)

## 카테고리 -> DB(or Enum)

## 로그인 -> spring scurity

## 비밀번호 암호화
1. 해시
2. 해시 방식
   1. SHA1
   2. SHA256
   3. MD5
   4. 왜 이런거로 비밀번호 암호화 하면 안되는지
3. BCrypt Scrypt, Argon2
    1. salt 값
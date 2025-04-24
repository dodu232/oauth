# 🔐 카카오 소셜 로그인

[📺 카카오 소셜 로그인 시연 영상 보기](https://youtu.be/8rHspTDvF9A)

---

## ✅ 로그인 흐름

1. **카카오 로그인 버튼 클릭**
2. 카카오에서 `localhost:8081/auth/login/kakao/` 로 `accessCode` 전달
3. 백엔드에서 해당 `accessCode`를 이용해 **카카오에 토큰 발급 요청**
4. 발급받은 `accessToken` 으로 **카카오 유저 정보 요청**

---

## 🧱 필터 동작 흐름

- 요청이 필터 대상인지 확인
- 카카오 API 호출:  
  `https://kapi.kakao.com/v1/user/access_token_info`
- 해당 `accessToken`을 카카오로 전송
- 카카오 응답 결과에 따라 필터 처리

---

## 👤 유저 정보 조회 과정

1. 클라이언트가 `GET localhost:8081/users` 요청
2. 필터를 거쳐 인증 처리
3. 카카오 API 호출:  
   `https://kapi.kakao.com/v1/user/access_token_info`
4. 카카오에서 토큰 유효 여부 응답
5. 필터 통과 시 → 컨트롤러에서 **유저 정보 응답**
6. `accessToken`이 없거나 유효하지 않을 경우 → `500` 에러 반환

---

📌 *OAuth 인증 플로우와 필터 기반 인증 흐름을 모두 포함한 구조입니다.*

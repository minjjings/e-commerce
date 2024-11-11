

## 이미지 처리 모듈 서비스 / 프로젝트 소개

**이커머스 이미지 관련 처리를 돕기 위한 B2B 이미지 처리 모듈**

## 📍 프로젝트 목표
- Redis 캐싱을 활용해 이미지 업로드와 조회 기능 성능 향상 ⚡

## 🛠️ 이미지 처리 시스템 개선 사항

### 1. CDN 조회 방식 개선
- **개선해야 할 사항**: 이미지 조회 시, 파일로 다운로드 후 레디스에 key와 파일 경로 저장
- **개선 방안**: 레디스 key에 URL과 byte 배열로 이미지를 저장하여 과정 단순화
- **개선 결과**:
  - 성능 향상 및 데이터 처리 간소화 🚀
  - 데이터 일관성 보장 🔄
  - 불필요한 복잡한 로직 제거 ✂️

### 2. 업로드 상태 모니터링 개선
- **개선해야 할 사항**: 현재 SSE 방식을 사용하여 업로드 상태를 알 수 있으나, 업로드 실패 시 재시도가 불가능
- **개선 방안**: 업로드 상태를 실시간으로 모니터링하고, 업로드 실패 시 재시도 가능하도록 Kafka 사용
- **개선 결과**:
  - 실시간 업로드 상태 모니터링 가능 📊
  - 업로드 실패 시 재시도 로직 추가 🔄

### 3. MinIO 접근 횟수 최적화
- **개선해야 할 사항**: 원본 이미지 업로드 후 변환 및 리사이징 작업 중 5회의 MinIO 접근 발생
- **개선 방안**: 이미지 변환 및 메타데이터 제거 업로드 전 동작, 캐시 처리로 MinIO 접근을 줄이고 Redis로 처리
- **개선 결과**:
  - MinIO 접근 횟수 감소 📉
  - 캐시 처리로 효율성 증가 ⚡

### 4. 데이터 검증
- **개선해야 할 사항**: 이상한 데이터 접근을 감지할 필요 있음
- **개선 방안**: 데이터 접근 및 변환 과정에서 이상 데이터를 감지하는 로직 추가, 매직 넘버 확인
- **개선 결과**:
  - 시스템 안정성 향상 🔐
  - 데이터 처리 안정성 보장 🛡️

---

## 🔧 수정 상세 내용

- **JPEG 압축**: WebP 대신 JPEG 형식으로 이미지를 압축, JPEG는 메타데이터 제거가 가능 📷
- **변환 서버 도입**: 업로드 전에 변환 서버에서 메타데이터 제거 및 JPEG 변환 수행
- **Redis 캐싱**: 이미지 데이터를 Redis에 캐싱하여 리사이징 서버가 MinIO 대신 Redis에서 이미지를 가져올 수 있도록 설정
- **리사이징 이미지 데이터 분리 저장**: 리사이징된 이미지 데이터를 별도의 테이블에 저장하여 확장성 개선
- **매직 넘버 검증**: 파일 확장자 대신 매직 넘버를 사용하여 이미지 파일의 유효성 검증 🔍
- **Kafka를 이용한 업로드 상태 모니터링**: 업로드 상태를 실시간 모니터링하고 실패 시 재시도 🔄
- **CDN 직접 접근**: URL 서버를 제거하고, CDN이 데이터 서버에 직접 접근하여 이미지를 조회
- **이미지 조회 시 파일시스템 다운로드 제거**: Redis에 CDN URL과 이미지 바이트를 저장, Redis에 이미지 없을 시 MinIO에서 조회
- **고화질 원본 이미지 최적화**: 압축된 원본 이미지와 리사이징 이미지는 MinIO에 저장, 고화질 원본 이미지는 별도 클라우드 스토리지에 저장 🌥️

---

## 💡 수정 후 기대 효과

- **MinIO 접근 횟수 감소**: 리사이징 작업 시 MinIO 접근이 기존 5회에서 1회로 줄어듦
- **데이터 검증 강화**: 매직 넘버 검증을 통해 비정상적 데이터 접근을 감지
- **업로드 실패 시 재시도 가능**: Kafka를 통해 업로드 실패 시 자동 재업로드 요청
- **동기화된 이미지 데이터 제공**: 이미지 수정 시 캐싱된 데이터도 동기화하여 최신 데이터를 제공 🔄

---

## 🏗️ 인프라 설계도

### 기존 인프라 설계도
![기존 설계도](https://github.com/user-attachments/assets/a482a794-c114-43ce-9616-a65bf102678b)

### 수정된 인프라 설계도
![수정된 설계도](https://github.com/user-attachments/assets/755a0e8a-3ff2-4b23-8457-95649dc3d63b)

---

## 🚀 주요 기능

### 1. Image Convert Server
- 확장자 체크
- 이미지 메타데이터 제거
- 이미지 JPEG 파일 변환
- 이미지 캐싱 처리

### 2. Image Upload Server
- 이미지 업로드
- 이미지 정보 DB 저장 (원본 이름, 확장자 등)

### 3. Image Resizing Server
- 이미지 리사이징
- 리사이징된 이미지 정보 DB 저장
- 리사이징 이미지 업로드

### 4. CDN Server
- 브라우저의 이미지 조회 요청 처리
- 이미지 캐싱 처리
- CDN 서버 용량 체크

---

## ⚙️ 적용 기술

![기술 스택](https://github.com/user-attachments/assets/7ecff12a-3bdf-46ff-ba60-4020224089a1)

---

**이 프로젝트**는 최적화된 이미지 관리로 사용자 경험을 개선하고, 안정적인 데이터 처리를 통해 이커머스 회사 내부에서 효율적인 이미지 처리를 제공합니다. 📈

---

이제 읽기 쉽고 친근한 느낌을 주는 형태로 수정되었습니다. 각 섹션에 이모티콘을 추가하여 이해를 돕고, 내용이 잘 전달되도록 구성했습니다.

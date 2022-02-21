# frontend

## 로컬에서 실행
```
git clone https://lab.ssafy.com/s06-webmobile1-sub2/S06P12A404.git
cd S06P12A404/frontend
npm i
npm run serve
```

* 보안유지파일인 .env.local, firebaseConfig.js 파일을 업로드하지 않아 정상적으로 실행되지않음

### 서버에서 배포
- Nginx 설치
```
sudo apt install -y nginx
```

- 프로젝트 폴더 build
```
npm run build
```

- Nginx 설정파일 수정
```
cd /etc/nginx
sudo vi nginx.conf
```
```
include /etc/nginx/modules-enabled/*.conf;
=> include /etc/nginx/modules-available/*.conf;
```
```
cd sites-available
sudo vi default
```
```
server {
        listen 80;
        location / {
                root {build folder directory};
                index index.html;
                try_files $uri $uri/ /index.html;
        }
}
```

- Nginx 재시작
```
sudo service nginx restart
```

### 사이트 인증서 (SSL) - HTTPS 설정
- core 설치 (EC2에 설치되어있음)
```bash
sudo snap install core; sudo snap refresh core
```

- certbot 설치
```bash
sudo snap install --classic certbot
```

- 심볼릭 링크
```bash
sudo ln -s /snap/bin/certbot /usr/bin/certbot
```

- Nginx에 https 설정
```bash
sudo certbot --nginx
```

- 이후 가이드라인에 따라 적용
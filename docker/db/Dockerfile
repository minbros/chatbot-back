FROM mysql:8.0
MAINTAINER Sehwan Park <sehwan505@gmail.com>

COPY ./initial.sql /docker-entrypoint-initdb.d/
# 포트 설정
EXPOSE 3306

# MySQL 서버 실행
CMD ["mysqld"]
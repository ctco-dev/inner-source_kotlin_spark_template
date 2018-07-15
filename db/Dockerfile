FROM boxfuse/flyway:5.1.1-alpine
COPY ./migrations /flyway/sql

ENTRYPOINT ["flyway"]
CMD ["-?"]

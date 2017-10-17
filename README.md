
## Register a job

```
curl -H "Content-Type: application/json" localhost:8080/jobs -d "{\"args\":[\"Hello\",\"World\"]}"
```

## Get finished jobs

```
curl localhost:8080/jobs
``` 
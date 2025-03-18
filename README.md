# R4.02

## Initialisation sur macOS

```bash
mkdir ~/.gitlab-runner
docker run -d --name gitlab-runner --restart always -v ~/.gitlab-runner:/etc/gitlab-runner -v /var/run/docker.sock:/var/run/docker.sock gitlab/gitlab-runner:latest
```

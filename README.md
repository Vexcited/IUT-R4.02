# R4.02 - Module de Qualité de Développement

## Sommaire

- [TD1](./resources/TD-1.pdf) : Installation, paramétrage et Hello World !
- [TD2](./resources/TD-2.pdf) : Chaîne CI/CD avec service de base de données
- [TP1](./resources/TP-1.pdf) : Tests JUnit simples et paramétrés
- [TP2](./resources/TP-2.pdf) : Tests avec AssertJ
- [TP3](./resources/TP-3.pdf) : Tests avec Spring Boot (injection et mock)
- [TP4](./resources/TP-4.pdf) : Tests avec DbUnit ou Spring Test DbUnit
- [TP5](./resources/TP-5.pdf) : Tests avec DbSetup et AssertJ-DB
- [TP6](./resources/TP-6.pdf) : Tests avec DbSetup et AssertJ-DB avec MariaDB

## Compléments

### Installation de GitLab Runner avec Docker sur macOS

> Permet de faire la configuration lors du TD1.

```bash
mkdir ~/.gitlab-runner
docker run -d --name gitlab-runner --restart always -v ~/.gitlab-runner:/etc/gitlab-runner -v /var/run/docker.sock:/var/run/docker.sock gitlab/gitlab-runner:latest
```

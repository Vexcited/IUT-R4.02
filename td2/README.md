# TD2

> Expliquez succinctement ce qu’il se passe durant l’exécution de cette chaine CI/CD.

Premièrement, le `stage_1` s'exécute, qui contient les jobs `count` et `version`.

## Job `count`

Ce job, qui tourne sur une image `ubuntu:22.04`, effectue plusieurs tâches avant de lancer le script principal.

- Il met à jour les paquets du système et installe `apt-transport-https` ainsi que `curl`
- Il ajoute la clé GPG de MariaDB
- Il ajoute le dépôt de MariaDB
- Il installe un client MariaDB (`mariadb-client`)
- Il exécute le script [`script.sql`](/script.sql) du dépôt pour créer la table `test`

Ensuite, il exécute `select count(*) from test;` pour afficher le nombre de lignes dans la table `test`.

## Job `version`

```console
Running with gitlab-runner 17.7.0~pre.103.g896916a8 (896916a8)
  on green-1.saas-linux-small-amd64.runners-manager.gitlab.com/default JLgUopmM, system ID: s_deaa2ca09de7
Preparing the "docker+machine" executor
00:21
Using Docker executor with image mariadb:10.11.2 ...
Starting service mariadb:10.11.2...
Pulling docker image mariadb:10.11.2 ...
Using docker image sha256:a3871bf45d8a6c3f5ad1ee49995a519d2b1f186d59dc7c792094b5014dd7571b for mariadb:10.11.2 with digest mariadb@sha256:1c33370a599c870eab28891191b1fc5f46ddf8dfd90bd5f56b03e8a16e83f2fb ...
Waiting for services to be up and running (timeout 30 seconds)...
Using docker image sha256:a3871bf45d8a6c3f5ad1ee49995a519d2b1f186d59dc7c792094b5014dd7571b for mariadb:10.11.2 with digest mariadb@sha256:1c33370a599c870eab28891191b1fc5f46ddf8dfd90bd5f56b03e8a16e83f2fb ...
Preparing environment
00:01
Running on runner-jlguopmm-project-66600986-concurrent-0 via runner-jlguopmm-s-l-s-amd64-1738086584-f6cb4a16...
Getting source from Git repository
00:01
Fetching changes with git depth set to 20...
Initialized empty Git repository in /builds/r402-almonte-ringaud-mikkel/td2/.git/
Created fresh repository.
Checking out 52d152a4 as detached HEAD (ref is main)...
Skipping Git submodules setup
$ git remote set-url origin "${CI_REPOSITORY_URL}"
Executing "step_script" stage of the job script
00:01
Using docker image sha256:a3871bf45d8a6c3f5ad1ee49995a519d2b1f186d59dc7c792094b5014dd7571b for mariadb:10.11.2 with digest mariadb@sha256:1c33370a599c870eab28891191b1fc5f46ddf8dfd90bd5f56b03e8a16e83f2fb ...
$ echo "select version()" | mariadb --user=user -ppassword --host=database -D td
version()
10.11.2-MariaDB-1:10.11.2+maria~ubu2204
Cleaning up project directory and file based variables
00:00
Job succeeded
```

Ce job, qui tourne sur une image `mariadb:10.11.2`, exécute `select version()` pour afficher la version du serveur MariaDB installé.

## Job `show_tables`

```console
Running with gitlab-runner 17.7.0~pre.103.g896916a8 (896916a8)
  on green-5.saas-linux-small-amd64.runners-manager.gitlab.com/default xS6Vzpvo, system ID: s_6b1e4f06fcfd
Preparing the "docker+machine" executor
00:21
Using Docker executor with image mariadb:10.11.2 ...
Starting service mariadb:10.11.2...
Pulling docker image mariadb:10.11.2 ...
Using docker image sha256:a3871bf45d8a6c3f5ad1ee49995a519d2b1f186d59dc7c792094b5014dd7571b for mariadb:10.11.2 with digest mariadb@sha256:1c33370a599c870eab28891191b1fc5f46ddf8dfd90bd5f56b03e8a16e83f2fb ...
Waiting for services to be up and running (timeout 30 seconds)...
Using docker image sha256:a3871bf45d8a6c3f5ad1ee49995a519d2b1f186d59dc7c792094b5014dd7571b for mariadb:10.11.2 with digest mariadb@sha256:1c33370a599c870eab28891191b1fc5f46ddf8dfd90bd5f56b03e8a16e83f2fb ...
Preparing environment
00:01
Running on runner-xs6vzpvo-project-66600986-concurrent-0 via runner-xs6vzpvo-s-l-s-amd64-1738086633-654a31f3...
Getting source from Git repository
00:01
Fetching changes with git depth set to 20...
Initialized empty Git repository in /builds/r402-almonte-ringaud-mikkel/td2/.git/
Created fresh repository.
Checking out 52d152a4 as detached HEAD (ref is main)...
Skipping Git submodules setup
$ git remote set-url origin "${CI_REPOSITORY_URL}"
Executing "step_script" stage of the job script
00:00
Using docker image sha256:a3871bf45d8a6c3f5ad1ee49995a519d2b1f186d59dc7c792094b5014dd7571b for mariadb:10.11.2 with digest mariadb@sha256:1c33370a599c870eab28891191b1fc5f46ddf8dfd90bd5f56b03e8a16e83f2fb ...
$ echo 'show tables' | mariadb --user=user -ppassword --host=database -D td
Cleaning up project directory and file based variables
00:01
Job succeeded
```

Ce job, qui tourne sur une image `mariadb:10.11.2`, exécute `show tables;` pour afficher les tables de la base de données.

## Services

On ajoute un service `mariadb:10.11.2` pour les jobs avec un alias `database`.
On y définit des variables pour initialiser la connexion à la base de données pour les clients.

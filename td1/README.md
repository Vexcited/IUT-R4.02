# TD1

> Expliquez succinctement ce qu’il se passe durant l’exécution de cette chaine CI/CD.

```console
Running with gitlab-runner 17.7.0~pre.103.g896916a8 (896916a8)
  on green-5.saas-linux-small-amd64.runners-manager.gitlab.com/default xS6Vzpvo, system ID: s_6b1e4f06fcfd
Preparing the "docker+machine" executor
00:08
Using Docker executor with image ubuntu:22.04 ...
Pulling docker image ubuntu:22.04 ...
Using docker image sha256:97271d29cb7956f0908cfb1449610a2cd9cb46b004ac8af25f0255663eb364ba for ubuntu:22.04 with digest ubuntu@sha256:0e5e4a57c2499249aafc3b40fcd541e9a456aab7296681a3994d631587203f97 ...
Preparing environment
00:01
Running on runner-xs6vzpvo-project-66600088-concurrent-0 via runner-xs6vzpvo-s-l-s-amd64-1738085871-99388c8e...
Getting source from Git repository
00:01
Fetching changes with git depth set to 20...
Initialized empty Git repository in /builds/r402-almonte-ringaud-mikkel/td1/.git/
Created fresh repository.
Checking out 0680b289 as detached HEAD (ref is main)...
Skipping Git submodules setup
$ git remote set-url origin "${CI_REPOSITORY_URL}"
Executing "step_script" stage of the job script
00:01
Using docker image sha256:97271d29cb7956f0908cfb1449610a2cd9cb46b004ac8af25f0255663eb364ba for ubuntu:22.04 with digest ubuntu@sha256:0e5e4a57c2499249aafc3b40fcd541e9a456aab7296681a3994d631587203f97 ...
$ echo "Hello, World !"
Hello, World !
Cleaning up project directory and file based variables
00:01
Job succeeded
```

Dans cette chaine CI/CD, on peut voir que l'on utilise le runner `gitlab-runner 17.7.0~pre.103.g896916a8`.
On utilise un runner `docker+machine` avec une image docker `ubuntu:22.04`.

1. On commence par préparer l'environnement
2. On récupère le code source depuis le dépôt git
3. Le runner exécuter le script `step_script` qui affiche `Hello, World !` en utilisant l'image docker `ubuntu:22.04` récupérée précédemment

À la fin on a un message `Job succeeded` qui indique que le job s'est bien déroulé.

